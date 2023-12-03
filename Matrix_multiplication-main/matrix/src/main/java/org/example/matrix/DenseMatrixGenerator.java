package org.example.matrix;



import java.util.Random;

public class DenseMatrixGenerator {
    public static DenseMatrix generateRandomMatrix(int rows, int columns) {
        double[][] matrix = new double[rows][columns];
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double randomValue = random.nextDouble();
                matrix[i][j] = randomValue;
            }
        }

        return new DenseMatrix(rows, matrix);
    }
}

