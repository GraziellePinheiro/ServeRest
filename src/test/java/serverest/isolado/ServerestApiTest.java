package serverest.isolado;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import org.aeonbits.owner.ConfigFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import serverest.factory.UsuarioDataFactory;
import serverest.pojo.UsuarioPojo;

public class ServerestApiTest {
    private UsuarioPojo usuarioAdministrador;
    private UsuarioPojo usuarioComum;

    @BeforeEach
    public void setUp(){
        baseURI = "https://serverest.dev";
        // port = 
        // basePath = "/v2";

        this.usuarioAdministrador = UsuarioDataFactory.criarUsuarioAdm();
        this.usuarioComum = UsuarioDataFactory.criarUsuarioComum();
        
    }

    @Test
    @DisplayName("Cadastrar um usuario administrador")
    public void testCadastrarAdmComSucesso(){

        String useeeeeeer = "{\n" +
              "  \"nome\": \"useeeeeeRr\",\n" +
              "  \"email\": \"haetdeehRbejehdgeghfe@qa.com.br\",\n" +
              "  \"password\": \"teste\",\n" +
              "  \"administrador\": \"false\"\n" +
              "}";


        String message = given()
            .contentType(ContentType.JSON)
            .body(useeeeeeer)
        .when()
            .post("/usuarios")
        .then()
            .log().all()
            .statusCode(201)
            .extract()
            .path("message");

        assertEquals("Cadastro realizado com sucesso", message);
    }
}
