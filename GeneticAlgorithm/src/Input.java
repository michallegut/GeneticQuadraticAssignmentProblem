import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

final class Input {
    static int[][] DISTANCE_MATRIX;
    static int[][] FLOW_MATRIX;

    static int OPTIMAL_FITESS;
    static int[] OPTIMAL_GENES;

    private Input() {
        throw new AssertionError();
    }

    static void initializeData() {
        try {
            initializeNumberOfGenes();
            initializeDistanceMatrix();
            initializeFlowMatrix();
            initializeOptimalGenes();
            initializeOptimalFitness();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void initializeNumberOfGenes() throws FileNotFoundException {
        int result;
        try (Scanner scanner = new Scanner(new FileReader(Parameters.PATH_OF_DATA))) {
            result = scanner.nextByte();
        }
        Parameters.NUMBER_OF_GENES = result;
    }

    private static void initializeDistanceMatrix() throws FileNotFoundException {
        int[][] result = new int[Parameters.NUMBER_OF_GENES][Parameters.NUMBER_OF_GENES];
        try (Scanner scanner = new Scanner(new FileReader(Parameters.PATH_OF_DATA))) {
            for (int i = 0; i < 2; i++) {
                scanner.nextLine();
            }
            for (int i = 0; i < Parameters.NUMBER_OF_GENES; i++) {
                for (int j = 0; j < Parameters.NUMBER_OF_GENES; j++) {
                    result[i][j] = scanner.nextInt();
                }
            }
        }
        DISTANCE_MATRIX = result;
    }

    private static void initializeFlowMatrix() throws FileNotFoundException {
        int[][] result = new int[Parameters.NUMBER_OF_GENES][Parameters.NUMBER_OF_GENES];
        try (Scanner scanner = new Scanner(new FileReader(Parameters.PATH_OF_DATA))) {
            for (int i = 0; i < 3 + Parameters.NUMBER_OF_GENES; i++) {
                scanner.nextLine();
            }
            for (int i = 0; i < Parameters.NUMBER_OF_GENES; i++) {
                for (int j = 0; j < Parameters.NUMBER_OF_GENES; j++) {
                    result[i][j] = scanner.nextByte();
                }
            }
        }
        FLOW_MATRIX = result;
    }

    private static void initializeOptimalFitness() throws FileNotFoundException {
        int result;
        try (Scanner scanner = new Scanner(new FileReader(Parameters.PATH_OF_SOLUTION))) {
            scanner.nextByte();
            result = scanner.nextInt();
        }
        OPTIMAL_FITESS = result;
    }

    private static void initializeOptimalGenes() throws FileNotFoundException {
        int[] result = new int[Parameters.NUMBER_OF_GENES];
        try (Scanner scanner = new Scanner(new FileReader(Parameters.PATH_OF_SOLUTION))) {
            scanner.nextLine();
            for (int i = 0; i < Parameters.NUMBER_OF_GENES; i++) {
                result[i] = scanner.nextByte();
            }
        }
        OPTIMAL_GENES = result;
    }
}
