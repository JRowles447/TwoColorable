import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Math.*;
import java.util.*;

public class Vertex { 
	// id of the vertex
	int id; 
	// list of Integer ids to other vertices
	ArrayList<Integer> edges; 
	int color; 
	boolean visited;
	// previous vertex that produced this vertex in dfsColoring
	int parent; 
	int discover_time;
	int finish_time;	


	public Vertex(int id, ArrayList<Integer> edges) { 
		this.id = id;
		this.edges = new ArrayList<Integer>();
		// initialize color to -1, i.e. vertex is uncolored
		this.color = -1; 
		// set visited to false
		this.visited = false; 
		this.parent = -1;
		// set discover and finish times to -1 just in case the traversal is partial (because there is an odd cycle)
		this.discover_time = -1; 
		this.finish_time = -1;
	}

	// return the Integer array list casted to an array of ints
	// used for easier processing
	public int[] getEdges() {
		int[] castedEdges = new int[edges.size()];
		for(int i = 0; i < edges.size(); i++) {
			castedEdges[i] = (int)(edges.get(i));
		}
		return castedEdges;
	}

	// add Integer edge to list of edges
	public void addEdge(int edge) {
		Integer newEdge = new Integer(edge);
		this.edges.add(newEdge);
	}
}