package kCenter;

import java.io.IOException;



public class PetitTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Graphe graphe = Traducteur.traduireOrlibPMED("data/pmed1.txt");
		
		System.out.println(graphe.data[0].adjacencies);
		
		AlgoDijkstra.computeAllPaths(graphe.data, graphe.distances);
		
		System.out.println("Distances crées ! "+graphe.distances[2][4] + " = 29 ");
		
	}

}
