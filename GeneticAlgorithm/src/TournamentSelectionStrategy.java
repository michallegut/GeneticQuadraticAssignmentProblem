public class TournamentSelectionStrategy implements SelectionStrategy {
    @Override
    public Population select(Population population) {
        Chromosome[] chromosomes = new Chromosome[Parameters.NUMBER_OF_SELECTED_CHROMOSOMES];
        int tournamentSize = Parameters.SIZE_OF_POPULATION / Parameters.NUMBER_OF_SELECTED_CHROMOSOMES;
        if (tournamentSize == 0) tournamentSize = 1;
        for (int i = 0; i < Parameters.NUMBER_OF_SELECTED_CHROMOSOMES - 1; i++) {
            chromosomes[i] = Population.findTheFittest(population, i * tournamentSize, (i + 1) * tournamentSize - 1);
        }
        chromosomes[Parameters.NUMBER_OF_SELECTED_CHROMOSOMES - 1] = Population.findTheFittest(population, (Parameters.NUMBER_OF_SELECTED_CHROMOSOMES - 1) * tournamentSize, Parameters.SIZE_OF_POPULATION);
        return new Population(chromosomes);
    }
}