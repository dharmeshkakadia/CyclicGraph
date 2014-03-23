What?
----

A data structure representing graphs (connected graphs?) and supporting isomorphism check for two graphs.

When are two graphs isomorphic?
-------------------------------
Formally, two graphs G and H with graph vertices V_n={1,2,...,n} are said to be isomorphic if there is a permutation p of V_n such that {u,v} is in the set of graph edges E(G) iff {p(u),p(v)} is in the set of graph edges E(H). (source: http://mathworld.wolfram.com/IsomorphicGraphs.html). Informally, it captures the notion of two graphs having "similar" structure.

Assumptions
-----------

1. Vertex contents are not guaranteed to be unique.For unique vertex identification, we used internal integer id which means that number of vertex should not exceed the integer range. This was done for the practical purpose, and can be easily replaced with larger range types.

2. Edges originating from same vertex have unique labels, although no validation checks has been enforced while creating graphs for uniqueness.

3. Graph has a root vertex, from which all the vertex are reachable. This check is also not enforced during creation of the graph.

Design
------

We have made both Edge and Vertex Generic. So, one graphs with different type of content and labels can be created. Algorithm is agnostic to the type of the content and label.The test covers the graphs with String contents and labels.

one interesting choice was about traversal data structure. Whether to make visited part of the Graph data structure(a field) ?

Pros:
- All the operations that require graph traversal become memory efficient, no need to keep the HashMap

Cons:
- Overhead on all the operations that don't require.
- not fundamentally part of the Vertex Object in OO sense.
- Without special isolation mechanism, two operations both using visited field will fail due to overlap.

Thus we decided that visited should not be part of the main data structure.

Algorithm
---------

Algorithm follows from the recursive definition of isomorphism.

1. compare the two vertex.
2. If they match, for each edge, find a isomorphic edge in the other graph. If no matching edge can be found for any edge, two graphs are not isomorphic.
3. For each isomorphic edge, recurse on destination vertex of these two edges and mark these vertex as visited.

Dependencies
------------

1. JAVA -any version should work. Tested with Java 1.6.
2. Maven to build.

Build
-----

Use maven to build the project. Following are maven targets

- compile, mvn compile
- create jar, mvn package
- run test, mvn test
- cleanup, mvn clean

To use it with your application, put the jar into your application CLASSPATH. jar can be found in target folder.

Code Structure
--------------

All the source is under src/ directory. Test case is under src/test directory.

- org.graph.model.* contains the graph representation (Edge and Vertex).
- org.graph.operations.* contains operations on the graph. Currently, only isomorphism operation is implemented.
- org.graph.util.* contains some useful utilities.
- TestIsomorphism.java is contains test cases for isomorphism operation.

Possible Future Improvement
---------------------------

1. One of the limitation of the current algorithm is that the visited vertex-ids are kept in memory. This can be a problem for large graphs. This can be solved using some form of disk based data structure.
Overall idea on how this can be done.

2. Better way to create and interact with the data structure. One possibility is to create a short interactive shell which supports basic CrUD operations and isomorphism on graphs.

3. Performance benchmark.

Feedback
--------

Have you used this library? Have a suggestion on improving? ping me @DharmeshKakadia or drop a mail to dharmesh.kakadia@research.iiit.ac.in