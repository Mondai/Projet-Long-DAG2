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
			return new MutationElementairePixel(-1,0);
		} else if (random==1) {
			return new MutationElementairePixel(0,1);
		} else if (random==2) {
			return new MutationElementairePixel(1,0);
		} else  {
			return new MutationElementairePixel(0,-1);
		}
	}
	@Override
	public void faire(Probleme probleme, Etat etat, MutationElementaire mutation) {
		Position2D position2D = (Position2D) etat;
		MutationElementairePixel m = (MutationElementairePixel) mutation;
		
		position2D.setX(position2D.getX()+m.deltaX%position2D.relief.largeur);
		position2D.setY(position2D.getY()+m.deltaY%position2D.relief.hauteur);
		
		
		
	}

}
