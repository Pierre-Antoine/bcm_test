package bcm.test.api;

import java.io.InputStream;

public interface DataSourceClient {

    String getData() throws Exception;

    InputStream streamData() throws Exception;
}
