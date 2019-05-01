package bcm.test.model;

public interface GeoPolygon {

    double getMinX();
    double getMinY();
    double getMaxX();
    double getMaxY();
    
    default GeoRectangle getBoundingBox() {

        double minX = getMinX();
        double minY = getMinY();
        double maxX = getMaxX();
        double maxY = getMaxY();

        return new GeoRectangle(new GeoPoint(maxX, maxY), new GeoPoint(minX, minY));

    }

    String getType();
}
