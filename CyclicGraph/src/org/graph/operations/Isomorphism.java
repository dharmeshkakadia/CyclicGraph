package org.graph.operations;

import java.util.HashSet;
import java.util.Set;

import org.graph.model.Edge;
import org.graph.model.Vertex;

public class Isomorphism {
	
	/** Checks for isomorphism of two graphs excluding @Vertexs from @g1Visited and @g2Visited 
	 * @param v1 @Vertex from Graph1
	 * @param v2 @Vertex from Graph2
	 * @param g1Visited @Set of vertex-ids already visited from graph1
	 * @param g2Visited @Set of vertex-ids already visited from graph2
	 * @return
	 */
	private static <V extends Comparable<V>, E extends Comparable<E>> boolean isIsomorphic(Vertex<V,E> v1, Vertex<V,E> v2, Set<Integer> g1Visited, Set<Integer> g2Visited){
		if(v1 == null && v2 == null){
			return true;
		}else if((v1==null && v2!= null) || (v1!=null && v2==null)){
			return false;
		}
		
		boolean v1Visited = g1Visited.contains(v1.getId());
		boolean v2Visited = g2Visited.contains(v2.getId());
		if(v1Visited && v2Visited){
			return true;
		}else if((!v1Visited && v2Visited) || (v1Visited && !v2Visited)){
			return false;
		}else{
			int vertexComparision = v1.compareTo(v2);
			if(vertexComparision != 0){
				return false;
			}
			Set<Integer> g1VisitedNew = new HashSet<Integer>(g1Visited);
			Set<Integer> g2VisitedNew = new HashSet<Integer>(g2Visited);
			g1VisitedNew.add(v1.getId());
			g2VisitedNew.add(v2.getId());
			boolean isomorphic = true;

			for(Edge<V, E> e1 : v1.getEdges()){
				
				boolean edgeIsomorphic = false;
				for(Edge<V, E> e2 : v2.getEdges()){
					if(e1.compareTo(e2)==0){
						edgeIsomorphic = isIsomorphic(e1.getDestination(), e2.getDestination(),g1VisitedNew,g2VisitedNew);
						if(edgeIsomorphic && e2.getDestination() != null){
							g1VisitedNew.add(e2.getDestination().getId());
						}
					}
				}
				if(!edgeIsomorphic){
					return false;
				}
			}
			return isomorphic;
		}
	
	}
	
	/** Checks for isomorphism of two graphs
	 * @param root1 root node of Graph1
	 * @param root2 root node of Graph2
	 * @return true if both graphs are isomorphic, false otherwise
	 */
	public static <V extends Comparable<V>, E extends Comparable<E>> boolean isIsomorphic(Vertex<V,E> root1, Vertex<V,E> root2){
		return isIsomorphic(root1, root2, new HashSet<Integer>(), new HashSet<Integer>());
	}
	
}
