package bcm.test.api;

import bcm.test.model.Temperature;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.when;

public class TemperatureServiceTest {

    @Test
    public void retrieveListOfTemperatures_test(){

        TemperatureClient client = Mockito.mock(TemperatureClient.class);

        when(client.getData()).thenReturn("[\n" +
                "  {\n" +
                "    \"id\": \"1b16b092-e8e8-490b-a9be-0db1580fb164\",\n" +
                "    \"timestamp\": \"2019-02-19 14:02:10\",\n" +
                "    \"lat\": 45.6258385,\n" +
                "    \"lon\": 0.0629891,\n" +
                "    \"temperature\": 29.4068,\n" +
                "    \"country\": \"France\"\n" +
                "  }\n" +
                "]");

        TemperatureService service = new TemperatureService(client);

        List<Temperature> result = service.retrieveListOfTemperatures();

        Assert.assertEquals(result.size(),1);
        Assert.assertEquals(result.get(0).getId(),"1b16b092-e8e8-490b-a9be-0db1580fb164");
        Assert.assertEquals(result.get(0).getLatitude(),45.6258385, 0);
        Assert.assertEquals(result.get(0).getLongitude(),0.0629891, 0);
        Assert.assertEquals(result.get(0).getTemperature(), 29.4068, 0);
        Assert.assertEquals(result.get(0).getTimestamp(), "2019-02-19 14:02:10");
        Assert.assertEquals(result.get(0).getCountry(), "France");
    }
}
