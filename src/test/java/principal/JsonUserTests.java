package principal;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import modelos.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import reader.JsonReader;
import utilities.BaseTest;

public class JsonUserTests extends BaseTest {
    private User user;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        user = JsonReader.leerUsuarioJson();
    }

    @Test(groups = {regresion, smoke})
    @io.qameta.allure.Description("Verificando que el id es mayor a 0 (positivo)")
    @Severity(SeverityLevel.CRITICAL)
    public void primerTest() {
        Assert.assertTrue(user.getId() > 0);
    }

    @Test(groups = {regresion, smoke})
    @io.qameta.allure.Description("Verificando valor correcto de lng")
    @Severity(SeverityLevel.MINOR)
    public void segundoTest() {
        Assert.assertEquals(user.getAddress().getGeo().getLng(), 71.7478);
    }

    @Test(groups = {regresion, smoke})
    @io.qameta.allure.Description("Verificando que la longitud de company bs sea correcta y mayor a 10")
    @Severity(SeverityLevel.CRITICAL)
    public void tercerTest() {
        Assert.assertTrue(user.getCompany().getBs().length() > 10);
    }

    @Test(groups = {regresion, smoke})
    @io.qameta.allure.Description("Verificando la información del usuario")
    @Severity(SeverityLevel.BLOCKER)
    public void cuartoTest() {
        softAssert.assertEquals(user.getName(), "Mrs. Dennis Schulist");
        softAssert.assertEquals(user.getId(), 6);
        softAssert.assertEquals(user.getUsername(), "Leopoldo_Corkery");
        softAssert.assertEquals(user.getWebsite(), "ola.org");

        softAssert.assertAll();
    }
}
