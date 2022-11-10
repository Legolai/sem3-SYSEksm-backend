package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.catDTO;
import dtos.weatherDTO;
import entities.User;
import utils.EMF_Creator;
import utils.HttpUtils;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
@Path("api2")
public class api2Resource {

    @Context
    private UriInfo context;
    private String urlBase = "https://vejr.eu/api.php?location=Koebenhavn&degree=C";
    private String urlGet = "https://vejr.eu/api.php?location=x&degree=C";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCopenhagenWeather() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String res = HttpUtils.fetchData(urlBase);
        weatherDTO weather = gson.fromJson(res, weatherDTO.class);
        //This is what your endpoint should return
        String weatherData = gson.toJson(weather);

        return weatherData;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getXWeatherInC(String content) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Object city = gson.fromJson(content, Object.class);
        String cityName = city.toString().substring(6,city.toString().length()-1);

        String url = urlGet.replace("x", cityName);
        String res = HttpUtils.fetchData(url);
        weatherDTO weather = gson.fromJson(res, weatherDTO.class);
        String weatherData = gson.toJson(weather);

        return weatherData;
    }

}