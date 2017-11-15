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
    // tracks the time so discovery and finish times can be determined
    int time; 
    int conflict_vert;
    int root_vert;


    /**
     * Constructor for the Graph object, which initializes and organizes the problem, reading file and initializing vertices, 
     * and calls dfs on the vertice. Returns an output file with either the assignment of colors to vertices or the invalid
     * substructure of the graph. 
     *
     * @param   filename is the name of the input file containing the number of vertices and the undirected edges in the 
     *          graph.
     * @param   outputFile is the name of the output file to print the color assignment or invalid substructure for graph.
     *
     */
    public Graph(String fileName, String outputFile) {
        Vertex[] vertices = parse_file(fileName);
        twoColorable = true;
        graph = vertices;

        Vertex[] twoColored = vertices;

        this.dfs(); 

        // set output file name
        String write = outputFile;
        
        // write results to output file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(write));
            writer.write("Two Colorable: " + twoColorable + "\n");

            // write the coloring assignment to the file if the graph is two colorable
            if (twoColorable) { 
                for(int i = 1; i < graph.length; i++) {
                    if(graph[i].color == 0) { 
                        writer.write(graph[i].id + ": " + "red\n");
                    }
                    else {
                        writer.write(graph[i].id + ": " + "black\n");
                    }
                }
            }
            // else write the cycle of vertices that invalidates 
            else {
                // swap the search order
                if(graph[this.conflict_vert].discover_time < graph[this.root_vert].discover_time) {
                    int temp = this.conflict_vert;
                    this.conflict_vert = this.root_vert;
                    this.root_vert = temp;
                }

                Vertex[] invalidSubstructure = getSubstructure(this.conflict_vert, this.root_vert);

                for(int i = 0; i < invalidSubstructure.length; i++){
                    writer.write(String.valueOf(invalidSubstructure[i].id));
                    if(i != (invalidSubstructure.length -1)) {
                        writer.write(", ");
                    }
                }
            }
            writer.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * Parses an input file according to the prespecified format. The vertex array returned
     * is the representation of the graph from the input file.  
     *
     * @param   fileName  The name of the file to parse
     * @return  A vertex array with initialized vertices and edges
     */
    // parse file and return an array of Vertex objects
    public Vertex[] parse_file(String fileName){
        Vertex[] vertices = null;
        try { 
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
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




    /**
     * Performs depth first search on the graph, calling dfsColor helper function to visit children in depth 
     * first search style. Returns a vertex array with vertices colored if the graph is two colorable, otherwise
     * returns a partial coloring.
     *
     * @return  Returns a vertex array with the two colored graph
     */
    public void dfs() {
        // initialize the start time for the search 
        time = 0;
        // iterate over the vertices one by one
        for(int i = 1; i < graph.length; i++) { 
            // if the vertex has not been colored, visit and dfs color
            if(twoColorable && graph[i].color == -1) {
                dfsColor(i, 1);
            }
        }
        // return this.graph; 
    }




    /**
     * Called from dfs, colors the vertex at vertexID in graph with color (color is passed as opposite from parent). 
     * Checks adjacent vertices. Colors adjacent opposite color with recursive calls to dfsColor, or, if they are already colored, 
     * check if opposite than current vertex. If there is a conflict, set global boolean, break from function. Operates on graph
     * global variable.  
     * 
     * @param   vertexID is the id of the vertex in the graph to be colored. After coloring, the vertex's adjacency list is checked
     *          to ensure valid graph (two colorable)
     * @param   color is the int to make the vertex with 
     */
    public void dfsColor(int vertexID, int color) {
        time +=1;
        graph[vertexID].color = color; 
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
                dfsColor(adjacent[i], Math.abs(color-1));
            }
            // color is opposite (and correct)
            else { 
                continue;
            }
        }
        time += 1;
        graph[vertexID].finish_time = time;
    }





    /**
     * Uses the conflict and root vertices from the graph to return the invalid substructure of the graph (i.e. the odd 
     * cycle). Works backwards using the discovery and finish times of vertices. 
     *
     * @param   conflict is the id of the vertex that was being colored/ checked in dfsColor routine that was adjacent to a vertex
     *          of the same color. 
     * @param   root is the id of the vertex adjacent to and the same color as conflict
     * @reutrn  Return the Vertex array with ordering for the vertices in forward order of exploration from the "root" of the cycle
     */ 
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
