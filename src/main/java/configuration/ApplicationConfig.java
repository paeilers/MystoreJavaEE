package configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Singleton
@Startup
public class ApplicationConfig {
	
//	EntityManagerFactory emf = Persistence.createEntityManagerFactory("PrototypeDB");

	@PostConstruct
	private void startup() {
		System.out.println("Application started...");
	}
	
	@PreDestroy
	private void shutdown() {
		System.out.println("Shutting down...");
	}
}
