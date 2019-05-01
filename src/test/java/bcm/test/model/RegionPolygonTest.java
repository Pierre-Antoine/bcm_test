package bcm.test.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RegionPolygonTest {

    List<GeoPoint> coordinates;
    List<List<GeoPoint>> globalCoordinates;
    RegionPolygon polygon;

    @Before
    public void init() {
        coordinates = new ArrayList<>();
        coordinates.add(new GeoPoint(6.940790195722472, 47.43332425184562));
        coordinates.add(new GeoPoint(6.94065973323084, 47.431022225751434));
        coordinates.add(new GeoPoint(6.940990381448314, 47.42944408233319));
        coordinates.add(new GeoPoint(6.942130608356922, 47.41632441257718));

        globalCoordinates = new ArrayList<>();
        globalCoordinates.add(coordinates);

        polygon = new RegionPolygon("Polygon", globalCoordinates);
    }

    @Test
    public void getMinX_test(){
        Assert.assertEquals(polygon.getMinX(),6.94065973323084,0);
    }

    @Test
    public void getMaxX_test(){
        Assert.assertEquals(polygon.getMaxX(),6.942130608356922,0);
    }

    @Test
    public void getMinY_test(){
        Assert.assertEquals(polygon.getMinY(),47.41632441257718,0);
    }

    @Test
    public void getMaxY_test(){
        Assert.assertEquals(polygon.getMaxY(),47.43332425184562,0);
    }

    @Test
    public void getBoundingBox_test(){
        GeoRectangle result = polygon.getBoundingBox();

        Assert.assertEquals(result.getLowerPoint().getX(),6.94065973323084,0);
        Assert.assertEquals(result.getLowerPoint().getY(),47.41632441257718,0);
        Assert.assertEquals(result.getUpperPoint().getX(),6.942130608356922,0);
        Assert.assertEquals(result.getUpperPoint().getY(),47.43332425184562,0);
    }
}
