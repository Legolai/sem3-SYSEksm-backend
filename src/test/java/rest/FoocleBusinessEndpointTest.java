package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ScoutRequestDTO;
import entities.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import junit.framework.Assert;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsArrayWithSize;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import utils.GsonLocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FoocleBusinessEndpointTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTime()).setPrettyPrinting().create();
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    private BusinessAccount businessAccount;
    private ScoutRequest scoutRequest;

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

            Account account = new Account("test@email.com", "+45 99 99 99 99", "test", "test", "test123");
            Account account1 = new Account("test1@email.com", "+45 98 99 99 99", "test1", "testsen", "test1234");
            FoocleScout foocleScout = new FoocleScout(account1);
            Location location = new Location("Hyrdehøj Bygade 6A", "Roskilde", "4000", "Danmark");
            Location location1 = new Location("Hyrdehøj Bygade 7A", "Roskilde", "4000", "Danmark");
            FoocleBusiness foocleBusiness = new FoocleBusiness("1", "testbusiness", "business@email.com", "+45 12 34 56 78", "There no description", location);
            businessAccount = new BusinessAccount(true, account, foocleBusiness);

            LocalDateTime time = LocalDateTime.now();
            time = time.minusNanos(time.getNano());
            FoocleSpot foocleSpot = new FoocleSpot(businessAccount, foocleBusiness, location);
            SpotMenu spotMenu = new SpotMenu("Very tasty", "https://s.inyourpocket.com/gallery/reykjavik/2020/02/270772.jpg", "vegan", time, time, foocleSpot);
            scoutRequest = new ScoutRequest("I like this food", "PENDING", spotMenu, foocleScout);




            em.persist(account);
            em.persist(account1);
            em.persist(location);
            em.persist(location1);
            em.persist(foocleScout);
            em.persist(foocleBusiness);
            em.persist(businessAccount);
            em.persist(foocleSpot);
            em.persist(spotMenu);
            em.persist(scoutRequest);

            //System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void loginBusiness(String email, String password) {
        String json = String.format("{email: \"%s\", password: \"%s\"}", email, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login/business")
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
    public void createBusinessAccount() {
        String json = String.format("" +
                "{" +
                "cvr: \"%s\", " +
                "name: \"%s\", " +
                "description: \"%s\", " +
                "businessEmail: \"%s\", " +
                "businessPhone: \"%s\", " +
                "address: \"%s\", " +
                "city: \"%s\", " +
                "zipCode: \"%s\", " +
                "country: \"%s\", " +
                "firstname: \"%s\", " +
                "lastname: \"%s\", " +
                "email: \"%s\", " +
                "password: \"%s\", " +
                "phone: \"%s\"}",
                "123",
                "NewBuziness",
                "None",
                "bizz@email.com",
                "+45 90 90 90 90",
                "Kildebrøndevej 50",
                "Greve",
                "2670",
                "Danmark",
                "Mick",
                "Mouse",
                "mm@email.com",
                "1234test",
                "+45 77 77 77 88");


        given().body(json)
        .contentType(ContentType.JSON)
        .when()
        .post("/business/").then()
        .statusCode(200)
        .body("cvr", equalTo("123"));
    }

    @Test
    public void getRequests() {
        loginBusiness("test@email.com","test123");
        given()
            .accept(ContentType.JSON)
            .header("x-access-token", securityToken)
            .when()
            .get("/business/"+businessAccount.getId()+"/request").then()
            .statusCode(200).body("", hasSize(1));

    }

    @Test
    public void getRelevantRequest() {
        loginBusiness("test@email.com","test123");
        given()
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/business/"+businessAccount.getId()+"/relevantRequest").then()
                .statusCode(200).body("", hasSize(0));
    }

    @Test
    public void updateRequestState() {
        loginBusiness("test@email.com","test123");
        String json = String.format("{id: %d, scoutID: %d, status: \"%s\"}", scoutRequest.getId(), scoutRequest.getFooclescout().getId(), "ACCPETED");
        given().body(json)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .put("/business/request").then()
                .statusCode(200);

    }
}
