import java.util.*;

public interface max3sat78 {
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
     //wrapper class to allow the return of both the formula and the truthassignment
     public static class wrapper{
        clause[] clauseList;
        literal[] litlist;
        public wrapper(clause[] clauseList, literal[] litlist){
            this.clauseList = clauseList;
            this.litlist = litlist;
        }
    }
    // public static wrapper gen3SAT(int litNum, int clauseNum){
    //     Random ran = new Random();
    //     literal[] litList = new literal[litNum];
    //     clause[] clauseList = new clause[clauseNum];
    //     for(int i = 0; i<litNum; i++){
    //         litList[i] = new literal("x" + i, null);
    //     }
    //     for(int i = 0; i<clauseNum; i++){
    //         //random value adding code based on https://stackoverflow.com/questions/8115722/generating-unique-random-numbers-in-java
    //         //shuffle the arraylist to randomize it
    //         ArrayList<literal> temp = new ArrayList<literal>(Arrays.asList(litList));
    //         Collections.shuffle(temp);

    //         //create new clause
    //         clause current = new clause();
    //         //fill clause values with first three values in shuffled arraylist(effectively randomizing them)
    //         for (int k=0; k<3; k++){
    //             //randomly decide if the literal is negated within the clause or not
    //             int genNot = ran.nextInt(11);
    //             if(genNot <=5){
    //             current.litlist[k] = new literalWrapper(temp.get(k), false);
    //             }
    //             else{
    //                 current.litlist[k] = new literalWrapper(temp.get(k), true);
    //             }
    //         } 
    //         //add the clause to the clauselist
    //         clauseList[i] = current;
    //     }

        
        
    //     return new wrapper(clauseList, litList);

    // }
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
    public static void print_litList(literalWrapper[] litlist){
        System.out.println("top");
      String s = "";
      for(int i = 0; i<litlist.length; i++){
          s+= "(";
          s += litlist[i].lit.name+ ",";
          
          s+= ")";
          System.out.println(s);
          s= "";
      }
      System.out.println("bottom");
  }
  public static void set_litlist(clause c, literalWrapper[] litlist){
    literalWrapper[] temp = new literalWrapper[litlist.length];
    System.arraycopy(litlist, 0, temp, 0, litlist.length);
    c.litlist = temp;
  }
    public static clause[] maxSAT78Alg(literal[] litlist, clause[] clauselist){
      
        Random ran = new Random();
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
    return clauselist;
    }
    public static int test_code(literal[] litlist, clause[] clauselist){
        clause[] clist = maxSAT78Alg(litlist, clauselist);
        return checkClause(clist);
    }
  ////////////////////////////////////////////////////////////////////////////////
    //MAIN
    public static void main(String[] args){
        // wrapper wrap = gen3SAT(100,428);
        // clause[] clauselist = maxSAT78Alg(wrap.litlist,wrap.clauseList);
        // System.out.println(checkClause(clauselist));
      }
}
