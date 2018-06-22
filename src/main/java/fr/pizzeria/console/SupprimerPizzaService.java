package fr.pizzeria.console;

import java.util.Scanner;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.exception.DeletePizzaException;

public class SupprimerPizzaService extends  MenuService{

	public SupprimerPizzaService(IPizzaDao pizzaDao) {
		super(pizzaDao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeUC(Scanner scan) {
		try {
			System.out.println("-Suppression d'une pizza-\n");
			//Affichage de la liste des pizza
			for(Pizza pizza : pizzaDao.findAllPizzas()) {
				System.out.println(pizza);
			}

			//Demande à l'utilisateur de choisir une pizza
			String pizzaChoiceDelete = null;
			System.out.println("Choisissez une pizza à supprimer:");
			do {
				if(pizzaChoiceDelete !=null && !pizzaChoiceDelete.isEmpty()) {
					System.out.println("Code pizza invalide. Saisissez un autre code: ");
				}
				pizzaChoiceDelete = scan.nextLine();
			} while (!pizzaDao.pizzaExists(pizzaChoiceDelete));

			//Suppression de la pizza dans le tableau
			pizzaDao.deletePizza(pizzaChoiceDelete);
		} catch (DeletePizzaException e) {
			e.printStackTrace();
		}
		

	}

}
