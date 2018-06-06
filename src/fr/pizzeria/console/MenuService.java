package fr.pizzeria.console;

import java.util.Scanner;

import fr.pizzeria.dao.IPizzaDao;

public abstract class MenuService {
	
	IPizzaDao pizzaDao;
	
	public MenuService(IPizzaDao pizzaDao) {
		this.pizzaDao = pizzaDao;
	}
	
	public abstract void executeUC(Scanner scan);
}
