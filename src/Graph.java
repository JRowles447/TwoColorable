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
            vertices = new Vertex[num_vertices];
            String line = null; 
            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(Exception e) { 
            e.printStackTrace();
        }
        return vertices;

    }
}

// void dfs(G, V, E):
//     System.out.println("hello world")

// def generate_graph(filename):
//     """
//     Takes a filename, reads the file, initializing a dictionary keyed by vertex_id that stores the adjacency list for that node

//     :param filename: name of the input file with the number of vertices and the enumerated, undirected edges
//     :return: ********************************************************************************************************************************** TODO what
//     """
//     graph = []

//     with open(filename) as file:
//         # get the number of vertices in graph
//         num_vertices = file.__next__()

//         for line in file:
//             i = 0
//             # print(line)

// if __name__ == '__main__':
//     import argparse

//     parser = argparse.ArgumentParser(description='Returns if a undirected graph is two colorable')
//     parser.add_argument('filename', help='name of the input file with number graph information')

//     args = parser.parse_args()
//     generate_graph(args.filename)


