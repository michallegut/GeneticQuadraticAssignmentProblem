public final class Parameters {
    static final String PATH_OF_DATA = "..\\Data\\had20.dat"; //Cannot be empty.
    static final String PATH_OF_SOLUTION = "..\\Data\\had20.sln"; //Cannot be empty.
    static final int NUMBER_OF_ITERATIONS = 10; //Only for number of populations finish condition. Must be greater than 0. Multiple iterations provide average data for chart.
    static final int SIZE_OF_POPULATION = 100; //Must be greater than 0.
    static final FinishCondition FINISH_CONDITION = new NumberOfPopulationsFinishCondition(); //Cannot be null. Chart is generated only for number of populations finish condition.
    static final float FITTNESS_DIFFERENCE = 0.00f; //Only for fitness finish condition. Expressed in percentage of optimal fitness. Must be greater than 0 or equal.
    static final int NUMBER_OF_POPULATIONS = 100; //Only for no improvement (number of populations without improvement) and number of populations finish conditions. Must be greater than 0.
    static final SelectionStrategy SELECTION_STRATEGY = new TournamentSelectionStrategy(); //Cannot be null.
    static final int NUMBER_OF_SELECTED_CHROMOSOMES = 20; //Must be grater than 0.
    static final boolean USE_FIXED_PIVOT = false; //Uses fixed pivot for crossover if true and random pivot if false
    static final int INDEX_OF_PIVOT = 0; //Only for fixed pivot. Must be greater than 0 or equal and lower than number of genes.
    static final float PROBABILITY_OF_CROSSOVER = 0.7f; //Values from 0 to 1 (inclusive).
    static final boolean GENERATE_SECOND_CHILDREN = true; //Generates two children from crossover if true and one child if false.
    static final float PROBABILITY_OF_MUTATION = 0.05f; //Values from 0 to 1 (inclusive).
    static final String CHART_TITLE = "had20 - Turniej\n \nWielkość populacji=100, Liczba pokoleń=100, Prawdopodobieństwo krzyżowania=0.7, Prawdopodobieństwo mutacji=0.05, Liczba wybieranych osobników=20,\nLosowy indeks pivota, Dwójka potomków, Liczba iteracji=10"; //Only for number of populations finish condition.
    static int NUMBER_OF_GENES; //Don't assign.

    private Parameters() {
        throw new AssertionError();
    }

    static boolean validateParameters() {
        return (!PATH_OF_DATA.isEmpty()) &&
                (!PATH_OF_SOLUTION.isEmpty()) &&
                (!(FINISH_CONDITION instanceof NumberOfPopulationsFinishCondition) || NUMBER_OF_ITERATIONS > 0) &&
                (SIZE_OF_POPULATION > 0) &&
                (FINISH_CONDITION != null) &&
                (!(FINISH_CONDITION instanceof FitnessFinishCondition) || FITTNESS_DIFFERENCE >= 0) &&
                ((!(FINISH_CONDITION instanceof NoImprovementFinishCondition) && !(FINISH_CONDITION instanceof NumberOfPopulationsFinishCondition)) || NUMBER_OF_POPULATIONS > 0) &&
                (SELECTION_STRATEGY != null) &&
                (NUMBER_OF_SELECTED_CHROMOSOMES > 0) &&
                (!USE_FIXED_PIVOT || INDEX_OF_PIVOT >= 0 && INDEX_OF_PIVOT < Parameters.NUMBER_OF_GENES) &&
                (PROBABILITY_OF_CROSSOVER >= 0 && PROBABILITY_OF_CROSSOVER <= 1) &&
                (PROBABILITY_OF_MUTATION >= 0 && PROBABILITY_OF_MUTATION <= 1);
    }
}