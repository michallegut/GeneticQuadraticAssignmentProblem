import java.util.Random;

class Population {
    private Chromosome[] chromosomes;

    private SelectionStrategy selectionStrategy;

    Population() {
        generateChromosomes();
        selectionStrategy = Parameters.SELECTION_STRATEGY;
    }

    Population(Chromosome[] chromosomes) {
        this.chromosomes = chromosomes;
        selectionStrategy = Parameters.SELECTION_STRATEGY;
    }

    static Chromosome findTheFittest(Population population, int startIndex, int endIndex) {
        Chromosome result = population.chromosomes[startIndex];
        for (int i = startIndex; i < endIndex; i++) {
            if (population.chromosomes[i].getFitness() < result.getFitness()) {
                result = population.chromosomes[i];
            }
        }
        return result;
    }

    Chromosome[] getChromosomes() {
        return chromosomes;
    }

    private void generateChromosomes() {
        Chromosome[] result = new Chromosome[Parameters.SIZE_OF_POPULATION];
        for (int i = 0; i < Parameters.SIZE_OF_POPULATION; i++) {
            result[i] = new Chromosome();
        }
        chromosomes = result;
    }

    Chromosome findTheFittest() {
        return findTheFittest(this, 0, Parameters.SIZE_OF_POPULATION);
    }

    double countAverageFitness() {
        double sum = 0;
        for (int i = 0; i < Parameters.SIZE_OF_POPULATION; i++) {
            sum += chromosomes[i].getFitness();
        }
        return sum / Parameters.SIZE_OF_POPULATION;
    }

    double findHighestFitness() {
        double result = chromosomes[0].getFitness();
        for (int i = 1; i < Parameters.SIZE_OF_POPULATION; i++) {
            if (result < chromosomes[i].getFitness()) {
                result = chromosomes[i].getFitness();
            }
        }
        return result;
    }

    Population select() {
        return selectionStrategy.select(this);
    }

    Population crossoverAll() {
        Chromosome[] newChromosomes = new Chromosome[Parameters.SIZE_OF_POPULATION];
        Random random = new Random();
        int currentSize = 0;
        for (int i = 0; currentSize < Parameters.SIZE_OF_POPULATION; i++) {
            if (i == chromosomes.length) i = 0;
            if (random.nextFloat() <= Parameters.PROBABILITY_OF_CROSSOVER) {
                Chromosome[] children = chromosomes[i].crossover(chromosomes[random.nextInt(chromosomes.length)]);
                for (int j = 0; j < children.length && currentSize < Parameters.SIZE_OF_POPULATION; j++) {
                    newChromosomes[currentSize] = children[j];
                    currentSize++;
                }
            }
        }
        return new Population(newChromosomes);
    }

    Population applyGeneticOperators() {
        for (int i = 0; i < Parameters.SIZE_OF_POPULATION; i++) {
            chromosomes[i].repair();
            chromosomes[i].mutate();
            chromosomes[i].countFitness();
        }
        return this;
    }
}