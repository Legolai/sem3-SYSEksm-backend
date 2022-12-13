package rest;

import entities.Account;
import entities.FoocleScout;
import entities.Notification;
import entities.StatusType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class NotificationEndpointTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    private Notification notification;
    private Account account;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createQuery("delete from Review").executeUpdate();
            em.createQuery("delete from ScoutRequest").executeUpdate();
            em.createQuery("delete from SpotMenu").executeUpdate();
            em.createQuery("delete from FoocleSpot ").executeUpdate();
            em.createQuery("delete from Notification").executeUpdate();
            em.createQuery("delete from BusinessAccount").executeUpdate();
            em.createQuery("delete from FoocleBusiness").executeUpdate();
            em.createQuery("delete from FoocleScout").executeUpdate();
            em.createQuery("delete from Account").executeUpdate();
            em.createQuery("delete from Location").executeUpdate();

            account = new Account("test1@email.com", "+45 98 99 99 99", "test1", "testsen", "test1234");
            FoocleScout foocleScout = new FoocleScout(account);
            notification = new Notification();
            notification.setAccount(account);
            notification.setMessage("Hey there I am new");
            notification.setStatus(StatusType.NEW);
            notification.setInstructions("");

            em.persist(account);
            em.persist(foocleScout);
            em.persist(notification);
            //System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void loginScout(String email, String password) {
        String json = String.format("{email: \"%s\", password: \"%s\"}", email, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login/scout")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void serverIsRunning() {
        given().when().get("/info").then().statusCode(200);
    }


    @Test
    public void getAll() {
        loginScout("test1@email.com", "test1234");
        given()
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/notification").then()
                .statusCode(200)
                .body("",hasSize(1));
    }

    @Test
    public void getAllNew() {
        loginScout("test1@email.com", "test1234");
        given()
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/notification/new").then()
                .statusCode(200)
                .body("", hasSize(1));
    }

    @Test
    public void updateToSeen() {
        loginScout("test1@email.com", "test1234");
        given()
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .put("/notification/"+notification.getId()+"/seen").then()
                .statusCode(200);
    }
}
