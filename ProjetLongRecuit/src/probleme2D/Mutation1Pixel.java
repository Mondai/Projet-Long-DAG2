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
	
	
	@Override
	public MutationElementaire getMutationElementaire(Probleme probleme,
			Etat etat) {
		HighQualityRandom randomizer = new HighQualityRandom();
		int random = randomizer.nextInt(4);
		
		if (random==0) {
			return new MutationElementairePixel(-4,0);
		} else if (random==1) {
			return new MutationElementairePixel(0,4);
		} else if (random==2) {
			return new MutationElementairePixel(4,0);
		} else  {
			return new MutationElementairePixel(0,-4);
		}
	}
	@Override
	public void faire(Probleme probleme, Etat etat, MutationElementaire mutation) {
		Position2D position2D = (Position2D) etat;
		MutationElementairePixel m = (MutationElementairePixel) mutation;
		
		
		int x=position2D.getX()+m.deltaX;
		
		int largeur=position2D.relief.largeur;
		position2D.setX((((x % (largeur-10)) + largeur-10) % (largeur-10)));
		//System.out.println("largeur "+largeur);
		int y=position2D.getY()+m.deltaY;
		
		
		int hauteur=position2D.relief.hauteur;
		position2D.setY((((y % (hauteur-10)) + hauteur-10) % (hauteur-10)));
		//System.out.println("hauteur "+hauteur);
		
		
		
		((Position2DParticule) probleme).ModificationGraphique(x, y, ((Position2D)etat).getNumero());
		
		//System.out.println(" x et y :"+position2D.getX()+ " "+position2D.getY());
		((Position2DParticule) probleme).getFenetre().updateGraphics();
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
