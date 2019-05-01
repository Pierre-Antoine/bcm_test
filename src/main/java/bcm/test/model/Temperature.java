package bcm.test.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Temperature {

    String id;
    String timestamp;
    double latitude;
    double longitude;
    double temperature;
    String country;
    String region;

    @JsonCreator
    public Temperature(@JsonProperty("id") String id,
                       @JsonProperty("timestamp") String timestamp,
                       @JsonProperty("lat") double latitude,
                       @JsonProperty("lon") double longitude,
                       @JsonProperty("temperature") double temperature,
                       @JsonProperty("country") String country) {
        this.id = id;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature = temperature;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getCountry() {
        return country;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }
}
