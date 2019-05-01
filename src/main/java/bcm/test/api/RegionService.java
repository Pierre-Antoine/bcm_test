package bcm.test.api;

import bcm.test.model.GeoPolygon;
import bcm.test.model.GeoRectangle;
import bcm.test.model.RegionMultiPolygon;
import bcm.test.model.RegionPolygon;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class RegionService {

    private final Logger LOGGER = Logger.getLogger(RegionService.class.getName());

    ObjectMapper mapper;
    RegionClient client;

    public RegionService(RegionClient client) {
        this.client = client;
        this.mapper = new ObjectMapper();
    }

    GeoPolygon parseJsonToPolygon(String source) throws Exception {

        if (source == null) {
            throw new Exception("La chaine de caractères à parser est nulle");
        }

        // dirty but the source is not properly formatted
        source = fixSource(source);

        GeoPolygon polygon = null;

        try{
            if (source.contains("MultiPolygon")) {
                polygon = mapper.readValue(source, RegionMultiPolygon.class);
            } else {
                polygon = mapper.readValue(source, RegionPolygon.class);
            }

        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
        return polygon;
    }

    /**
     * @return list of region bounds mapped by name
     */
    public Map<String, GeoRectangle> retrieveRegionsBounds() {

        Map<String, GeoRectangle> result = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(client.streamData(),"UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // ignore header
                if(line.length() < 50) {
                    continue;
                }
                String[] values = line.split(";");
                String regionName = values[2];
                GeoPolygon polygon = parseJsonToPolygon(values[1]);
                result.put(regionName,polygon.getBoundingBox());
            }
        } catch (FileNotFoundException e) {
            LOGGER.warning(e.getMessage());
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
        return result;
    }

    String fixSource(String source){

        source = StringEscapeUtils.unescapeCsv(source);

        return source;

    }

}
