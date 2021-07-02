package org.tal.webrtc.steps.local;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.steps.TestStep;
import io.cosmosoftware.kite.util.TestUtils;
import org.tal.webrtc.pages.local.LocalO2oRTCPage;
import org.webrtc.kite.tests.TestRunner;

public class LocalWaitNativeUnMuteVideoStep extends TestStep {
    LocalO2oRTCPage localO2oRTCPage;

    public LocalWaitNativeUnMuteVideoStep(TestRunner runner){
        super(runner);
        this.localO2oRTCPage=new LocalO2oRTCPage(runner);
    }
    @Override
    protected void step() throws KiteTestException {
        String remoteVideoEnabled="uninit";
        while (true){
            remoteVideoEnabled=localO2oRTCPage.getVideoState(1);
            if(!"false".equalsIgnoreCase(remoteVideoEnabled)&&
                    !"unknow".equalsIgnoreCase(remoteVideoEnabled)&&
                    !"uninit".equalsIgnoreCase(remoteVideoEnabled)){
                logger.info("remote 操作了unmute视频，remoteVideoEnabled："+remoteVideoEnabled);
                return;
            }else{
                logger.info("remote 还未操作unmute视频，等待2s。remoteVideoEnabled："+remoteVideoEnabled);
                TestUtils.waitAround(2000);
            }
        }
    }

    @Override
    public String stepDescription() {
        return "半自动化：等待native unmute视频";
    }
}
