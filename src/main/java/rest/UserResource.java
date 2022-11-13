package rest;

import com.google.gson.*;
import com.nimbusds.jose.JOSEException;
import entities.Role;
import entities.User;
import errorhandling.API_Exception;
import errorhandling.GenericExceptionMapper;
import facades.UserFacade;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/user")
public class UserResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final UserFacade USER_FACADE = UserFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewUser(String content) throws API_Exception {
        String username;
        String password;
        List<Role> roles = new ArrayList<>();
        try {
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            username = json.get("username").getAsString();
            password = json.get("password").getAsString();
            for (JsonElement role : json.get("roles").getAsJsonArray()) {
                roles.add(new Role(role.getAsString()));
            }
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied",400,e);
        }

        try {
            User user = USER_FACADE.createUser(username, password, roles);
            return Response.ok(GSON.toJson(user)).build();

        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("Failed to create a new account!");

    }
}