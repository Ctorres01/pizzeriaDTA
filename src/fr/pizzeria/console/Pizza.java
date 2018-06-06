package fr.pizzeria.console;

import java.lang.reflect.Field;

import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.utils.ToString;

public class Pizza {

	@ToString
	private int id;
	@ToString(upperCase=true)
	private String code;
	@ToString
	private String libelle;
	private double prix;
	private CategoriePizza categorie;
	private static int currentId = 0;

	public Pizza(String code, String libelle, double prix, CategoriePizza categorie) {
		this.code = code;
		this.libelle = libelle;
		this.prix = prix;
		this.categorie = categorie;
		this.id = Pizza.currentId;
		Pizza.currentId += 1;
	}

	public String getLibelle() {
		return libelle;
	}

	public Pizza(String code, String libelle, double prix, CategoriePizza categorie, int id) {
		this(code, libelle, prix, categorie);
		this.id = id;
	}

	@Override
	public String toString() {
		//return this.code+" -> "+this.libelle+" ("+this.categorie.getNom()+")"+" - "+this.prix+"€";
		String result = "";
		Field[] fields = this.getClass().getDeclaredFields();
		for(Field field : fields) {

			ToString annot = field.getAnnotation(ToString.class); 
			if(annot != null) {
				if (annot.upperCase()) {
					try {
						result +=" "+ field.get(this).toString().toUpperCase();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else {
					try {
						result +=" " + field.get(this);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}

		}
		return result;
	}

	public int getId() {
		return this.id;
	}

	public static int getCurrentId() {
		return currentId;
	}

	public static void setCurrentId(int currentId) {
		Pizza.currentId = currentId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public CategoriePizza getCategorie() {
		return categorie;
	}

	public void setCategorie(CategoriePizza categorie) {
		this.categorie = categorie;
	}


}
