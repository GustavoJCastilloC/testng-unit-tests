package reader;

import com.poiji.bind.Poiji;
import modelos.Monstruo;

import java.io.File;
import java.util.List;

public class ExcelReader {
    private final static String excelPath = "src/main/resources/data/dataExcel.xlsx";

    public static List<Monstruo> obtenerListaMonstruo() {
        return Poiji.fromExcel(new File(excelPath), Monstruo.class);
    }
}