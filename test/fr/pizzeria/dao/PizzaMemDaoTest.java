package fr.pizzeria.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.pizzeria.console.Pizza;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;

public class PizzaMemDaoTest {
	
	PizzaMemDao dao;
	
	@Before
	public void setUp() {
		this.dao = new PizzaMemDao();
	}
	
	@Test(expected=SavePizzaException.class)
	public void TestSaveNewPizzaException() throws SavePizzaException{
		dao.saveNewPizza(new Pizza("PEP", "PEPITO", 15.5, CategoriePizza.SANS_VIANDE));
	}
	
	@Test
	public void testSaveNewPizza() {
		Pizza pizza = new Pizza("POU", "POULPE", 15.5, CategoriePizza.POISSON);
		try {
			dao.saveNewPizza(pizza);
		} catch (SavePizzaException e) {
			e.printStackTrace();
		}
		
		//On vérifie que la dernière pizza de la list est bien celle que l'on a voulu ajouter
		Assert.assertEquals(dao.findAllPizzas().get(dao.findAllPizzas().size()-1), pizza);
	}
	
	@Test
	public void testUpdatePizza() {
		Pizza pizza =  new Pizza("POU", "Poule", 60.75, CategoriePizza.POISSON);
		try {
			dao.updatePizza("REI", pizza);
		} catch (UpdatePizzaException e){
			e.printStackTrace();
		}
		Assert.assertEquals(dao.findAllPizzas().get(2), pizza);
	}
	
	@Test(expected=UpdatePizzaException.class)
	public void testUpdatePizzaException() throws UpdatePizzaException{
		dao.updatePizza("POM", new Pizza("POM", "Pomme", 60.75, CategoriePizza.SANS_VIANDE));
		Assert.fail();
	}
	
	
	@Test
	public void testDeletePizza() {
		try {
			dao.deletePizza("PEP");
		} catch (DeletePizzaException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(!dao.pizzaExists("PEP"));
	}
	
	@Test(expected=DeletePizzaException.class)
	public void testDeletePizzaException() throws DeletePizzaException {
		dao.deletePizza("POM");
		Assert.fail();
	}
	
	@Test
	public void testPizzaExists() {
		Assert.assertTrue(dao.pizzaExists("SAV"));
		Assert.assertFalse(dao.pizzaExists("POM"));
	}
	
	
	
	
	

}
