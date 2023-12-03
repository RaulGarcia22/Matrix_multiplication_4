package org.example.operations;

import org.example.matrix.DenseMatrix;

public class SimpleMatrixMultiplication implements MultiplicationInterface {
    private DenseMatrix a;
    private DenseMatrix b;
    private DenseMatrix c;

    public SimpleMatrixMultiplication(DenseMatrix a, DenseMatrix b) {
        this.a = a;
        this.b = b;
        this.c = new DenseMatrix(a.size(), new double[a.size()][b.size()]);
    }

    public void multiply() {
        int size = a.size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < b.size(); j++) {
                for (int k = 0; k < c.size(); k++) {
                    c.matrix()[i][j] += a.matrix()[i][k] * b.matrix()[k][j];
                }
            }
        }

    }

    public DenseMatrix getResult() {
        return c;
    }
}

