package solver.quantique;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;

import solver.commun.Etat;
import solver.commun.IRecuit;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;
import solver.graphique.ListEnergie;
import solver.parametres.ConstanteK;
import solver.parametres.Fonction;

public class RecuitQuantique_Graphique implements IRecuit { 

	// creer vos propres Temperature, ConstanteK et trucs pour les graphes
	public Fonction Gamma;
	public ConstanteK K;
	public double meilleureEnergie = Double.MAX_VALUE;									// en soit, nos energies pourraient etre des Int, mais bon 
	public double temperature;
	public int nbMaxIteration; 							// nombre maximale d'iteration si la solution n'est pas trouvee, redondance avec t.nbIteration
	public int palier;
	public double rapportMoyen;
	// abstract void init(); 				        	// initialisation // mais de quoi ?

	public RecuitQuantique_Graphique(Fonction Gamma, ConstanteK K, int palier, double temperature) {
		this.Gamma=Gamma;												// contructeur : on lui donne la facon de calculer l'energie, K et tout le blabla
		this.K=K;												// en creant une classe dedie et reutilisable qui extends temperature
		this.nbMaxIteration=this.Gamma.nbIteration;						// ainsi on combine le tout facilement
		this.palier = palier;		
		this.temperature = temperature;		
		this.rapportMoyen=0;
		//en quantique, la température est constante et le Gamma est variable, d'où le fait que Gamma soit une "température"
	}





	public void lancer(Probleme probleme) { return;};



