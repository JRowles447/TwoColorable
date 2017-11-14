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
    public Graph(String filename) {
        Vertex[] vertices = parse_file(filename);
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

                System.out.println(line);
            }
            for(int i = 1; i < vertices.length; i++){
                System.out.println(vertices[i].edges);
            }

        }
        catch(Exception e) { 
            e.printStackTrace();
        }
        return vertices;
    }

    // // preform color-dfs on graph  
    // public boolean dfs(Vertex[] vertices) {
        
    // }

}
