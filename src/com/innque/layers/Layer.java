package com.innque.layers;

import com.innque.Matrix;

public abstract class Layer {
    public int inputSize;
    public int outputSize;
    public Matrix weights;
    public Matrix biases;
    public Matrix input;
    public Matrix output;
    public boolean isTrainable = false;
    public abstract Matrix forward(Matrix inputs);
}
