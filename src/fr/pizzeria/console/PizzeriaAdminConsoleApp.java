package fr.pizzeria.console;

import java.util.Locale.Category;
import java.util.Scanner;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.dao.PizzaMemDao;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.exception.StockageException;
import fr.pizzeria.model.CategoriePizza;

public class PizzeriaAdminConsoleApp {

	public static void main(String[] args) {
		try {
			Scanner scan = new Scanner(System.in);
			int menuChoice;
			IPizzaDao pizzaDao = new PizzaMemDao();
			Pizza pizza0 = new Pizza("PEP", "Pépéroni", 12.5, CategoriePizza.VIANDE);
			Pizza pizza1 = new Pizza("MAR", "Margherita", 14.0, CategoriePizza.SANS_VIANDE);
			Pizza pizza2 = new Pizza("REI", "La Reine", 11.5, CategoriePizza.VIANDE);
			Pizza pizza3 = new Pizza("FRO", "La 4 fromages", 12.0, CategoriePizza.SANS_VIANDE);
			Pizza pizza4 = new Pizza("CAN", "La cannibale", 12.5, CategoriePizza.VIANDE);
			Pizza pizza5 = new Pizza("SAV", "La savoyarde", 13.0, CategoriePizza.VIANDE);
			Pizza pizza6 = new Pizza("ORI", "L'orientale", 13.5, CategoriePizza.VIANDE);
			Pizza pizza7 = new Pizza("NOR", "Nordique", 15.0, CategoriePizza.POISSON);
			pizzaDao.saveNewPizza(pizza0);
			pizzaDao.saveNewPizza(pizza1);
			pizzaDao.saveNewPizza(pizza2);
			pizzaDao.saveNewPizza(pizza3);
			pizzaDao.saveNewPizza(pizza4);
			pizzaDao.saveNewPizza(pizza5);
			pizzaDao.saveNewPizza(pizza6);
			pizzaDao.saveNewPizza(pizza7);
			try {
				do {
					System.out.println("***** Pizzeria Administration *****\n1. Lister les pizzas\n2. Ajouter une nouvelle pizza\n3. Mettre à jour une pizza\n4. Supprimer une pizza\n5. Sortir");
					menuChoice = scan.nextInt();
					if(menuChoice >= 1 && menuChoice <= 5) {
						MenuService service = MenuServiceFactory.getMenuServiceFactory(menuChoice, pizzaDao);
						service.executeUC(scan);
					} else {
						System.out.println("Veuillez taper un numéro de la liste\n");
					}
				} while (menuChoice != 5);
				scan.close();
			}catch (StockageException e) {
				e.printStackTrace();
			}


		} catch (SavePizzaException e) {
			e.printStackTrace();
		}

	}
}
