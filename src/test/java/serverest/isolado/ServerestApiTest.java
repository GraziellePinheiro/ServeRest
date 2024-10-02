package serverest.isolado;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import org.aeonbits.owner.ConfigFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import serverest.pojo.LoginPojo;
import serverest.pojo.UsuarioPojo;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServerestApiTest {
    
    private static String tokenAdm;
    private UsuarioPojo usuario;
    private LoginPojo loginUsuAdm;

    @BeforeEach
    public void setUp(){
        baseURI = "https://serverest.dev";
        // port = 
        // basePath = "/v2";

        
        
        // this.loginAdministrador = LoginDataFactory.fazerLoginComAdm();
        // this.loginComum = LoginDataFactory.fazerLoginComUsuarioComum();
        
    }

    @Disabled
    @Test
    @DisplayName("Cadastrar um usuario administrador")
    public void testCadastrarAdmComSucesso(){
        
        this.usuario = new UsuarioPojo("user107", "user107@email.com.br", "user105", "true");

        String message = given()
            .contentType(ContentType.JSON)
            .body(usuario)
        .when()
            .post("/usuarios")
        .then()
            .log().all()
            .statusCode(201)
            .extract()
            .path("message");

        assertEquals("Cadastro realizado com sucesso", message);
    }

    
    @Test
    @DisplayName("Fazer login e obter sucesso com usuario administrar")
    public void testLoginComAdm(){

        this.loginUsuAdm = new LoginPojo("user107@email.com.br", "user105");

        tokenAdm = given()
            .contentType(ContentType.JSON)
            .body(loginUsuAdm)
        .when()
            .post("/login")
        .then()
            .log().all()
            .statusCode(200)
            .extract()
            .path("authorization");
        assertNotNull(tokenAdm, "O token não deveria ser nulo");
        assertFalse(tokenAdm.isEmpty(), "O token não deveria ser vazio");
    }
    @Test
    @DisplayName("Fazer login sem sucesso com usuario administrar")
    public void testLoginComAdmSemSucesso(){

        try {
            this.loginUsuAdm = new LoginPojo("user107@email.com", "user105");

         String message = given()
            .contentType(ContentType.JSON)
            .body(loginUsuAdm)
         .when()
            .post("/login")
         .then()
            .log().all()
            .statusCode(401)
            .extract()
            .path("message");

         assertEquals("Email e/ou senha inválidos", message);
        
        } catch (Exception e) {
            fail("Falha ao fazer login: " + e.getMessage());
        }
    }


    @Test
    @DisplayName("Deve retornar uma lista de usuarios previamente cadastrados")
    public void testDeveRetornarListaUsuarios(){

        Response response = given()
            .queryParam("nome", "user107")
            .queryParam("administrador", "true")
        .when()
            .get("/usuarios")
        .then()
            .log().all()
            .statusCode(200)
            .extract()
            .response();

        String responseBody = response.getBody().asString();
        
        assertNotNull(responseBody, "A resposta não deveria ser nula");

    }

    @Test
    @DisplayName("Deve retornar uma lista de usuarios previamente cadastrados sem parametros")
    public void testDeveRetornarListaUsuariosSemEnvioDeParametros(){

        Response response = when()
            .get("/usuarios")
        .then()
            .log().all()
            .statusCode(200)
            .extract()
            .response();

        String responseBody = response.getBody().asString();
        
        assertNotNull(responseBody, "A resposta não deveria ser nula");
    }

    @Test
    @DisplayName("Deve retornar um usuario previamente cadastrado pelo ID")
    public void testDeveRetornarUmUsuarioPorID(){

        String usuarioID = "N2PobnQXQZJEKBbG";

        try {
            Response response = given()
            .pathParam("_id", usuarioID)        
         .when()
            
            .get("/usuarios/{_id}")
         .then()
            .log().all()
            .statusCode(200)
            .extract()
            .response();

         String responseBody = response.getBody().asString();
        
         assertNotNull(responseBody, "A resposta não deveria ser nula");
        } catch (Exception e) {
            fail("Erro ao buscar usuário: " + e.getMessage());
        }
    }


}
