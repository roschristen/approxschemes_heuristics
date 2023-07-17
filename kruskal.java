
import java.util.*;
import java.io.FileNotFoundException;

public class kruskal {

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
            String name;
            vertex parent;
            int rank;
            //constructor:
            public vertex(String name){
                this.name = name;
                this.rank = 0;
                this.adj_list = new LinkedList<edge>();
            }
        }
        //EDGE CLASS
        public static class edge{
            //attributes:
            vertex from;
            vertex to;
            int weight;
            //constructor:
            public edge(vertex from, vertex to, int weight){
                this.from = from;
                this.to = to;
                this.weight = weight;
            }
            //comparator helper class(for sorting edges by weight) based on this tutorial:https://www.geeksforgeeks.org/comparator-interface-java/
            public static class sortEdge implements Comparator<edge>{
                public int compare(edge a, edge b){
                    return a.weight-b.weight;
                }
            }
            //comparison function for sorting
            public static ArrayList<edge> edgeSort(ArrayList<edge> edgelist){
                Collections.sort(edgelist, new sortEdge());
                return edgelist;
            }

        }
//////////////////////////////////////////////////////////////
//GRAPH GENERATION FUNCTION
  //Graph 1(pulled from my notes):
        //edges:
        // (a,b) 3
        // (a,e) 4
        // (e,b) 1
        // (e,c) 3
        // (e,f) 8
        // (f,c) 4
        // (f,d) 2
        // (c,d) 5
        // (b,c) 2
        //adjacency lists:
        // a: b, e
        // b: c, e, a
        // c: b, e, f, d
        // d: c, f
        // e:a,b,c,f
        // f:d,c,e

        // MST solution: (a,b), (a,e), (b,e), (e,c), (c,f), (f,d)

    //Graph 2(pulled from algorithms textbook):
    //edges:
    //(a,b) 4
    //(a,h) 8
    //(b,h) 11
    //(h,i) 7
    //(h,g) 1
    //(b,c) 8
    //(i,c) 2
    //(i,g) 6
    //(c,f) 4
    //(c,d) 7
    //(g,f) 2
    //(f,e) 10
    //(f,d) 14
    //(d,e) 9
    //(d,f) 14
    //adjacency lists:
    //a: b,h
    //b:a,h,c
    //c:b,i,f,d
    //d:c,e,f
    //e:d,f
    //f:c,d,e,g
    //g:h,i,f
    //h:a,i,g
    //i:h,g,c
        public static Graph mtx_to_graph(int[][] adj){
            int vnum = adj.length;
            System.out.println(vnum);
            LinkedList<vertex> vertlist = new LinkedList<vertex>();
            ArrayList<edge> edgelist = new ArrayList<edge>();
            vertex[] vArr = new vertex[vnum];
            //create and fill edgelist
            for(int i = 0; i<vnum; i++){
                vertex v = new vertex("v" + i);
                vArr[i] = v;
                vertlist.add(v);
            }
            for(int i = 0; i<vnum; i++){
                for(int k = 0; k<vnum; k++){
                    if(i !=k){
                        edgelist.add(new edge(vArr[i], vArr[k], adj[i][k]));
                    }
                }
            }
            
            //fill adjacency lists of each vertex
            for(vertex x : vertlist){
                for(int i = 0; i<edgelist.size(); i++){
                    if(edgelist.get(i).from == x){
                        x.adj_list.add(edgelist.get(i));
                }
            }
        }
            // System.out.println("edgelist_size: " + edgelist.size());
            // for(int i = 0; i<edgelist.size(); i++){
            //     System.out.println(edgelist.get(i).from.name + " " + edgelist.get(i).to.name + " " + edgelist.get(i).weight);
            // }
        return new Graph(vertlist,edgelist);
        }
    //MST solution: (a,b), (a,h),(h,g),(g,f),(f,c),(i,c),(c,d),(d,e)

        public static Graph MAKE_GRAPH(int index){
        if(index ==1){
        //create vertices:
        vertex a = new vertex("a");
        vertex b = new vertex("b");
        vertex c = new vertex("c");
        vertex d = new vertex("d");
        vertex e = new vertex("e");
        vertex f = new vertex("f");
        //create vertex and edgelist arraylists
        vertex[] vArr = {a,b,c,d,e,f};
        edge[] eArr = {new edge(a,b,3),new edge(a,e,4),new edge(b,c,2),new edge(b,e,1),new edge(b,a,3),new edge(c,b,2),new edge(c,e,3),new edge(c,f,4),new edge(d,c,5),new edge(d,f,2),new edge(e,a,4),new edge(e,c,3),new edge(e,f,8),new edge(f,d,2),new edge(f,c,4),new edge(f,e,8)};
        LinkedList<vertex> vertList = new LinkedList<vertex>(Arrays.asList(vArr));
        ArrayList<edge> edgelist = new ArrayList<edge>(Arrays.asList(eArr));

        //fill adjacency lists of each vertex
        for(vertex x : vertList){
            for(int i = 0; i<edgelist.size(); i++){
                if(edgelist.get(i).from == x){
                    x.adj_list.add(edgelist.get(i));
                }
            }
        }
        return new Graph(vertList,edgelist);
    }
    else{
        //create vertices:
        vertex a = new vertex("a");
        vertex b = new vertex("b");
        vertex c = new vertex("c");
        vertex d = new vertex("d");
        vertex e = new vertex("e");
        vertex f = new vertex("f");
        vertex g = new vertex("g");
        vertex h = new vertex("h");
        vertex i = new vertex("i");

        //create vertex and edgelist arraylists
        vertex[] vArr = {a,b,c,d,e,f,g,h,i};
        edge[] eArr = {new edge(a,b,4),new edge(a,h,8),new edge(b,a,4),new edge(b,h,11),new edge(b,c,8),new edge(c,b,8),new edge(c,i,2),new edge(c,f,4),new edge(c,d,7),new edge(d,c,7),new edge(d,e,9),new edge(d,f,14),new edge(e,d,9),new edge(e,f,10),new edge(f,c,4),new edge(f,d,14),new edge(f,e,10),new edge(f,g,2),new edge(g,h,1),new edge(g,i,6),new edge(g,f,2),new edge(h,a,8),new edge(h,i,7),new edge(h,g,1),new edge(i,h,7),new edge(i,g,6),new edge(i,c,2)};
        LinkedList<vertex> vertList = new LinkedList<vertex>(Arrays.asList(vArr));
        ArrayList<edge> edgelist = new ArrayList<edge>(Arrays.asList(eArr));

        //fill adjacency lists of each vertex
        for(vertex x : vertList){
            for(int n = 0; n<edgelist.size(); n++){
                if(edgelist.get(n).from == x){
                    x.adj_list.add(edgelist.get(n));
                }
            }
        }
        return new Graph(vertList,edgelist);

    }

    }
////////////////////////////////////////////////////////////
    //SET FUNCTIONS
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
    public static LinkedList<edge> kruskalAlg(Graph G){
        //create set for final output
        LinkedList<edge> A = new LinkedList<edge>();
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
      return A;
    }
/////////////////////////////////////////////////////////////////////////////////////
// MAIN FUNCTION
    public static void main(String[] args) throws FileNotFoundException{
        //run kruskal's algorithm on test graph 1:
        Graph G = MAKE_GRAPH(1);
        System.out.println("Graph 1:");
        LinkedList<edge> MST = kruskalAlg(G);
        int total = 0;
        for(edge m :MST){
            System.out.println("(" + m.from.name + "," + m.to.name + ")");
            total +=m.weight;
        }
        System.out.println("Total: " + total);
    ///////////////////////////////////////////////////////////////////
        //run kruskal's algorithm on test graph 2:
        System.out.println("Graph 2:");
        Graph G1 = MAKE_GRAPH(2);
        LinkedList<edge> MST1 = kruskalAlg(G1);
        int total1 = 0;
        for(edge m :MST1){
            System.out.println("(" + m.from.name + "," + m.to.name + ")");
            total1 +=m.weight;
        }
        System.out.println("Total: " + total1);
      
      
    }
  }


