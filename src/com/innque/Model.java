package com.innque;

import com.innque.layers.Layer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Model {
    public List<Layer> layers;

    public Model() {
        layers = new ArrayList<>();
    }

    public void add(Layer layer) {
        layers.add(layer);
    }

    public Matrix predict(Matrix inputs) {
        for (Layer layer : this.layers) {
            inputs = layer.forward(inputs);
        }
        return inputs;
    }

    private List<Double> getParameters(String filename) {
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(filename));
            String st;
            List<Double> parameters = new ArrayList<>();
            while ((st = buffer.readLine()) != null) {
                parameters.add(Double.parseDouble(st));
            }
            return parameters;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadWeights(String filename) {
        List<Double> parameters = this.getParameters(filename);
        int pos = 0;
        for (int i = 0; i < this.layers.size(); i++) {
            Layer layer = this.layers.get(i);
            if (layer.isTrainable) {
                int size = layer.inputSize * layer.outputSize;
                List<Double> weights = parameters.subList(pos, pos + size);
                pos += weights.size();
                List<Double> biases = parameters.subList(pos, pos + layer.outputSize);
                pos += biases.size();
                // set parameters
                Double[] w = new Double[size];
                weights.toArray(w);
                layer.weights = Matrix.array(layer.inputSize, layer.outputSize, w);
                Double[] b = new Double[layer.outputSize];
                biases.toArray(b);
                layer.biases = Matrix.array(1, layer.outputSize, b);
            }
        }
    }
}
