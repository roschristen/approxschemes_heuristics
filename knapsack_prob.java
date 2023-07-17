//From https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/
//Given weights and values of N items, put these items in a knapsack of capacity W 
//to get the maximum total value in the knapsack. In other words, given two integer 
//arrays val[0..N-1] and wt[0..N-1] which represent values and weights associated 
//with N items respectively. Also given an integer W which represents knapsack capacity, 
//find out the maximum value subset of val[] such that the sum of the weights of this 
//subset is smaller than or equal to W. You cannot break an item, either pick the complete 
//item or don’t pick it (0-1 property)

import java.util.*;

public interface knapsack_prob {

    //matrix printing fxn from https://www.geeksforgeeks.org/print-2-d-array-matrix-java/
    public static void print2D(int mat[][])
    {
        // Loop through all rows
        for (int[] row : mat)
 
            // converting each row as string
            // and then printing in a separate line
            System.out.println(Arrays.toString(row));
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //KNAPSACK INSTANCE GENERATING FUNCTION(not used for final data):
    public static void test_knapsack(){
        System.out.println("knapsack_dynamic: ");
        Random ran = new Random();  // random int code based on tutorial at https://www.geeksforgeeks.org/java-util-random-nextint-java/

        for(int j = 0; j<100; j++){ //generate 100 different knapsack problems

        int len = 50+ran.nextInt(50);   //decide number of objects(minimum 50)
        int[] testvals = new int[len];      
        int[] testweights = new int[len];

        int max = 30+ ran.nextInt(20); //decide maximum weight (minimum 30)
        // System.out.println("len" + len);
        // System.out.println("max" + max);

        for(int i = 0; i< len; i ++){               //fill value and weight arrays with random values(minimum 1 for each)
            testvals[i] = 1+ran.nextInt(15);
            testweights[i] = 1+ran.nextInt(10);
        }
        final long startTime = System.currentTimeMillis();
        System.out.println(knapsack(testvals,testweights,max)); //run 0-1 knapsack problem on the scenario and print the results
        final long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime-startTime));
        }
    }
 /////////////////////////////////////////////////////////////////////////////////////////////////////
 //KNAPSACK PROBLEM ALGORITHM:
    public static int knapsack(int[] val, int[] weights, int W){
        int n = val.length; //get number of items
        int[][] F= new int[n+1][W+1]; //initialize F table
        //initialize base cases
        for(int w = 0; w<= W; w++){ //initialize base case(ran out of items)
            F[n][w] = 0;
        }
        for(int i = 0; i<=n; i++){ //initialize base case(ran out of space)
            F[i][0] = 0;
        }
        //loop through F table
        for(int i = n-1; i>=0; i--){ 
            for(int w = 1; w<=W; w++){
                if (weights[i] <= w){ //if there is still space left in knapsack
                F[i][w] = Math.max(F[i+1][w-weights[i]] + val[i], F[i+1][w]); //take the last item plus this one or skip(whichever is better)
                }
                else{
                    F[i][w] = F[i+1][w]; //otherwise skip(item can't fit)
                }
            }
        }

        return F[0][W]; //return the highest possible value
    }
    public static void main(String[] args) {
        test_knapsack();
    }
  }


