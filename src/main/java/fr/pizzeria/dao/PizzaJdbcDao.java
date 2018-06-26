package fr.pizzeria.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import fr.pizzeria.console.Pizza;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.exception.UnknownCategorieException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;

public class PizzaJdbcDao implements IPizzaDao {

	private Connection bdConnection = null;
	private PreparedStatement statement = null;
	private ResultSet result = null;

	public PizzaJdbcDao() {
		// Récupération des paramètres de connexion à partir du fichier de
		// configuration "jdbc.properties"
		ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
		String dbUrl = bundle.getString("db.url");
		String dbUserName = bundle.getString("db.username");
		String dbPassword = bundle.getString("db.password");
		String dbDriver = bundle.getString("db.driver");

		// Récupération de la classe Driver de MariaDB
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			System.out.println("Classe " + dbDriver + " Introuvable");
			e.printStackTrace();
		}

		// Instanciation de la connexion
		try {
			this.bdConnection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
		} catch (SQLException e) {
			System.out.println(
					"Connexion à a base échouée. Vérifier les paramètres de connexion. Paramètre de connexion actuels:\nURL: "
							+ dbUrl + "\nUser Name :" + dbUserName + "\nPassword: " + dbPassword);
			e.printStackTrace();
		}
	}

	@Override
	public List<Pizza> findAllPizzas() {
		List<Pizza> listePizza = new ArrayList<>();

		// Instanciation de Statement
		try {
			this.statement = bdConnection.prepareStatement("SELECT * FROM pizza");
			this.result = this.statement.executeQuery();
			while (result.next()) {
				int pizzaId = result.getInt(1);
				String pizzaCode = result.getString(2);
				String pizzaLibelle = result.getString(3);
				double pizzaPrix = result.getDouble(4);
				int pizzaCategorie = result.getInt(5);
				listePizza.add(new Pizza(pizzaCode, pizzaLibelle, pizzaPrix,
						CategoriePizza.getCategoriefromNumber(pizzaCategorie), pizzaId));
			}
		} catch (SQLException e) {
			System.out.println("Récupération des enregistrements échouée");
			e.printStackTrace();
			this.rollback();
		} catch (UnknownCategorieException e) {
			System.out.println(
					"La catégorie est éronnée (et c'est assez bizarre parce que cette exception ne devrait pas être lancée si la BD est correcte");
			e.printStackTrace();
		} finally {
			this.commit();
			this.closeResult();
			this.closeStatement();
		}
		return listePizza;
	}

	@Override
	public void saveNewPizza(Pizza pizza) throws SavePizzaException {
		String pizzaCode = pizza.getCode();
		String pizzaLibelle = pizza.getLibelle();
		double pizzaPrix = pizza.getPrix();
		CategoriePizza pizzaCategorie = pizza.getCategorie();
		// Instanciation de Statement
		try {
			this.statement = bdConnection
					.prepareStatement("INSERT INTO pizza (code, libelle, prix, categorie) VALUES (?,?,?,?)");
			this.statement.setString(1, pizzaCode);
			this.statement.setString(2, pizzaLibelle);
			this.statement.setDouble(3, pizzaPrix);
			this.statement.setInt(4, pizzaCategorie.getNumber());
			this.statement.executeUpdate();
		} catch (SQLException e) {
			this.rollback();
			System.out.println("Execution de la requête échouée");
			e.printStackTrace();
		} finally {
			this.commit();
			this.closeStatement();
		}

	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) throws UpdatePizzaException {
		String pizzaLibelle = pizza.getLibelle();
		double pizzaPrix = pizza.getPrix();
		CategoriePizza pizzaCategorie = pizza.getCategorie();
		// Instanciation de Statement
		try {
			this.statement = bdConnection
					.prepareStatement("UPDATE pizza SET libelle = ?, prix = ?, categorie = ? WHERE UPPER(code) = ?");
			this.statement.setString(1, pizzaLibelle);
			this.statement.setDouble(2, pizzaPrix);
			this.statement.setInt(3, pizzaCategorie.getNumber());
			this.statement.setString(4, codePizza);
			this.statement.executeUpdate();
		} catch (SQLException e) {
			this.rollback();
			System.out.println("Execution de la requête échouée");
			e.printStackTrace();
		} finally {
			this.commit();
			this.closeStatement();
		}

	}

	@Override
	public void deletePizza(String codePizza) throws DeletePizzaException {
		// Instanciation de Statement
		try {
			this.statement = bdConnection.prepareStatement("DELETE FROM pizza WHERE code = ?");
			this.statement.setString(1, codePizza);
			this.statement.executeUpdate();
		} catch (SQLException e) {
			this.rollback();
			System.out.println("Execution de la requête échouée");
			e.printStackTrace();
		} finally {
			this.commit();
			this.closeStatement();
		}

	}

	@Override
	public Pizza findPizzaByCode(String codePizza) {
		Pizza pizza = null;
		try {
			this.statement = bdConnection.prepareStatement("SELECT * FROM pizza WHERE code = ?");
			this.statement.setString(1, codePizza);
			this.result = this.statement.executeQuery();
			while (result.next()) {
				int pizzaId = result.getInt(1);
				String pizzaCode = result.getString(2);
				String pizzaLibelle = result.getString(3);
				double pizzaPrix = result.getDouble(4);
				int pizzaCategorie = result.getInt(5);
				pizza =  new Pizza(pizzaCode, pizzaLibelle, pizzaPrix, CategoriePizza.getCategoriefromNumber(pizzaCategorie), pizzaId);
			}
		} catch (SQLException e) {
			this.rollback();
			System.out.println("Récupération des enregistrements échouée");
			e.printStackTrace();
		} catch (UnknownCategorieException e) {
			this.rollback();
			System.out.println("La catégorie est éronnée (et c'est assez bizarre parce que cette exception ne devrait pas être lancée si la BD est correcte");
			e.printStackTrace();
		} finally {
			this.commit();
			this.closeResult();
			this.closeStatement();
		}
		return pizza;
	}

	@Override
	public boolean pizzaExists(String codePizza) {
		boolean doesPizzaExist = false;
		if(this.findPizzaByCode(codePizza) != null) {
			doesPizzaExist = true;
		}
		return doesPizzaExist;
	}

	public void closeConnexion() {
		try {
			this.bdConnection.close();
		} catch (SQLException e) {
			System.out.println("Impossible de fermer la connexion à la base de données.");
			e.printStackTrace();
		}
	}

	public void closeStatement() {
		try {
			if (this.statement != null) {
				this.statement.close();
			}
		} catch (SQLException e) {
			System.out.println("Impossible de fermer le statement.");
			e.printStackTrace();
		}
	}

	public void closeResult() {
		try {
			if (this.result != null) {
				this.result.close();
			}
		} catch (SQLException e) {
			System.out.println("Impossible de fermer le resultSet.");
			e.printStackTrace();
		}
	}
	
	public void rollback() {
		try {
			this.bdConnection.rollback();
		} catch (SQLException e) {
			System.out.println("Rollback Impossible");
			e.printStackTrace();
		}
	}
	
	public void commit() {
		try {
			this.bdConnection.commit();
		} catch (SQLException e) {
			System.out.println("Commit Impossible");
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
	}

}
