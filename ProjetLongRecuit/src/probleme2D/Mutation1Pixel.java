package probleme2D;

import solver.commun.Etat;
import solver.commun.HighQualityRandom;
import solver.commun.IMutation;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;
import vertexColoring.GrapheColorie;
import vertexColoring.MutationElementaireNoeud;

public class Mutation1Pixel implements IMutation{

	
	String direction;
	static final int Diametre = 10;
	static final int bordure = 50;
	
	
	
	@Override
	public MutationElementaire getMutationElementaire(Probleme probleme,
			Etat etat) {
		HighQualityRandom randomizer = ((Position2D)etat).getGen();
		int random = randomizer.nextInt(4);
		int distance = 1;
		//int distance = randomizer.nextInt(10);
		if (random==0) {
			return new MutationElementairePixel(-distance,0);
		} else if (random==1) {
			return new MutationElementairePixel(0,distance);
		} else if (random==2) {
			return new MutationElementairePixel(distance,0);
		} else if (random==3) {
			return new MutationElementairePixel(0,-distance);
		} else {
			return new MutationElementairePixel(0,0);
		}
	}
	@Override
	public void faire(Probleme probleme, Etat etat, MutationElementaire mutation) {
		Position2D position2D = (Position2D) etat;
		MutationElementairePixel m = (MutationElementairePixel) mutation;
		
		int largeur=position2D.relief.largeur;
		int hauteur=position2D.relief.hauteur;
		
		int x=position2D.getX()+m.deltaX;
		
		x=Math.max(bordure, x);
		x=Math.min(largeur-Diametre-bordure, x);
		
		
		position2D.setX(x);
		//System.out.println("largeur "+largeur);
		int y=position2D.getY()+m.deltaY;
		
		y=Math.max(bordure, y);
		y=Math.min(hauteur-Diametre-bordure, y);
		
	
		position2D.setY(y);
		//System.out.println("hauteur "+hauteur);
		
		
		int numero = ((Position2D)etat).getNumero();
		((Position2DParticule) probleme).ModificationGraphique(x, y, ((Position2D)etat).getNumero());
		
		//System.out.println(" x et y :"+position2D.getX()+ " "+position2D.getY());
		((Position2DParticule) probleme).getFenetre().updateGraphics(numero);
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
