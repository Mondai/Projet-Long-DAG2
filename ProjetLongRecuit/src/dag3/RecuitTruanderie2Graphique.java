package dag3;
import modele.*;
import parametrage.*;
import solver.graphique.ListEnergie;
import mutation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

// Cette classe definit le probleme du recuit. Il se charge d'effectuer les mutations elementaires, de calculer l'energie et de diminuer T...

/**
 * 
 * @author Pierre
 *
 */
public class RecuitTruanderie2Graphique extends JFrame
{
	public double solutionNumerique;
	private static final long serialVersionUID = 2L;

	static int nbTours = 1;
	static ParametreK K = new ParametreK(1);
	double meilleureEnergie;
	int mutationtentees=0;
	
	// Graphique
	
	ListEnergie listeMeilleureEnergie;
	ListEnergie listeValeursJr;
	ListEnergie listeRapport;
	ListEnergie listeGamma;
	ListEnergie listeprobaMoyenne;
	double rapportMoyen=0;
	double probaMoyenne;


	/**
	 * Methode qui calcule la probabilit� d'acceptation d'un �tat mut�. 
	 * Elle est utilis�e dans la methode solution qui effectue le recuit.
	 * @param deltaE
	 * Variation de H
	 * @param deltaEpot
	 * Variation de H potentiel
	 * @param temperature
	 * Temp�rature du recuit
	 * @return
	 * Probabilit� (entre 0 et 1) d'effectuer la mutation
	 * @throws IOException
	 */
	public static double probaAcceptation(double deltaE, double deltaEpot, Temperature temperature) throws IOException 
	{	if(deltaE<=0 || deltaEpot<0){
		return 1;
	}
	return Math.exp( (-deltaE) / (K.getK()*temperature.getValue()));

	}	
	
	
	/**
	 * C'est la m�thode qui effectue le recuit quantique. 
	 * @param p
	 * Probl�me construit par l'utilisateur
	 * @param m
	 * Une mutation al�atoire correspondant au probl�me trait�.
	 * Les g�n�rations de mutation al�atoires au fil du recuit s'effectueront avec la m�thode maj que l'utilisateur aura impl�ment� dans sa classe (voir IMutation)
	 * @param nombreIterations
	 * Nombre d'iterations pour chaque r�plique. Le nombre de r�pliques, on le rappelle, est d�fini dans Probl�me.
	 * @param seed
	 * Rentrer 1 en argument
	 * @param M
	 * Nombre de fois cons�cutives que l'on traite une r�plique. Rentrer 1 pour une utilisation normale
	 * @param sortie
	 * Fichier .txt dans lequel on stocke le r�sultat final du recuit.
	 * @return
	 * Solution du recuit : la meilleure �nergie rencontr�e par la particule
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public  double solution(Probleme p,IMutation m,int nombreIterations, int seed, int M) throws IOException, InterruptedException
	{	
		int nombreEtat=p.nombreEtat();
		//TODO
		//Probleme pBest = p.clone();
		//TODO
		double palierTEST = 1e7;
		
		ArrayList<Etat> e = p.getEtat();
		//System.out.println(e);
		Ponderation J = new Ponderation(p.getGamma());
		double Epot = p.calculerEnergiePotentielle();
		//System.out.println("Epot " + Epot);
		double compteurSpinique = p.calculerEnergieCinetique();
		//System.out.println("CompteurSpin " + compteurSpinique);
		double E = Epot-J.calcul(p.getT(), nombreEtat)*compteurSpinique;
		//System.out.println("E " + E);
		double deltapot  = 0;
		double energie = (e.get(0)).getEnergie();
		//System.out.println("En : " + energie);
		double energieBest = energie;
		
		int i = 0;
		int mutationsAcceptees = 0;
		int mutationsTentees = 0;
		
		while(i<nombreIterations && energieBest!=0){
			
			
			 E = Epot-J.calcul(p.getT(), nombreEtat)*compteurSpinique;
			 
			 double jG = J.calcul(p.getT(),nombreEtat);
			 
			 //TODO
			 int testEc = 0;
			 int A=0;
			 double DE = 0;
			 
			//System.out.println("Iter");
			 
			for(int j=0;j<nombreEtat;j++){// on effectue M  fois la mutation sur chaque r�plique avant de descendre gamma
				
				Etat r = e.get(j);
				
				for(int k=0; k<M; k++){
					//System.out.println(energieBest);
					mutationsTentees++;
				
					
					//System.out.println("E");
					
					m.maj(p, r);
					
					deltapot =  m.calculer(p,r);
					//System.out.println("deltapot : " + deltapot);
					
					double delta = deltapot/nombreEtat  - jG*p.differenceSpins(r,m);

					//TODO
					testEc += p.differenceSpins(r,m);
					DE+=delta;
					
					//test deltaE < 0
					/*
					if (deltapot < 0){
						mutationsAcceptees++;
						System.out.println("DE < 0 : " + r2);
						m.faire(p2,r2);
						compteurSpinique += p.differenceSpins(r2,m);
						
						e.set(j, r2);
						p.setEtat(e);
						
						Epot += deltapot/nombreEtat;
						//System.out.println("Epot "+Epot);
						E += delta;// L'energie courante est modifi�e
						//System.out.println("delta "+delta);
						energie += deltapot;
						//System.out.println("deltapot "+deltapot);
						//System.out.println("energie "+energie);
					}*/
			//		else{
						
