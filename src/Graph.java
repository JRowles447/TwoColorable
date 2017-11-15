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

        int[] adjacent = graph[vertexID].getEdges();
        for(int i = 0; i < adjacent.length; i++) {
            // check first if there is a coloring for adjacent vertex that matches parent
            // this means the graph is NOT two colorable
            if(graph[adjacent[i]].color == graph[vertexID].color) {
                // invalidate valid and stop the trav ersal

                twoColorable = false; 

                graph[vertexID].finish_time = time;

                // return the graph substructure that is invalid
                this.conflict_vert = vertexID;
                this.root_vert = adjacent[i];

                break;
            }
            // if the vertex does not have coloring, color it opposite parent!
            else if (graph[adjacent[i]].color == -1) {
                dfsColor(adjacent[i], Math.abs(color-1), vertexID);
            }
            // color is opposite (and correct)
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

        int root_dis = graph[root].discover_time;
        int root_fin = graph[root].finish_time;
        int con_dis = graph[conflict].discover_time;
        int con_fin = graph[conflict].finish_time;

        boolean finished = false;
        path.add(conflict);

        // add check for the last vertex connected to conflict
        int[] edges = graph[curr].getEdges();

        while(!finished) {
            for(int i = 0; i < edges.length; i++) {
                Vertex adjacent = graph[edges[i]];

                if(adjacent.id == this.root_vert && curr != conflict && graph[curr].discover_time > adjacent.discover_time) {
                    finished = true;
                    break;
                }
                if(adjacent.discover_time < graph[curr].discover_time && root_dis < adjacent.discover_time && adjacent.discover_time < con_dis) {
                    if(adjacent.finish_time > con_fin && adjacent.finish_time < root_fin) {
                        curr = edges[i];
                        edges = graph[curr].getEdges();

                        path.add(new Integer(adjacent.id));
                        continue;
                    }
                }
            }
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
