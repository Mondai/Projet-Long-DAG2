
package kCenter;

public class Graphe {
	
	Vertex[] data;
	double[][] distances ;
	int[] centres ;
	
	Graphe (int n, int k){
		this.data = new Vertex[n] ;
		this.distances = new double[n][n];
		this.centres= new int[k];
	}
	
	
}
