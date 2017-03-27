package br.ufc.npi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("br.ufc.quixada.npi.*")
@ComponentScan("br.ufc.npi.*")
public class DesafioNpiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioNpiApplication.class, args);
	}
}
