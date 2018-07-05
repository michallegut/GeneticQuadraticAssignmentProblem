public class GeneticAlgorithm {
    private static double[] theBestChromosomeData = new double[Parameters.NUMBER_OF_POPULATIONS];
    private static double[] averageValueInPopulationData = new double[Parameters.NUMBER_OF_POPULATIONS];
    private static double[] theWorstChromosomeData = new double[Parameters.NUMBER_OF_POPULATIONS];
    private static int numberOfIterations;
    private static int numberOfGlobalFittestIteration;
    private static int numberOfGlobalFittestPopulation;
    private static Chromosome globalFittest;
    private int numberOfPopulations;
    private Population population;
    private int numberOfTheFittestsPopulation;
    private Chromosome theFittest;
    private FinishCondition finishCondition;

    private GeneticAlgorithm() {
        numberOfPopulations = 1;
        population = new Population();
        numberOfTheFittestsPopulation = 1;
        theFittest = population.findTheFittest();
        if (globalFittest == null || globalFittest.getFitness() > theFittest.getFitness()) {
            numberOfGlobalFittestIteration = numberOfIterations;
            numberOfGlobalFittestPopulation = 1;
            globalFittest = theFittest;
        }
        this.finishCondition = Parameters.FINISH_CONDITION;
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Input.initializeData();
        if (Parameters.validateParameters()) {
            numberOfGlobalFittestIteration = 1;
            numberOfGlobalFittestPopulation = 1;
            GeneticAlgorithm geneticAlgorithm = null;
            for (numberOfIterations = 0; numberOfIterations == 0 || Parameters.FINISH_CONDITION instanceof NumberOfPopulationsFinishCondition && numberOfIterations < Parameters.NUMBER_OF_ITERATIONS; numberOfIterations++) {
                geneticAlgorithm = new GeneticAlgorithm();

                if (Parameters.FINISH_CONDITION instanceof NumberOfPopulationsFinishCondition) {
                    theBestChromosomeData[0] += geneticAlgorithm.theFittest.getFitness();
                    averageValueInPopulationData[0] += geneticAlgorithm.population.countAverageFitness();
                    theWorstChromosomeData[0] += geneticAlgorithm.population.findHighestFitness();
                }

                while (!geneticAlgorithm.checkFinishCondition()) {
                    geneticAlgorithm.numberOfPopulations++;
                    geneticAlgorithm.population = geneticAlgorithm.population.select().crossoverAll().applyGeneticOperators();
                    Chromosome newGenerationFittest = geneticAlgorithm.population.findTheFittest();

                    if (Parameters.FINISH_CONDITION instanceof NumberOfPopulationsFinishCondition) {
                        theBestChromosomeData[geneticAlgorithm.getNumberOfPopulations() - 1] += newGenerationFittest.getFitness();
                        averageValueInPopulationData[geneticAlgorithm.getNumberOfPopulations() - 1] += geneticAlgorithm.population.countAverageFitness();
                        theWorstChromosomeData[geneticAlgorithm.getNumberOfPopulations() - 1] += geneticAlgorithm.population.findHighestFitness();
                    }

                    if (newGenerationFittest.getFitness() < geneticAlgorithm.theFittest.getFitness()) {
                        geneticAlgorithm.theFittest = newGenerationFittest;
                        if (globalFittest.getFitness() > geneticAlgorithm.theFittest.getFitness()) {
                            numberOfGlobalFittestIteration = numberOfIterations + 1;
                            numberOfGlobalFittestPopulation = geneticAlgorithm.numberOfPopulations;
                            globalFittest = geneticAlgorithm.theFittest;
                        }
                    }
                }
            }
            System.out.println("Optimal");
            System.out.println("͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞");
            System.out.println(Chromosome.toString(Input.OPTIMAL_FITESS, Input.OPTIMAL_GENES));
            System.out.println("_________________________________________________________\n");
            System.out.println("The fittest chromosome, found in population number " + numberOfGlobalFittestPopulation + ", iteration number " + numberOfGlobalFittestIteration);
            System.out.println("͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞͞");
            for (int j = 0; j < Parameters.NUMBER_OF_GENES; j++) {
                globalFittest.getGenes()[j]++;
            }
            System.out.println(globalFittest.toString());
            System.out.println("_________________________________________________________\n");
            System.out.println("Runtime: " + (System.nanoTime() - startTime) + " nanoseconds");

            if (Parameters.FINISH_CONDITION instanceof NumberOfPopulationsFinishCondition) {
                XYChart xyChart = new XYChart("Algorytm genetyczny QAP", Parameters.CHART_TITLE);
                for (int j = 0; j < Parameters.NUMBER_OF_POPULATIONS; j++) {
                    theBestChromosomeData[j] = theBestChromosomeData[j] / Parameters.NUMBER_OF_ITERATIONS;
                    averageValueInPopulationData[j] = averageValueInPopulationData[j] / Parameters.NUMBER_OF_ITERATIONS;
                    theWorstChromosomeData[j] = theWorstChromosomeData[j] / Parameters.NUMBER_OF_ITERATIONS;
                }
                double theBestChromosomeDataStandardDeviation = geneticAlgorithm.countStandardDeviation(theBestChromosomeData);
                double averageValueInPopulationDataStandardDeviation = geneticAlgorithm.countStandardDeviation(averageValueInPopulationData);
                double theWorstChromosomeDataStandardDeviation = geneticAlgorithm.countStandardDeviation(theWorstChromosomeData);
                for (int j = 0; j < Parameters.NUMBER_OF_POPULATIONS; j++) {
                    xyChart.getTheBestChromosomeSeries().add(j + 1, theBestChromosomeData[j], theBestChromosomeData[j] - theBestChromosomeDataStandardDeviation, theBestChromosomeData[j] + theBestChromosomeDataStandardDeviation);
                    xyChart.getAverageValueInPopulationSeries().add(j + 1, averageValueInPopulationData[j], averageValueInPopulationData[j] - averageValueInPopulationDataStandardDeviation, averageValueInPopulationData[j] + averageValueInPopulationDataStandardDeviation);
                    xyChart.getTheWorstChromosomeSeries().add(j + 1, theWorstChromosomeData[j], theWorstChromosomeData[j] - theWorstChromosomeDataStandardDeviation, theWorstChromosomeData[j] + theWorstChromosomeDataStandardDeviation);
                }
                xyChart.generateChart();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    int getNumberOfPopulations() {
        return numberOfPopulations;
    }

    int getNumberOfTheFittestsPopulation() {
        return numberOfTheFittestsPopulation;
    }

    Chromosome getTheFittest() {
        return theFittest;
    }

    private boolean checkFinishCondition() {
        return finishCondition.checkFinishCondition(this);
    }

    private double countStandardDeviation(double[] data) {
        double result = 0;
        double average = 0;
        for (int i = 0; i < Parameters.NUMBER_OF_POPULATIONS; i++) {
            average += data[i];
        }
        average /= Parameters.NUMBER_OF_POPULATIONS;
        for (int i = 0; i < Parameters.NUMBER_OF_POPULATIONS; i++) {
            result += (data[i] - average) * (data[i] - average);
        }
        return Math.sqrt(result / Parameters.NUMBER_OF_POPULATIONS);
    }
}