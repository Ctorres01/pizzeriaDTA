package fr.pizzeria.console;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.exception.StockageException;

public class MenuServiceFactory {
	
	public static MenuService getMenuServiceFactory(int menuChoice, IPizzaDao pizzaDao) throws StockageException {
		switch(menuChoice) {
		case 1 :
			return new ListerPizzasService(pizzaDao);
		case 2 :
			return new AjouterPizzaService(pizzaDao);
		case 3 :
			return new ModifierPizzaService(pizzaDao);
		case 4 :
			return new SupprimerPizzaService(pizzaDao);
		case 5 :
			return new ExitService(pizzaDao);
		default:
			throw new StockageException("Choix inexistant");
		}
	}
}
