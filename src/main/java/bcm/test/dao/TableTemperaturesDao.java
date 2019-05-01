package bcm.test.dao;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TableTemperaturesDao {

    private SimpleJdbcInsert insertTemperature;

    public TableTemperaturesDao(DataSource dataSource) {
        this.insertTemperature = new SimpleJdbcInsert(dataSource);

        insertTemperature.setTableName("temperatures");
    }

    public void insertData(Map<String,Double> averages, Map<String,Long> counts, LocalDateTime dateTime) {
        for(String region:averages.keySet()) {
            Map<String, Object> values = new HashMap<String, Object>(4);
            values.put("region", region);
            values.put("date_import", dateTime);
            values.put("val_temperature",averages.get(region));
            values.put("nb_points",counts.get(region));

            insertTemperature.execute(values);
        }
    }
}
