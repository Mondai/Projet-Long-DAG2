package kCenter;

import java.util.PriorityQueue;


public class AlgoDijkstra {

	public static void resetAllVertex(Vertex[] graphe) {
		for (Vertex v : graphe) {
			v.minDistance = Double.POSITIVE_INFINITY;
		}
	}

	public static void computeAllPaths(Vertex[] sources, double[][] distances) {
		for (Vertex source : sources) {
			resetAllVertex(sources);
			source.minDistance = 0.;
			PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
			vertexQueue.add(source);
			while (!vertexQueue.isEmpty()) {
				Vertex u = vertexQueue.poll();
				// Visit each edge exiting u
				for (Edge e : u.adjacencies) {
					Vertex v = e.target;
					double weight = e.weight;
					double distanceThroughU = u.minDistance + weight;
					if (distanceThroughU < v.minDistance) {
						vertexQueue.remove(v);
						v.minDistance = distanceThroughU;
						vertexQueue.add(v);
					}
				}
			}
			int i = source.numero;
			for (Vertex v : sources) {
				distances[i][v.numero] = v.minDistance ;				
			}
		}
	}

}
