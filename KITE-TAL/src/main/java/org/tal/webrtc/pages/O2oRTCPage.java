package org.tal.webrtc.pages;

import io.cosmosoftware.kite.entities.Timeouts;
import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.pages.BasePage;
import io.cosmosoftware.kite.util.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class O2oRTCPage extends BasePage {

    @FindBy(xpath = "/html[1]/body[1]/div[9]/div[1]/video[1]")
    WebElement FirstSubscribeVideo;

    public O2oRTCPage(Runner runner) {
        super(runner);
    }

    public String getIceConnectionState()throws KiteTestException {
        return (String) TestUtils.executeJsScript(this.webDriver,getIceConnectionStateScript());
    }
    public String getIceConnectionStateScript(){
        return "var state;" +
                "try{state=client._sendTransport._connectionState}catch(expection){}" +
                "if(state){return state;}else{return 'unknow';}";
    }

    public String getVideoState(String index){
        return "var streamI=streamMap.keys();" +
                "var i=0;" +
                "while(i<"+index+"){" +
                "    streamI.next();" +
                "    i++;" +
                "}" +
                "var videoTrack=streamI.next().value.getVideoTrack()" +
                "if(videoTrack){return videoTrack.enabled;}else{return 'unknow';}";
    }
    public String getAudioState(String index){
        return "var streamI=streamMap.keys();" +
                "var i=0;" +
                "while(i<"+index+"){" +
                "    streamI.next();" +
                "    i++;" +
                "}" +
                "var videoTrack=streamI.next().value.getAudioTrack()" +
                "if(videoTrack){return videoTrack.enabled;}else{return 'unknow';}";
    }

    public String getAudioLevel(String index){
        return "var streamI=streamMap.keys();" +
                "var i=0;" +
                "while(i<"+index+"){" +
                "    streamI.next();" +
                "    i++;" +
                "}" +
                "var stream=streamI.next().value;" +
                "if(stream){return stream.getAudioLevel()}else{return -1;}";
    }
    public String subscribeVideoCheck() throws KiteTestException {
        waitUntilVisibilityOf(FirstSubscribeVideo, Timeouts.TEN_SECOND_INTERVAL_IN_SECONDS);
        return TestUtils.videoCheck(webDriver,1);
    }
}
