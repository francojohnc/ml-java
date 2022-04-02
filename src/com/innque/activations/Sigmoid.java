package com.innque.activations;


import com.innque.Matrix;
import com.innque.layers.Layer;

public class Sigmoid extends Layer {
    @Override
    public Matrix forward(Matrix x) {
        this.output = Matrix.copy(x).map((e, row, column, index) -> (1 / (1 + Math.exp(-e))));
        return this.output;
    }
}
