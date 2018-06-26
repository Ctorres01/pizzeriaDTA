package fr.pizzeria.console;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.utils.ToString;

@Entity
@Table(name="pizza")
public class Pizza {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@ToString
	private int id;
	
	@ToString(upperCase=true)
	@Column(name="code", nullable = false, unique = true)
	private String code;
	
	@ToString
	private String libelle;
	private double prix;
	
	@ToString
	private CategoriePizza categorie;
	private static int currentId = 0;
	
	public Pizza() {
		super();
	}

	public Pizza(String code, String libelle, double prix, CategoriePizza categorie) {
		this.code = code;
		this.libelle = libelle;
		this.prix = prix;
		this.categorie = categorie;
		this.id = Pizza.currentId;
		Pizza.currentId += 1;
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
	
	public void setId(int id) {
		this.id = id;
	}

	public static int getCurrentId() {
		return currentId;
	}

	public static void setCurrentId(int currentId) {
		Pizza.currentId = currentId;
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
	
	public String getLibelle() {
		return libelle;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categorie == null) ? 0 : categorie.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + id;
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
		long temp;
		temp = Double.doubleToLongBits(prix);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pizza other = (Pizza) obj;
		if (categorie != other.categorie)
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		if (Double.doubleToLongBits(prix) != Double.doubleToLongBits(other.prix))
			return false;
		return true;
	}

	
}
