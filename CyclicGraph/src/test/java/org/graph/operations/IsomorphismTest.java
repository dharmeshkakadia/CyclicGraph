package org.graph.operations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.graph.model.Vertex;
import org.junit.Test;

public class IsomorphismTest {

	@Test
	public void testIsIsomorphicOnIdentity() {
		Vertex<String, String> graph = new Vertex<String, String>("root");
		assertTrue(Isomorphism.isIsomorphic(graph, graph)); // automorphism test
	}
	
	@Test
	public void testIsIsomorphicOnNullGraphs() {
		Vertex<String, String> nullGraph1 = null;
		Vertex<String, String> nullGraph2 = null;
		Vertex<String, String> nonNullGraph = new Vertex<String, String>("root");
		
		assertTrue(Isomorphism.isIsomorphic(nullGraph1, nullGraph2));
		assertFalse(Isomorphism.isIsomorphic(nullGraph1, nonNullGraph));
	}
	
	@Test
	public void testIsIsomorphicOnSingleVertexGraphs() {
		Vertex<String, String> graph1 = new Vertex<String, String>("root");
		Vertex<String, String> graph2 = new Vertex<String, String>("root");
		Vertex<String, String> nullGraph = null;
		Vertex<String, String> differentContentGraph = new Vertex<String, String>("different");
		Vertex<String, String> longGraph = new Vertex<String, String>("root");
		longGraph.addEdge("edge", new Vertex<String, String>("child"));
		
		assertTrue(Isomorphism.isIsomorphic(graph1, graph2));
		assertFalse(Isomorphism.isIsomorphic(graph1, nullGraph));
		assertFalse(Isomorphism.isIsomorphic(graph1, differentContentGraph));
		assertFalse(Isomorphism.isIsomorphic(graph1, longGraph));
	}

	@Test
	public void testIsIsomorphicOnSingleVertexCyclicGraph() {
		Vertex<String, String> graph1 = new Vertex<String, String>("root");
		graph1.addEdge("e1", graph1);
		Vertex<String, String> graph2 = new Vertex<String, String>("root");
		graph2.addEdge("e1", graph2);
		Vertex<String, String> nullGraph = null;
		Vertex<String, String> differentContentGraph = new Vertex<String, String>("different");
		differentContentGraph.addEdge("e1", differentContentGraph);
		Vertex<String, String> differentEdgeGraph = new Vertex<String, String>("root");
		differentEdgeGraph.addEdge("differentEdge", differentEdgeGraph);
		Vertex<String, String> longGraph = new Vertex<String, String>("root");
		longGraph.addEdge("edge", new Vertex<String, String>("child"));
		
		assertTrue(Isomorphism.isIsomorphic(graph1, graph2));
		assertFalse(Isomorphism.isIsomorphic(graph1, nullGraph));
		assertFalse(Isomorphism.isIsomorphic(graph1, differentContentGraph));
		assertFalse(Isomorphism.isIsomorphic(graph1, differentEdgeGraph));
		assertFalse(Isomorphism.isIsomorphic(graph1, longGraph));
	}
	
	@Test
	public void testIsIsomorphicOnSkwedTree() {
		int skewLength = 10;
		Vertex<String, String> graph1 = new Vertex<String, String>("root");
		Vertex<String, String> current = graph1;
		Vertex<String, String> temp = null;
		for (int i=1;i<skewLength; i++) {
			temp = new Vertex<String, String>("vertex"+i);
			current.addEdge("e"+i, temp);
			current = temp;
		}
		
		Vertex<String, String> graph2 = new Vertex<String, String>("root");
		current = graph2;
		temp = null;
		for (int i=1;i<skewLength; i++) {
			temp = new Vertex<String, String>("vertex"+i);
			current.addEdge("e"+i, temp);
			current = temp;
		}
		
		Vertex<String, String> skewedSuperSet = new Vertex<String, String>("root");
		current = skewedSuperSet;
		temp = null;
		for (int i=1;i<skewLength; i++) {
			temp = new Vertex<String, String>("vertex"+i);
			current.addEdge("e"+i, temp);
			current.addEdge("rightEdge"+i, new Vertex<String, String>("vertex"+i));
			current = temp;
		}
		
		Vertex<String, String> longGraph = new Vertex<String, String>("root");
		graph1.addEdge("e1", graph1);
		graph2.addEdge("e1", graph2);
		longGraph.addEdge("edge", new Vertex<String, String>("child"));
		
		assertTrue(Isomorphism.isIsomorphic(graph1, graph2));
		assertFalse(Isomorphism.isIsomorphic(longGraph, graph1));
		assertFalse(Isomorphism.isIsomorphic(graph1, skewedSuperSet));
	}
	
	@Test
	public void testIsIsomorphicOnTriangleGraphs() {
		Vertex<String, String> g1A = new Vertex<String, String>("A");
		Vertex<String, String> g1B = new Vertex<String, String>("B");
		Vertex<String, String> g1C = new Vertex<String, String>("C");
		g1A.addEdge("AB", g1B);
		g1B.addEdge("BC", g1C);
		g1C.addEdge("CA", g1A);
		
		Vertex<String, String> g2A = new Vertex<String, String>("A");
		Vertex<String, String> g2B = new Vertex<String, String>("B");
		Vertex<String, String> g2C = new Vertex<String, String>("C");
		g2A.addEdge("AB", g2B);
		g2B.addEdge("BC", g2C);
		g2C.addEdge("CA", g2A);
		
		//reverse directional edges
		Vertex<String, String> g3A = new Vertex<String, String>("A");
		Vertex<String, String> g3B = new Vertex<String, String>("B");
		Vertex<String, String> g3C = new Vertex<String, String>("C");
		g3B.addEdge("AB", g3A);
		g3C.addEdge("BC", g1B);
		g3A.addEdge("CA", g1C);
		
		// Treating any vertex as a root
		assertTrue(Isomorphism.isIsomorphic(g1A, g2A));
		assertTrue(Isomorphism.isIsomorphic(g1B, g2B));
		assertTrue(Isomorphism.isIsomorphic(g1C, g2C));
		
		// Wrong vertex as a root
		assertFalse(Isomorphism.isIsomorphic(g1A, g2B));
		assertFalse(Isomorphism.isIsomorphic(g1A, g2C));
		
		// Wrong direction of edges 
		assertFalse(Isomorphism.isIsomorphic(g1A, g3A));
		assertFalse(Isomorphism.isIsomorphic(g1A, g3B));
		assertFalse(Isomorphism.isIsomorphic(g1A, g3C));
	}
	
