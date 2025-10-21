package com.zerobank.qa.tests.java;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserTests {

    @BeforeAll
    static void setup() {
        // 1. Configuración global de RestAssured
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api/v1";

        // 2. Health Check: Verificamos si la API está activa antes de correr las pruebas.
        try {
            // Hacemos una llamada simple a un endpoint que sabemos que debería existir.
            // La opción .head() es ligera, no necesita procesar un body.
            given().head("/users").then().assertThat().statusCode(200);
        } catch (Exception e) {
            // Si la excepción es porque la conexión fue rechazada...
            if (e.getCause() instanceof ConnectException) {
                // Lanzamos una excepción clara que detiene todas las pruebas de la clase.
                // Assumptions.abort() es una forma limpia de hacerlo en JUnit 5.
                Assumptions.abort(
                        "ABORTANDO PRUEBAS: No se pudo conectar a la API en " + RestAssured.baseURI + ":" + RestAssured.port + ". " +
                                "Por favor, asegúrate de que la aplicación 'zerobank-api' esté en ejecución."
                );
            }
            // Si es otra excepción, la dejamos pasar para que la prueba falle normalmente.
            throw e;
        }
    }

    @Test
    @DisplayName("Debería obtener los detalles de la cuenta para un usuario existente")
    void shouldGetUserAccountDetailsForExistingUser() {
        // Usamos el patrón Given-When-Then, que es muy legible.

        given()
                // GIVEN (Dado): La pre-condición. En este caso, no hay headers o body complejos.
                .accept(ContentType.JSON)
                .pathParam("userId", 1) // Parámetro para reemplazar {userId} en la URL.

                .when()
                // WHEN (Cuando): La acción que queremos probar.
                .get("/users/{userId}/account")

                .then()
                // THEN (Entonces): Las verificaciones o "assertions".
                .assertThat()
                .statusCode(200) // 1. Verificar que el código de estado es 200 (OK).
                .contentType(ContentType.JSON) // 2. Verificar que la respuesta es de tipo JSON.
                .body("id", equalTo(1)) // 3. Verificar que el ID de la cuenta es 1.
                .body("balance", equalTo(1000.00f)); // 4. Verificar que el balance es 1000.00.
        //    Usamos 'f' para tratarlo como float,
        //    que es como RestAssured lo interpreta por defecto.
    }
}

