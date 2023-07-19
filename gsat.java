import java.util.*;

public interface gsat {
    //literal and clause classes
    public static class literal{
        String name;
        Boolean value;
        public literal(String name, Boolean value){
            this.name = name;
            this.value = value;
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
    //debugging fxn
    public static void print_clauseList(clause[] clauselist){
        System.out.println("top");
      String s = "";
      for(int i = 0; i<clauselist.length; i++){
          s+= "(";
          for(int k = 0; k<3; k++){
          s += clauselist[i].litlist[k].lit.name + ",";
          }
          s+= ")";
          System.out.println(s);
          s= "";
      }
      System.out.println("bottom");
  }
    //fxn to set the literal list of a clause
  public static void set_litlist(clause c, literalWrapper[] litlist){
    literalWrapper[] temp = new literalWrapper[litlist.length];
    System.arraycopy(litlist, 0, temp, 0, litlist.length);
    c.litlist = temp;
  }
    //problem generating fxn for debugging
    public static int gen3SAT(clause[] clauseList, literal[] litList){
        // Random ran = new Random();
        // literal[] litList = new literal[litNum];
        // clause[] clauseList = new clause[clauseNum];
        // for(int i = 0; i<litNum; i++){
        //     litList[i] = new literal("x" + i, null);
        // }
        // for(int i = 0; i<clauseNum; i++){
        //     //random value adding code based on https://stackoverflow.com/questions/8115722/generating-unique-random-numbers-in-java
        //     //shuffle the arraylist to randomize it
        //     ArrayList<literal> temp = new ArrayList<literal>(Arrays.asList(litList));
        //     Collections.shuffle(temp);

        //     //create new clause
        //     clause current = new clause();
        //     //fill clause values with first three values in shuffled arraylist(effectively randomizing them)
        //     for (int k=0; k<3; k++){
        //         //randomly decide if the literal is negated within the clause or not
        //         int genNot = ran.nextInt(11);
        //         if(genNot <=5){
        //         current.litlist[k] = new literalWrapper(temp.get(k), false);
        //         }
        //         else{
        //             current.litlist[k] = new literalWrapper(temp.get(k), true);
        //         }
        //     } 
        //     //add the clause to the clauselist
        //     clauseList[i] = current;
        // }

        Boolean[] litList1 = gsatAlg(clauseList, litList, 1000);
        for(int i = 0; i<litList.length; i++){
           // System.out.println("x" + i + " = " + litList1[i]);
            litList[i].value = litList1[i];
        }
        
        return checkClause(clauseList);

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
//main GSAT algorithm fxn
    public static Boolean[] gsatAlg(clause[] clauseList, literal[] litlist, int flipnum){
        
        //random variable declaration
        Random ran = new Random();
        //track the current best truth assignment
        Boolean[] best = new Boolean[litlist.length];
        int max = 0;
        int tempmax = 0;


        //FLIP LOOP
        for(int j = 0; j<flipnum; j++){

       // randomly pick a truth assignment
        for(int i = 0; i<litlist.length; i++){
                int pick = 1+ ran.nextInt(11);
                if(pick <=5){
                    litlist[i].value = false;
                }
                else{
                    litlist[i].value = true;
                }   
        }
        
        //variable flipping
        for(int i = 0; i<litlist.length; i++){
            //get the number of satisfied clauses 
            tempmax = checkClause(clauseList);
           // System.out.println("temp max1: " + tempmax);

            //if it has already found a maximum value truth assignment, exit
            if(tempmax == clauseList.length){
            //System.out.println("found max clausenum, exitting");
            for(int k = 0; k<litlist.length; k++){
                best[k] = litlist[k].value;
            }
            return best;
            }
            else{   
                //otherwise, check if the truth assignment is the best so far
                if((tempmax > max)){
                 //   System.out.println("overwriting max = " + max + " to " + tempmax);
                    max = tempmax;
                    //store the current literal configuration
                   
                    for(int k = 0; k<litlist.length; k++){
                        best[k] = litlist[k].value;
                    }
                }

                //flip the variable and check again
                litlist[i].value = !litlist[i].value;
                tempmax = checkClause(clauseList);
                //System.out.println("temp max2: " + tempmax);

                //if it has already found a maximum value truth assignment
                if(tempmax == clauseList.length){
               // System.out.println("found max clausenum, exitting");
                for(int k = 0; k<litlist.length; k++){
                    best[k] = litlist[k].value;
                }
                return best;
                }
                else{   
                    if((tempmax > max)){
                        //System.out.println("overwriting max = " + max + " to " + tempmax);
                        max = tempmax;
                        for(int k = 0; k<litlist.length; k++){
                            best[k] = litlist[k].value;
                        }
                    }

            }
       }
    }
}
//     for(int k = 0; k<best.length; k++){
//         System.out.println("Exitting best[" + k + "]= " + best[k]);
// }
    
            return best;
    }

    ////////////////////////////////////////////////////////////////////////////////
    //MAIN
    public static void main(String[] args){
    //   int result = gen3SAT(100, 428);
    //   System.out.println(result);
    }
}
