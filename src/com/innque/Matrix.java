package com.innque;

import java.io.IOException;
import java.util.Arrays;

public class Matrix {
    public Double data[][];
    private int rows;
    private int cols;

    public interface Callback {
        Double call(Double element, int row, int column, int index);
    }

    public interface BroadCallback {
        Double call(Double i, Double b, int row, int column, int index);
    }

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new Double[rows][cols];
    }

    @Override
    public String toString() {
        return "Matrix{" +
                "data=" + Arrays.toString(data) +
                ", rows=" + rows +
                ", cols=" + cols +
                '}';
    }

    public void print() {
        for (int i = 0; i < this.data.length; i++) {
            for (int j = 0; j < this.data[i].length; j++) {
                System.out.print(this.data[i][j] + " ");
            }
            System.out.println();
        }
    }

    public Matrix map(Callback callback) {
        int index = 0;
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.cols; c++) {
                Double element = this.data[r][c];
                this.data[r][c] = callback.call(element, r, c, index);
                index++;
            }
        }
        return this;
    }

    public static Matrix random(int rows, int columns) {
        return new Matrix(rows, columns).map((element, row, column, i) -> (Double) Math.random());
    }

    public static Matrix zeros(int rows, int columns) {
        return new Matrix(rows, columns).map((element, row, column, i) -> 0.0);
    }

    public static Matrix array(int rows, int cols, Double[] data) {
        return new Matrix(rows, cols).map((element, r, c, i) -> data[i]);
    }

    public static Matrix broadcast(Matrix a, Matrix b, BroadCallback callback) {
        if (a.rows == b.rows && a.cols == b.cols) {
            return new Matrix(a.rows, a.cols)
                    .map((e, r, c, i) -> callback.call(a.data[r][c], b.data[r][c], r, c, i));
        }
        if (a.rows == b.rows && b.cols == 1) {
            return new Matrix(a.rows, a.cols)
                    .map((e, r, c, i) -> callback.call(a.data[r][c], b.data[r][0], r, c, i));
        }
        if (a.cols == b.cols && b.rows == 1) {
            return new Matrix(a.rows, a.cols)
                    .map((i, r, c, j) -> callback.call(a.data[r][c], b.data[0][c], r, c, j));
        }
        System.out.println("could not broadcast with the shape of " + a.rows + "," + a.cols + " " + b.rows + "," + b.cols);
        return null;
    }

    public static Matrix dot(Matrix a, Matrix b) {
        return new Matrix(a.rows, b.cols)
                .map((e, r, c, j) -> {
                    double sum = 0;
                    for (int i = 0; i < a.cols; i++) {
                        sum += a.data[r][i] * b.data[i][c];
                    }
                    return sum;
                });
    }

    public static Matrix add(Matrix a, Matrix b) {
        return broadcast(a, b, (i, j, r, c, k) -> i + j);
    }

    public static Matrix copy(Matrix matrix) {
        return new Matrix(matrix.rows, matrix.cols).map((element, row, column, index) -> matrix.data[row][column]);
    }

    public static Matrix argmax(Matrix a) {
        return new Matrix(a.rows, 1)
                .map((element, row, column, index) -> {
                    double temp = 0.0;
                    double position = 0;
                    for (int i = 0; i < a.cols; i++) {
                        double value = a.data[row][i];
                        if (value > temp) {
                            temp = value;
                            position = i;
                        }
                    }
                    return position;
                });
    }
}
