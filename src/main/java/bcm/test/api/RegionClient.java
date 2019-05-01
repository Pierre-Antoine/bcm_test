package bcm.test.api;

import bcm.test.batch.Launcher;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class RegionClient implements DataSourceClient {

    String filepath;

    public RegionClient(String filepath) {
        this.filepath = filepath;
    }

    public String getData() {
        return null;
    }

    @Override
    public InputStream streamData() throws Exception {

        return new ClassPathResource(filepath).getInputStream();

    }


}
