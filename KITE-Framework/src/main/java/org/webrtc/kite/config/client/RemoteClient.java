package org.webrtc.kite.config.client;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.util.ReportUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.webrtc.kite.WebDriverFactory;
import org.webrtc.kite.exception.KiteGridException;

import javax.json.JsonObject;
import java.net.MalformedURLException;

public class RemoteClient extends Client {
    public static WebDriver remoteWebDriver;
    public static WebDriver anotherRemoteWebDriver;
    public RemoteClient(JsonObject jsonObject) {
        super(jsonObject);
    }
    public WebDriver createWebDriver() throws MalformedURLException, WebDriverException, KiteTestException, KiteGridException {
        try {
            remoteWebDriver=WebDriverFactory.createWebDriver(this, null, null, "");
            return remoteWebDriver;
        } catch (Exception e) {
            logger.error(ReportUtils.getStackTrace(e));
            throw new KiteGridException(
                    e.getClass().getSimpleName()
                            + " creating webdriver for \n"
                            + this.toString()
                            + ":\n"
                            + e.getLocalizedMessage());
        }
    }
    public WebDriver createAnotherRemoteWebDriver()throws MalformedURLException, WebDriverException, KiteTestException, KiteGridException {
        try {
            anotherRemoteWebDriver=WebDriverFactory.createWebDriver(this, null, null, "");
            return anotherRemoteWebDriver;
        } catch (Exception e) {
            logger.error(ReportUtils.getStackTrace(e));
            throw new KiteGridException(
                    e.getClass().getSimpleName()
                            + " creating webdriver for \n"
                            + this.toString()
                            + ":\n"
                            + e.getLocalizedMessage());
        }
    }
}
