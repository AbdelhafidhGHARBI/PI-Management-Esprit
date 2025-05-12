package tn.esprit.document_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "tn.esprit")
@EnableMongoRepositories(basePackages = "tn.esprit.document_service.repositories")
public class DocumentServiceApplication {

	public static void main(String[] args) {
		System.out.println("Hello");
		SpringApplication.run(DocumentServiceApplication.class, args);
	}

}
