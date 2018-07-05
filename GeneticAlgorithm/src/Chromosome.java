import java.util.Random;

public class Chromosome {
    private int[] genes;
    private int fitness;

    Chromosome() {
        generateGenes();
        countFitness();
    }

    private Chromosome(int[] genes) {
        this.genes = genes;
    }

    static String toString(int fitness, int[] genes) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fitness: ");
        stringBuilder.append(fitness);
        stringBuilder.append("\n");
        stringBuilder.append("Genes: ");
        for (int i = 0; i < Parameters.NUMBER_OF_GENES; i++) {
            stringBuilder.append(genes[i]);
            stringBuilder.append(" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    int[] getGenes() {
        return genes;
    }

    int getFitness() {
        return fitness;
    }

    private void generateGenes() {
        int[] result = new int[Parameters.NUMBER_OF_GENES];
        for (int i = 0; i < Parameters.NUMBER_OF_GENES; i++) {
            result[i] = i;
        }
        Random random = new Random();
        for (int i = Parameters.NUMBER_OF_GENES - 1; i > 0; i--) {
            int index = random.nextInt(i);
            int temporary = result[index];
            result[index] = result[i];
            result[i] = temporary;
        }
        genes = result;
    }

    void countFitness() {
        int result = 0;
        for (int i = 0; i < Parameters.NUMBER_OF_GENES; i++) {
            for (int j = 0; j < Parameters.NUMBER_OF_GENES; j++) {
                result += Input.DISTANCE_MATRIX[i][j] * Input.FLOW_MATRIX[genes[i]][genes[j]];
            }
        }
        fitness = result;
    }

    Chromosome[] crossover(Chromosome chromosome) {
        Random random = new Random();
        Chromosome[] result = new Chromosome[Parameters.GENERATE_SECOND_CHILDREN ? 2 : 1];
        int[] firstChildGenes = new int[Parameters.NUMBER_OF_GENES];
        int[] secondChildGenes;
        if (Parameters.GENERATE_SECOND_CHILDREN) {
            secondChildGenes = new int[Parameters.NUMBER_OF_GENES];
        }
        for (int i = 0; i < Parameters.NUMBER_OF_GENES; i++) {
            if (i <= (Parameters.USE_FIXED_PIVOT ? Parameters.INDEX_OF_PIVOT : random.nextInt(Parameters.NUMBER_OF_GENES))) {
                firstChildGenes[i] = genes[i];
                if (Parameters.GENERATE_SECOND_CHILDREN) {
                    secondChildGenes[i] = chromosome.getGenes()[i];
                }
            } else {
                firstChildGenes[i] = chromosome.getGenes()[i];
                if (Parameters.GENERATE_SECOND_CHILDREN) {
                    secondChildGenes[i] = chromosome.getGenes()[i];
                }
            }
        }
        result[0] = new Chromosome(firstChildGenes);
        if (Parameters.GENERATE_SECOND_CHILDREN) {
            result[1] = new Chromosome(secondChildGenes);
        }
        return result;
    }

    void repair() {
        int[] occurences = new int[Parameters.NUMBER_OF_GENES];
        for (int i = 0; i < Parameters.NUMBER_OF_GENES; i++) {
            occurences[genes[i]]++;
        }
        for (int i = Parameters.USE_FIXED_PIVOT ? Parameters.INDEX_OF_PIVOT : 0; i < Parameters.NUMBER_OF_GENES; i++) {
            if (occurences[genes[i]] > 0) {
                int j = 0;
                while (j < Parameters.NUMBER_OF_GENES && occurences[j] != 0) {
                    j++;
                }
                if (j < Parameters.NUMBER_OF_GENES) {
                    occurences[genes[i]]--;
                    genes[i] = j;
                    occurences[j]++;
                }
            }
        }
    }

    void mutate() {
        Random random = new Random();
        for (int i = 0; i < Parameters.NUMBER_OF_GENES; i++) {
            if (random.nextFloat() <= Parameters.PROBABILITY_OF_MUTATION) {
                int temporary = genes[i];
                int index;
                do {
                    index = random.nextInt(Parameters.NUMBER_OF_GENES);
                }
                while (index == i);
                genes[i] = genes[index];
                genes[index] = temporary;
            }
        }
    }

    public String toString() {
        return toString(fitness, genes);
    }
}