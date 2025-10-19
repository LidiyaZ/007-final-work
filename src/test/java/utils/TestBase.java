package utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class TestBase {
    @BeforeAll
    static void setup() {
        Configuration.remote = "http://localhost:4444/wd/hub";


        Configuration.browser = "chrome";
        Configuration.baseUrl = "http://host.docker.internal:8080";
        Configuration.headless = false;
        Configuration.timeout = 5000;
        Configuration.textCheck = com.codeborne.selenide.TextCheck.FULL_TEXT;

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));
        DesiredCapabilities capabilities = new DesiredCapabilities();

        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", true);
        selenoidOptions.put("enableVideo", false);
        selenoidOptions.put("sessionTimeout", "2m");
        capabilities.setCapability("selenoid:options", selenoidOptions);
        Configuration.browserCapabilities = capabilities;
    }

    @BeforeEach
    void openBasePage() {
        open("/food");
        getWebDriver().manage().window().maximize();
    }
}