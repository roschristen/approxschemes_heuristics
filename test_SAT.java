import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public interface test_SAT extends DPLL,gsat,max3sat78 {

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
   public static void run_progs(DPLL.clause[] clauseList1, gsat.clause[] clauseList2, max3sat78.clause[] clauseList3, DPLL.literal[] litList1, gsat.literal[] litList2, max3sat78.literal[] litList3, int j, int[][] answers, Long[] DPLL_times, Long[] gsat_times, Long[] max3SAT_78_times){
    //run the algorithms and record their stats
     final long startTime1 = System.nanoTime();
     ArrayList<DPLL.clause> DPLL_formula = new ArrayList<DPLL.clause>(Arrays.asList(clauseList1));
     Boolean[] DPLLAnswer = DPLL.DPLL_alg(DPLL_formula, new Boolean[100]); 
     if(DPLLAnswer !=null){
         answers[0][j] = 1;
     }
     else{
         answers[0][j] = 0;
     }

     final long endTime1 = System.nanoTime();

     final long startTime2 = System.nanoTime();
     int answer =  gsat.gen3SAT(clauseList2, litList2); 
     answers[1][j] = answer;
     final long endTime2 = System.nanoTime();

     final long startTime3 = System.nanoTime();
     int answer2 = max3sat78.test_code(litList3, clauseList3);
   
     answers[2][j] = answer2;
     final long endTime3 = System.nanoTime();

     Long drun = endTime1 - startTime1;
     DPLL_times[j] = drun;
     Long gRun = endTime2 - startTime2;
     gsat_times[j] = gRun;
     Long saRun = endTime3 - startTime3;
     max3SAT_78_times[j] = saRun;
   }
    public static void main(String args[]) throws FileNotFoundException{
        Long[] DPLL_times = new Long[100];
        Long[] gsat_times = new Long[100];
        Long[] max3SAT_78_times = new Long[100]; 
        int[][] answers = new int[3][100];
        DPLL.literal[] litList1 = new DPLL.literal[100];
        DPLL.clause[] clauseList1 = new DPLL.clause[428];
        gsat.literal[] litList2 = new gsat.literal[100];
        gsat.clause[] clauseList2 = new gsat.clause[428];
        max3sat78.literal[] litList3 = new max3sat78.literal[100];
        max3sat78.clause[] clauseList3 = new max3sat78.clause[428];
        DPLL.literalWrapper[] clitlist1 = new DPLL.literalWrapper[3];
        gsat.literalWrapper[] clitlist2 = new gsat.literalWrapper[3];
        max3sat78.literalWrapper[] clitlist3 = new max3sat78.literalWrapper[3];
             //max vars
             Long dMax = 0L;
             Long gMax = 0L;
             Long saMax = 0L;
             //min vars
             Long dMin = Long.MAX_VALUE;
             Long gMin = Long.MAX_VALUE;
             Long saMin = Long.MAX_VALUE;

        //propogate literal lists
        for(int i = 0; i<100; i++){
            litList1[i] = new DPLL.literal(i+1);
            litList2[i] = new gsat.literal(Integer.toString((i+1)), null);
            litList3[i] = new max3sat78.literal(Integer.toString(i+1), null);
        }

        File f = new File(args[0]);
        Scanner s1 = new Scanner(f);
        int n = 0;
        int probNum = 0;
        while(s1.hasNextLine()){
                //variable declarations
                String line = s1.nextLine();
                Scanner s2 = new Scanner(line);
                int k =0;
                Boolean end_prob = false;
                //System.out.println("current line: " + line);
                //initiate line token scanner
                while(s2.hasNext()){
                    String token = s2.next();
                    //System.out.println("current token: " + token);
                    if(!token.contains("BREAK")){
                    //System.out.println("adding token " + token + " to litlist " + k);
                    if(token.contains("!")){
                    clitlist1[k] = new DPLL.literalWrapper(litList1[Integer.valueOf(token.substring(1))-1], true);
                    clitlist2[k] = new gsat.literalWrapper(litList2[Integer.valueOf(token.substring(1))-1], true);
                    clitlist3[k] = new max3sat78.literalWrapper(litList3[Integer.valueOf(token.substring(1))-1], true);
                    }
                    else{
                        clitlist1[k] = new DPLL.literalWrapper(litList1[Integer.valueOf(token)-1], false);
                        clitlist2[k] = new gsat.literalWrapper(litList2[Integer.valueOf(token)-1], false);
                        clitlist3[k] = new max3sat78.literalWrapper(litList3[Integer.valueOf(token)-1], false);
                    }
              
                    k ++;
                }
                else{
                    n = 0;
                    probNum ++;
                    end_prob = true;
                }
                }

                s2.close();
            if(end_prob){
            //    System.out.println("DPLL clauselist for probnum" + probNum + ":");
            //    DPLL.print_clauseList(clauseList1);
            //    System.out.println("gsat clauselist:for probnum" + probNum + ":");
            //    gsat.print_clauseList(clauseList2);
            //    System.out.println("max3sat clauselist for probnum" + probNum + ":");
            //     max3sat78.print_clauseList(clauseList3);
            end_prob = false;
            run_progs(clauseList1, clauseList2, clauseList3, litList1, litList2, litList3, probNum-1, answers, DPLL_times, gsat_times, max3SAT_78_times);
            }
            else{
  

            DPLL.clause current1 = new DPLL.clause(clitlist1);
            gsat.clause current2 = new gsat.clause(clitlist2);
            max3sat78.clause current3 = new max3sat78.clause(clitlist3);
            clauseList1[n] = current1;
            clauseList2[n] = current2;
            clauseList3[n] = current3;
            max3sat78.set_litlist(current3, clitlist3);
            DPLL.set_litlist(current1, clitlist1);
            gsat.set_litlist(current2, clitlist2);
            n++;
            }
            
                
        }
        s1.close();
       
    //find maxes and min running times
    for(int k = 0; k<100; k++){
        if(DPLL_times[k]<dMin){
            dMin = DPLL_times[k];
        }
        else if (DPLL_times[k] > dMax){
            dMax = DPLL_times[k];
        }
        if(gsat_times[k]<gMin){
            gMin = gsat_times[k];
        }
        else if (gsat_times[k] > gMax){
            gMax = gsat_times[k];
        }
        if(max3SAT_78_times[k]<saMin){
            saMin = max3SAT_78_times[k];
        }
        else if (max3SAT_78_times[k] > saMax){
            saMax = max3SAT_78_times[k];
        }
    }
     //print statements
     System.out.println("Average run times:");
     System.out.println("DPLL: " + average(DPLL_times));
     System.out.println("gsat: " + average(gsat_times));
     System.out.println("7/8 approx: " + average(max3SAT_78_times));
 
     System.out.println("\nMedian run times:");
     System.out.println("DPLL: " + median(DPLL_times));
     System.out.println("gsat: " + median(gsat_times));
     System.out.println("7/8 approx: " + median(max3SAT_78_times));
 
     System.out.println("\nMax run times:");
     System.out.println("DPLL: " + dMax);
     System.out.println("gsat: " + gMax);
     System.out.println("7/8 approx: " + saMax);
 
     System.out.println("\nMin run times:");
     System.out.println("DPLL: " + dMin);
     System.out.println("gsat: " + gMin);
     System.out.println("7/8 approx: " + saMin);

    //print solution table
    System.out.println("\nSolution table:");
    System.out.println("Key: ");
    System.out.println("0 - DPLL");
    System.out.println("1 - gsat");
    System.out.println("2 - max3sat78");
    System.out.println("  0,  1,  2,");
    String ks = "";
    for(int k = 0; k<100; k++){
    for(int i = 0; i<3; i++){ 
        ks += answers[i][k] + ",";
    }
    System.out.println(ks);
    ks = "";
    }
   
}
}
