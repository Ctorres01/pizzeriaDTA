package fr.pizzeria.console;

import java.util.Scanner;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.dao.PizzaJdbcDao;
import fr.pizzeria.dao.PizzaJpaDao;
import fr.pizzeria.dao.PizzaMemDao;
import fr.pizzeria.exception.StockageException;

public class PizzeriaAdminConsoleApp {

	public static void main(String[] args) {

			Scanner scan = new Scanner(System.in);
			int menuChoice;
			IPizzaDao pizzaDao = new PizzaJpaDao();

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
				pizzaDao.close();
			}catch (StockageException e) {
				e.printStackTrace();
			}

	}
}
