package com.innque;

import com.innque.activations.Sigmoid;
import com.innque.layers.Dense;


public class Xor {

    public static void main(String[] args) {
        Double[] x = {
                0.0, 0.0,
                0.0, 1.0,
                1.0, 0.0,
                1.0, 1.0,
        };
        Double[] y = {
                0.0,
                1.0,
                1.0,
                0.0,
        };
        Model model = new Model();
        model.add(new Dense(2, 2));
        model.add(new Sigmoid());
        model.add(new Dense(2, 1));
        model.add(new Sigmoid());
        model.loadWeights("/Users/johncarlofranco/Desktop/Opensource/ml-java/assets/weights.txt");
        Matrix inputs = Matrix.array(4, 2, x);
        Matrix outputs = model.predict(inputs);
        outputs.print();
    }

}
