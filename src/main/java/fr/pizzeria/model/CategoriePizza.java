package fr.pizzeria.model;

import fr.pizzeria.exception.UnknownCategorieException;

public enum CategoriePizza {
	
	VIANDE("Viande", 1),
	POISSON("Poisson", 2),
	SANS_VIANDE("Sans Viande", 3);
	
	private String nom;
	private int number;
	
	private CategoriePizza(String nom, int number) {
		this.nom = nom;
		this.number = number;
	}

	public static CategoriePizza getCategoriefromNumber(int number) throws UnknownCategorieException{
		switch(number) {
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
	
	public int getNumber() {
		return this.number;
	}

	public String getNom() {
		return this.nom;
	}

	
	
}
