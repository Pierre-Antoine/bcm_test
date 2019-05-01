package bcm.test.batch;

import bcm.test.api.RegionClient;
import bcm.test.api.RegionService;
import bcm.test.api.TemperatureClient;
import bcm.test.api.TemperatureService;
import bcm.test.dao.TableTemperaturesDao;
import bcm.test.model.GeoRectangle;
import bcm.test.model.Temperature;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Launcher {

    private static final Logger LOGGER = Logger.getLogger(TemperatureService.class.getName());

    public static void main(String... args){

        LOGGER.info("Début du programme d'import des températures");

        LocalDateTime importDate = LocalDateTime.now();

        Properties props = new Properties();
        InputStream fis = null;
        MysqlDataSource dataSource = null;

        try {
            fis = new FileInputStream("database.properties");
            props.load(fis);
            dataSource = new MysqlDataSource();
            dataSource.setURL(props.getProperty("MYSQL_DB_URL"));
            dataSource.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            dataSource.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
        } catch (FileNotFoundException e) {
            LOGGER.warning("Fichier properties non trouvé. Fin du programme.");
            System.exit(1);
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }

        try {
        LOGGER.info("Connexion à la base de données");
            dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.warning("Pas de connexion à la base de données, fin du programme d'import des températures.");
            System.exit(1);
        }

        // regions mapping
        LOGGER.info("Récupération des régions...");
        RegionClient regionClient = new RegionClient("regions/contours.csv");
        RegionService regionService = new RegionService(regionClient);

        Map<String, GeoRectangle> regionBounds = regionService.retrieveRegionsBounds();

        LOGGER.info(String.format("-- %s régions récupérées !", regionBounds.size()));

        // get temperatures
        LOGGER.info("Récupération des températures");
        String urlTemperature = "https://my.api.mockaroo.com/sensor/temperatures";
        String apiKey = "dd764f40";

        TemperatureClient temperatureClient = new TemperatureClient(urlTemperature, apiKey);
        TemperatureService temperatureService = new TemperatureService(temperatureClient);

        List<Temperature> listTemperatures = temperatureService.retrieveListOfTemperatures();

        LOGGER.info(String.format("-- %s températures récupérées !", listTemperatures.size()));

        // keep temperatures from france
        LOGGER.info("Filtre sur les températures et calcul des régions associées...");
        listTemperatures = temperatureService.filterListOfTemperatureOnlyFrance(listTemperatures);

        // Associate region for each temperature
        for(Temperature temperature:listTemperatures) {
            for(String region:regionBounds.keySet()) {
                if (regionBounds.get(region).containsGeoPoint(temperature.getLongitude(), temperature.getLatitude())){
                    temperature.setRegion(region);
                    continue;
                }
            }
        }

        LOGGER.info("Calcul des moyennes et du nombre de points");
        Map<String,Double> listAverage = listTemperatures.stream()
                .collect(Collectors.groupingBy(Temperature::getRegion,
                        Collectors.averagingDouble(Temperature::getTemperature)));

        Map<String, Long> listCount = listTemperatures.stream()
                .collect(Collectors.groupingBy(Temperature::getRegion,
                        Collectors.counting()));

        LOGGER.info("Import en base de donnnées.");
        TableTemperaturesDao dao = new TableTemperaturesDao(dataSource);
        dao.insertData(listAverage, listCount, importDate);

        LOGGER.info("Fin du programme.");
        System.exit(0);

    }

}
