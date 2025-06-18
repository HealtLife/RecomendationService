package com.acme.nutrimove.recomendationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.sql.*;

@SpringBootApplication
@EnableFeignClients
public class RecomendationServiceApplication {
    public static void main(String[] args) {
        createDatabaseIfNotExists("jdbc:postgresql://localhost:5432/", "postgres", "renato", "nutrimove_recomendationservice");

        SpringApplication.run(RecomendationServiceApplication.class, args);
    }
    private static void createDatabaseIfNotExists(String url, String user, String password, String dbName) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'");
            if (!rs.next()) {
                stmt.executeUpdate("CREATE DATABASE " + dbName);
                System.out.println("Base de datos '" + dbName + "' creada correctamente.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error verificando/creando base de datos: " + dbName, e);
        }
    }
}