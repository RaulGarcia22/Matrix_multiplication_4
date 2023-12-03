package org.example.main;

import org.example.matrix.DenseMatrix;
import org.example.matrix.DenseMatrixGenerator;
import org.example.operations.TiledMatrixMultiplier;

import java.io.IOException;

public class Controller {
    public static void controller() throws IOException, InterruptedException {

        DenseMatrix matrixA = DenseMatrixGenerator.generateRandomMatrix(1024, 1024);
        DenseMatrix matrixB = DenseMatrixGenerator.generateRandomMatrix(1024, 1024);

        int blockSize = 32;

        TiledMatrixMultiplier matrixMultiplier = new TiledMatrixMultiplier(matrixA, matrixB, blockSize);
        matrixMultiplier.multiply();
        DenseMatrix result = matrixMultiplier.getResult();
    }
}

