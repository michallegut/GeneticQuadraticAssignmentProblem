import java.util.LinkedList;
import java.util.List;

public class GreedyAlgorithm {
    private static final boolean maximizeLocalFitness = false; //Algorithm maximizes local fitness instead of minimizing it. This strategy gives better results because it assigns greater flows in shorter distances.

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Input.initializeData();

        int[] genes = new int[Input.NUMBER_OF_GENES];
        genes[0] = Input.OPTIMAL_GENES[0] - 1;
        List<Integer> remaingGenes = new LinkedList<>();
        for (int i = 0; i < Input.NUMBER_OF_GENES; i++) {
            if (i != Input.OPTIMAL_GENES[0] - 1) {
                remaingGenes.add(i);
            }
        }

        for (int i = 1; i < Input.NUMBER_OF_GENES; i++) {
            int currentFitness = 0;
            Integer bestGene = remaingGenes.get(0);
            for (int j = 0; j < i; j++) {
                currentFitness += Input.DISTANCE_MATRIX[j][i] * Input.FLOW_MATRIX[genes[j]][remaingGenes.get(0)];
            }
            for (Integer gene : remaingGenes.subList(1, remaingGenes.size())) {
                int fitness = 0;
                for (int j = 0; j < i; j++) {
                    fitness += Input.DISTANCE_MATRIX[j][i] * Input.FLOW_MATRIX[genes[j]][gene];
                }
                if (maximizeLocalFitness ? fitness > currentFitness : fitness < currentFitness) {
                    currentFitness = fitness;
                    bestGene = gene;
                }
            }
            genes[i] = bestGene;
            remaingGenes.remove(bestGene);
        }

        int fitness = 0;
        for (int i = 0; i < Input.NUMBER_OF_GENES; i++) {
            for (int j = 0; j < Input.NUMBER_OF_GENES; j++) {
                fitness += Input.DISTANCE_MATRIX[i][j] * Input.FLOW_MATRIX[genes[i]][genes[j]];
            }
        }

        System.out.println("Optimal");
        System.out.println("͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞");
        System.out.println("Fitness: " + Input.OPTIMAL_FITESS);
        System.out.println("Genes: ");
        for (int i = 0; i < Input.NUMBER_OF_GENES; i++) {
            System.out.print(Input.OPTIMAL_GENES[i] + " ");
        }
        System.out.println("\n_________________________________________________________\n");
        System.out.println("Result");
        System.out.println("͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞");
        System.out.println("Fitness: " + fitness);
        System.out.print("Genes: ");
        for (int i = 0; i < Input.NUMBER_OF_GENES; i++) {
            System.out.print((genes[i] + 1) + " ");
        }
        System.out.println("\n_________________________________________________________\n");
        System.out.println("Runtime: " + (System.nanoTime() - startTime) + " nanoseconds");
    }
}
