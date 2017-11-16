# TwoColorable
Checks whether a given undirected graph is Two Colorable. Two Colorable entails that each vertices is assigned one of two colors and that no adjacent vertices have the same color. Assigned through my Algorithms course, where we were limited in tools available for our implementation. If the graph meets the Two Colorable requirements, an assignment of colors for each vertex in the graph is returned. If the graph fails to meet the requirements, then the substructure that does not meet the requirements is returned. This substructure will be an odd cycle in the graph. Implemented using depth first traversal for exploration of the graph.

## Implementation Details
The project uses a Vertex class that tracks information from the input file and information gathered during the depth first traversal of the graph (e.g. color, discovery time, etc). The Graph object contains the collection of Vertex objects and manages the traversal and file writing. First, the input file is parsed using the parse_file() function. During  parse_file(), the file is traversed line-by-line and each pair of vertices is added to the vertices Vertex[] at the appropriate location (vertices has mapping directly to each Vertex (i.e. Vertex with id = 1 is stored at vertices[1]) (vertices[0] is empty)).

After parsing the file, the dfs() function is called. This performs the general dfs routine outlined in Introduction To Algorithms, except it calls dfsColor() instead of dfsVisit(). First, dfs() begins with the first Vertex in the graph array of Vertex objects. It checks whether or not the Vertex is colored, it is not, so it calls dfsColor(). In dfsColor(), we increment the time and set the color and discovery time for the Vertex. We then retrieve the adjacency list. We then check:
1. If the color of the adjacent vertex matches the current Vertex. If they match, the graph is not two colorable, so we set the global variable “twoColorable” to false and the two vertex ids where the conflict occurred and we break out of the loop.
2. We check if the adjacent Vertex has no color, if it does not, we call dfsColor() with the opposite color of the current Vertex.
3. Otherwise, the adjacent Vertex is already colored the opposite of the current Vertex and we can continue.   

After traversing the adjacency list and recursively calling dfsColor(), we increment the time and set the finish time of the Vertex. If there are any components disconnected from what has currently been traversed, dfs() will start the dfsColor() at a Vertex in the disconnect portion.

After the dfs() function completes, the program opens the output file to write the results to. If the graph is two colorable, we traverse through the graph and write Vertex ids and the assigned colors to the output file. If the graph is not two colorable, we call the getSubstructure() methoud, which returns the odd cycle that causes the graph to not be two colorable. We pass the “start” and “end” of the cycle to the function. These values correspond to the adjacent Vertex with the same color as the current Vertex and the Vertex being evaluated when the conflicting color assignments occurred (described in 1 in the previous paragraph). We then traverse the cycle from the “end” Vertex back to the “start” Vertex using the discovery and finish times for the Vertex objects in the adjacency list. We keep traversing the path until we encounter the “start” Vertex of the cycle. After this, we return the substructure as an array. After the return, the list of the Vertex objects is then traversed and written to the output file.

## Dependencies
Utilizes Java 1.8.0_112

## Running TwoColorable
Compile the project with the following command:   
`javac ./src/* -d ./build`

Run `TwoColorable` from the `build` folder of the project directory using the following command:   
`java TwoColorable ../inputs/small_graph ../outputs/small_graph_out`

The first argument is the input file with a graph following the format specified in <!-- TODO add link --> Input Format. The second input is the file to write the output to.

For graphs of larger size (`large_graph_1` and `large_graph_2`), use this command:   
`java -Xss14M TwoColorable ../inputs/large_graph_2 ../outputs/large_graph_2_out`
This increases the stack size for the program (necessary for the deep levels of recursion for a large number of vertices).

## Input File format
The input file must list the number of vertices in the graph on the first line. Each subsequent line of the graph are the undirected edges between the vertices. For example:

```
4
1 2
2 3
3 1
3 4
```

Corresponds to the following graph:
```
    1
   / \
  3 - 2
  |
  4
```

## Sample Outputs
The following is produced when running TwoColorable on `inputs/smallgraph`, a Two Colorable graph:
```
> java TwoColorable ../inputs/small_graph ../outputs/small_graph_out

> more ../outputs/small_graph_out
Two Colorable: true
1: black
2: red
3: black
4: black
5: red
6: black
7: red
8: red
9: black
10: red

>
```

The following is produced when TwoColorable is run on `inputs/small_invalid`, a graph that is not Two Colorable:
```
> java TwoColorable ../inputs/small_invalid ../outputs/small_invalid_out

> more ../outputs/small_invalid_out
Two Colorable: false
7, 6, 8

>
```
