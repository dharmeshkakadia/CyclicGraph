package org.graph.util;

import java.util.HashSet;
import java.util.Set;

import org.graph.model.Edge;
import org.graph.model.Vertex;

public class GraphUtil {

	private static <V extends Comparable<V>, E extends Comparable<E>> StringBuffer graphToString(Vertex<V,E> root, Set<Integer> visited){
		StringBuffer sb = new StringBuffer();
		if(root == null || visited.contains(root.getId())){
			return sb;
		}
		visited.add(root.getId());
		sb.append(root.toString()).append("[");
		for(Edge<V, E> e : root.getEdges()){
			sb.append(e.toString()).append(graphToString(e.getDestination(),visited));
		}
		sb.append("]");
		return sb;
	}

	public static <V extends Comparable<V>, E extends Comparable<E>> String graphToString(Vertex<V,E> root){
		return graphToString(root, new HashSet<Integer>()).toString();
	}
}
