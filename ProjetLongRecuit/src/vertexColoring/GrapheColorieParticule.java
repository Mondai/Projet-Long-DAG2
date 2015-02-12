package vertexColoring;

import solver.commun.EnergieCinetique;
import solver.commun.EnergiePotentielle;
import solver.commun.Etat;
import solver.commun.HighQualityRandom;
import solver.commun.IMutation;
import solver.commun.Probleme;

public class GrapheColorieParticule extends Probleme {
	int k;
	int replique;
	Graphe graphe;
	
	public GrapheColorieParticule(EnergiePotentielle Ep, IMutation mutation, EnergieCinetique Ec, int k, int replique, Graphe graphe, int seed) {
		
		this.Ec = Ec;
		this.k = k; 
		this.graphe = graphe;
		this.mutation = mutation;
		this.replique = replique;
		
		this.setSeed(seed);
		this.gen = new HighQualityRandom(seed);
		
		this.etats = new Etat[replique];
		for (int i = 0; i < this.replique; i++){
			this.etats[i] = new GrapheColorie(Ep, k, graphe, this.gen.nextInt());
		}
		
	}
	
	public GrapheColorieParticule(EnergiePotentielle Ep, IMutation mutation, EnergieCinetique Ec, int k, int replique, Graphe graphe) {
		this(Ep, mutation, Ec, k, replique, graphe, new HighQualityRandom().nextInt()); 
	}
	
	// Initialisation de l'etat: affectation de couleurs aleatoires
	public void initialiserSansSeed(){
		EnergiePotentielle Ep = this.etats[0].Ep;
		
		this.etats = new Etat[replique];
		
		for (int i = 0; i < this.replique; i++){
			GrapheColorie etat =  new GrapheColorie(Ep, k, graphe, this.gen.nextInt());
			etat.initialiser();
			this.etats[i] = etat;
		}
		
	}
	
	public void initialiser(){
		this.setSeed(this.getSeed() + 100);
		this.gen = new HighQualityRandom(this.getSeed());
		this.initialiserSansSeed();
	}

	@Override
	public void sauvegarderSolution() {
		for (Etat grapheColorie : etats){
			( (GrapheColorie) grapheColorie).sauvegarderSolution();
		}
		
	}
	
	public Etat[] getEtats(){
		return this.etats;
	}
	
	public int getAdjacenceMax(){
		return this.graphe.getAdjacenceMax();
	}

}
