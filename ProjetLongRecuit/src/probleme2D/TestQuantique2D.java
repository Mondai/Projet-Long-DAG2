package probleme2D;


import solver.parametres.ConstanteKConstant;
import solver.parametres.FonctionLineaire;
import solver.quantique.RecuitQuantique;

public class TestQuantique2D {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		
		int[] v = { 1, 2, 3, 1, 2, 1, 5, 1, 2, 3, 4, 5,5,6,5,2,3,4,5,2,6,4,6,4,5,5,5,4,6,5,6,2,5,8,4,2,6,2,8,6,5,4,2,6,5,4,2,6,0,5,2,5,4,5,2,6,0,5,6,8,6,5,9,8,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int[][] u = {v,v,v,v,v,v,v,v,v,v,v};
		
		 Relief2D relief = new Relief2D(u[0].length,u.length,u);

		 
		
		 
		 // Paramètres du recuit
		double k = 1;
		int M = 4;
		double G0 = 0.75;
		int P = 10;
		double T = 0.35/P;
		int maxSteps = (int) Math.pow(10,4);
		FonctionLineaire Tparam = new FonctionLineaire(G0,0,maxSteps);
		ConstanteKConstant Kparam = new ConstanteKConstant(k);
		RecuitQuantique recuit = new RecuitQuantique(Tparam,Kparam, M, T);
		 
		 //Initialisation probleme
		Distances Ec = new Distances();
		Hauteur Ep=new Hauteur();
		Mutation1Pixel mutation = new Mutation1Pixel();
		
		 Position2DParticule probleme= new Position2DParticule(Ep,Ec, P, relief,mutation);
		 probleme.initialiser();
		
		 
		
		long startTime = System.nanoTime();
		recuit.lancer(probleme);
		long endTime = System.nanoTime();
		
			
		for (int i=0;i<P;i++) {
		System.out.println("Energie du ième : "+Ep.calculer(probleme.etats[i]));	
		}
		
		for (int i=0;i<P;i++) {
			System.out.println("Meilleure Energie du ième : "+((Position2D)probleme.etats[i]).getMeilleureEnergie());	
			}
		
		System.out.println("duree = "+(endTime-startTime)/1000000000+" s");
		
		
		 
	}

}
