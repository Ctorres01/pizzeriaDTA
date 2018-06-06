package fr.pizzeria.model;

import fr.pizzeria.exception.UnknownCategorieException;

public enum CategoriePizza {
	
	VIANDE("Viande"),
	POISSON("Poisson"),
	SANS_VIANDE("Sans Viande");
	
	private String nom;
	
	private CategoriePizza(String nom) {
		this.nom = nom;
	}

	public static CategoriePizza getCategoriefromUserChoice(int userChoice) throws UnknownCategorieException{
		switch(userChoice) {
		case 1:
			return CategoriePizza.VIANDE;
		case 2:
			return CategoriePizza.SANS_VIANDE;
		case 3:
			return CategoriePizza.POISSON;
		default:
			throw new UnknownCategorieException("Catégorie de pizza inconnue");
		}
	}

	public String getNom() {
		return this.nom;
	}

	
	
}
