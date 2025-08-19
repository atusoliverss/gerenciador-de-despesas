package com.atus.gerdp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal que inicia a aplicação backend.
 * É o ponto de partida do projeto.
 */
@SpringBootApplication
public class GerdpApplication {

    /**
     * Método main que executa o servidor Spring Boot.
     */
    public static void main(String[] args) {
        SpringApplication.run(GerdpApplication.class, args);
    }

}
