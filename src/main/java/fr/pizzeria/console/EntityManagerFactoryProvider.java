package fr.pizzeria.console;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryProvider {

	private static EntityManagerFactory emf;

	private EntityManagerFactoryProvider() {

	}

	public static EntityManagerFactory getInstance(String entityManagerFactoryName) throws Exception {
		if (EntityManagerFactoryProvider.emf == null) {
			if (entityManagerFactoryName != null || !entityManagerFactoryName.isEmpty()) {
				EntityManagerFactoryProvider.emf = Persistence.createEntityManagerFactory(entityManagerFactoryName);
			} else {
				throw new Exception();
			}
		}
		return EntityManagerFactoryProvider.emf;
	}
	
	public static EntityManagerFactory getInstance() {
		if (EntityManagerFactoryProvider.emf == null) {
			EntityManagerFactoryProvider.emf = Persistence.createEntityManagerFactory("pizza-jpa");
		}
		return EntityManagerFactoryProvider.emf;
	}
}
