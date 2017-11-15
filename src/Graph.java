import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Math.*;
import java.util.*;

public class Graph {
    boolean twoColorable; 
    Vertex[] graph;
    Vertex[] invalidSubstructure;

    public Graph(String filename) {
        Vertex[] vertices = parse_file(filename);
        twoColorable = true;
        graph = vertices;
        invalidSubstructure = null; 

        Vertex[] twoColored = vertices;


        Vertex[] result = this.dfs();
        for(int i = 1; i < twoColored.length; i++){
            System.out.println(twoColored[i].color);
        }
        System.out.println("Two Colorable: " + twoColorable);
        if (!twoColorable){
            for(int i = 0; i < invalidSubstructure.length; i++){
                System.out.println(invalidSubstructure[i].id);
            }
        }
    }


    // parse file and return an array of Vertex objects
    public Vertex[] parse_file(String filename){
        Vertex[] vertices = null;
        try { 
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            int num_vertices = Integer.parseInt(reader.readLine());
            vertices = new Vertex[num_vertices+1];
            // initialize vertices 1 -> num_vertices
            for(int i = 1; i <= num_vertices; i++) {
                ArrayList<Integer> edge_list = new ArrayList<Integer>();
                vertices[i] = new Vertex((i+1), edge_list);
            }

            String line = null; 
            while((line = reader.readLine()) != null) {
                String[] edge = line.split(" ");
                int first = Integer.parseInt(edge[0]);
                int second = Integer.parseInt(edge[1]);
                
                // add first vertex to second vertex adjacency list
                vertices[first].edges.add(second);

                // add second vertex to first vertex adjacency list
                vertices[second].edges.add(first);

            }
        }
        catch(Exception e) { 
            e.printStackTrace();
        }
        return vertices;
    }

    // returns the Vertex[] of colored graph or the invalid substructure of the graph
    // (i.e. an odd cycle)
    public Vertex[] dfs() {
        // iterate over the nodes one by one
        for(int i = 1; i < graph.length; i++) { 
            // if the vertex has not been colored, visit and dfs color
            if(twoColorable && graph[i].color == -1) {
                System.out.println("Searching from vertex: " + i);
                dfsColor(i, 1, -1);
            }
            // if(!twoColorable){
            //     return getInvalid(); 
            // }
        }
        
        return this.graph; 
    }

    public void dfsColor(int vertexID, int color, int parent) {
        graph[vertexID].color = color; 
        graph[vertexID].parent = parent; 


        // check if the coloring is valid with the adjacent nodes
        // boolean valid = checkValid(vertices, vertexID
        // might not be good idea cause run time complexity



        int[] adjacent = graph[vertexID].getEdges();
        for(int i = 0; i < adjacent.length; i++) {
            // check first if there is a coloring for adjacent vertex that matches parent
            // this means the graph is NOT two colorable
            if(graph[adjacent[i]].color == graph[vertexID].color) {
                // invalidate valid and stop the trav ersal

                twoColorable = false; 

                // return the graph substructure that is invalid
                // this.invalidSubstructure = getSubstructure(vertexID, adjacent[i]);


                break;
            }
            // if the vertex does not have coloring, color it opposite parent!
            else if (graph[adjacent[i]].color == -1) {
                System.out.println("Exploring vertex: " + adjacent[i] + " from parent: " + vertexID);
                dfsColor(adjacent[i], Math.abs(color-1), vertexID);
            }
            // color is opposite
            else { 
                continue;
            }
        }
    }

    // conflict vertex has root adjacent with same color, follow the parents to the root 
    public Vertex[] getSubstructure(int conflict, int root) {
        // Stores the Integer IDs for the path 
        ArrayList<Integer> path = new ArrayList<Integer>();
        int curr = conflict;
        // add vertices from path to list
        System.out.println("Root: " + root);
        System.out.println("Conflict: " + conflict);
        while(graph[curr].parent != root) {
            System.out.println("Parent is: " + graph[curr].parent);
            path.add(new Integer(curr)); 
            curr = graph[curr].parent;
        }
        path.add(root);

        // stores vertex substructure that proves a graph is not two colorable
        Vertex[] substructure = new Vertex[(path.size())];

        // populate the path with Vertex objects
        for(int i = 0; i < substructure.length; i++) {
            substructure[i] = graph[(int)(path.get(i))];
        }
        return substructure;
    }

}
