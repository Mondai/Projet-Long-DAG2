// classe implementant l'energie dans le probleme du coloriage

import java.util.Iterator;


public class Conflits implements IEnergie {

	public double calculer(Probleme probleme) {
		
		Coloriage coloriage = (Coloriage)	probleme;
		double conflits = 0;
		
		for (int j = 1; j <= coloriage.graphe.nombreNoeuds; j++) {
			conflits++;
		}
		
		conflits /=2;
		
		return conflits;

	}

}
