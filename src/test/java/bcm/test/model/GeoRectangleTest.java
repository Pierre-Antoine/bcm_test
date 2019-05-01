package bcm.test.model;

import org.junit.Assert;
import org.junit.Test;

public class GeoRectangleTest {
    @Test
    public void containsGeoPoint_test() {
        GeoRectangle rectangle = new GeoRectangle(new GeoPoint(6.921300744217509, 47.6745526569768),new GeoPoint(5.411654472567439, 47.4045526569768));

        Assert.assertFalse(rectangle.containsGeoPoint(0.0629891,45.6258385));
        Assert.assertTrue(rectangle.containsGeoPoint(6.521300744217509,47.5745526569768));
    }
}
