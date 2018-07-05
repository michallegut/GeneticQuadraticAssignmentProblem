import java.util.Random;

public class RouletteSelectionStrategy implements SelectionStrategy {
    @Override
    public Population select(Population population) {
        Chromosome[] selectedChromosomes = new Chromosome[Parameters.NUMBER_OF_SELECTED_CHROMOSOMES];
        Chromosome[] allChromosomes = population.getChromosomes();
        double highestFitness = 1.0 / population.findHighestFitness();
        double sumOfFitnesses = -highestFitness * Parameters.SIZE_OF_POPULATION;
        float sumOfProbabilities = 0;
        Random random = new Random();
        double[] roulette = new double[Parameters.SIZE_OF_POPULATION];
        for (int i = 0; i < Parameters.SIZE_OF_POPULATION; i++) {
            sumOfFitnesses += 1.0 / (allChromosomes[i].getFitness());
        }
        for (int i = 0; i < Parameters.SIZE_OF_POPULATION - 1; i++) {
            double a = (1.0 / allChromosomes[i].getFitness() - highestFitness);
            sumOfProbabilities += (1.0 / allChromosomes[i].getFitness() - highestFitness) / sumOfFitnesses;
            roulette[i] = sumOfProbabilities;
        }
        roulette[Parameters.SIZE_OF_POPULATION - 1] = 1;
        for (int i = 0; i < Parameters.NUMBER_OF_SELECTED_CHROMOSOMES; i++) {
            double randomNumber = random.nextDouble();
            for (int j = 0; j < Parameters.SIZE_OF_POPULATION; j++) {
                if (randomNumber <= roulette[j]) {
                    selectedChromosomes[i] = allChromosomes[j];
                }
            }
        }
        return new Population(selectedChromosomes);
    }
}