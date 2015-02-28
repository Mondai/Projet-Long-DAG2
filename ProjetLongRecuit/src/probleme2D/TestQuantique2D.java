package probleme2D;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;

import javax.swing.ImageIcon;

import GraphiqueProbleme2D.FenetreRepliques;
import GraphiqueProbleme2D.PanneauRepliques;
import solver.parametres.ConstanteKConstant;
import solver.parametres.FonctionExpoRecursive;
import solver.parametres.FonctionLineaire;
import solver.quantique.RecuitQuantique;
import solver.quantique.RecuitQuantiqueAccelere;

public class TestQuantique2D {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		
		String path = "src/images/grey-gradient-background180Flouénew.jpg";
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
				u[i][j] = rast.getSample(j,i,0);
			}
		}
		
		//System.out.println(u[283][140]);
		//System.out.println(u[51][110]);
		//System.out.println(u[256][310]);
		//System.out.println(u[16][548]);
		
		
		
		
		 Relief2D relief = new Relief2D(u.length,u[0].length,u);
		 
		 
		
		 
		 // Paramètres du recuit
		double k = 1;
		int M = 100;
		double G0 = 2;
		int P = 10;
		double T = 0.7/P;
		int maxSteps = (int) Math.pow(10,2);
		//FonctionLineaire Tparam = new FonctionLineaire(G0,0,maxSteps);
		FonctionExpoRecursive Tparam = new FonctionExpoRecursive(G0,0.,maxSteps,5);
	
		ConstanteKConstant Kparam = new ConstanteKConstant(k);
		//RecuitQuantiqueAccelere recuit = new RecuitQuantiqueAccelere(Tparam,Kparam, M, T);
		RecuitQuantique recuit = new RecuitQuantique(Tparam,Kparam, M, T);
		 
		 //Initialisation probleme
		Distances Ec = new Distances();
		Hauteur Ep=new Hauteur();
		Mutation1Pixel mutation = new Mutation1Pixel();
		
		//initialisation avec image
		int echantillonage = 50;
		Position2DParticule probleme= new Position2DParticule(Ep,Ec,P,relief,mutation,new PanneauRepliques(image),echantillonage);
		probleme.initialiser();
		
		FenetreRepliques fenetre = new FenetreRepliques(path,((Position2DParticule)probleme).getPanneau(),echantillonage);
		((Position2DParticule) probleme).setFenetre(fenetre); // Attention la fenetre fois bien être mise
		
		long startTime = System.nanoTime();
		recuit.lancer(probleme);
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
