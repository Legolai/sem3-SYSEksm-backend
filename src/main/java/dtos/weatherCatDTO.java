package dtos;

public class weatherCatDTO {

    private weatherDTO weather;
    private catDTO cat;

    public weatherCatDTO(weatherDTO weather, catDTO cat) {
        this.weather = weather;
        this.cat = cat;
    }

    public weatherDTO getWeather() {
        return weather;
    }

    public void setWeather(weatherDTO weather) {
        this.weather = weather;
    }

    public catDTO getCat() {
        return cat;
    }

    public void setCat(catDTO cat) {
        this.cat = cat;
    }
}
