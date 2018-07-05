public class NumberOfPopulationsFinishCondition implements FinishCondition {
    @Override
    public boolean checkFinishCondition(GeneticAlgorithm geneticAlgorithm) {
        return geneticAlgorithm.getNumberOfPopulations() == Parameters.NUMBER_OF_POPULATIONS;
    }
}