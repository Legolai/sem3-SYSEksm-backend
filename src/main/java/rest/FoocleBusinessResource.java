package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.FoocleBusinessDTO;
import dtos.FoocleScoutDTO;
import errorhandling.API_Exception;
import errorhandling.GenericExceptionMapper;
import facades.FoocleBusinessFacade;
import facades.FoocleScoutFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/business")
public class FoocleBusinessResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final FoocleBusinessFacade BUSINESS_FACADE = FoocleBusinessFacade.getFoocleBusinessFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewBusiness(String content) throws API_Exception {
        String cvr, name, businessEmail, description;
        String address, city, zipCode, country;
        String firstname, lastname, businessAccountEmail, password;
        String phoneNumber, areaCode;

        try {
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            cvr = json.get("cvr").getAsString();
            name = json.get("name").getAsString();
            businessEmail = json.get("businessEmail").getAsString();
            description = json.get("description").getAsString();

            address = json.get("address").getAsString();
            city = json.get("city").getAsString();
            zipCode = json.get("zipCode").getAsString();
            country = json.get("country").getAsString();

            firstname = json.get("firstname").getAsString();
            lastname = json.get("lastname").getAsString();
            businessAccountEmail = json.get("businessAccountEmail").getAsString();
            password = json.get("password").getAsString();

            phoneNumber = json.get("phoneNumber").getAsString();
            areaCode = json.get("areaCode").getAsString();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Supplied",400,e);
        }

        try {
            FoocleBusinessDTO scout = BUSINESS_FACADE.createBusiness(cvr, name, businessEmail, description, address, city, zipCode, country, firstname, lastname, businessAccountEmail, password, phoneNumber, areaCode);
            return Response.ok(GSON.toJson(scout)).build();

        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("Failed to create a new FoocleBusiness!");

    }
}