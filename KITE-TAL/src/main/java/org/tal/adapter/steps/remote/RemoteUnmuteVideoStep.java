package org.tal.adapter.steps.remote;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.steps.TestStep;
import org.tal.adapter.pages.remote.RemoteO2oRTCPage;
import org.webrtc.kite.tests.TestRunner;

import java.net.MalformedURLException;

public class RemoteUnmuteVideoStep extends TestStep {
    RemoteO2oRTCPage remoteO2oRTCPage;

    public RemoteUnmuteVideoStep(TestRunner runner){
        super(runner);
        try {
            this.remoteO2oRTCPage=new RemoteO2oRTCPage(runner);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        }
    }
    @Override
    protected void step() throws KiteTestException {
        remoteO2oRTCPage.unmuteLocalVideo();
    }

    @Override
    public String stepDescription() {
        return "remote unmute视频。";
    }
}
