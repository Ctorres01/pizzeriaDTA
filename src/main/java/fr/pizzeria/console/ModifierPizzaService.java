package fr.pizzeria.console;

import java.util.Scanner;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.exception.UnknownCategorieException;
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
			System.out.print("\nVeuillez saisir le nouveau nom: ");
			String modifiedPizzaName = scan.nextLine();
			System.out.print("\nVeuillez saisir le nouveau prix: ");
			double modifiedPizzaPrice = Double.parseDouble(scan.nextLine());
			System.out.print("\nChoisissez la catégorie de la pizza:\n1.Viande\n2.Sans Viande\n3.Poisson ");
			String str = scan.nextLine();
			if(str.isEmpty()) {
				str = "-1";
			}
			CategoriePizza modifiedPizzaCategorie;
			try {
				modifiedPizzaCategorie = CategoriePizza.getCategoriefromNumber(Integer.parseInt(str));
				//Modification des informations de la pizza
				pizzaDao.updatePizza(pizzaChoice, new Pizza(pizzaChoice, modifiedPizzaName, modifiedPizzaPrice, modifiedPizzaCategorie));
			} catch (NumberFormatException e) {
				System.out.println("Format Invalide");
				e.printStackTrace();
			} catch (UnknownCategorieException e) {
				e.printStackTrace();
			}
			System.out.println();

			
		} catch (UpdatePizzaException e) {
			e.printStackTrace();
		}
		
	}

}
