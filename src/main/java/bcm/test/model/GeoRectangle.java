package bcm.test.model;

public class GeoRectangle {

    GeoPoint upperPoint;
    GeoPoint lowerPoint;

    public GeoRectangle(GeoPoint upperPoint, GeoPoint lowerPoint) {
        this.upperPoint = upperPoint;
        this.lowerPoint = lowerPoint;
    }

    public Boolean containsGeoPoint(double x, double y){
        GeoPoint geoPoint = new GeoPoint(x,y);

        if (geoPoint.getX() <= upperPoint.getX()
        && geoPoint.getX() >= lowerPoint.getX()
        && geoPoint.getY() <= upperPoint.getY()
        && geoPoint.getY() >= lowerPoint.getX()){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public GeoPoint getUpperPoint() {
        return upperPoint;
    }

    public GeoPoint getLowerPoint() {
        return lowerPoint;
    }
}
