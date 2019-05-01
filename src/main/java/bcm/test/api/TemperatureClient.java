package bcm.test.api;

import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

public class TemperatureClient implements DataSourceClient {

    String url;
    String apiKey;

    Client initClient(){
        ClientConfig config = new ClientConfig();
        return ClientBuilder.newClient(config);

    }

    public TemperatureClient(String url, String apiKey) {
        this.url = url;
        this.apiKey = apiKey;
    }

    public String getData() {
        Client client = initClient();

        WebTarget service = client.target(url);

        return service.path("/")
                .request().accept(MediaType.APPLICATION_JSON)
                .header("X-API-Key",apiKey)
                .get(String.class);
    }

    @Override
    public InputStream streamData() throws Exception{
        throw new Exception("Nn streaming available from this client.");
    }
}
