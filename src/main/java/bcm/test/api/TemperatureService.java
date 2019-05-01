package bcm.test.api;

import bcm.test.model.Temperature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TemperatureService {

    private final Logger LOGGER = Logger.getLogger(TemperatureService.class.getName());

    ObjectMapper mapper;
    TemperatureClient client;

    public TemperatureService(TemperatureClient client) {
        this.client = client;
        this.mapper = new ObjectMapper();
    }

    public List<Temperature> retrieveListOfTemperatures(){

        List<Temperature> temperatures = new ArrayList<Temperature>();

        try {
            temperatures = mapper.readValue(client.getData(), TypeFactory.defaultInstance().constructCollectionType(List.class, Temperature.class));
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }

        return temperatures;
    }

    public List<Temperature> filterListOfTemperatureOnlyFrance(List<Temperature> temperatureList) {
        return temperatureList.stream()
                .filter(temperature -> temperature.getCountry().equals("France"))
                .collect(Collectors.toList());
    }
}
