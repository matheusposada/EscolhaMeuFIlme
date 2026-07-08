package projeto.filme;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    public static String carregarApiKey() throws IOException {
        Properties props = new Properties();
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException(
                        "Arquivo config.properties não encontrado. " +
                                "Copie config.properties.example para config.properties e preencha sua API key."
                );
            }
            props.load(input);
        }
        return props.getProperty("tmdb.api.key");
    }
}
