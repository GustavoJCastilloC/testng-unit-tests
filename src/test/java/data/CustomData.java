package data;

import org.testng.annotations.DataProvider;
import reader.ExcelReader;

public class CustomData {
    public static final String DP_NAME = "Data Monstruo";

    @DataProvider(name = DP_NAME)
    public Object[][] monstruosDataProvider() {
        final var listaMonstruo = ExcelReader.obtenerListaMonstruo();

        final var n = listaMonstruo.size();
        final var object = new Object[n][];

        for (var i = 0; i < n; i++) {
            object[i] = new Object[]{listaMonstruo.get(i)}; // le paso el monstruo al test
        }

        return object;
    }

}
