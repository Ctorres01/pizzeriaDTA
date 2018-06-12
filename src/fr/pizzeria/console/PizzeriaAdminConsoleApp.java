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

			Scanner scan = new Scanner(System.in);
			int menuChoice;
			IPizzaDao pizzaDao = new PizzaMemDao();
			
			try {
				do {
					System.out.println("***** Pizzeria Administration *****\n1. Lister les pizzas\n2. Ajouter une nouvelle pizza\n3. Mettre � jour une pizza\n4. Supprimer une pizza\n5. Sortir");
					menuChoice = scan.nextInt();
					if(menuChoice >= 1 && menuChoice <= 5) {
						MenuService service = MenuServiceFactory.getMenuServiceFactory(menuChoice, pizzaDao);
						service.executeUC(scan);
					} else {
						System.out.println("Veuillez taper un num�ro de la liste\n");
					}
				} while (menuChoice != 5);
				scan.close();
			}catch (StockageException e) {
				e.printStackTrace();
			}

	}
}
