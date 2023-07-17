import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Math.*;

public class tsp{
    //kruskal code
        // GRAPH CLASS
        public static class Graph{
            LinkedList<vertex> V; //adjacency list
            ArrayList<edge> Edgelist; //edgelist(for sorting later)
            //Graph constructor
            public Graph(LinkedList<vertex> V, ArrayList<edge> Edgelist){
                this.V = V;
                this.Edgelist = Edgelist;
            }
           
    
        }
     //VERTEX CLASS
            public static class vertex{
                //attributes:
                LinkedList<edge> adj_list;
                int name;
                vertex parent;
                int rank;
                boolean visited;
                //constructor:
                public vertex(int name){
                    this.name = name;
                    this.rank = 0;
                    this.adj_list = new LinkedList<edge>();
                    visited = false;
                }
            }
            //EDGE CLASS
            public static class edge{
                //attributes:
                vertex from;
                vertex to;
                Double weight;
                //constructor:
                public edge(vertex from, vertex to, Double weight){
                    this.from = from;
                    this.to = to;
                    this.weight = weight;
                }
                //comparator helper class(for sorting edges by weight) based on this tutorial:https://www.geeksforgeeks.org/comparator-interface-java/
                public static class sortEdge implements Comparator<edge>{
                    public int compare(edge a, edge b){
                        return Double.compare(a.weight, b.weight);
                    }
                }
                //comparison function for sorting
                public static ArrayList<edge> edgeSort(ArrayList<edge> edgelist){
                    Collections.sort(edgelist, new sortEdge());
                    return edgelist;
                }
                
    
            }
            public static class wrapper{
                int[][] adj;
                Boolean node;
                ArrayList<point> nodes;
                int dimension;
                public wrapper(int[][] adj, Boolean node, ArrayList<point> nodes, int dim){
                    this.adj = adj;
                    this.node = node;
                    this.nodes = nodes;
                    this.dimension = dim;
                }
            }
            public static class point{
                int vert;
                int x;
                int y;
                public point(int vert, int x, int y){
                    this.vert = vert;
                    this.x = x;
                    this.y = y;
                }
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
              //matrix printing fxn from https://www.geeksforgeeks.org/print-2-d-array-matrix-java/
    public static void print2D(int mat[][])
    {
        // Loop through all rows
        for (int[] row : mat)
 
            // converting each row as string
            // and then printing in a separate line
            System.out.println(Arrays.toString(row));
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////
//FILE READING AND MATRIX FILLING SECTION
    //code based on https://www.java67.com/2012/11/how-to-read-file-in-java-using-scanner-example.html
    //get test file
    public static wrapper read_tsp(String fileLoc) throws FileNotFoundException{

        File tsp = new File(fileLoc);
        Scanner s2 = new Scanner(tsp);
        Scanner s1 = new Scanner(tsp);
        int lineNumber = 0;
        int dimension = 0;
        Boolean node = false;
        ArrayList<point> nodeList = new ArrayList<point>();

        //1 pass with scanner to get dimension of matrix
            while(s1.hasNext()){
                String token = s1.next();
                if(token.contains("DIMENSION:")){
                    dimension = s1.nextInt();
                }
                else if(token.contains("NODE_COORD_SECTION")){
                    node = true;
                }
            }
            s1.close();
            System.out.println("dimension: " + dimension);
            if(!node){
                 //define matrix to store values in second pass
            int[][] adj = new int[dimension][dimension];
            for(int i = 0; i<dimension; i++){
                for(int k = 0; k<dimension; k++){
                    adj[i][k] = 0;
                }
            }
            boolean found_mtx = false;
    
            //start scanning file line by line for matrix values
            while(s2.hasNextLine()){
                //variable declarations
                String line = s2.nextLine();
                int counter = 0;
                Scanner s3 = new Scanner(line);
    
                //initiate line token scanner
                while(s3.hasNext()){
                String token = s3.next();
    
                //make sure the end of the matrix section hasn't been reached
                if(token.contains("DISPLAY_DATA_SECTION")){
                    found_mtx = false;
                    break;
                }
    
                //if the matrix section has been reached
                if(found_mtx){
                    //add token values to matrix
                    adj[lineNumber][counter] = Integer.valueOf(token);
                }
                //find the start of the matrix section
                if(token.contains("EDGE_WEIGHT_SECTION")){
                    //reset linenumber(it gets incremented once between now and the matrix filling section, so I set it to -1. Messy, I know.)
                    lineNumber = -1;
                    //fill matrix
                    found_mtx = true;
                }
            //increment token counter
            counter ++;
            }
    
            //increment line counter
            lineNumber ++;
            s3.close();
            }     
        s2.close(); 
        print2D(adj);
        return new wrapper(adj, false, null, dimension); 
            }
           else{
            Boolean found_coords = false;
            String[] tokens = new String[3];
            int lineCounter = 0;
            //it is in node coordinate form
                while(s2.hasNextLine()){
                //variable declarations
                String line = s2.nextLine();
                if(line.contains("EOF")){
                    found_coords = false;
                    break;
                }
                if(found_coords){
                tokens = line.split(" ", 3);
                nodeList.add(new point(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]), Integer.valueOf(tokens[2])));
                }
                if(line.contains("NODE_COORD_SECTION")){
                    System.out.println("found node coord");
                    found_coords = true;
                    lineCounter = -1;
                }
                lineCounter +=1;
                }
            
                s2.close();
            return new wrapper(null,true, nodeList, lineCounter);
           }
           
    
        }
/////////////////////////////////////////////////////////////////////////////////////////////////
//MATRIX TO GRAPH OBJECT CONVERSION FUNCTION
            public static Graph mtx_to_graph(wrapper w){
                int[][] adj = w.adj;
                int[] opt_tour;
                ArrayList<point> nodes = w.nodes;

                if(!w.node){
                   int[] opt_tour2 = {1,28,6,12,9,26,3,29,5,21,2,20,10,4,15,18,14,17,22,11,19,25,7,23,8,27,16,13,24}; 
                   opt_tour = opt_tour2;
                }
                else{
                    int[] opt_tour2 = {1,69,27,101,53,28,26,12,80,68,29,24,54,55,25,4,39,67,23,56,75,41,22,74,72,73,21,40,58,13,94,95,97,87,2,57,15,43,42,14,44,38,86,16,61,85,91,100,98,37,92,59,93,99,96,6,89,52,18,83,60,5,84,17,45,8,46,47,36,49,64,63,90,32,10,62,11,19,48,82,7,88,31,70,30,20,66,71,65,35,34,78,81,9,51,33,79,3,77,76,50};
                    opt_tour = opt_tour2;
                }
                //find out how many vertices there are
                int vnum = w.dimension;
                //initialize vertlist and edgelist
                 ArrayList<edge> edgelist = new ArrayList<edge>();
                 vertex[] vArr = new vertex[vnum];
 
                 //create vertlist array
                 for(int i = 0; i<vnum; i++){
                     //name each vertex
                     vertex v = new vertex(i+1);
                     //add new vertex to array
                     vArr[i] = v;
                 }

                
              //full matrix code
                if(!w.node){
                
                //fill edgelist with new values, reads just one half of the matrix to avoid creating duplicate edges
                for(int i = 0; i<vnum; i++){
                    for(int k = 0; k<i; k++){
                        if(i !=k){
                            edgelist.add(new edge(vArr[i], vArr[k], Double.valueOf(adj[i][k])));
                           //System.out.println("new edge from v" + (i+1) + " to v" + (k+1) + "of size " + adj[i][k]);
                        }
                    }
                }
            }
            //node coordinate code
            else{
                System.out.println("node code reached " + w.dimension);
                System.out.println("nodelist size: " + nodes.size());
                //generate edgelist(without creating duplicate edges)
                for(int j = 0; j<w.dimension; j++){
                    for(int k = j+1; k<w.dimension; k++){
                        //get distance between the two points-- point 1 is (nodes[j].x, nodes[j].y), point 2 is (nodes[k].x, nodes[k].y)
                        double xdiff = (nodes.get(j).x-nodes.get(k).x);
                        double sec1 = xdiff * xdiff;
                        double ydiff = (nodes.get(j).y-nodes.get(k).y);
                        double sec2 = ydiff * ydiff;
                        double dist = Math.sqrt( sec1+ sec2);
                        //System.out.println("dist between node " + (j+1) + "(" + nodes.get(j).x + "," + nodes.get(j).y + ") and node" + (k+1) +"(" + nodes.get(k).x + "," + nodes.get(k).y + ") = " + dist);
                        edgelist.add(new edge(vArr[j], vArr[k], dist));
                    }
                }
            }
            // for(edge e: edgelist){
            //     System.out.println(e.from.name + "," + e.to.name + ":" + e.weight);
            // }
            //optimal tour cost calculations
            int total = 0;
            int nextInd;
            for(int i = 0; i<opt_tour.length; i++){
                if(i+1 < opt_tour.length){
                    nextInd = i+1;
                }
                else{
                    nextInd = 0;
                }
                for(edge e:edgelist){
                    if(e.from.name == opt_tour[i] && e.to.name == opt_tour[nextInd] || e.from.name == opt_tour[nextInd] &&e.to.name == opt_tour[i] ){
                      //  System.out.println("adding edge " + e.from.name + e.to.name + "to total");
                        total += e.weight;
                    }
                }
            }

            System.out.println("opt_total: " + total);

            //create vertlist arraylist
            LinkedList<vertex> vertlist = new LinkedList<vertex>(Arrays.asList(vArr));
                
            //fill adjacency lists of each vertex
            for(vertex x : vertlist){
                for(int i = 0; i<edgelist.size(); i++){
                    if(edgelist.get(i).from == x){
                        x.adj_list.add(edgelist.get(i));
                    }
                }
                
               
            }
            //return a new graph with the values from the file
            return new Graph(vertlist,edgelist);
                }
////////////////////////////////////////////////////////////
//KRUSKAL SET FUNCTIONS
        public static vertex MAKE_SET(vertex n){
            //make the vertex its own tree
            n.parent = n; //set parent to itself
            n.rank = 0; //set rank to 0
            return n;
        }
    
