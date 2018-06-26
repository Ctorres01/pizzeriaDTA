package fr.pizzeria.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.pizzeria.console.EntityManagerFactoryProvider;
import fr.pizzeria.console.Pizza;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.exception.UpdatePizzaException;

public class PizzaJpaDao implements IPizzaDao {

	private EntityManager em;
	private EntityManagerFactory emf;
	
	public PizzaJpaDao() {
		this.emf = EntityManagerFactoryProvider.getInstance();
		this.em = emf.createEntityManager();
	}
	
	@Override
	public List<Pizza> findAllPizzas() {
		TypedQuery<Pizza> query = em.createQuery("SELECT p FROM Pizza p", Pizza.class);
		return query.getResultList();
	}

	@Override
	public void saveNewPizza(Pizza pizza) throws SavePizzaException {
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(pizza);
		et.commit();
	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) throws UpdatePizzaException {
		EntityTransaction et = em.getTransaction();
		et.begin();
		TypedQuery<Pizza> query = em.createQuery("SELECT p FROM Pizza p WHERE UPPER(p.code)=:code", Pizza.class);
		query.setParameter("code", codePizza);
		Pizza p = query.getSingleResult();
		p.setCategorie(pizza.getCategorie());
		p.setLibelle(pizza.getLibelle());
		p.setPrix(pizza.getPrix());
		et.commit();
	}

	@Override
	public void deletePizza(String codePizza) throws DeletePizzaException {
		EntityTransaction et = em.getTransaction();
		et.begin();
		Query query = em.createQuery("DELETE FROM pizza p WHERE UPPER(p.code)=:code");
		query.setParameter("code", codePizza);
		et.commit();
	}

	@Override
	public Pizza findPizzaByCode(String codePizza) {
		TypedQuery<Pizza> query = em.createQuery("SELECT p FROM Pizza p WHERE p.code=:code", Pizza.class);
		query.setParameter("code", codePizza);
		return query.getSingleResult();
	}

	@Override
	public boolean pizzaExists(String codePizza) {
		TypedQuery<Pizza> query = em.createQuery("SELECT p FROM Pizza p WHERE p.code=:code", Pizza.class);
		query.setParameter("code", codePizza);
		return query.getSingleResult() != null;
	}

	@Override
	public void close() {
		this.em.close();
		this.emf.close();
	}

}
