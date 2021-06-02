package org.tal.webrtc.steps.remote;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.steps.TestStep;
import org.tal.webrtc.pages.remote.RemoteO2oRTCPage;
import org.webrtc.kite.tests.TestRunner;

import java.net.MalformedURLException;

public class RemoteControlAudioStep extends TestStep {
    RemoteO2oRTCPage remoteO2oRTCPage;

    public RemoteControlAudioStep(TestRunner runner){
        super(runner);
        try {
            this.remoteO2oRTCPage=new RemoteO2oRTCPage(runner);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        }
    }
    @Override
    protected void step() throws KiteTestException {
        remoteO2oRTCPage.audioControl();
    }

    @Override
    public String stepDescription() {
        return "remote 点击音频控制按钮。";
    }
}
