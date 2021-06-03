package org.tal.webrtc.steps.local;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.steps.TestStep;
import org.tal.webrtc.pages.local.LocalO2oRTCPage;
import org.tal.webrtc.pages.remote.RemoteO2oRTCPage;
import org.webrtc.kite.tests.TestRunner;

import java.net.MalformedURLException;

public class LocalControlAudioStep extends TestStep {
    LocalO2oRTCPage localO2oRTCPage;

    public LocalControlAudioStep(TestRunner runner){
        super(runner);
        this.localO2oRTCPage=new LocalO2oRTCPage(runner);
    }
    @Override
    protected void step() throws KiteTestException {
        localO2oRTCPage.audioControl();
    }

    @Override
    public String stepDescription() {
        return "local 点击音频控制按钮。";
    }
}
