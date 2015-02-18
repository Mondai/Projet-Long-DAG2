package probleme2D;

import solver.commun.EnergiePotentielle;
import solver.commun.Etat;
import solver.commun.HighQualityRandom;

public class Position2D extends Etat {

		Relief2D relief;
		int x;
		int y;
		int meilleurX;
		int meilleurY;
		HighQualityRandom gen = new HighQualityRandom(getSeed());
		private int seed = new HighQualityRandom().nextInt();
		public double meilleureEnergie;
		
		
		
		
		public HighQualityRandom getGen() {
			return gen;
		}


		public int getSeed() {
			return seed;
		}


		public void setGen(HighQualityRandom gen) {
			this.gen = gen;
		}


		public void setSeed(int seed) {
			this.seed = seed;
		}


		public Position2D(EnergiePotentielle Ep, Relief2D relief,int seed, int x, int y, int meilleurX,int meilleurY) {
			this.Ep=Ep;
			this.relief = relief;
			this.x = x;
			this.y = y;
			this.meilleurX = meilleurX;
			this.meilleurY = meilleurY;
			this.seed=seed;
			this.meilleureEnergie=1000;
		}
		
		
		public double getMeilleureEnergie() {
			return meilleureEnergie;
		}


		public void setMeilleureEnergie(double meilleureEnergie) {
			this.meilleureEnergie = meilleureEnergie;
		}


		public void initialiserSansSeed() {
			HighQualityRandom randomizer = new HighQualityRandom();
			int x = randomizer.nextInt(relief.getHauteur());
			this.setMeilleurX(x);
			this.setX(x);
			System.out.println("x d'initialisation"+x);
			
			int y = randomizer.nextInt(relief.getLargeur());
			this.setMeilleurY(relief.getLargeur());
			this.setY(y);
		}
		
		public void initialiser() {
			this.initialiserSansSeed();
			this.gen = new HighQualityRandom(this.getSeed());
		}
		
		
		
		
		


		public void sauvegarderSolution(){
			this.meilleurX=x;
			this.meilleurY=y;
		}
		
		public void sauverMeilleureEnergie(double E){
			this.meilleureEnergie=E;
		}


		public int getX() {
			return x;
		}


		public int getY() {
			return y;
		}


		public int getMeilleurX() {
			return meilleurX;
		}


		public int getMeilleurY() {
			return meilleurY;
		}


		public void setX(int x) {
			this.x = x;
		}


		public void setY(int y) {
			this.y = y;
		}


		public Relief2D getRelief() {
			return relief;
		}


		public void setRelief(Relief2D relief) {
			this.relief = relief;
		}


		public void setMeilleurX(int meilleurX) {
			this.meilleurX = meilleurX;
		}


		public void setMeilleurY(int meilleurY) {
			this.meilleurY = meilleurY;
		}
			
		
		
		
		
		}


