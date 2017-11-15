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
    int time; 
    int conflict_vert;
    int root_vert;

    public Graph(String filename) {
        Vertex[] vertices = parse_file(filename);
        twoColorable = true;
        graph = vertices;
        invalidSubstructure = null; 

        Vertex[] twoColored = vertices;


        Vertex[] result = this.dfs();
        for(int i = 1; i < twoColored.length; i++){
            System.out.println(twoColored[i].color);
            System.out.println("Vertex " + i + " discoved at time " + twoColored[i].discover_time + " finished at time " + twoColored[i].finish_time);
        }
        System.out.println("Two Colorable: " + twoColorable);
        if (!twoColorable) {
            // swap the search order
            if(graph[this.conflict_vert].discover_time < graph[this.root_vert].discover_time) {
                int temp = this.conflict_vert;
                this.conflict_vert = this.root_vert;
                this.root_vert = temp;
            }
            this.invalidSubstructure = getSubstructure(this.conflict_vert, this.root_vert);

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
                vertices[i] = new Vertex((i), edge_list);
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
        // initialize the start time for the search 
        time = 0;
        // iterate over the nodes one by one
        for(int i = 1; i < graph.length; i++) { 
            // if the vertex has not been colored, visit and dfs color
            if(twoColorable && graph[i].color == -1) {
                System.out.println("Searching from vertex: " + i);
                dfsColor(i, 1, -1);
            }
        }
        
        return this.graph; 
    }




    public void dfsColor(int vertexID, int color, int parent) {
        time +=1;
        graph[vertexID].color = color; 
        graph[vertexID].parent = parent; 
        graph[vertexID].discover_time = time;


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

                // time += 1;
                graph[vertexID].finish_time = time;

                // return the graph substructure that is invalid
                this.conflict_vert = vertexID;
                this.root_vert = adjacent[i];

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
        time += 1;
        graph[vertexID].finish_time = time;

    }






 // conflict vertex has root adjacent with same color, follow the parents to the root 
    public Vertex[] getSubstructure(int conflict, int root) {
        // Stores the Integer IDs for the path 
        ArrayList<Integer> path = new ArrayList<Integer>();
        int curr = conflict;
        // add vertices from path to list
        System.out.println("Root: " + root);
        System.out.println("Conflict: " + conflict);

        int root_dis = graph[root].discover_time;
        int root_fin = graph[root].finish_time;
        int con_dis = graph[conflict].discover_time;
        int con_fin = graph[conflict].finish_time;

        // length of the subcycle

        int iterations = (graph[conflict].finish_time - graph[conflict].discover_time - 1)/2;
        System.out.println("finish: " + graph[conflict].finish_time + " discover_time: " + graph[conflict].discover_time);
        System.out.println("Iterations: " + iterations);
        iterations = 0;
        path.add(conflict);
        // add check for the last vertex connected to conflict
        while(iterations >= 0) {
            int[] edges = graph[curr].getEdges();
            for(int i = 0; i < edges.length; i++) {
                Vertex adjacent = graph[edges[i]];
                System.out.println("Look at vertex: " + adjacent.id + " with dis_time " + adjacent.discover_time + " and finish_time " + adjacent.finish_time);
                // System.out.println("Has dis_time " + adjacent.discover_time + " and finish_time " + adjacent.finish_time);
                // System.out.println("Vertex 4 was discovered at " + graph[4].discover_time + " has id " + graph[4].id);
                if(root_dis < adjacent.discover_time && adjacent.discover_time < con_dis) {
                    System.out.println("Meets first *********************");
                    System.out.println("con_fin " + con_fin);
                    System.out.println("root_fin " + root_fin);
                    System.out.println("adjacent_finish " + adjacent.finish_time);
                    System.out.println("adjacent id " + adjacent.id);
                    if(adjacent.finish_time > con_fin && adjacent.finish_time < root_fin) {
                        curr = i;
                        System.out.println();
                        System.out.println("adding vertex: " + i + " to cycle nodes\n");
                        path.add(new Integer(adjacent.id));
                        continue;
                    }
                }
            }
            iterations--;
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

    // // conflict vertex has root adjacent with same color, follow the parents to the root 
    // public Vertex[] getSubstructure(int conflict, int root) {
    //     // Stores the Integer IDs for the path 
    //     ArrayList<Integer> path = new ArrayList<Integer>();
    //     int curr = conflict;
    //     // add vertices from path to list
    //     System.out.println("Root: " + root);
    //     System.out.println("Conflict: " + conflict);

    //     // length of the subcycle

    //     int iterations = (graph[conflict].finish_time - graph[conflict].discover_time - 1)/2;
    //     System.out.println("finish: " + graph[conflict].finish_time + " discover_time: " + graph[conflict].discover_time);
    //     System.out.println("Iterations: " + iterations);

    //     // add check for the last vertex connected to conflict
    //     while(iterations >= 0) {
    //         int[] edges = graph[curr].getEdges();
    //         for(int i = 0; i < edges.length; i
    //             if(graph[root].discover_time++) {
    //             Vertex adjacent = graph[i]; < adjacent.discover_time && adjacent.discover_time < graph[conflict].finish_time) {
    //                 if(graph[conflict].discover_time < adjacent.finish_time && adjacent.finish_time < graph[root].finish_time){
    //                     curr = i;
    //                     System.out.println("adding vertex: " + i + " to cycle nodes");
    //                     path.add(new Integer(i));
    //                     continue;
    //                 }
    //             }
    //         }
    //         iterations--;
    //     }

    //     path.add(root);

    //     // stores vertex substructure that proves a graph is not two colorable
    //     Vertex[] substructure = new Vertex[(path.size())];

    //     // populate the path with Vertex objects
    //     for(int i = 0; i < substructure.length; i++) {
    //         substructure[i] = graph[(int)(path.get(i))];
    //     }
    //     return substructure;
    // }
}
