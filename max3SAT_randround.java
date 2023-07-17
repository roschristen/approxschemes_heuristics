import java.util.ArrayList;
import java.util.Collection;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

import org.apache.commons.math3.geometry.Point;
// import org.apache.commons.math4.legacy.optim.*;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

public interface max3SAT_randround {
    public static class literal{
        int name;
        Boolean value;
        public literal(int name){
            this.name = name;
        }
    }
    public static class literalWrapper{
        literal lit;
        Boolean not;
        public literalWrapper(literal lit, Boolean not){
            this.lit = lit;
            this.not = not;
        }
    }
    public static class clause{
       literalWrapper[] litlist = new literalWrapper[3];
       public clause(literalWrapper[] litlist){
        this.litlist = litlist;
       }
    }
      //wrapper class to allow the return of both the formula and the truthassignment
      public static class wrapper{
        clause[] clauseList;
        literal[] litlist;
        public wrapper(clause[] clauseList, literal[] litlist){
            this.clauseList = clauseList;
            this.litlist = litlist;
        }
    }
       //generate a 3SAT problem
       public static wrapper gen3SAT(int litNum, int clauseNum){
        Random ran = new Random();
        literal[] litlist = new literal[litNum];
        clause[] clauseList = new clause[clauseNum];
        for(int i = 0; i<litNum; i++){
            litlist[i] = new literal(i+1);
        }
        for(int i = 0; i<clauseNum; i++){
            //random value adding code based on https://stackoverflow.com/questions/8115722/generating-unique-random-numbers-in-java
            //shuffle the arraylist to randomize it
            ArrayList<literal> temp = new ArrayList<literal>(Arrays.asList(litlist));
            Collections.shuffle(temp);

            literalWrapper[] clitlist = new literalWrapper[3];
            //fill clause values with first three values in shuffled arraylist(effectively randomizing them)
            for (int k=0; k<3; k++){
                //randomly decide if the literal is negated within the clause or not
                int genNot = ran.nextInt(11);
                if(genNot <=5){
                clitlist[k] = new literalWrapper(temp.get(k), false);
                }
                else{
                    clitlist[k] = new literalWrapper(temp.get(k), true);
                }
            } 
            //create new clause
            clause current = new clause(clitlist);
            //add the clause to the clauselist
            clauseList[i] = current;
         }


        //simple solvable example for debugging:
        //(x1v!x2vx3) (x2vx5vx4) (x3v!x2v!x1) (!x1v!x2vx4) (!x5v!x1v!x2)
        // literal x1 = new literal(1);
        // literal x2 = new literal(2);
        // literal x3 = new literal(3);
        // literal x4 = new literal(4);
        // literal x5 = new literal(5);
        // literal[] litlist = {x1,x2,x3,x4,x5};

        // literalWrapper[] l1 = {new literalWrapper(x1, false), new literalWrapper(x2,true), new literalWrapper(x3,false)};
        // literalWrapper[] l2 = {new literalWrapper(x2, false), new literalWrapper(x5,false), new literalWrapper(x4,false)};
        // literalWrapper[] l3 = {new literalWrapper(x3, false), new literalWrapper(x2,true), new literalWrapper(x1,true)};
        // literalWrapper[] l4 = {new literalWrapper(x1, true), new literalWrapper(x2,true), new literalWrapper(x4,false)};
        // literalWrapper[] l5 = {new literalWrapper(x5, true), new literalWrapper(x1,true), new literalWrapper(x2,true)};
        // clause c1 = new clause();
        // c1.litlist = l1;
        // clause c2 = new clause();
        // c2.litlist = l2;
        // clause c3 = new clause();
        // c3.litlist = l3;
        // clause c4 = new clause();
        // c4.litlist = l4;
        // clause c5 = new clause();
        // c5.litlist = l5;
        // clause[] clauseList = {c1,c2,c3,c4,c5};
   
        return new wrapper(clauseList, litlist);

    }
    public static void set_litlist(clause c, literalWrapper[] litlist){
        literalWrapper[] temp = new literalWrapper[litlist.length];
        System.arraycopy(litlist, 0, temp, 0, litlist.length);
        c.litlist = temp;
      }
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
    public static void read_prob(String file) throws FileNotFoundException{
        File f = new File(file);
        Scanner s1 = new Scanner(f);
        int n = 0;
        int probNum = 0;
        literalWrapper[] clitlist = new literalWrapper[3];
        literal[] litList1 = new literal[100];
        clause[] clauseList = new clause[428];
        int[] answers = new int[100];
        Long[] times = new Long[100];
        Long min = Long.MAX_VALUE;
        Long max = 0L;

           //propogate literal lists
           for(int i = 0; i<100; i++){
            litList1[i] = new literal(i+1);

        }

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
                    clitlist[k] = new literalWrapper(litList1[Integer.valueOf(token.substring(1))-1], true);
                    }
                    else{
                        
                        clitlist[k] = new literalWrapper(litList1[Integer.valueOf(token)-1], false);
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
            end_prob = false;
            //run function and get time
            Long start_time = System.nanoTime();

            answers[probNum-1] = randRound(clauseList, litList1, 100,428);

            Long end_time = System.nanoTime();
            times[probNum-1] = end_time - start_time;
            }
            else{
            clause current = new clause(clitlist);
            clauseList[n] = current;
            set_litlist(current, clitlist);
            n++;
            }
            
                
        }
        s1.close();
        //print solution table
        System.out.println("Solutions: ");
        for(int i = 0; i<answers.length; i++){
            System.out.println(answers[i]);
        }

        //get min and max times
        for(int k = 0; k<100; k++){
            if(times[k]< min){
                min = times[k];
            }
            else if (times[k] > max){
                max = times[k];
            }
        }

         //print statements
         System.out.println("Average run time: " + average(times));

         System.out.println("\nMedian run time: " + median(times));

         System.out.println("\nMax run time: " + max);

         System.out.println("\nMin run time: " + min);




    }
    //function that returns the truth value of a clause
    public static int checkClause(clause[] c){
        int max = 0;
        for(int i = 0; i<c.length; i++){
            Boolean v1;
            Boolean v2;
            Boolean v3;

            //set the truth values of each literal based on whether they are negated in the clause
            if(c[i].litlist[0].not) {
                v1 = !c[i].litlist[0].lit.value;
            }
            else{
                v1 = c[i].litlist[0].lit.value;
            }

            if(c[i].litlist[1].not) {
                v2 = !c[i].litlist[1].lit.value;
            }
            else{
                v2 = c[i].litlist[1].lit.value;
            }

            if(c[i].litlist[2].not) {
                v3 = !c[i].litlist[2].lit.value;
            }
            else{
                v3 = c[i].litlist[2].lit.value;
            }

            if(v1||v2||v3){
                max +=1;
            }
        }
        return max; 
    }
    public static PointValuePair makeLP(clause[] clauseList, literal[] litlist){

        int varNum = litlist.length;
        int clauseNum = clauseList.length;
        double[] opt = new double[clauseNum+varNum];
        for(int i = 0; i<clauseNum; i++){
            opt[varNum+i] = 1;
        }
        //describe the optimization problem
        LinearObjectiveFunction f = new LinearObjectiveFunction(opt, 0);

        //make a linear constraint for each clause
        Collection<LinearConstraint> constraints = new ArrayList<>();
        //make sure each variable must be <=1
        for(int i = 0; i<varNum+clauseNum; i++){
            double[] lhs = new double[clauseNum+varNum];
            lhs[i] = 1;
            LinearConstraint c = new LinearConstraint(lhs, Relationship.LEQ,1);
            constraints.add(c);
        }


        //loop through clauselist making a linear constraint for each clause
        for(int i = 0; i<clauseList.length; i++){
            //variable declarations
            double[] lhscoeff = new double[varNum + clauseNum];
            double[] rhs = new double[clauseNum];
            int negInt = 0;

            rhs[i] = 1;
            for(int j = 0; j<3; j++){
                //indicate which variables are involved in the clause
                int lit = clauseList[i].litlist[j].lit.name-1;

                //decide coefficients
                if(clauseList[i].litlist[j].not){
                    lhscoeff[lit] = -1;
                    negInt +=1;
                }
                else{
                    lhscoeff[lit] = 1;
                }
            }
            lhscoeff[varNum+ i] = -1;
            LinearConstraint c = new LinearConstraint(lhscoeff, Relationship.GEQ,-negInt);
            constraints.add(c);
       
        }

         //create and run solver
         PointValuePair solution = null;
         solution = new SimplexSolver().optimize(f, new LinearConstraintSet(constraints), GoalType.MAXIMIZE);
 
         if (solution != null) {
             //get solution
             double max = solution.getValue();
          //   System.out.println("Opt: " + max);
 
            //  //print decision variables
            //  for (int i = 0; i < varNum; i++) {
            //      System.out.print(solution.getPoint()[i] + "\t");
            //  }
         }
     return solution;
    }
    public static int randRound(clause[] clauseList, literal[] litlist,int varNum, int clauseNum){
        // wrapper w = gen3SAT(varNum, clauseNum);
        Random rand = new Random();
        Boolean[] truthAssignment = new Boolean[varNum];
        PointValuePair p = makeLP(clauseList, litlist);
         //print decision variables
         for (int i = 0; i < varNum; i++) {
            double prob = rand.nextDouble(1);
            if(prob<p.getPoint()[i]){
                truthAssignment[i] = true;
            }
            else{
                truthAssignment[i] = false;
            }
        }
        //set literal values
        for(int i =0; i<varNum; i++){
            litlist[i].value = truthAssignment[i];
        }
        int max = checkClause(clauseList);
        // System.out.println("Max: " + max);
        // System.out.println("ratio: " + max/p.getValue());
        return max;
    }




    public static void main(String[] args) throws FileNotFoundException{
       read_prob(args[0]);
     
    }
}
