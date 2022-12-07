package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.*;
import errorhandling.API_Exception;
import errorhandling.GenericExceptionMapper;
import facades.FoocleBusinessFacade;
import facades.FoocleScoutFacade;
import security.Permission;
import utils.EMF_Creator;
import utils.GsonLocalDateTime;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/business")
public class FoocleBusinessResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final FoocleBusinessFacade BUSINESS_FACADE = FoocleBusinessFacade.getFoocleBusinessFacade(EMF);
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTime()).setPrettyPrinting().create();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewBusiness(String content) throws API_Exception {
        String cvr, name, businessEmail, businessPhoneNumber, description;
        String address, city, zipCode, country;
        String firstname, lastname, businessAccountEmail, phoneNumber, password;

        try {
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            cvr = json.get("cvr").getAsString();
            name = json.get("name").getAsString();
            description = json.get("description").getAsString();
            businessEmail = json.get("businessEmail").getAsString();
            businessPhoneNumber = json.get("businessPhone").getAsString();

            address = json.get("address").getAsString();
            city = json.get("city").getAsString();
            zipCode = json.get("zipCode").getAsString();
            country = json.get("country").getAsString();

            firstname = json.get("firstname").getAsString();
            lastname = json.get("lastname").getAsString();
            businessAccountEmail = json.get("email").getAsString();
            password = json.get("password").getAsString();

            phoneNumber = json.get("phone").getAsString();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Supplied",400,e);
        }

        try {
            FoocleBusinessDTO business = BUSINESS_FACADE.createBusiness(cvr, name, businessEmail, businessPhoneNumber, description, address, city, zipCode, country, businessAccountEmail, phoneNumber, firstname, lastname, password);
            return Response.ok(GSON.toJson(business)).build();

        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("Failed to create a new FoocleBusiness!");
    }




}
