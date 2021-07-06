package org.tal.adapter.pages.remote;

import io.cosmosoftware.kite.entities.Timeouts;
import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.util.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.tal.adapter.pages.RemoteBasePage;

import java.net.MalformedURLException;

import static org.webrtc.kite.config.client.RemoteClient.anotherRemoteWebDriver;
import static org.webrtc.kite.config.client.RemoteClient.remoteWebDriver;

public class AnotherRemoteO2oRTCPage extends RemoteBasePage {

    public AnotherRemoteO2oRTCPage(Runner runner) throws MalformedURLException {
        super(runner);
    }

    public String getIceConnectionState() throws KiteTestException {
        return (String) TestUtils.executeJsScript(anotherRemoteWebDriver, getIceConnectionStateScript());
    }

    public String getIceConnectionStateScript() {
        return "var state;" +
                "try{state=window.defaultEngine.engineImpl.roomState}catch(expection){}" +
                "if(state){return state;}else{return 'unknow';}";
    }

    public String subscribeVideoCheck(int index) throws KiteTestException {
        int divIndex=index;
        WebElement subscribeVideo=this.webDriver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[2]/div["+divIndex+"]/div[1]/video[1]"));
        waitUntilVisibilityOf(subscribeVideo, Timeouts.TEN_SECOND_INTERVAL_IN_SECONDS);
        return TestUtils.videoCheck(webDriver, index);
    }
}
