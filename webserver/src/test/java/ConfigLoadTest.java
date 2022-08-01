import com.WebSever;
import com.hz.autoload.ConfigFileLoading;
import com.hz.autoload.impl.PropertiesAutoLoading;
import com.hz.autoload.impl.XmlAutoLoading;
import com.hz.autoload.impl.YamlAutoLoading;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class ConfigLoadTest {
    @Test
    public void loadYaml() throws IOException {
        ConfigFileLoading autoLoading = new YamlAutoLoading();
        File file = new File(".");
        URL resource = WebSever.class.getResource("/application.yaml");
        System.out.println(resource.getFile());
        for (String s : file.list()) {
            System.out.println(s);
        }
        Map<String, Object> stringObjectMap = autoLoading.ConfigToMap("application.yaml", WebSever.class);
        System.out.println(stringObjectMap.get("web.port"));
        autoLoading = new PropertiesAutoLoading();
        autoLoading.ConfigToMap("application.properties",WebSever.class);
        autoLoading = new XmlAutoLoading();
        autoLoading.ConfigToMap("application.xml",WebSever.class);
    }

}
