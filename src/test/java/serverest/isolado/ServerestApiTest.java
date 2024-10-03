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
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import serverest.pojo.LoginPojo;
import serverest.pojo.UsuarioPojo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServerestApiTest {

    private static String tokenAdm;
    private static String idUm;

    private UsuarioPojo usuario;
    private LoginPojo loginUsuAdm;

    @BeforeEach
    public void setUp() {
        baseURI = "https://serverest.dev";
        // port =
        // basePath = "/v2";

        // this.loginAdministrador = LoginDataFactory.fazerLoginComAdm();
        // this.loginComum = LoginDataFactory.fazerLoginComUsuarioComum();

    }

    @Order(1)
    @Test
    @DisplayName("Cadastrar um usuario administrador")
    public void testCadastrarAdmComSucesso() {

        this.usuario = new UsuarioPojo("user1128", "user1128@email.com.br", "user1128", "true");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .response();

        idUm = response.path("_id");
        System.out.println("ID capturado: " + idUm);
    }

    @Order(2)
    @Test
    @DisplayName("Fazer login e obter sucesso com usuario administrar")
    public void testLoginComAdm() {

        this.loginUsuAdm = new LoginPojo("user1128@email.com.br", "user1128");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(loginUsuAdm)
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        tokenAdm = response.path("authorization");

        assertNotNull(tokenAdm, "O token não deveria ser nulo");
        assertTrue(!tokenAdm.isEmpty(), "O token não deveria ser vazio");

    }

    @Order(3)
    @Test
    @DisplayName("Fazer login sem sucesso com usuario administrar")
    public void testLoginComAdmSemSucesso() {

        try {
            this.loginUsuAdm = new LoginPojo("user109@email.com", "user105");

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

    @Order(4)
    @Test
    @DisplayName("Deve retornar uma lista de usuarios previamente cadastrados")
    public void testDeveRetornarListaUsuarios() {

        Response response = given()
                .queryParam("nome", "user1128")
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

    @Order(5)
    @Test
    @DisplayName("Deve retornar uma lista de usuarios previamente cadastrados sem parametros")
    public void testDeveRetornarListaUsuariosSemEnvioDeParametros() {

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

    @Disabled
    @Order(6)
    @Test
    @DisplayName("Deve retornar um usuario previamente cadastrado pelo ID")
    public void testDeveRetornarUmUsuarioPorID() {

        Response response = given()
                .pathParam("_id", idUm)
                .when()
                .get("/usuarios/{_id}")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        String responseBody = response.getBody().asString();

        assertNotNull(responseBody, "A resposta não deveria ser nula");
    }

    @Disabled
    @Order(7)
    @Test
    @DisplayName("Deve realizar a alteração de um usuario previamente cadastrado")
    public void testAlterarUmUsuarioCadastrado() {

        UsuarioPojo usuarioEditado = new UsuarioPojo("user1126", "user1126@email.com.br", "user1126", "true");

        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("_id", idUm)
                .body(usuarioEditado)
                .when()
                .put("/usuarios/{_id}")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        String responseBody = response.getBody().asString();

        assertNotNull(responseBody, "A resposta não deveria ser nula.");

    }

    @Order(8)
    @Test
    @DisplayName("Deve retornar erro ao tentar editar usuário que não existe")
    public void testDeveRetornarErroAoEditarUsuarioInexistente() {
        String usuarioIDInexistente = "inexistenteID"; // ID de um usuário que não existe

        UsuarioPojo usuarioEditado = new UsuarioPojo("inexistente", "inexistente@qa.com.br", "fbatista", "false");

        given()
                .contentType(ContentType.JSON)
                .pathParam("_id", usuarioIDInexistente)
                .body(usuarioEditado)
                .when()
                .put("/usuarios/{_id}")
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Este email já está sendo usado"));
    }

    @Order(9)
    @Test
    @DisplayName("Deve excluir um usuário existente")
    public void testDeveExcluirUsuarioExistente() {

        Response response = given()
                .pathParam("_id", idUm)
                .when()
                .delete("/usuarios/{_id}")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        String expectedMessage = "Registro excluído com sucesso";

        assertTrue(responseBody.contains(expectedMessage));
    }

}
