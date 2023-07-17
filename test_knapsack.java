import java.util.*;
import java.util.ArrayList;

public interface test_knapsack extends mincost, max_knapsack, knapsack_prob {
    //////////////////////////////////////////////
    //average and median fxns for time calculations
    public static Long average(Long[] l){
        Long sum = 0L;
        for(Long s : l){
            sum += s;
        }
        return sum/l.length;
    }
    public static double median(Long[] l){
        ArrayList<Long> a = new ArrayList<Long>(Arrays.asList(l));
        Collections.sort(a);
        //median code from https://www.tutorialspoint.com/find-mean-and-median-of-an-unsorted-array-in-java
        int len = l.length;
      if (len % 2 != 0)
      {
         return l[len / 2];
      }
      Long x = l[len/2 -1];
      Long y = l[(len/2)];
      double c = ((float)x+(float)y)/2;
      return c;
   }
   ////////////////////////////////////////////////
   //MAIN
    
    public static void main(String args[]){
        //Variable declarationsS
        Random ran = new Random();
        Long[] knapsack = new Long[100];
        Long[] maxknapsack = new Long[100];
        Long[] mincost1 = new Long[100];
        Long[] mincostApprox = new Long[100];
        int[][] answers = new int[4][100];

        //max vars
        Long kMax = 0L;
        Long mkMax = 0L;
        Long mcMax = 0L;
        Long maMax = 0L;
        //min vars
        Long kMin = Long.MAX_VALUE;
        Long mkMin = Long.MAX_VALUE;
        Long mcMin = Long.MAX_VALUE;
        Long maMin = Long.MAX_VALUE;

/////////////////////////////////////////////////////////////////////////////////////////////////
//RANDOM PROBLEM GENERATOR
        for(int j = 0; j<100; j++){ //generate 100 different knapsack problems
        
        int len = 100+ran.nextInt(50);   //decide number of objects(minimum 100)
        int max = 50+ ran.nextInt(25); //decide maximum weight (minimum 50)

        //declare arraylists and list for knapsack_dynamic
        int[] testvals = new int[len];      
        int[] testweights = new int[len];
        ArrayList<mincost.item> A = new ArrayList<mincost.item>();
        ArrayList<max_knapsack.item> A1 = new ArrayList<max_knapsack.item>();
        int maxVal = 0; //get maxVal in random prob for knapsack_approx

        for(int k = 0; k< len; k ++){    //fill value and weight arrays with random values(minimum 1 for each)
           //generate values and weights
            int val = 400+ran.nextInt(200);
            int weight = 1+ran.nextInt(10);
            if(val>maxVal){
                maxVal = val;
            }
            //add to arrays
            testvals[k] = val;
            testweights[k] = weight;
            A.add(new mincost.item(val, weight));
            A1.add(new max_knapsack.item(val, weight));
        }
       
/////////////////////////////////////////////////////////////////////////////////////////////////
//RUN PROGRAMS 
        final long startTime1 = System.nanoTime();
        int kAnswer = knapsack_prob.knapsack(testvals,testweights,max); 
        answers[0][j] = kAnswer;
       // System.out.println("Kanswer: " + kAnswer);
        final long endTime1 = System.nanoTime();

       // System.out.println("mincost:");
        final long startTime2 = System.nanoTime();
        int answer = mincost.ret_int(mincost.solve_maxknapsack(A, max));   
        answers[1][j] = answer;
        //System.out.println("mcanswer: " + answer);
        final long endTime2 = System.nanoTime();

        //System.out.println("mincost_approx:");
        final long startTime3 = System.nanoTime();

        double ratio = (float)maxVal/(float)len;
        int answer1 = mincost.knapsackApprox(A,(ratio)/4, max)-1;
       // System.out.println("e is " + 1.5*(len/maxVal));
        answers[2][j] = answer1;
        //System.out.println("approx_answer: " + answer1);
        final long endTime3 = System.nanoTime();

        final long startTime4 = System.nanoTime();
        int answer3 = max_knapsack.greedy_knapsack(A1, max);
        //System.out.println("greedy_answer: " + answer3);
        answers[3][j] = answer3;
        final long endTime4 = System.nanoTime();
////////////////////////////////////////////////////////////////////////////////
//TIME CODE
        //calculate min and max runtimes
        Long krun = endTime1 - startTime1;
        if(krun > kMax){
            kMax = krun;
        }
        else if(krun <kMin){
            kMin = krun;
        }
        knapsack[j] = krun;

        Long mkRun = endTime2 - startTime2;
        maxknapsack[j] = mkRun;
        if(mkRun > mkMax){
            mkMax = mkRun;
        }
        else if(mkRun <mkMin){
            mkMin = mkRun;
        }

        Long mcRun = endTime3 - startTime3;
        mincost1[j] = mcRun;
        if(mcRun > mcMax){
            mcMax = mcRun;
        }
        else if(mcRun < mcMin){
            mcMin = mcRun;
        }

        Long maRun = endTime4 - startTime4;
        mincostApprox[j] = maRun;
        if(maRun > maMax){
            maMax = maRun;
        }
        else if(maRun <maMin){
            maMin = maRun;
        }
    }
    ////////////////////////////////////////////////////////////
    //PRINT RESULTS
    System.out.println("Average run times:");
    System.out.println("Knapsack_dynamic: " + average(knapsack));
    System.out.println("max_knapsack: " + average(maxknapsack));
    System.out.println("mincost: " + average(mincost1));
    System.out.println("mincost_approx: " + average(mincostApprox));

    System.out.println("\nMedian run times:");
    System.out.println("Knapsack_dynamic: " + median(knapsack));
    System.out.println("max_knapsack: " + median(maxknapsack));
    System.out.println("mincost: " + median(mincost1));
    System.out.println("mincost_approx: " + median(mincostApprox));

    System.out.println("\nMax run times:");
    System.out.println("Knapsack_dynamic: " + kMax);
    System.out.println("max_knapsack: " + mkMax);
    System.out.println("mincost: " + mcMax);
    System.out.println("mincost_approx: " + maMax);

    System.out.println("\nMin run times:");
    System.out.println("Knapsack_dynamic: " + kMin);
    System.out.println("max_knapsack: " + mkMin);
    System.out.println("mincost: " + mcMin);
    System.out.println("mincost_approx: " + maMin);

    System.out.println("\nSolution table:");
    System.out.println("Key: ");
    System.out.println("0 - Knapsack_dynamic");
    System.out.println("1 - mincost");
    System.out.println("2 - mincost_approx");
    System.out.println("3 - greedy");
    System.out.println("  0,  1,  2,  3,");
    String ks = "";
    for(int k = 0; k<100; k++){
    for(int i = 0; i<4; i++){ 
        ks += answers[i][k] + ",";
    }
    System.out.println(ks);
    ks = "";
}

}
}