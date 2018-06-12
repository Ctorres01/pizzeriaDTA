package fr.pizzeria.console;

import java.util.Scanner;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.exception.UnknownCategorieException;
import fr.pizzeria.model.CategoriePizza;

public class AjouterPizzaService extends  MenuService{

	public AjouterPizzaService(IPizzaDao pizzaDao) {
		super(pizzaDao);
	}

	@Override
	public void executeUC(Scanner scan){
		try {
			System.out.println("-Ajout d'une nouvelle pizza-");
			System.out.print("Veuillez saisir le code: ");
			scan.nextLine();
			String newPizzaCode = scan.nextLine();
			System.out.print("\nVeuillez saisir le nom: ");
			String newPizzaName = scan.nextLine();
			System.out.print("\nVeuillez saisir le prix: ");
			
			String str = scan.nextLine();
			double newPizzaPrice;
			if(str.isEmpty()) {
				newPizzaPrice = 0;
			} else {
				newPizzaPrice = Double.parseDouble(str);
			}
			System.out.print("\nChoisissez la catégorie de la pizza:\n1.Viande\n2.Sans Viande\n3.Poisson ");
			str = scan.nextLine();
			if(str.isEmpty()) {
				str = "-1";
			}
			CategoriePizza newPizzaCategorie = CategoriePizza.getCategoriefromUserChoice(Integer.parseInt(str));
			System.out.println();

			//Ajout de la pizza dans le tableau
			pizzaDao.saveNewPizza(new Pizza(newPizzaCode, newPizzaName, newPizzaPrice, newPizzaCategorie));
		} catch (SavePizzaException e) {
			e.printStackTrace();
		} catch (UnknownCategorieException e) {
			e.printStackTrace();
		}

	}
}
