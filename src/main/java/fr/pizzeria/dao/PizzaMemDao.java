package fr.pizzeria.dao;

import java.util.List;
import java.util.ArrayList;

import fr.pizzeria.console.Pizza;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;

public class PizzaMemDao implements IPizzaDao {

	private List<Pizza> pizzaList = new ArrayList<Pizza>();

	public PizzaMemDao() {
		Pizza pizza0 = new Pizza("PEP", "Pépéroni", 12.5, CategoriePizza.VIANDE);
		Pizza pizza1 = new Pizza("MAR", "Margherita", 14.0, CategoriePizza.SANS_VIANDE);
		Pizza pizza2 = new Pizza("REI", "La Reine", 11.5, CategoriePizza.VIANDE);
		Pizza pizza3 = new Pizza("FRO", "La 4 fromages", 12.0, CategoriePizza.SANS_VIANDE);
		Pizza pizza4 = new Pizza("CAN", "La cannibale", 12.5, CategoriePizza.VIANDE);
		Pizza pizza5 = new Pizza("SAV", "La savoyarde", 13.0, CategoriePizza.VIANDE);
		Pizza pizza6 = new Pizza("ORI", "L'orientale", 13.5, CategoriePizza.VIANDE);
		Pizza pizza7 = new Pizza("NOR", "Nordique", 15.0, CategoriePizza.POISSON);
		this.pizzaList.add(pizza0);
		this.pizzaList.add(pizza1);
		this.pizzaList.add(pizza2);
		this.pizzaList.add(pizza3);
		this.pizzaList.add(pizza4);
		this.pizzaList.add(pizza5);
		this.pizzaList.add(pizza6);
		this.pizzaList.add(pizza7);
	}

	@Override
	public List<Pizza> findAllPizzas() {
		return this.pizzaList;
	}

	@Override
	public void saveNewPizza(Pizza pizza) throws SavePizzaException {
		if (!this.pizzaExists(pizza.getCode())) {
			this.pizzaList.add(pizza);
		} else {
			throw new SavePizzaException("Le code pizza existe déjà. Veuillez entrer un autre code");
		}

	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) throws UpdatePizzaException {
		if (this.pizzaExists(codePizza)) {
			int index = this.findPizzaIndexByCode(codePizza);
			pizzaList.remove(index);
			pizzaList.add(index, pizza);
			System.out.println("Pizza modifiée avec succès");
		} else {
			throw new UpdatePizzaException("Aucune pizza ne correspond au code '" + codePizza + "'");
		}
	}

	@Override
	public void deletePizza(String codePizza) throws DeletePizzaException {
		if (this.pizzaExists(codePizza)) {
			pizzaList.remove(this.findPizzaByCode(codePizza));
			System.out.println("Pizza supprimée avec succès");
		} else {
			throw new DeletePizzaException("Aucune pizza ne correspond au code '" + codePizza + "'");
		}
	}

	@Override
	public Pizza findPizzaByCode(String codePizza) {
		Pizza pizza = null;
		if (pizzaExists(codePizza)) {
			pizza = pizzaList.get(findPizzaIndexByCode(codePizza));
		}
		return pizza;
	}

	@Override
	public boolean pizzaExists(String codePizza) {
		boolean pizzaFound = false;
		int indexPizza = this.findPizzaIndexByCode(codePizza);
		if (indexPizza != -1) {
			pizzaFound = true;
		}
		return pizzaFound;
	}

	// Renvoie -1 si la pizza n'a pas été trouvée
	public int findPizzaIndexByCode(String codePizza) {
		int pizzaIndex = 0;
		String currentPizzaCode = "";
		boolean pizzaFound = false;
		Pizza currentPizza = null;

		while (!pizzaFound && pizzaIndex < pizzaList.size()) {
			currentPizza = pizzaList.get(pizzaIndex);
			currentPizzaCode = currentPizza.getCode();
			if (currentPizzaCode.equals(codePizza)) {
				pizzaFound = true;
			} else {
				pizzaIndex++;
			}
		}
		if (pizzaIndex == pizzaList.size()) {
			pizzaIndex = -1;
		}
		return pizzaIndex;
	}

	@Override
	public void close() {
	}

}
