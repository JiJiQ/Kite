package org.tal.webrtc.tests;

import io.cosmosoftware.kite.steps.WebRTCInternalsStep;
import io.cosmosoftware.kite.util.WebDriverUtils;
import org.tal.webrtc.checks.PeerConnectionCheck;
import org.tal.webrtc.checks.SubscribeVideoDisplayCheck;
import org.tal.webrtc.steps.JoinRoomStep;
import org.tal.webrtc.steps.ScreenRecordStep;
import org.webrtc.kite.stats.GetStatsStep;
import org.webrtc.kite.tests.TestRunner;


public class IceConnectionTest extends MediasoupTest {

    @Override
    protected void populateTestSteps(TestRunner runner) {
        JoinRoomStep joinRoomStep=new JoinRoomStep(runner);

        joinRoomStep.setRoomId("23982308");
        runner.addStep(joinRoomStep);


        runner.addStep(new PeerConnectionCheck(runner));
        runner.addStep(new SubscribeVideoDisplayCheck(runner));

        if(this.getStats()){
            runner.addStep(new GetStatsStep(runner,this.getStatsConfig));
        }

        if(this.takeScreenshotForEachTest()){
            runner.addStep(new ScreenRecordStep(runner));
        }

        if(WebDriverUtils.isChrome(runner.getWebDriver())){
            runner.addStep(new WebRTCInternalsStep(runner));
        }
    }
}
