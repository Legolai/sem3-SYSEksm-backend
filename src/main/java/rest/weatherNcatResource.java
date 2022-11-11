package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.catDTO;
import dtos.weatherCatDTO;
import dtos.weatherDTO;
import utils.HttpUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author lam@cphbusiness.dk
 */
@Path("weatherNcat")
public class weatherNcatResource {

    @Context
    private UriInfo context;
    private String urlCat = "https://api.thecatapi.com/v1/images/search";
    private String urlWeatherBase = "https://vejr.eu/api.php?location=Koebenhavn&degree=C";
    private String urlWeatherGet = "https://vejr.eu/api.php?location=x&degree=C";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCatUrl() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String res = HttpUtils.fetchData(urlCat);
        catDTO[] catDTO = new Gson().fromJson(res, catDTO[].class);

        res = HttpUtils.fetchData(urlWeatherBase);
        weatherDTO weather = gson.fromJson(res, weatherDTO.class);

        weatherCatDTO weatherCat = new weatherCatDTO(weather, catDTO[0]);
        String result = gson.toJson(weatherCat);

        return Response.ok().entity(result).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getXWeatherInC(String content) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Object city = gson.fromJson(content, Object.class);
        String cityName = city.toString().substring(6,city.toString().length()-1);

        String url = urlWeatherGet.replace("x", cityName);
        String res = HttpUtils.fetchData(url);
        weatherDTO weather = gson.fromJson(res, weatherDTO.class);
        String weatherData = gson.toJson(weather);

        return weatherData;
    }


}