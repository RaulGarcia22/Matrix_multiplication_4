package org.example.operations;

import org.example.matrix.DenseMatrix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TiledMatrixMultiplier implements MultiplicationInterface {

    private final DenseMatrix a;
    private final DenseMatrix b;
    private final DenseMatrix c;
    private final int blockSize;

    public TiledMatrixMultiplier(DenseMatrix a, DenseMatrix b, int blockSize) {
        this.a = a;
        this.b = b;
        this.blockSize = blockSize;
        this.c = new DenseMatrix(a.size(), new double[a.size()][b.size()]);
    }

    @Override
    public void multiply() throws InterruptedException {
        int size = a.size();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < size; i += blockSize) {
            for (int j = 0; j < size; j += blockSize) {
                for (int k = 0; k < size; k += blockSize) {
                    final int ii = i;
                    final int jj = j;
                    final int kk = k;
                    executor.execute(() -> multiplyBlock(ii, jj, kk));
                }
            }
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    private void multiplyBlock(int row, int col, int i2) {
        for (int i = row; i < Math.min(row + blockSize, a.size()); i++) {
            for (int j = col; j < Math.min(col + blockSize, b.size()); j++) {
                double result = 0.0;
                for (int k = i2; k < Math.min(i2 + blockSize, a.size()); k++) {
                    result += a.matrix()[i][k] * b.matrix()[k][j];
                }
                synchronized (c) {
                    c.matrix()[i][j] += result;
                }
            }
        }
    }

    public DenseMatrix getResult() {
        return c;
    }
}