	public void lancer(Probleme probleme, ListEnergie[] listeEnergie, ListEnergie[] listeProba, ListEnergie listeMeilleureEnergie, ListEnergie listeValeursJr,ListEnergie listeRapport) {
		
		// Fenetre
		FenetreAffichageQuantique fenetre = new FenetreAffichageQuantique();
		fenetre.setVisible(true);
		fenetre.setTitle("Valeurs Jr, Rapport");
		PanneauAffichageQuantique pan = new PanneauAffichageQuantique();
		
		
		int nombreRepliques = probleme.etats.length;
		double[] vectProbaPas1= new double[nombreRepliques];
		
		for (int j=0;j<nombreRepliques;j++) {
			vectProbaPas1[j]=0;
		}

		Etat etat = probleme.etats[0];
		Etat previous = probleme.etats[nombreRepliques-1];
		Etat next = probleme.etats[1];
		for (int i = 0; i < nombreRepliques; i++){	// initialisation de meilleureEnergie
			double energie = probleme.etats[i].Ep.calculer(probleme.etats[i]) ;
			if (energie < this.meilleureEnergie){
				this.meilleureEnergie = energie ;
			}
		}

		double proba = 1;

		// tableau des indices des etats a parcourir dans un certain ordre
		ArrayList<Integer> indiceEtats = new ArrayList<Integer>(); 
		for( int i = 0; i < nombreRepliques ; i++){
			indiceEtats.add(i);
		}

		while(Gamma.modifierT() && this.meilleureEnergie!=0){
			
			Collections.shuffle(indiceEtats, probleme.gen);	// melanger l'ordre de parcours des indices
			double Jr = -this.temperature/2*Math.log(Math.tanh(this.Gamma.t/nombreRepliques/this.temperature));	// calcul de Jr pour ce palier
		
			for (Integer p : indiceEtats){

				etat = probleme.etats[p];
				if(p == 0){
					previous = probleme.etats[nombreRepliques-1];
				}
				else{
					previous = probleme.etats[p-1];
				}

				if (p == nombreRepliques - 1){
					next = probleme.etats[0];
				}
				else{
					next = probleme.etats[p+1];
				}

				for (int j = 0; j < this.palier; j++){


					MutationElementaire mutation = probleme.getMutationElementaire(etat);	// trouver une mutation possible
					double deltaEp = probleme.calculerDeltaEp(etat, mutation);	// calculer deltaEp si la mutation etait acceptee
					double deltaEc = probleme.calculerDeltaEc(etat, previous, next, mutation);  // calculer deltaIEc si la mutation etait acceptee


					//différences du hamiltonien total
					//multiplier deltaIEc par JGamma
					double deltaE = deltaEp/nombreRepliques - deltaEc*Jr;


					// On descend en énergie
					if( deltaE <= 0 || deltaEp <= 0){
						probleme.modifElem(etat, mutation);				// faire la mutation
						proba = 1;


						// On teste la proba
					} else {
						proba = Math.exp(-deltaE / (this.K.k * this.temperature));	// calcul de la proba
						if (proba >= probleme.gen.nextDouble()) {							
							probleme.modifElem(etat, mutation);  		// accepter la mutation 
						}
					}
					double EpActuelle = etat.Ep.calculer(etat);		// energie potentielle temporelle

					// On regarde les énergies obtenues
					if( EpActuelle < this.meilleureEnergie ){		// mettre a jour la meilleur energie
						this.meilleureEnergie = EpActuelle;
						if (this.meilleureEnergie == 0){	// fin du programme
							listeMeilleureEnergie.getlistEnergie().add(0.);
							listeEnergie[p].getlistEnergie().add(0.);
							listeValeursJr.add(Jr);
							listeRapport.add(this.rapportMoyen);
							
							System.out.println("fin par ce cooté la");
							return;
						}
					}


					// Ajout dans les listes 
					
					if (deltaEp!=0 && p==1) {
						double rapport = Math.abs(deltaEc*(-Jr))/(deltaEp/nombreRepliques);
						listeRapport.addTotal(rapport);
						calculerRapportMoyen(rapport, listeRapport);
						listeRapport.add(this.rapportMoyen);
						}
					
					
					if (p==1) {
						listeValeursJr.add(Jr);
					}
					
					
					if (proba!=1) {
						listeProba[p].add(proba);
						vectProbaPas1[p]=proba;
					} else {
						listeProba[p].add(vectProbaPas1[p]);
					}
					
					
					listeEnergie[p].add(EpActuelle);

					if ( p==1 || EpActuelle==0.) {
						if( EpActuelle > this.meilleureEnergie ){
							listeMeilleureEnergie.add(this.meilleureEnergie);
						} else {
							listeMeilleureEnergie.add(EpActuelle);
						}
					}
				} // arrivée au palier
			} // fin d'un tour sur les répliques
		} // fin du while du recuit
		return;
	}
	
	
	
public void lancer(Probleme probleme, ListEnergie listeValeursJr,ListEnergie listeRapport) {
		
		// Graphiques
		PanneauAffichageQuantique pan = new PanneauAffichageQuantique();
		FenetreAffichageQuantique fenetre = new FenetreAffichageQuantique(pan);
		fenetre.setVisible(true);
		fenetre.setTitle("Valeurs Jr, Rapport");

		
		
		int nombreRepliques = probleme.etats.length;
		double[] vectProbaPas1= new double[nombreRepliques];
		
		for (int j=0;j<nombreRepliques;j++) {
			vectProbaPas1[j]=0;
		}

		Etat etat = probleme.etats[0];
		Etat previous = probleme.etats[nombreRepliques-1];
		Etat next = probleme.etats[1];
		for (int i = 0; i < nombreRepliques; i++){	// initialisation de meilleureEnergie
			double energie = probleme.etats[i].Ep.calculer(probleme.etats[i]) ;
			if (energie < this.meilleureEnergie){
				this.meilleureEnergie = energie ;
			}
		}

		double proba = 1;

		// tableau des indices des etats a parcourir dans un certain ordre
		ArrayList<Integer> indiceEtats = new ArrayList<Integer>(); 
		for( int i = 0; i < nombreRepliques ; i++){
			indiceEtats.add(i);
		}

		while(Gamma.modifierT() && this.meilleureEnergie!=0){
			
			Collections.shuffle(indiceEtats, probleme.gen);	// melanger l'ordre de parcours des indices
			double Jr = -this.temperature/2*Math.log(Math.tanh(this.Gamma.t/nombreRepliques/this.temperature));	// calcul de Jr pour ce palier
			
			for (Integer p : indiceEtats){
				
				etat = probleme.etats[p];
				if(p == 0){
					previous = probleme.etats[nombreRepliques-1];
				}
				else{
					previous = probleme.etats[p-1];
				}

				if (p == nombreRepliques - 1){
					next = probleme.etats[0];
				}
				else{
					next = probleme.etats[p+1];
				}

				fenetre.updateGraphics(this.rapportMoyen,Jr, this.meilleureEnergie );
				for (int j = 0; j < this.palier; j++){
					

					MutationElementaire mutation = probleme.getMutationElementaire(etat);	// trouver une mutation possible
					double deltaEp = probleme.calculerDeltaEp(etat, mutation);	// calculer deltaEp si la mutation etait acceptee
					double deltaEc = probleme.calculerDeltaEc(etat, previous, next, mutation);  // calculer deltaIEc si la mutation etait acceptee


					//différences du hamiltonien total
					//multiplier deltaIEc par JGamma
					double deltaE = deltaEp/nombreRepliques - deltaEc*Jr;


					// On descend en énergie
					if( deltaE <= 0) { //|| deltaEp <= 0){
						probleme.modifElem(etat, mutation);				// faire la mutation
						proba = 1;


						// On teste la proba
					} else {
						proba = Math.exp(-deltaE / (this.K.k * this.temperature));	// calcul de la proba
						if (proba >= probleme.gen.nextDouble()) {							
							probleme.modifElem(etat, mutation);  		// accepter la mutation 
						}
					}
					double EpActuelle = etat.Ep.calculer(etat);		// energie potentielle temporelle

					// On regarde les énergies obtenues
					if( EpActuelle < this.meilleureEnergie ){		// mettre a jour la meilleur energie
						this.meilleureEnergie = EpActuelle;
						if (this.meilleureEnergie == 0){	// fin du programme
					
							listeValeursJr.add(Jr);
							listeRapport.add(this.rapportMoyen);
							
							System.out.println("fin par ce cooté la");
							return;
						}
					}


					// Ajout dans les listes 
					
					if (deltaEp!=0 && p==1) {
						double rapport = Math.abs(deltaEc*(-Jr))/(deltaEp/nombreRepliques);
						listeRapport.addTotal(rapport);
						calculerRapportMoyen(rapport, listeRapport);
						listeRapport.add(this.rapportMoyen);
						}
					
					
					if (p==1) {
						listeValeursJr.add(Jr);
					}
					
					

				} // arrivée au palier
			} // fin d'un tour sur les répliques
		} // fin du while du recuit
		return;
	}
	
	
	
	
	public void lancer(Probleme probleme,ListEnergie listeMeilleureEnergie) {

		int nombreRepliques = probleme.etats.length;
		double[] vectProbaPas1= new double[nombreRepliques];
		
		for (int j=0;j<nombreRepliques;j++) {
			vectProbaPas1[j]=0;
		}

		Etat etat = probleme.etats[0];
		Etat previous = probleme.etats[nombreRepliques-1];
		Etat next = probleme.etats[1];
		for (int i = 0; i < nombreRepliques; i++){	// initialisation de meilleureEnergie
			double energie = probleme.etats[i].Ep.calculer(probleme.etats[i]) ;
			if (energie < this.meilleureEnergie){
				this.meilleureEnergie = energie ;
			}
		}

		double proba = 1;

		// tableau des indices des etats a parcourir dans un certain ordre
		ArrayList<Integer> indiceEtats = new ArrayList<Integer>(); 
		for( int i = 0; i < nombreRepliques ; i++){
			indiceEtats.add(i);
		}

		while(Gamma.modifierT() && this.meilleureEnergie!=0){
			Collections.shuffle(indiceEtats, probleme.gen);	// melanger l'ordre de parcours des indices
			double Jr = -this.temperature/2*Math.log(Math.tanh(this.Gamma.t/nombreRepliques/this.temperature));	// calcul de Jr pour ce palier
		
			for (Integer p : indiceEtats){

				etat = probleme.etats[p];
				if(p == 0){
					previous = probleme.etats[nombreRepliques-1];
				}
				else{
					previous = probleme.etats[p-1];
				}

				if (p == nombreRepliques - 1){
					next = probleme.etats[0];
				}
				else{
					next = probleme.etats[p+1];
				}

				for (int j = 0; j < this.palier; j++){


					MutationElementaire mutation = probleme.getMutationElementaire(etat);	// trouver une mutation possible
					double deltaEp = probleme.calculerDeltaEp(etat, mutation);	// calculer deltaEp si la mutation etait acceptee
					double deltaEc = probleme.calculerDeltaEc(etat, previous, next, mutation);  // calculer deltaIEc si la mutation etait acceptee


					//différences du hamiltonien total
					//multiplier deltaIEc par JGamma
					double deltaE = deltaEp/nombreRepliques - deltaEc*Jr;


					// On descend en énergie
					if( deltaE <= 0 || deltaEp <= 0){
						probleme.modifElem(etat, mutation);				// faire la mutation
						proba = 1;


						// On teste la proba
					} else {
						proba = Math.exp(-deltaE / (this.K.k * this.temperature));	// calcul de la proba
						if (proba >= probleme.gen.nextDouble()) {							
							probleme.modifElem(etat, mutation);  		// accepter la mutation 
						}
					}
					double EpActuelle = etat.Ep.calculer(etat);		// energie potentielle temporelle

					// On regarde les énergies obtenues
					if( EpActuelle < this.meilleureEnergie ){		// mettre a jour la meilleur energie
						this.meilleureEnergie = EpActuelle;
						if (this.meilleureEnergie == 0){	// fin du programme
							listeMeilleureEnergie.getlistEnergie().add(0.);
							System.out.println("fin par ce cooté la");
							return;
						}
					}


					// Ajout dans les listes 

					
					
						if( EpActuelle > this.meilleureEnergie ){
							listeMeilleureEnergie.add(this.meilleureEnergie);
						} else {
							listeMeilleureEnergie.add(EpActuelle);
						}
					
				} // arrivée au palier
			} // fin d'un tour sur les répliques
		} // fin du while du recuit
		return;
	}
	
	
	public void calculerRapportMoyen(double proba, ListEnergie listProba){
		int taille = listProba.getTaille();
		ArrayList<Double> list = listProba.getlistEnergieTotale();
		int tailleL = list.size();
		int fenetreK = listProba.getFenetreK();
		//System.out.println(list);
		
		
		if (taille == 1){
			this.rapportMoyen = proba;
		} else if (taille <= fenetreK ){
			this.rapportMoyen = (this.rapportMoyen*(tailleL-1)+ proba) /tailleL;  // moyenne des probas
		} else{
			// Moyenne des probas
			this.rapportMoyen = (this.rapportMoyen*fenetreK - list.get(0) + proba )/ fenetreK;
		}
		// System.out.println("Proba Moyenne : " + this.probaMoyenne); TEST
	}
	
	
	
	
}




