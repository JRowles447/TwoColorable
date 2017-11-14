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
    boolean twoColorable = true; 

    public Graph(String filename) {
        Vertex[] vertices = parse_file(filename);
        Vertex[] twoColored = vertices;

        boolean result = dfs(twoColored);
        for(int i = 1; i < twoColored.length; i++){
            System.out.println(twoColored[i].color);
        }
        System.out.println("Two Colorable: " + twoColorable);
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

    public boolean dfs(Vertex[] vertices) {
        // iterate over the nodes one by one
        boolean valid = true;
        for(int i = 1; i < vertices.length; i++) { 
            // if the vertex has not been colored, visit and dfs color
            if(valid && vertices[i].color == -1) {
                dfsColor(vertices, i, 1, -1, valid);
            }
            if(!valid){
                System.out.println("GETS TO DFS FAILURE");
                return false; 
            }
        }
        
        return valid; 
    }

    public void dfsColor(Vertex[] vertices, int vertexID, int color, int parent, boolean valid) {
        vertices[vertexID].color = color; 
        vertices[vertexID].parent = parent; 


        // check if the coloring is valid with the adjacent nodes
        // boolean valid = checkValid(vertices, vertexID
        // might not be good idea cause run time complexity



        int[] adjacent = vertices[vertexID].getEdges();
        for(int i = 0; i < adjacent.length; i++) {
            // check first if there is a coloring for adjacent vertex that matches parent
            // this means the graph is NOT two colorable
            if(vertices[adjacent[i]].color == vertices[vertexID].color) {
                // invalidate valid and stop the traversal
                System.out.println("BAD GRAPH FAILURE");
                twoColorable = false; 
                break;
            }
            // if the vertex does not have coloring, color it opposite parent!
            else if (vertices[adjacent[i]].color == -1) {
                dfsColor(vertices, adjacent[i], Math.abs(color-1), vertexID, valid);
            }
            // color is opposite
            else { 
                continue;
            }
        }
    }

    // // preform color-dfs on graph  
    // public boolean dfs(Vertex[] vertices) {
        
    // }

}
