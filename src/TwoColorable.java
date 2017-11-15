/**
 * The TwoColorable program reads in a graph input file and determines whether or not the graph is two colorable. If it is, 
 * the output file will contain color assignments of each vertex. If it is not two colorable, the output file contains the 
 * substructure of the graph that makes it not two colorable (i.e. the odd cycle)
 * 
 *
 * @Author Jason Rowley
 */
public class TwoColorable {

    public static void main(String[] args) {
        // arg[0] is input file with number of vertices and enumerated edges
        // arg[1] is output file to print to 
        Graph graph = new Graph(args[0], args[1]);
    }
}