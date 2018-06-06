package fr.pizzeria.console;

import java.util.Scanner;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;

public class ModifierPizzaService extends  MenuService{

	public ModifierPizzaService(IPizzaDao pizzaDao) {
		super(pizzaDao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeUC(Scanner scan) {
		try {
			System.out.println("-Mise à jour d'une pizza-\n");
			//Affichage de la liste des pizza
			for(Pizza pizza : pizzaDao.findAllPizzas()) {
				System.out.println(pizza);
			}

			//Demande à l'utilisateur de choisir une pizza
			String pizzaChoice = null;
			System.out.println("Choisissez une pizza à modifier:");
			do {
				if(pizzaChoice !=null && !pizzaChoice.isEmpty()) {
					System.out.println("Code pizza invalide. Saisissez un autre code: ");
				}
				pizzaChoice = scan.nextLine();
			} while(!pizzaDao.pizzaExists(pizzaChoice));


			//Demande à l'utilisateur les nouvelles informations de la pizza
			System.out.print("Veuillez saisir le nouveau code: ");
			String modifiedPizzaCode = scan.nextLine();
			System.out.print("\nVeuillez saisir le nouveau nom: ");
			String modifiedPizzaName = scan.nextLine();
			System.out.print("\nVeuillez saisir le nouveau prix: ");
			double modifiedPizzaPrice = Double.parseDouble(scan.nextLine());
			System.out.print("\nVeuillez saisir la nouvelle catégorie de pizza: ");
			CategoriePizza modifiedPizzaCategorie = CategoriePizza.valueOf(scan.nextLine());
			System.out.println();

			//Modification des informations de la pizza
			pizzaDao.updatePizza(pizzaChoice, new Pizza(modifiedPizzaCode, modifiedPizzaName, modifiedPizzaPrice, modifiedPizzaCategorie));
		} catch (UpdatePizzaException e) {
			e.printStackTrace();
		}
		
	}

}
