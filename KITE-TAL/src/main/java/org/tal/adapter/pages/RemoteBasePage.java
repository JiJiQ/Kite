package org.tal.adapter.pages;

import io.cosmosoftware.kite.exception.KiteInteractionException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.pages.BasePage;
import io.cosmosoftware.kite.util.WebDriverUtils;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.webrtc.kite.config.client.RemoteClient;

import javax.json.JsonObject;
import java.net.MalformedURLException;

import static io.cosmosoftware.kite.entities.Timeouts.EXTENDED_TIMEOUT_IN_SECONDS;
import static io.cosmosoftware.kite.util.TestUtils.readJsonFile;
import static org.webrtc.kite.config.client.RemoteClient.remoteWebDriver;

public class RemoteBasePage extends BasePage {
    public RemoteClient remoteClient;
    public JsonObject configObject;
    public WebDriverWait defaultWait;
    protected RemoteBasePage(Runner runner) throws MalformedURLException {
        super(runner);
        configObject=readJsonFile(System.getProperty("config.file.path"));
        if (remoteWebDriver != null) {
            this.defaultWait = new WebDriverWait(remoteWebDriver, EXTENDED_TIMEOUT_IN_SECONDS);
            PageFactory.initElements(remoteWebDriver, this);
        } else {
            this.defaultWait = null;
        }
    }
    public void maximizeCurrentWindow() throws KiteInteractionException {
        WebDriverUtils.maximizeCurrentWindow(remoteWebDriver);
    }
}
