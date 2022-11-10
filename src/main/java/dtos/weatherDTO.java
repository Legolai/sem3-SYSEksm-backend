package dtos;

public class weatherDTO {
    private String LocationName;
    private weatherData CurrentData;

    public weatherDTO(String locationName, weatherData currentData) {
        LocationName = locationName;
        CurrentData = currentData;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public weatherData getCurrentData() {
        return CurrentData;
    }

    public void setCurrentData(weatherData currentData) {
        CurrentData = currentData;
    }


    private class weatherData {
        private String temperature;
        private String skyText;
        private String humidity;
        private String windText;
        public weatherData(String temperature, String skyText, String humidity, String windText) {
            this.temperature = temperature;
            this.skyText = skyText;
            this.humidity = humidity;
            this.windText = windText;
        }
        public String getTemperature() {
            return temperature;
        }
        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }
        public String getSkyText() {
            return skyText;
        }
        public void setSkyText(String skyText) {
            this.skyText = skyText;
        }
        public String getHumidity() {
            return humidity;
        }
        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }
        public String getWindText() {
            return windText;
        }
        public void setWindText(String windText) {
            this.windText = windText;
        }
        @Override
        public String toString() {
            return "weatherData{" + "temperature='" + temperature + '\'' + ", skyText='" + skyText + '\'' + ", humidity='" + humidity + '\'' + ", windText='" + windText + '\'' + '}';
        }
    }

    @Override
    public String toString() {
        return "weatherDTO{" + "LocationName='" + LocationName + '\'' + ", CurrentData=" + CurrentData.toString() + '}';
    }
}
