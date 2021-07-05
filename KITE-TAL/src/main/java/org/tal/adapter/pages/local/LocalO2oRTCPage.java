package org.tal.adapter.pages.local;

import io.cosmosoftware.kite.entities.Timeouts;
import io.cosmosoftware.kite.exception.KiteInteractionException;
import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.pages.BasePage;
import io.cosmosoftware.kite.util.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LocalO2oRTCPage extends BasePage {

    @FindBy(xpath = "/html[1]/body[1]/div[8]/div[1]/div[3]/button[1]")
    WebElement localVideoControl;

    @FindBy(xpath = "/html[1]/body[1]/div[8]/div[1]/div[3]/button[2]")
    WebElement localAudioControl;

    public LocalO2oRTCPage(Runner runner) {
        super(runner);
    }

    public String getIceConnectionState() throws KiteTestException {
        return (String) TestUtils.executeJsScript(this.webDriver, getIceConnectionStateScript());
    }

    public String getIceConnectionStateScript() {
        return "var state;" +
                "try{state=window.defaultEngine.engineImpl.roomState}catch(expection){}" +
                "if(state){return state;}else{return 'unknow';}";
    }

    public String getVideoStateScripts(String remoteId) {
        return "var stream=window.defaultEngine.engineImpl.remoteStreams.get("+remoteId+");" +
                "if(stream){return stream.getVideoTrack().enabled;}else{return 'unknow';}";
    }

    public String getDownloadStreamScripts(String mp4Name)
    {
        return "";
    }
    public String getVideoState(String remoteId) throws KiteInteractionException {
        return String.valueOf(TestUtils.executeJsScript(this.webDriver,getVideoStateScripts(remoteId)));
    }

    public String getAudioStateScripts(String remoteId) {
        return "var stream=window.defaultEngine.engineImpl.remoteStreams.get("+remoteId+");" +
                "if(stream){return stream.getAudioTrack().enabled;}else{return 'unknow';}";
    }

    public String getAudioState(String remoteId) throws KiteInteractionException {
        return String.valueOf(TestUtils.executeJsScript(this.webDriver,getAudioStateScripts(remoteId)));
    }

    public String subscribeVideoCheck(int index) throws KiteTestException {
        int divIndex=index;
        WebElement subscribeVideo=this.webDriver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[2]/div["+divIndex+"]/div[1]/video[1]"));
        waitUntilVisibilityOf(subscribeVideo, Timeouts.TEN_SECOND_INTERVAL_IN_SECONDS);
        return TestUtils.videoCheck(webDriver, index);
    }
}
