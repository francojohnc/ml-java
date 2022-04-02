package com.innque.layers;

import com.innque.Matrix;

public class Dense extends Layer {
    public Dense(int inputSize, int outputSize) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.weights = Matrix.random(this.inputSize, this.outputSize);
        this.biases = Matrix.zeros(1, this.outputSize);
        this.isTrainable = true;
    }
    @Override
    public Matrix forward(Matrix input) {
        this.input = input;
        this.output = Matrix.add(Matrix.dot(this.input, this.weights), this.biases);
        return this.output;
    }
}
