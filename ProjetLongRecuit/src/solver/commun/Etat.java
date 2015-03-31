package solver.commun;

/**
 * Classe abstraite servant de template pour les états d'un problème. Un état représente une configuration 
 * possible d'une particule. Un état dispose nécessairement d'un lien vers une énergie potentielle. 
 * <p>
 * Il est nécessaire d'implémenter une sous-classe fille, avec les informations du problème en particulier.
 * Il doit aussi y avoir moyen de l'initialiser, pour que ce soit utiliser par la fonction initialiser() du problème.
 * Il y a aussi moyen de décomposer les fonctionnalités de faire() de l'implémentation de IMutation pour qu'elle appelle
 * une fonction dans Etat qui effectue la mutation sur les variables internes.
 * @see EnergiePotentielle, Particule
 *
 */
public abstract class Etat {
	
	public EnergiePotentielle Ep;
	
}