						//VA REGARDER SI L'ON APPLIQUE LA MUTATION OU NON
						double pr=probaAcceptation(delta,deltapot,p.getT());
						if(pr>Math.random()){
							mutationsAcceptees++;
							//System.out.println("PA " + r2);
							//System.out.println("avant "+r.getEnergie());

							m.faire(p,r); //redonne comportement du recuit DAG3 --> tr�s bizarre
							
							//TODO
							A++;
							
							//TODO � supprimer
							//e.set(j, r);
							//p.setEtat(e);
							
							Epot += deltapot/nombreEtat;
							//System.out.println("Epot "+Epot);
							E += delta;// L'energie courante est modifi�e
							//System.out.println("delta "+delta);
							energie = r.getEnergie();
							//System.out.println("Dpot "+deltapot);
							//System.out.println("apres "+r.getEnergie());
							//System.out.println("deltapot "+deltapot);
							//System.out.println("energie "+energie);
							
						}
						if (energie < energieBest){
							this.setMeilleureEnergie(energieBest);
							//pBest.setEtat(e); //TODO
							energieBest = energie;
							System.out.println("meilleureEnergie = "+energieBest);
							System.out.println("mutationsTentees = "+ mutationsTentees);
							if(energieBest==0){	// condition de fin
								// nb mutations 
								System.out.println("Mutations tent�es : " + mutationsTentees);
								System.out.println("Mutations accept�es : " + mutationsAcceptees);
								System.out.println("Gfin : "+p.getGamma().getGamma());
								this.mutationtentees=mutationsTentees;
								return energieBest;
							}
							/*
							//TODO
							/*
							System.out.println();
							for (Etat etat : p.getEtat()){
								GrapheColorie g = (GrapheColorie) etat;
								System.out.println("Energie de l'�tat : " + Conflits.calculer(g));
								System.out.println("Nombre de noeuds en conflits : " + g.nombreNoeudsEnConflit());
								System.out.println("Nombre d'ar�tes en conflits : " + g.getNombreConflitsAretes());
							}

							System.out.println();
							*/

						}
				}
			}
			
			
			
			
			
			//UNE FOIS EFFECTUEE SUR tout les etat de la particule on descend gamma
			p.majgamma();
			J.setGamma(p.getGamma());
			Collections.shuffle(p.getEtat());
			
			i++;
			
			//TODO
			//System.out.println(testEc/((double)M*nombreEtat));
			//System.out.println("A : "+A);
			//System.out.println("DE : "+DE/((double)M*nombreEtat));
			
			//TEST TODO
			/*
			if(mutationsTentees>palierTEST){
				palierTEST+=1e7;
				System.out.println();
				System.out.println("mutationsTentees : "+mutationsTentees);
				System.out.println("J : "+J.calcul(p.getT(),nombreEtat));
				System.out.println("G : "+p.getGamma().getGamma());
				//TODO
				for (int i1=0;i1<nombreEtat;i1++){
					GrapheColorie g = (GrapheColorie) p.getEtat().get(i1);
					System.out.print("{"+g.nombreNoeudsEnConflit()+","+g.getNombreConflitsAretes()+"} ");
				}
				System.out.println();
			}
			*/
			
			
			
			
			
			
			
		}
		// nb mutations 
		System.out.println("Mutations tent�es : " + mutationsTentees);
		System.out.println("Mutations accept�es : " + mutationsAcceptees);
		this.mutationtentees=mutationsTentees;
		return energieBest;

	}
	
	
	public  double solutionGraphique(Probleme p,IMutation m,int nombreIterations, int seed, int M) throws IOException, InterruptedException
	{	
		this.probaMoyenne=0;
		int nombreEtat=p.nombreEtat();
		//TODO
		//Probleme pBest = p.clone();
		//TODO
		double palierTEST = 1e7;
		
		ArrayList<Etat> e = p.getEtat();
		//System.out.println(e);
		Ponderation J = new Ponderation(p.getGamma());
		double Epot = p.calculerEnergiePotentielle();
		//System.out.println("Epot " + Epot);
		double compteurSpinique = p.calculerEnergieCinetique();
		//System.out.println("CompteurSpin " + compteurSpinique);
		double E = Epot-J.calcul(p.getT(), nombreEtat)*compteurSpinique;
		//System.out.println("E " + E);
		double deltapot  = 0;
		double energie = (e.get(0)).getEnergie();
		//System.out.println("En : " + energie);
		double energieBest = energie;
		
		int i = 0;
		int mutationsAcceptees = 0;
		int mutationsTentees = 0;
		
		while(i<nombreIterations && energieBest!=0){
			
			
			 E = Epot-J.calcul(p.getT(), nombreEtat)*compteurSpinique;
			 
			 double jG = J.calcul(p.getT(),nombreEtat);
			 
			 //TODO
			 int testEc = 0;
			 int A=0;
			 double DE = 0;
			 
			//System.out.println("Iter");
			 
			for(int j=0;j<nombreEtat;j++){// on effectue M  fois la mutation sur chaque r�plique avant de descendre gamma
				
				Etat r = e.get(j);
				
				for(int k=0; k<M; k++){
					//System.out.println(energieBest);
					mutationsTentees++;
					
					//System.out.println("E");
					
					m.maj(p, r);
					
					deltapot =  m.calculer(p,r);
					//System.out.println("deltapot : " + deltapot);
					
					double delta = deltapot/nombreEtat  - jG*p.differenceSpins(r,m);

					//TODO
					testEc += p.differenceSpins(r,m);
					DE+=delta;
					
					//test deltaE < 0
					/*
					if (deltapot < 0){
						mutationsAcceptees++;
						System.out.println("DE < 0 : " + r2);
						m.faire(p2,r2);
						compteurSpinique += p.differenceSpins(r2,m);
						
						e.set(j, r2);
						p.setEtat(e);
						
						Epot += deltapot/nombreEtat;
						//System.out.println("Epot "+Epot);
						E += delta;// L'energie courante est modifi�e
						//System.out.println("delta "+delta);
						energie += deltapot;
						//System.out.println("deltapot "+deltapot);
						//System.out.println("energie "+energie);
					}*/
			//		else{
						
						//VA REGARDER SI L'ON APPLIQUE LA MUTATION OU NON
						double pr=probaAcceptation(delta,deltapot,p.getT());
						if(pr>Math.random()){
							mutationsAcceptees++;
							//System.out.println("PA " + r2);
							//System.out.println("avant "+r.getEnergie());

							m.faire(p,r); //redonne comportement du recuit DAG3 --> tr�s bizarre
							
							//TODO
							A++;
							
							//TODO � supprimer
							//e.set(j, r);
							//p.setEtat(e);
							
							Epot += deltapot/nombreEtat;
							//System.out.println("Epot "+Epot);
							E += delta;// L'energie courante est modifi�e
							//System.out.println("delta "+delta);
							energie = r.getEnergie();
							//System.out.println("Dpot "+deltapot);
							//System.out.println("apres "+r.getEnergie());
							//System.out.println("deltapot "+deltapot);
							//System.out.println("energie "+energie);
							
						}
						if (energie < energieBest){
							this.setMeilleureEnergie(energieBest);
							//pBest.setEtat(e); //TODO
							energieBest = energie;
							System.out.println("meilleureEnergie = "+energieBest);
							System.out.println("mutationsTentees = "+ mutationsTentees);
							if(energieBest==0){	// condition de fin
								// nb mutations 
								System.out.println("Mutations tent�es : " + mutationsTentees);
								System.out.println("Mutations accept�es : " + mutationsAcceptees);
								System.out.println("Gfin : "+p.getGamma().getGamma());
								this.mutationtentees=mutationsTentees;
								return energieBest;
							}
							/*
							//TODO
							/*
							System.out.println();
							for (Etat etat : p.getEtat()){
								GrapheColorie g = (GrapheColorie) etat;
								System.out.println("Energie de l'�tat : " + Conflits.calculer(g));
								System.out.println("Nombre de noeuds en conflits : " + g.nombreNoeudsEnConflit());
								System.out.println("Nombre d'ar�tes en conflits : " + g.getNombreConflitsAretes());
							}

							System.out.println();
							*/

						}
						
						if (j==0) {
						listeMeilleureEnergie.add(energieBest);
						listeValeursJr.add(jG);
						listeGamma.add(p.getGamma().getGamma());
						
						double rapport = ((jG*p.differenceSpins(r,m)/deltapot/nombreEtat));
						listeRapport.addTotal(rapport);
						calculerRapportMoyen(rapport, listeRapport);
						listeRapport.add(rapportMoyen);
						
						listeprobaMoyenne.addTotal(pr);
						calculerRapportMoyen(pr, listeprobaMoyenne);
						listeprobaMoyenne.add(probaMoyenne);
						
						
						}
						
				}
			}
			//UNE FOIS EFFECTUEE SUR tout les etat de la particule on descend gamma
			p.majgamma();
			J.setGamma(p.getGamma());
			Collections.shuffle(p.getEtat());
			
			i++;
			
			
			
			//TODO
			//System.out.println(testEc/((double)M*nombreEtat));
			//System.out.println("A : "+A);
			//System.out.println("DE : "+DE/((double)M*nombreEtat));
			
			//TEST TODO
			/*
			if(mutationsTentees>palierTEST){
				palierTEST+=1e7;
				System.out.println();
				System.out.println("mutationsTentees : "+mutationsTentees);
				System.out.println("J : "+J.calcul(p.getT(),nombreEtat));
				System.out.println("G : "+p.getGamma().getGamma());
				//TODO
				for (int i1=0;i1<nombreEtat;i1++){
					GrapheColorie g = (GrapheColorie) p.getEtat().get(i1);
					System.out.print("{"+g.nombreNoeudsEnConflit()+","+g.getNombreConflitsAretes()+"} ");
				}
				System.out.println();
			}
			*/
		}
		// nb mutations 
		System.out.println("Mutations tent�es : " + mutationsTentees);
		System.out.println("Mutations accept�es : " + mutationsAcceptees);
		this.mutationtentees=mutationsTentees;
		return energieBest;

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
	
	

	public double getMeilleureEnergie() {
		return meilleureEnergie;
	}


	public void setMeilleureEnergie(double meilleureEnergie) {
		this.meilleureEnergie = meilleureEnergie;
	}


	public int getMutationtentees() {
		return mutationtentees;
	}


	public ListEnergie getListeMeilleureEnergie() {
		return listeMeilleureEnergie;
	}


	public ListEnergie getListeValeursJr() {
		return listeValeursJr;
	}


	public ListEnergie getListeRapport() {
		return listeRapport;
	}


	public ListEnergie getListeGamma() {
		return listeGamma;
	}


	public ListEnergie getListeprobaMoyenne() {
		return listeprobaMoyenne;
	}


	public void setListeMeilleureEnergie(ListEnergie listeMeilleureEnergie) {
		this.listeMeilleureEnergie = listeMeilleureEnergie;
	}


	public void setListeValeursJr(ListEnergie listeValeursJr) {
		this.listeValeursJr = listeValeursJr;
	}


	public void setListeRapport(ListEnergie listeRapport) {
		this.listeRapport = listeRapport;
	}


	public void setListeGamma(ListEnergie listeGamma) {
		this.listeGamma = listeGamma;
	}


	public void setListeprobaMoyenne(ListEnergie listeprobaMoyenne) {
		this.listeprobaMoyenne = listeprobaMoyenne;
	}
	
	
	
}
