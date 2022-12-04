package utils;

import entities.Location;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GeoCodeFetcherTest {


    @Test
    void getGeoCode() throws IOException {
        Location location = new Location();
        location.setAddress("2 nybrovej");
        location.setCity("gentofte");
        location.setZipCode("2820");
        location.setCountry("danmark");
        GeoCodeFetcher geoCodeFetcher = new GeoCodeFetcher();
        GeoCodeFetcher.GeoCode geoCode = geoCodeFetcher.fetchGeoCode(location);
        System.out.println(geoCode);

    }
}