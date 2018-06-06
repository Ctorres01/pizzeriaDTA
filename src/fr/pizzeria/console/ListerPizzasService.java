package fr.pizzeria.console;

import java.util.Scanner;

import fr.pizzeria.dao.IPizzaDao;

public class ListerPizzasService extends  MenuService{

	public ListerPizzasService(IPizzaDao pizzaDao) {
		super(pizzaDao);
	}

	@Override
	public void executeUC(Scanner scan) {
		System.out.println("-Liste des pizzas-");
		for(Pizza pizza : pizzaDao.findAllPizzas()) {
			System.out.println(pizza);
		}
	}

}
