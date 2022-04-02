package com.innque;

import com.innque.activations.Sigmoid;
import com.innque.layers.Dense;

import java.io.*;


public class Main {

    private static Matrix read() {
        try {
            InputStream in = new FileInputStream("/Users/johncarlofranco/Desktop/Opensource/ml-java/assets/t10k-images-idx3-ubyte");
            DataInputStream data = new DataInputStream(in);
            int magicNumber = data.readInt();
            int numberOfItems = data.readInt();
            int rows = data.readInt();
            int cols = data.readInt();
            Matrix item = new Matrix(1, 784);
            item.map((element, row, column, index) -> {
                double value = 0;
                try {
                    value = data.readUnsignedByte() / 255.0;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return value;
            });
            return item;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Matrix input = read();
        Model model = new Model();
        model.add(new Dense(784, 32));
        model.add(new Sigmoid());
        model.add(new Dense(32, 10));
        model.add(new Sigmoid());
        model.loadWeights("/Users/johncarlofranco/Desktop/Opensource/ml-java/assets/weights.txt");
        Matrix output = model.predict(input);
        output = Matrix.argmax(output);
        output.print();
    }

}
