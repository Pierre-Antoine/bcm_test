package bcm.test.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class GeoPoint {

    double x;
    double y;

    public GeoPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @JsonCreator
    public GeoPoint(double[] coordinates) {
        this.x = coordinates[0];
        this.y = coordinates[1];
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
