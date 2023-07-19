# approxschemes_heuristics
An assortment of coding solutions for problems assigned in the class Computer Computability, Complexity and Heuristics taught by Professor Tom O'Connell at Skidmore College in Spring 2023.

The final probject for class was to compare the runtimes of 2 groups of algorithms(some dynamic and optimal, some approximation schemes) on 100 randomly generated instances of their problem, and test an approximation algorithm for minimum TSP.\n
Max3SAT algorithms:
The algorithms compared for max3SAT were DPLL, gsat, max3SAT 7/8 approximation scheme, and Randomized Rounding.\n
Max knapsack:
The algorithms compared for max knapsack were Dynamic knapsack, Dynamic minCost, Greedy knapsack, and mincost approximation scheme\n
TSP:
The two-approximation for the Traveling Salesman Problem using kruskal's algorithm.

File notes:
The test_SAT file runs three different SAT problem solving algorithms(GSAT, DPLL and the max 3SAT 7/8 approximation scheme) on 100 random max 3SAT instances and compares their runtimes and answers. 

The test_knapsack file runs three different knapsack problem solving algorithms(Dynamic knapsack, Dynamic minCost, and the greedy knapsack approximation scheme) on 100 randomized instances of the knapsack problem and compares their runtimes and answers.

max3sat_randround.java is the Randomized Rounding file, when running the same test file as used for the other sat algorithms, its runtimes and results can be compared to the other sat algorithms.

tsp.java takes two different Symmetric Traveling Salesman problem files from TSPLib as input and returns the approximate solution. It then calculates and compares it to the optimal solution
