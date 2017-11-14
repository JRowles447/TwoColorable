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

	public Vertex(int id, ArrayList<Integer> edges) { 
		this.id = id;
		this.edges = edges;
		// initialize color to -1, i.e. vertex is uncolored
		this.color = -1; 
		// set visited to false
		this.visited = false; 
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
}