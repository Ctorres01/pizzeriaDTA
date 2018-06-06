package fr.pizzeria.dao;

import java.util.List;
import java.util.ArrayList;

import fr.pizzeria.console.Pizza;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.exception.UpdatePizzaException;

public class PizzaMemDao implements IPizzaDao {
	
	private List<Pizza> pizzaList = new ArrayList<Pizza>();

	@Override
	public List<Pizza> findAllPizzas() { 
		return this.pizzaList;
	}

	@Override
	public void saveNewPizza(Pizza pizza) throws SavePizzaException{
		if(!this.pizzaExists(pizza.getCode())) {
			this.pizzaList.add(pizza);
		} else {
			throw new SavePizzaException("Le code pizza existe déjà. Veuillez entrer un autre code");
		}
		
	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) throws UpdatePizzaException {
		if(this.pizzaExists(codePizza)) {
			int index = this.findPizzaIndexByCode(codePizza);
			pizzaList.remove(index);
			pizzaList.add(index, pizza);
			System.out.println("Pizza modifiée aevc succès");
		} else {
			throw new UpdatePizzaException("Aucune pizza ne correspond au code '"+codePizza+"'");
		}
	}

	@Override
	public void deletePizza(String codePizza) throws DeletePizzaException {
		if(this.pizzaExists(codePizza)) {
			pizzaList.remove(this.findPizzaByCode(codePizza));
			System.out.println("Pizza supprimée avec succès");
		} else {
			throw new DeletePizzaException("Aucune pizza ne correspond au code '"+codePizza+"'");
		}	
	}

	@Override
	public Pizza findPizzaByCode(String codePizza) {
		Pizza pizza = null;
		if(pizzaExists(codePizza)) {
			pizza = pizzaList.get(findPizzaIndexByCode(codePizza));
		}
		return pizza;
	}

	
	@Override
	public boolean pizzaExists(String codePizza) {
		boolean pizzaFound = false;
		int indexPizza = this.findPizzaIndexByCode(codePizza);
		if(indexPizza != -1) {
			pizzaFound = true;
		}
		return pizzaFound;
	}
	
	
	//Renvoie -1 si la pizza n'a pas été trouvée
	@Override
	public int findPizzaIndexByCode(String codePizza) { 
		int pizzaIndex = 0;
		String currentPizzaCode = "";
		boolean pizzaFound = false;
		Pizza currentPizza = null;
		
			while(!pizzaFound && pizzaIndex<pizzaList.size()) {
				currentPizza = pizzaList.get(pizzaIndex);
				currentPizzaCode = currentPizza.getCode();
				if(currentPizzaCode.equals(codePizza)) {
					pizzaFound = true;
				} else {
					pizzaIndex++;
				}	
			} if(pizzaIndex == pizzaList.size()) {
				pizzaIndex = -1;
			}
		return pizzaIndex;
	}

}
