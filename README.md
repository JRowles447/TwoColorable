# TwoColorable
---
Checks whether a given undirected graph is Two Colorable. Two Colorable entails that each vertices is assigned one of two colors and that no adjacent vertices have the same color. Assigned through my Algorithms course, where we were limited in tools available for our implementation. If the graph meets the Two Colorable requirements, an assignment of colors for each vertex in the graph is returned. If the graph fails to meet the requirements, then the substructure that does not meet the requirements is returned. This substructure will be an odd cycle in the graph. Implemented using depth first traversal for exploration of the graph.

## Running TwoColorable
---
Compile the project with the following command:   
`javac ./src/* -d ./build`

Run `TwoColorable` from the `build` folder of the project directory using the following command:   
`java TwoColorable ../inputs/smallgraph ../outputs/smallgraphout`

For graphs of larger size (`largegraph1` and `largegraph2`), use this command:   
`java -Xss14M TwoColorable ../inputs/largegraph2 ../outputs/largegraph2out`
This increases the stack size for the program (necessary for the deep levels of recursion for a large number of vertices).

## Sample Output
---


## Dependencies
---
Utilizes Java 1.8.0_112
