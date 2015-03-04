package probleme2D;

import GraphiqueProbleme2D.FenetreRepliques;
import GraphiqueProbleme2D.PanneauRepliques;
import solver.commun.EnergieCinetique;

import solver.commun.EnergiePotentielle;
import solver.commun.Etat;
import solver.commun.HighQualityRandom;
import solver.commun.IMutation;
import solver.commun.Probleme;


public class Position2DParticule extends Probleme{

	int replique;
	Relief2D relief;
	PanneauRepliques panneau;
	FenetreRepliques fenetre;
	
	

	public Position2DParticule(EnergiePotentielle Ep, EnergieCinetique Ec, int replique, Relief2D relief,IMutation mutation,PanneauRepliques panneau, int echantillonage) {
		this.Ec=Ec;
		this.replique = replique;
		this.relief = relief;
		this.mutation = mutation;
		
		this.etats = new Etat[replique];
		for (int i = 0; i < this.replique; i++){
			this.etats[i] = new Position2D(Ep,relief, this.gen.nextInt(),0,0,0,0,i);
		}
		this.panneau=panneau;
		this.fenetre=new FenetreRepliques(echantillonage);
		
		
	}
	
	
	
	public Position2DParticule(EnergiePotentielle Ep, EnergieCinetique Ec, int replique, Relief2D relief,IMutation mutation,PanneauRepliques panneau, int echantillonage,int seed) {
		this.setSeed(seed);
		this.gen = new HighQualityRandom(seed);
		this.Ec=Ec;
		this.replique = replique;
		this.relief = relief;
		this.mutation = mutation;
		
		this.etats = new Etat[replique];
		for (int i = 0; i < this.replique; i++){
			this.etats[i] = new Position2D(Ep,relief, seed,0,0,0,0,i);
		}
		this.panneau=panneau;
		this.fenetre=new FenetreRepliques(echantillonage);
		
		
		
	}
	
	


	public void ModificationGraphique (int X, int Y, int numeroReplique) {
		this.panneau.deltaSetDesX( X, numeroReplique);
		this.panneau.deltaSetDesY( Y, numeroReplique);
	}
	
	
	
	
	
	
	public void initialiser() {
		
		EnergiePotentielle Ep = this.etats[0].Ep;
		this.etats = new Etat[replique];
		
		int[] SetDesXStarter= new int[replique];
		int[] SetDesYStarter= new int[replique];
		
			for (int i = 0; i < this.replique; i++){
				int futureSeed = this.gen.nextInt();
				Position2D etat =  new Position2D(Ep, relief, futureSeed,0,0,0,0,i);
				System.out.println(i+"ème générateur :"+futureSeed);
				etat.initialiser();
				this.etats[i] = etat;
				SetDesXStarter[i]=etat.x;
				SetDesYStarter[i]=etat.y;
			}
			
			this.panneau.setSetDesX(SetDesXStarter);
			this.panneau.setSetDesY(SetDesYStarter);
		}
	

	
	public void sauvegarderSolution() {
		for (Etat position2D : etats){
			( (Position2D) position2D).sauvegarderSolution();
		}
		
	}


	public PanneauRepliques getPanneau() {
		return panneau;
	}


	public FenetreRepliques getFenetre() {
		return fenetre;
	}


	public void setPanneau(PanneauRepliques panneau) {
		this.panneau = panneau;
	}


	public void setFenetre(FenetreRepliques fenetre) {
		this.fenetre = fenetre;
	}


	
	

}
