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
        String res = HttpUtils.fetchData(urlBase);
        City city = gson.fromJson(res, City.class);
        System.out.println(city.toString());

        String url = urlGet.replace("x", city.getCityName());
        String weather = HttpUtils.fetchData(url);
        weatherDTO weatherDTO = gson.fromJson(weather, weatherDTO.class);
        String weatherData = gson.toJson(weatherDTO);

        return weatherData;
    }

    private class City {
        private String cityName;
        public City(String cityName) {
            this.cityName = cityName;
        }
        public String getCityName() {
            return cityName;
        }
        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
        @Override
        public String toString() {
            return "City{" + "cityName='" + cityName + '\'' + '}';
        }
    }

}