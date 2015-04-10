package dag3;
import modele.*;
import parametrage.*;
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
public class RecuitTruanderie2 extends JFrame
{
	public double solutionNumerique;
	private static final long serialVersionUID = 2L;

	static int nbTours = 1;
	static ParametreK K = new ParametreK(1);



	/**
	 * Methode qui calcule la probabilité d'acceptation d'un état muté. 
	 * Elle est utilisée dans la methode solution qui effectue le recuit.
	 * @param deltaE
	 * Variation de H
	 * @param deltaEpot
	 * Variation de H potentiel
	 * @param temperature
	 * Température du recuit
	 * @return
	 * Probabilité (entre 0 et 1) d'effectuer la mutation
	 * @throws IOException
	 */
	public static double probaAcceptation(double deltaE, double deltaEpot, Temperature temperature) throws IOException 
	{	if(deltaE<=0 || deltaEpot<0){
		return 1;
	}
	return Math.exp( (-deltaE) / (K.getK()*temperature.getValue()));

	}	
	
	
	/**
	 * C'est la méthode qui effectue le recuit quantique. 
	 * @param p
	 * Problème construit par l'utilisateur
	 * @param m
	 * Une mutation aléatoire correspondant au problème traité.
	 * Les générations de mutation aléatoires au fil du recuit s'effectueront avec la méthode maj que l'utilisateur aura implémenté dans sa classe (voir IMutation)
	 * @param nombreIterations
	 * Nombre d'iterations pour chaque réplique. Le nombre de répliques, on le rappelle, est défini dans Problème.
	 * @param seed
	 * Rentrer 1 en argument
	 * @param M
	 * Nombre de fois consécutives que l'on traite une réplique. Rentrer 1 pour une utilisation normale
	 * @param sortie
	 * Fichier .txt dans lequel on stocke le résultat final du recuit.
	 * @return
	 * Solution du recuit : la meilleure énergie rencontrée par la particule
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
		System.out.println(e);
		Ponderation J = new Ponderation(p.getGamma());
		double Epot = p.calculerEnergiePotentielle();
		System.out.println("Epot " + Epot);
		double compteurSpinique = p.calculerEnergieCinetique();
		System.out.println("CompteurSpin " + compteurSpinique);
		double E = Epot-J.calcul(p.getT(), nombreEtat)*compteurSpinique;
		System.out.println("E " + E);
		double deltapot  = 0;
		double energie = (e.get(0)).getEnergie();
		System.out.println("En : " + energie);
		double energieBest = energie;
		
		int i = 0;
		int mutationsAcceptees = 0;
		int mutationsTentees = 0;
		
		while(i<nombreIterations && energieBest!=0){
			
			
			 E = Epot-J.calcul(p.getT(), nombreEtat)*compteurSpinique;
			 
			 double jG = J.calcul(p.getT(),nombreEtat);

			//System.out.println("Iter");
			 
			for(int j=0;j<nombreEtat;j++){// on effectue M  fois la mutation sur chaque réplique avant de descendre gamma
				
				Etat r = e.get(j);
				
				for(int k=0; k<M; k++){
					//System.out.println(energieBest);
					mutationsTentees++;
					
					//System.out.println("E");
					
					m.maj(p, r);
					
					deltapot =  m.calculer(p,r);
					//System.out.println("deltapot : " + deltapot);
					
					double delta = deltapot/nombreEtat  - jG*p.differenceSpins(r,m);
					
					
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
						E += delta;// L'energie courante est modifiée
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
							m.faire(p,r); //redonne comportement du recuit DAG3 --> très bizarre
							
							//TODO à supprimer
							//e.set(j, r);
							//p.setEtat(e);
							
							Epot += deltapot/nombreEtat;
							//System.out.println("Epot "+Epot);
							E += delta;// L'energie courante est modifiée
							//System.out.println("delta "+delta);
							energie = r.getEnergie();
							//System.out.println("Dpot "+deltapot);
							//System.out.println("apres "+r.getEnergie());
							//System.out.println("deltapot "+deltapot);
							//System.out.println("energie "+energie);
							
						}
						if (energie < energieBest){
							//pBest.setEtat(e); //TODO
							energieBest = energie;
							System.out.println("meilleureEnergie = "+energieBest);
							if(energieBest==0){	// condition de fin
								// nb mutations 
								System.out.println("Mutations tentées : " + mutationsTentees);
								System.out.println("Mutations acceptées : " + mutationsAcceptees);
								return energieBest;
							}
							//TODO
							System.out.println();
							for (Etat etat : p.getEtat()){
								GrapheColorie g = (GrapheColorie) etat;
								System.out.println("Energie de l'état : " + Conflits.calculer(g));
								System.out.println("Nombre de noeuds en conflits : " + g.nombreNoeudsEnConflit());
								System.out.println("Nombre d'arêtes en conflits : " + g.getNombreConflitsAretes());
							}
							System.out.println();
						}
				}
			}
			//UNE FOIS EFFECTUEE SUR tout les etat de la particule on descend gamma
			p.majgamma();
			J.setGamma(p.getGamma());
			Collections.shuffle(p.getEtat());
			
			i++;
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
		System.out.println("Mutations tentées : " + mutationsTentees);
		System.out.println("Mutations acceptées : " + mutationsAcceptees);
		
		return energieBest;

	}
}
