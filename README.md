# TwoColorable
Checks whether a given undirected graph is Two Colorable. Two Colorable entails that each vertices is assigned one of two colors and that no adjacent vertices have the same color. Assigned through my Algorithms course, where we were limited in tools available for our implementation. If the graph meets the Two Colorable requirements, an assignment of colors for each vertex in the graph is returned. If the graph fails to meet the requirements, then the substructure that does not meet the requirements is returned. This substructure will be an odd cycle in the graph. Implemented using depth first traversal for exploration of the graph.

## Dependencies
Utilizes Java 1.8.0_112

## Running TwoColorable
Compile the project with the following command:   
`javac ./src/* -d ./build`

Run `TwoColorable` from the `build` folder of the project directory using the following command:   
`java TwoColorable ../inputs/smallgraph ../outputs/smallgraphout`

The first argument is the input file with a graph following the format specified in <!-- TODO add link --> Input Format. The second input is the file to write the output to.

For graphs of larger size (`largegraph1` and `largegraph2`), use this command:   
`java -Xss14M TwoColorable ../inputs/largegraph2 ../outputs/largegraph2out`
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

## Sample Output
The following is produced when running TwoColorable on `inputs/smallgraph`:
```
> java TwoColorable ../inputs/smallgraph ../outputs/smallgraphoutput

> more smallgraphoutput
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

> ```
