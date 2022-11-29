package rest;

import com.google.gson.*;
import dtos.FoocleScoutDTO;
import errorhandling.API_Exception;
import errorhandling.GenericExceptionMapper;
import facades.FoocleScoutFacade;
import utils.EMF_Creator;
import utils.GsonLocalDateTime;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
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
        String email, phoneNumber;
        String firstname, lastname, password;
        try {
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            email = json.get("email").getAsString();
            phoneNumber = json.get("phoneNumber").getAsString();
            firstname = json.get("firstname").getAsString();
            lastname = json.get("lastname").getAsString();
            password = json.get("password").getAsString();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Supplied",400,e);
        }

        try {
            FoocleScoutDTO scout = SCOUT_FACADE.createScout(email, phoneNumber, firstname, lastname, password);
            return Response.ok(GSON.toJson(scout)).build();

        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("Failed to create a new FoocleScout account!");
    }
}