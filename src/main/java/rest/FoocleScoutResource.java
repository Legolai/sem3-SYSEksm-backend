package rest;

import com.google.gson.*;
import com.nimbusds.jose.JOSEException;
import dtos.FoocleScoutDTO;
import entities.Role;
import entities.User;
import errorhandling.API_Exception;
import errorhandling.GenericExceptionMapper;
import facades.FoocleScoutFacade;
import facades.UserFacade;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;
import utils.GsonLocalDateTime;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/scout")
public class FoocleScoutResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final FoocleScoutFacade SCOUT_FACADE = FoocleScoutFacade.getFoocleScoutFacade(EMF);
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTime()).setPrettyPrinting().create();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewScout(String content) throws API_Exception {
        String firstname, lastname;
        String email;
        String password;
        String phoneNumber, areaCode;
        try {
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            firstname = json.get("firstname").getAsString();
            lastname = json.get("lastname").getAsString();
            email = json.get("email").getAsString();
            password = json.get("password").getAsString();
            phoneNumber = json.get("phoneNumber").getAsString();
            areaCode = json.get("areaCode").getAsString();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Supplied",400,e);
        }

        try {
            FoocleScoutDTO scout = SCOUT_FACADE.createScout(email, firstname, lastname, password, phoneNumber, areaCode);
            return Response.ok(GSON.toJson(scout)).build();

        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("Failed to create a new FoocleScout account!");

    }
}