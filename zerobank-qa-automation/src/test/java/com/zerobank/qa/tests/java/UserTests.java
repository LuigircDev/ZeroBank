package com.zerobank.qa.tests.java;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTests {

    private static String authToken;

    @BeforeAll
    static void setup() {
        // 1. Configuración global de RestAssured
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api/v1";

        // 2. Health Check: Verificamos si la API está activa antes de correr las pruebas.
        try {
            given()
                    .when()
                    .get("/auth/health") // Usamos el nuevo endpoint público de salud
                    .then()
                    .statusCode(200)
                    .body(equalTo("API is up and running"));
        } catch (Exception e) {
            if (e.getCause() instanceof ConnectException) {
                Assumptions.abort(
                        "ABORTANDO PRUEBAS: No se pudo conectar a la API en " + RestAssured.baseURI + ":" + RestAssured.port + ". " +
                                "Por favor, asegúrate de que la aplicación 'zerobank-api' esté en ejecución."
                );
            }
            throw e;
        }

        // 3. Autenticación: Obtener el token para todas las pruebas
        // Creamos el JSON para el login
        Map<String, String> loginPayload = Map.of(
                "email", "luigi@mail.com", // Usuario de tu data.sql
                "password", "password123"   // Contraseña de tu data.sql
        );

        authToken = given()
                .contentType(ContentType.JSON)
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token"); // Extrae el token de la respuesta {"token": "..."}

        // Verificamos que el token no sea nulo
        assertNotNull(authToken, "El token de autenticación no debe ser nulo");
    }

    @Test
    @DisplayName("Debe obtener los detalles de la cuenta propia (Happy Path)")
    void shouldGetOwnAccountDetails() {
        given()
                .auth().oauth2(authToken) // Envía el token como "Bearer <token>"
                .accept(ContentType.JSON)
                .pathParam("userId", 1) // Luigi (ID 1) pide su propia cuenta
                .when()
                .get("/users/{userId}/account")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("balance", equalTo(1000.00f)); // Verificamos el balance
    }

    @Test
    @DisplayName("Debe fallar con 403 (Forbidden) al intentar ver la cuenta de otro usuario")
    void shouldBeForbiddenFromGettingOtherUserAccount() {
        given()
                .auth().oauth2(authToken) // Autenticado como Luigi (ID 1)
                .pathParam("userId", 2) // Intentando ver la cuenta de Mario (ID 2)
                .when()
                .get("/users/{userId}/account")
                .then()
                .assertThat()
                .statusCode(403); // Esperamos "Forbidden"
    }

    @Test
    @DisplayName("Debe fallar con 401 (Unauthorized) si no se envía token")
    void shouldBeUnauthorizedWithoutToken() {
        given()
                // Sin token de autenticación
                .pathParam("userId", 1)
                .when()
                .get("/users/{userId}/account")
                .then()
                .assertThat()
                .statusCode(401); // Esperamos "Unauthorized"
    }
}