        public static vertex FIND_SET(vertex a){
            //find root of tree
            if(a != a.parent){
                a.parent = FIND_SET(a.parent);
            }
    
            return a.parent;
        }
    
    
        public static void LINK(vertex x, vertex y){
            //set parent based off of rank(union by rank)
            if(x.rank > y.rank){
                y.parent = x;
            }
            else{
                x.parent = y;
                if(x.rank == y.rank){
                    y.rank +=1;
                }
            }
        }
    
        public static void UNION(edge e){
            vertex a = e.from;
            vertex b = e.to;
            LINK(FIND_SET(a), FIND_SET(b));
        }
    //////////////////////////////////////////////////////////////////
    //KRUSKAL'S ALGORITHM
        public static Graph kruskalAlg(Graph G){
            //create set for final output
            ArrayList<edge> A = new ArrayList<edge>();
            //get edgelist from graph
            ArrayList<edge> E = G.Edgelist;
          
            //make each vertex into its own tree
            for(vertex v: G.V ){
                MAKE_SET(v);
            }
            //sort elements
            E = edge.edgeSort(E);
            //add edges in ascending order by weight
            for(edge e : E){
                //if the vertices don't belong to the same tree
                if(FIND_SET(e.from) != FIND_SET(e.to)){
                    //add edge to final set and union
                    A.add(e);
                    UNION(e);
                }
            }

        //update the adjacency lists for the vertices
        for(vertex v: G.V){
            LinkedList<edge> adj_list1 = new LinkedList<edge>();
        for(edge e : A){
            if(e.from == v || e.to == v){
                adj_list1.add(e);
            }
        }
        v.adj_list = adj_list1;
    }
    int opt = 0;
    System.out.println("MST: ");
    for(edge e:A){
        System.out.println("(" + e.from.name + "," + e.to.name + ")");
        opt += e.weight;
    }
    System.out.println("MST cost: " + opt);
          return new Graph(G.V,A);
        }


/////////////////////////////////////////////////////////////////////////////////////////////////////
    //DFS ALGORITHM
    public static void DFS(vertex top, vertex last, edge laste, ArrayList<vertex> v){
        //record current vertex
        v.add(top);
        //run DFS on all other edges in adjacency list
       for(edge e : top.adj_list){
        //skip returning edge
        if(!(e.from == last || e.to == last)){

        //check outgoing edges that haven't already been visited
        if(!e.to.visited && e.to !=top){
            //run DFS
            DFS(e.to, top, e,v);
        }
        else if(!e.from.visited && e.from !=top){
            //run DFS
            DFS(e.from, top,e,v);
        }
    
       }
    }
    //once all outoing edges visited, record self as visited
    top.visited = true;
    }
    ////////////////////////////////////////////////////////////////////////////////////
    //TSP APPROXIMATION SCHEME ALGORITHM
    public static ArrayList<edge> solve_tsp(String fileLoc) throws FileNotFoundException{

        //run kruskal's alg on graph, first convert file contents to graph data structures
        Graph original = mtx_to_graph(read_tsp(fileLoc));
        Graph MST = kruskalAlg(original);

        //initialize route result array
        ArrayList<vertex> TSP = new ArrayList<vertex>();

        //run DFS on the MST generated by kruskal's and output to TSP arraylist
        DFS(MST.V.get(0), null,null, TSP);
        ArrayList<edge> tour = new ArrayList<edge>(); 
        int max = 0;
    
        for(int i = 0; i<TSP.size(); i++){
            for(edge e:original.Edgelist){
                int nextInd;
                if(i+1 <TSP.size()){
                    nextInd = i+1;
                }
                else{
                    nextInd = 0;
                }
                if((e.from == TSP.get(i) && e.to == TSP.get(nextInd)) || (e.to == TSP.get(i) && e.from == TSP.get(nextInd))){
                    tour.add(e);
                    max += e.weight;
                    System.out.println("Adding edge: " + e.from.name + " " + e.to.name + " weight:" + e.weight);

                }
            }
        }
        System.out.println("Tour vertices:");
        for(vertex v:TSP){
            System.out.println(v.name);
        }
        System.out.println("Total: " + max);
        return tour;
        
    }

    ////////////////////////////////////////////////////////////////////////////////
    //MAIN
    public static void main(String[] args) throws FileNotFoundException{
        //run TSP algorithm
        //file location: "C:\\Users\\aneno\\Documents\\Code\\bays29.tsp\\bays29.tsp"
        Long start_time = System.nanoTime();
        ArrayList<edge> TSP = solve_tsp(args[0]);
        Long end_time = System.nanoTime();
        System.out.println("Running time: " + (end_time - start_time));
        //print results
        String result = "{";

        for(edge e :TSP){
            result += e.from.name + ":" + e.to.name + ", ";
        }
        result += "}";
        System.out.println("TSP route: " + result);
 
    }


}
