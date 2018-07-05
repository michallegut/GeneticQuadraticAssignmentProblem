public class NoImprovementFinishCondition implements FinishCondition {
    @Override
    public boolean checkFinishCondition(GeneticAlgorithm geneticAlgorithm) {
        return geneticAlgorithm.getNumberOfPopulations() - geneticAlgorithm.getNumberOfTheFittestsPopulation() == Parameters.NUMBER_OF_POPULATIONS;
    }
}