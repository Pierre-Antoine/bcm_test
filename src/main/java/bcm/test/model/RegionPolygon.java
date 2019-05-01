package bcm.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RegionPolygon implements GeoPolygon{

    String type;
    List<List<GeoPoint>> geometry;

    public RegionPolygon(@JsonProperty("type") String type,
                         @JsonProperty("coordinates") List<List<GeoPoint>> geometry) {
        this.type = type;
        this.geometry = geometry;
    }

    public String getType() {
        return type;
    }

    public List<List<GeoPoint>> getGeometry() {
        return geometry;
    }



    public double getMinX() {
        return  geometry.stream()
                .flatMap(List::stream)
                .map(geoPoint -> geoPoint.getX())
                .min(Double::compare)
                .get();
    }

    public double getMinY() {
        return  geometry.stream()
                .flatMap(List::stream)
                .map(geoPoint -> geoPoint.getY())
                .min(Double::compare)
                .get();
    }

    public double getMaxX() {
        return  geometry.stream()
                .flatMap(List::stream)
                .map(geoPoint -> geoPoint.getX())
                .max(Double::compare)
                .get();
    }

    public double getMaxY() {
        return  geometry.stream()
                .flatMap(List::stream)
                .map(geoPoint -> geoPoint.getY())
                .max(Double::compare)
                .get();
    }
}
