package org.example.operations;


import org.example.matrix.DenseMatrix;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ParallelStreamMultiplication implements MultiplicationInterface {
    private DenseMatrix a;
    private DenseMatrix b;
    private DenseMatrix c;
    private AtomicInteger rowCounter;
    public ParallelStreamMultiplication(DenseMatrix a, DenseMatrix b) {
        this.a = a;
        this.b = b;
        this.c = new DenseMatrix(a.size(), new double[a.size()][b.size()]);
        this.rowCounter = new AtomicInteger(0);
    }

    @Override
    public void multiply() {
        int size = a.size();
        IntStream.range(0, size).parallel().forEach(i -> {
            int row;
            while ((row = rowCounter.getAndIncrement()) < size) {
                for (int k = 0; k < b.size(); k++) {
                    for (int j = 0; j < c.size(); j++) {
                        c.matrix()[row][j] += a.matrix()[row][k] * b.matrix()[k][j];
                    }
                }
            }
        });
    }

    public DenseMatrix getResult() {
        return c;
    }
}
