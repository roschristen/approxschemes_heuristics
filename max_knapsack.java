

import java.util.*;
import java.lang.Math.*;


//Pseudocode:
// ModifiedGreedyKnapsack(A,v,c,B)
// 1.  Initialize G to be the empty set 
// 2.  Sort A in decreasing order of v(a)/c(a)
// 3.  Initialize L to B 
// 4.  for each a in A and while L>0
// 5.      if c(a) <=L 
// 6.          Then begin
// 7.              Add a to G 
// 8.              Decrease L by c(a)
// 9.              end
// 10.  end for
// 11.  Let amax be the object with maximum value
// 12.  if v(amax) > Value(G)
// 13.     then return {amax}
// 14   else return G  

public interface max_knapsack {
    static class item{
        int value;
        int cost;
        public item(int value, int cost){
            this.value = value;
            this.cost = cost;
        }
                    //comparator helper class(for sorting edges by weight) based on this tutorial:https://www.geeksforgeeks.org/comparator-interface-java/
         static class sortItem implements Comparator<item>{
            public int compare(item a, item b){
                return (a.value/a.cost)-(b.value/b.cost);
            }
        }
        //comparison function for sorting
        static ArrayList<item> itemSort(ArrayList<item> itemlist){
            Collections.sort(itemlist, Collections.reverseOrder(new sortItem()));
            return itemlist;
        }
    }

    // public static void test_knapsack(){
    //     System.out.println("Max_knapsack: ");
    //     //create a random knapsack problem 
    //     ArrayList<item> A = new ArrayList<item>();
    //     Random ran = new Random();  // random int code based on tutorial at https://www.geeksforgeeks.org/java-util-random-nextint-java/
    //     for(int i =0;i<15; i++){
    //         A.add(new item(1 + ran.nextInt(20), 1 + ran.nextInt(10))); //add a new item object with a value 1 < v < 21 and cost 1<c <11
    //     }

    //     int total = 0;
    //     //run greedy knapsack on the random problem, with a capacity of 30
    //     final long startTime = System.currentTimeMillis();
    //     ArrayList<item> answer = greedy_knapsack(A,30);
    //     final long endTime = System.currentTimeMillis();
    //     System.out.println("Time: " + (endTime-startTime));
    //     // //print out the answer
    //     // System.out.println("Answer: ");
    //     // for(item i : answer){
    //     //     System.out.println("Value: " + i.value + " Cost: " + i.cost + " ratio: " + i.value/i.cost); 
    //     //     total += i.value;
    //     // }
    //     // //print the total
    //     // System.out.println("Total: " + total);
    //     }
    
    public static int greedy_knapsack(ArrayList<item> A, int startCost){
        //initialize G
        ArrayList<item> G = new ArrayList<item>();
        //sort the items by ratio
        A = item.itemSort(A);
        //print out the sorted item list
        // System.out.println("Item list:");
        // for(item i : A){
        //     System.out.println("Value: " + i.value + " Cost: " + i.cost + " ratio: " + i.value/i.cost); 
        // }
        // System.out.println("\n");

        //add items in ascending order by ratio if they don't exceed the limit of the knapsack
        int L = startCost;
        int Gval = 0;
        for(item i : A){
            if(L > 0){
                if(i.cost <= L){
                    G.add(i);
                    L -= i.cost;
                    //increment Gval
                    Gval += i.value;
                }
            }
            else{
                break;
            }
        }
        //check if the largest value item is better 
        item amax = A.get(A.size()-1);
        if(amax.value >= Gval){
            ArrayList<item> ret = new ArrayList<item>();
            ret.add(amax);
            int a = 0;
            for(int i = 0; i<ret.size(); i++){
                a += ret.get(i).value;
            }
            return a;
        }
        else{
            int a = 0;
            for(int i = 0; i<G.size(); i++){
                a += G.get(i).value;
            }
            return a;
        }
        

        }
    


    public static void main(String[] args) {
        //test_knapsack();
    }
}
