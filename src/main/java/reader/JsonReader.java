package reader;


import modelos.User;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.io.File;


public class JsonReader {
    private final static String userPath = "src/main/resources/data/user.json";

    public static User leerUsuarioJson() {
        final var objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(new File(userPath), User.class);
        } catch (JacksonException exception) {
            throw new RuntimeException();
        }
    }
}
