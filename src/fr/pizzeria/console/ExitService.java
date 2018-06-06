package fr.pizzeria.console;

import java.util.Scanner;

import fr.pizzeria.dao.IPizzaDao;

public class ExitService extends MenuService {

	public ExitService(IPizzaDao pizzaDao) {
		super(pizzaDao);
	}

	@Override
	public void executeUC(Scanner scan) {
		System.out.println("Au Revoir...");
	}

}
