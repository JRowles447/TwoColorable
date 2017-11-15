import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Math.*;
import java.util.*;


/** 
 * Class represents the Vertices in the Graph.
 */ 
public class Vertex { 
	/**
	 * The unique id of the vertex
	 */
	int id; 
	/**
	 * A list of Integers with the ids of the adjacent vertices
	 */
	ArrayList<Integer> edges; 
	/**
	 * int with value "0" for first color, "1" for second color, "-1" for no assignment
	 */
	int color; 
	/**
	 * Tracks whether the vertex has been visited in the depth first coloring
	 */
	boolean visited;
	/**
	 * Time that the node was first visited (and colored) in the coloring routine in Graph.java. 
	 */
	int discover_time;
	/**
	 * Time that the node was finished being visited (i.e. the children of the vertex have all been traversed and colored) 
	 */
	int finish_time;	


	/**
	 * 	Creates a new Vertex object that contains state outlined in input and tracks additional state information when coloring
	 *  the Graph. 
	 *
	 * 	@param 	id is the unique id of the Vertex object
	 *	@param	edges is the list of adjacent vertices
	 */
	public Vertex(int id, ArrayList<Integer> edges) { 
		this.id = id;
		this.edges = new ArrayList<Integer>();
		
		// initialize color to -1, i.e. vertex is uncolored
		this.color = -1; 

		// set visited to false
		this.visited = false; 

		// set discover and finish times to -1 just in case the traversal is partial (because there is an odd cycle)
		this.discover_time = -1; 
		this.finish_time = -1;
	}


	/** 
	 * Utility function for returning an array of ints from array of Integers with edge vertex ids. Useful for functions in
	 * Graph.java
	 * 
	 * @return 	int array with casted edges
	 */
	public int[] getEdges() {
		int[] castedEdges = new int[edges.size()];
		for(int i = 0; i < edges.size(); i++) {
			castedEdges[i] = (int)(edges.get(i));
		}
		return castedEdges;
	}
}