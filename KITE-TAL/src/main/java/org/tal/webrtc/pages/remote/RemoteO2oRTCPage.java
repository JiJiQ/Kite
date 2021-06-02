package org.tal.webrtc.pages.remote;

import io.cosmosoftware.kite.entities.Timeouts;
import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.pages.BasePage;
import io.cosmosoftware.kite.util.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.tal.webrtc.pages.RemoteBasePage;

import java.net.MalformedURLException;

import static org.webrtc.kite.config.client.RemoteClient.remoteWebDriver;

public class RemoteO2oRTCPage extends RemoteBasePage {

    WebElement localVideo;

    @FindBy(xpath = "/html[1]/body[1]/div[8]/div[1]/div[3]/button[2]")
    WebElement localAudioControl;

    public RemoteO2oRTCPage(Runner runner) throws MalformedURLException {
        super(runner);
    }

    public String getIceConnectionState() throws KiteTestException {
        return (String) TestUtils.executeJsScript(remoteWebDriver, getIceConnectionStateScript());
    }

    public String getIceConnectionStateScript() {
        return "var state;" +
                "try{state=client._sendTransport._connectionState}catch(expection){}" +
                "if(state){return state;}else{return 'unknow';}";
    }

    public String subscribeVideoCheck(int index) throws KiteTestException {
        int divIndex=8+index;
        WebElement subscribeVideo=remoteWebDriver.findElement(By.xpath("/html[1]/body[1]/div["+divIndex+"]/div[1]/video[1]"));
        waitUntilVisibilityOf(subscribeVideo, Timeouts.TEN_SECOND_INTERVAL_IN_SECONDS);
        return TestUtils.videoCheck(remoteWebDriver, index);
    }
}
