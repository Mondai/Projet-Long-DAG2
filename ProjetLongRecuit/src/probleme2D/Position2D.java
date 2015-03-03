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
		private int seed;
		//HighQualityRandom gen = new HighQualityRandom(getSeed());
		HighQualityRandom gen;
		public double meilleureEnergie;
		int numero;
		
		
		
		
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


		public Position2D(EnergiePotentielle Ep, Relief2D relief,int seed, int x, int y, int meilleurX,int meilleurY,int i) {
			this.Ep=Ep;
			this.relief = relief;
			this.x = x;
			this.y = y;
			this.meilleurX = meilleurX;
			this.meilleurY = meilleurY;
			this.seed=seed;
			this.gen = new HighQualityRandom(seed);
			
			this.meilleureEnergie=1000;
			this.numero=i;
		}
		
		
		public double getMeilleureEnergie() {
			return meilleureEnergie;
		}


		public void setMeilleureEnergie(double meilleureEnergie) {
			this.meilleureEnergie = meilleureEnergie;
		}

		public int getNumero() {
			return numero;
		}


		public void setNumero(int numero) {
			this.numero = numero;
		}


		public void initialiserSansSeed() {
			HighQualityRandom randomizer = new HighQualityRandom();
			int x = randomizer.nextInt(relief.getLargeur());
			this.setMeilleurX(x);
			this.setX(x);
			System.out.println("x d'initialisation"+x);
			
			int y = randomizer.nextInt(relief.getHauteur());
			this.setMeilleurY(relief.getLargeur());
			this.setY(y);
			System.out.println("y d'initialisation"+y);
		}
		
		public void initialiser() {
			int x = this.gen.nextInt(relief.getLargeur());
			this.setMeilleurX(x);
			this.setX(x);
			System.out.println("x d'initialisation"+x);
			
			int y = this.gen.nextInt(relief.getHauteur());
			this.setMeilleurY(relief.getLargeur());
			this.setY(y);
			System.out.println("y d'initialisation"+y);
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


