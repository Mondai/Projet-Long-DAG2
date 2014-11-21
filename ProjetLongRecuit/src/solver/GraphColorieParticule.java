package solver;

import java.util.ArrayList;

import solverCommun.EnergieCinetique;
import solverCommun.EnergiePotentielle;
import solverCommun.Etat;
import solverCommun.IMutation;
import solverCommun.Probleme;

public class GraphColorieParticule extends Probleme {
	int k;
	int replique;
	Graphe graphe;
	
	public GraphColorieParticule(EnergiePotentielle Ep, IMutation mutation, EnergieCinetique Ec, int k, int replique, Graphe graphe, int seed) {
		
		this.Ec = Ec;
		this.k = k; 
		this.graphe = graphe;
		this.mutation = mutation;
		this.replique = replique;
		
		this.setSeed(seed);
		this.gen = new HighQualityRandom(seed);
		
		this.etats = new ArrayList<Etat>();
		for (int i = 0; i < this.replique; i++){
			this.etats.add( new GrapheColorie(Ep, k, graphe, this.gen.nextInt()));
		}
		
	}
	
	public GraphColorieParticule(EnergiePotentielle Ep, IMutation mutation, EnergieCinetique Ec, int k, int replique, Graphe graphe) {
		this(Ep, mutation, Ec, k, replique, graphe, new HighQualityRandom().nextInt()); 
	}
	
	// Initialisation de l'etat: affectation de couleurs aleatoires
	public void initialiserSansSeed(){
		EnergiePotentielle Ep = this.etats.get(0).Ep;
		
		this.etats = new ArrayList<Etat>();
		
		for (int i = 0; i < this.replique; i++){
			this.etats.add( new GrapheColorie(Ep, k, graphe, this.gen.nextInt()));
		}
		
	}
	
	public void initialiser(){
		this.initialiserSansSeed();
		this.setSeed(this.getSeed() + 1);
		this.gen = new HighQualityRandom(this.getSeed());
	}

	@Override
	public void sauvegarderSolution() {
		for (Etat grapheColorie : etats){
			( (GrapheColorie) grapheColorie).sauvegarderSolution();
		}
		
	}


}