	@Test
	public void testIsIsomorphicOnMirredTrees() {
		Vertex<String, String> g1Root = new Vertex<String, String>("Root");
		Vertex<String, String> g1Left = new Vertex<String, String>("Left");
		Vertex<String, String> g1Right = new Vertex<String, String>("Right");
		Vertex<String, String> g1LeftLeft = new Vertex<String, String>("LeftLeft");
		Vertex<String, String> g1LeftRight = new Vertex<String, String>("LeftRight");
		g1Root.addEdge("e1", g1Left);
		g1Root.addEdge("e2", g1Right);
		g1Left.addEdge("e3", g1LeftLeft);
		g1Left.addEdge("e4", g1LeftRight);
		
		Vertex<String, String> g2Root = new Vertex<String, String>("Root");
		Vertex<String, String> g2MirroredLeft = new Vertex<String, String>("Left");
		Vertex<String, String> g2MirroredRight = new Vertex<String, String>("Right");
		Vertex<String, String> g2MirroredLeftLeft = new Vertex<String, String>("LeftLeft");
		Vertex<String, String> g2MirroredLeftRight = new Vertex<String, String>("LeftRight");
		
		g2Root.addEdge("e1", g2MirroredLeft);
		g2Root.addEdge("e2", g2MirroredRight);
		g2MirroredLeft.addEdge("e3", g2MirroredLeftLeft);
		g2MirroredLeft.addEdge("e4", g2MirroredLeftRight);
		
		assertTrue(Isomorphism.isIsomorphic(g1Root, g2Root));		
	}

	@Test
	public void testIsIsomorphicOnStarGraphs() {
		int starSize=10;
		Vertex<String, String> g1Root = new Vertex<String, String>(0, "root");
		for(int i=1;i<=starSize;i++){
			g1Root.addEdge("e"+i, new Vertex<String, String>(i, "v"+i));
		}
		
		// adding in the reverse order
		Vertex<String, String> g2Root = new Vertex<String, String>(0, "root");
		for(int i=starSize;i>=1;i--){
			g2Root.addEdge("e"+i, new Vertex<String, String>(i, "v"+i));
		}
		assertTrue(Isomorphism.isIsomorphic(g1Root, g2Root));		
	}
	
	@Test
	public void testIsIsomorphicOnMultiCycledGraphs() {
		Vertex<String, String> g1Root = new Vertex<String, String>("root");
		Vertex<String, String> g1A = new Vertex<String, String>("A");
		Vertex<String, String> g1B = new Vertex<String, String>("B");
		Vertex<String, String> g1C = new Vertex<String, String>("C");
		g1A.addEdge("AB", g1B);
		g1B.addEdge("BC", g1C);
		g1C.addEdge("CA", g1A);
		// second cycle from the vertex g1A
		Vertex<String, String> g1BB = new Vertex<String, String>("BB");
		Vertex<String, String> g1CC = new Vertex<String, String>("CC");
		g1A.addEdge("ABB", g1BB);
		g1BB.addEdge("BBC", g1CC);
		g1CC.addEdge("CCA", g1A);
		
		Vertex<String, String> g2Root = new Vertex<String, String>("root");
		Vertex<String, String> g2A = new Vertex<String, String>("A");
		Vertex<String, String> g2B = new Vertex<String, String>("B");
		Vertex<String, String> g2C = new Vertex<String, String>("C");
		g2A.addEdge("AB", g2B);
		g2B.addEdge("BC", g2C);
		g2C.addEdge("CA", g2A);
		// second cycle from the vertex g2A
		Vertex<String, String> g2BB = new Vertex<String, String>("BB");
		Vertex<String, String> g2CC = new Vertex<String, String>("CC");
		g2A.addEdge("ABB", g2BB);
		g2BB.addEdge("BBC", g2CC);
		g2CC.addEdge("CCA", g2A);
		
		assertTrue(Isomorphism.isIsomorphic(g1Root, g2Root));
	}
	
	@Test
	public void testIsIsomorphicOnRepeatingLabelsAndContents() {
		Vertex<String, String> g1v1 = new Vertex<String, String>("v1");
		Vertex<String, String> g1v2 = new Vertex<String, String>("v1");
		g1v1.addEdge("e1", g1v2);
		g1v2.addEdge("e1", null);
		
		Vertex<String, String> g2v1 = new Vertex<String, String>("v1");
		Vertex<String, String> g2v2 = new Vertex<String, String>("v1");
		g2v1.addEdge("e1", g2v2);
		g2v2.addEdge("e1", null);
		
		Vertex<String, String> g3v1 = new Vertex<String, String>("v1");
		g3v1.addEdge("e1", null);
		
		assertTrue(Isomorphism.isIsomorphic(g1v1, g2v1));
		assertFalse(Isomorphism.isIsomorphic(g1v1, g3v1));
	}
}
