package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Location;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GeoCodeFetcher {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String API_URL = "https://geocode.maps.co/search?";



    private class GeoCodeDataObject {
        private Long place_id;
        private String licence;
        private String powered_by;
        private String osm_type;
        private Long osm_id;
        private List<String> boundingbox;
        private String lat;
        private String lon;
        private String display_name;
        private String type;
        private Double importance;

        public GeoCodeDataObject(Long place_id, String licence, String powered_by, String osm_type, Long osm_id, List<String> boundingbox, String lat, String lon, String display_name, String type, Double importance) {
            this.place_id = place_id;
            this.licence = licence;
            this.powered_by = powered_by;
            this.osm_type = osm_type;
            this.osm_id = osm_id;
            this.boundingbox = boundingbox;
            this.lat = lat;
            this.lon = lon;
            this.display_name = display_name;
            this.type = type;
            this.importance = importance;
        }

        public Long getPlace_id() {
            return place_id;
        }

        public String getLicence() {
            return licence;
        }

        public String getPowered_by() {
            return powered_by;
        }

        public String getOsm_type() {
            return osm_type;
        }

        public Long getOsm_id() {
            return osm_id;
        }

        public List<String> getBoundingbox() {
            return boundingbox;
        }

        public String getLat() {
            return lat;
        }

        public String getLon() {
            return lon;
        }

        public String getDisplay_name() {
            return display_name;
        }

        public String getType() {
            return type;
        }

        public Double getImportance() {
            return importance;
        }
    }

    public class GeoCode {
        private final String longitude;
        private final String latitude;

        public GeoCode(String longitude, String latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        @Override
        public String toString() {
            return "GeoCode{" +
                    "longitude=" + longitude +
                    ", latitude=" + latitude +
                    '}';
        }
    }

    public GeoCode fetchGeoCode(Location location) throws IOException {
        String searchUrl = createSearchUrl(location);
        String json = HttpUtils.fetchData(searchUrl);
        List<GeoCodeDataObject> data = Arrays.asList(GSON.fromJson(json, GeoCodeDataObject[].class));
        if (data.isEmpty()) {
            throw new RuntimeException("GeoCode for location could not be found!");
        }
        GeoCodeDataObject object = data.get(0);
        return new GeoCode(object.getLon(), object.getLat());
    }

    private String createSearchUrl(Location location) {
        StringBuilder url = new StringBuilder(API_URL);
        String street = String.format("street=%s", location.getAddress()).replace(" ", "+");
        String city = String.format("city=%s", location.getCity()).replace(" ", "+");
        String zipCode = String.format("postalcode=%s", location.getZipCode()).replace(" ", "+");
        String country = String.format("country=%s", location.getCountry()).replace(" ", "+");
        url.append(street).append("&").append(city).append("&").append(zipCode).append("&").append(country);
        return url.toString();
    }

}
