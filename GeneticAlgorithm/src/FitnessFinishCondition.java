public class FitnessFinishCondition implements FinishCondition {
    @Override
    public boolean checkFinishCondition(GeneticAlgorithm geneticAlgorithm) {
        return ((float) (geneticAlgorithm.getTheFittest().getFitness() - Input.OPTIMAL_FITESS)) / Input.OPTIMAL_FITESS <= Parameters.FITTNESS_DIFFERENCE;
    }
}