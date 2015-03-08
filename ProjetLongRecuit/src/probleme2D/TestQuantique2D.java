package probleme2D;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;

import javax.swing.ImageIcon;

import GraphiqueProbleme2D.FenetreRepliques;
import GraphiqueProbleme2D.PanneauRepliques;
import solver.commun.Probleme;
import solver.graphique.IListEnergie;
import solver.graphique.ListEnergie;
import solver.graphique.ListEnergieVide;
import solver.parametres.ConstanteKConstant;
import solver.parametres.FonctionExpoRecursive;
import solver.parametres.FonctionLineaire;
import solver.quantique.RecuitQuantique;
import solver.quantique.RecuitQuantiqueAccelere;
import solver.quantique.RecuitQuantique_Graphique;

public class TestQuantique2D {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		
		String path = "src/images/marilyn.jpg";
		Image image = (new ImageIcon(path).getImage());
		BufferedImage bimage = new BufferedImage(image.getWidth(null), image
		        .getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
		
		Graphics g = bimage.getGraphics();  
		g.drawImage(image, 0, 0, null);  
		g.dispose();  
		
		
		DataBuffer dataBuffer = bimage.getRaster().getDataBuffer();
		
		
		Raster rast = bimage.getRaster();
		int largeur =bimage.getWidth();
    	int hauteur = bimage.getHeight();
    	
    	
    	// Création de la matrice représentatrice
		int[][] u = new int[hauteur][largeur];
		
		for (int i=0;i<hauteur;i++) {
			for (int j=0;j<largeur;j++) {
				//u[i][j] = dataBuffer.getElem(i * largeur + j);
				u[i][j] = rast.getSample(j,i,0)+1;
			}
		}
		
		//System.out.println(u[283][140]);
		//System.out.println(u[51][110]);
		//System.out.println(u[256][310]);
		//System.out.println(u[16][548]);
		
		
		
		
		 Relief2D relief = new Relief2D(u.length,u[0].length,u);
		 
		 
		
		 
		 // Paramètres du recuit
		double k = 1;
		int M = 50;
		double G0 = 2;
		int P = 20;
		double T = 0.7/P;
		int maxSteps = 2*(int) Math.pow(10,2);
		//FonctionLineaire Tparam = new FonctionLineaire(G0,0,maxSteps);
		FonctionExpoRecursive Tparam = new FonctionExpoRecursive(G0,0.,maxSteps,5);
	
		ConstanteKConstant Kparam = new ConstanteKConstant(k);
		//RecuitQuantiqueAccelere recuit = new RecuitQuantiqueAccelere(Tparam,Kparam, M, T);
		//RecuitQuantique recuit = new RecuitQuantique(Tparam,Kparam, M, T);
		
		// Avec fenetre Graphique
		int echantillonage = 10;
		ListEnergie ListValeursJr = new ListEnergie(echantillonage,1);
		ListEnergie ListRapport = new ListEnergie(echantillonage,1000);
		RecuitQuantique_Graphique recuit = new RecuitQuantique_Graphique(Tparam,Kparam, M, T);
		
		 //Initialisation probleme
		Distances Ec = new Distances();
		Hauteur Ep=new Hauteur();
		Mutation1Pixel mutation = new Mutation1Pixel();
		
		//initialisation avec image
		int seed=4;
		Position2DParticule probleme = new Position2DParticule(Ep,Ec,P,relief,mutation,new PanneauRepliques(image),echantillonage,seed);
		probleme.initialiser();
		
		FenetreRepliques fenetre = new FenetreRepliques(path,((Position2DParticule)probleme).getPanneau(),echantillonage);
		((Position2DParticule) probleme).setFenetre(fenetre); // Attention la fenetre fois bien être mise
		
		long startTime = System.nanoTime();
		//recuit.lancer(probleme);
		recuit.lancer(probleme,ListValeursJr,ListRapport);
		long endTime = System.nanoTime();
		
			
		for (int i=0;i<P;i++) {
		System.out.println("Energie du "+i+"ème : "+Ep.calculer(probleme.etats[i]));	
		}
		
		for (int i=0;i<P;i++) {
			System.out.println("Meilleure Energie du "+i+"ème : "+((Position2D)probleme.etats[i]).getMeilleureEnergie());	
			}
		
		for (int i=0;i<P;i++) {
			System.out.println("Position du "+i+"eme : x:="+((Position2D)probleme.etats[i]).x+" y:="+((Position2D)probleme.etats[i]).y);	
			}
		
		System.out.println("duree = "+(endTime-startTime)/1000000000+" s");
		
		
		 
	}

}
