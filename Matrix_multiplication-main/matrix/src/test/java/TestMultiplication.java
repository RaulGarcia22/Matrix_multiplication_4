import org.example.matrix.*;
import org.example.operations.ParallelStreamMultiplication;
import org.example.operations.SimpleMatrixMultiplication;
import org.example.operations.TiledMatrixMultiplier;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class TestMultiplication {

    @RepeatedTest(10)
    public void testTiledMatrixMultiplication() throws InterruptedException {
        int matrixSize = 512;
        int blockSize = 128;
        DenseMatrix matrixA = new DenseMatrixGenerator().generateRandomMatrix(matrixSize, matrixSize);
        DenseMatrix matrixB = new DenseMatrixGenerator().generateRandomMatrix(matrixSize, matrixSize);

        TiledMatrixMultiplier matrixMultiplier = new TiledMatrixMultiplier(matrixA, matrixB, blockSize);

        long startTime = System.currentTimeMillis();
        matrixMultiplier.multiply();
        long endTime = System.currentTimeMillis();

        System.out.println("TiledMatrixMultiplication Time: " + (endTime - startTime) + " milliseconds");

        DenseMatrix result1 = matrixMultiplier.getResult();
        SimpleMatrixMultiplication multiplication2 = new SimpleMatrixMultiplication(matrixA, matrixB);

        startTime = System.currentTimeMillis();
        multiplication2.multiply();
        endTime = System.currentTimeMillis();

        System.out.println("SimpleMatrixMultiplication Time: " + (endTime - startTime) + " milliseconds");

        DenseMatrix result2 = multiplication2.getResult();
        assertTrue(matricesAreEqual(result1, result2));
    }

    @RepeatedTest(10)
    public void testStreamsMatrixMultiplication() {
        int matrixSize = 256;

        DenseMatrix matrixA = new DenseMatrixGenerator().generateRandomMatrix(matrixSize, matrixSize);
        DenseMatrix matrixB = new DenseMatrixGenerator().generateRandomMatrix(matrixSize, matrixSize);

        ParallelStreamMultiplication multiplication1 = new ParallelStreamMultiplication(matrixA, matrixB);

        long startTime = System.currentTimeMillis();
        multiplication1.multiply();
        long endTime = System.currentTimeMillis();

        System.out.println("StreamsMatrixMultiplication Time: " + (endTime - startTime) + " milliseconds");

        DenseMatrix result1 = multiplication1.getResult();
        SimpleMatrixMultiplication multiplication2 = new SimpleMatrixMultiplication(matrixA, matrixB);

        startTime = System.currentTimeMillis();
        multiplication2.multiply();
        endTime = System.currentTimeMillis();

        System.out.println("SimpleMatrixMultiplication Time: " + (endTime - startTime) + " milliseconds");

        DenseMatrix result2 = multiplication2.getResult();
        assertTrue(matricesAreEqual(result1, result2));
    }

    private boolean matricesAreEqual(DenseMatrix matrix1, DenseMatrix matrix2) {
        int size = matrix1.size();
        double epsilon = 0.0001;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Math.abs(matrix1.matrix()[i][j] - matrix2.matrix()[i][j]) > epsilon) {
                    System.out.println(matrix1.matrix()[i][j] + " hola " + matrix2.matrix()[i][j]);
                    return false;
                }
            }
        }
        return true;
    }
}
