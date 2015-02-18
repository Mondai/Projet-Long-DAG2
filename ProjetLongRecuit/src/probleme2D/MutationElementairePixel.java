package probleme2D;

import solver.commun.MutationElementaire;

public class MutationElementairePixel extends MutationElementaire {

	int deltaX;
	int deltaY;
	
	

	public MutationElementairePixel(int deltaX, int deltaY) {
		super();
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}



	public int getDeltaX() {
		return deltaX;
	}



	public int getDeltaY() {
		return deltaY;
	}



	public void setDeltaX(int deltaX) {
		this.deltaX = deltaX;
	}



	public void setDeltaY(int deltaY) {
		this.deltaY = deltaY;
	}

	


}
