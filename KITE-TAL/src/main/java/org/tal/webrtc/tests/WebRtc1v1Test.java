package org.tal.webrtc.tests;

import io.cosmosoftware.kite.steps.WebRTCInternalsStep;
import io.cosmosoftware.kite.util.WebDriverUtils;
import org.tal.webrtc.checks.PeerConnectionCheck;
import org.tal.webrtc.checks.RemotePublishVideoDisplayCheck;
import org.tal.webrtc.checks.LocalSubscribeVideoDisplayCheck;
import org.tal.webrtc.steps.local.LocalJoinRoomStep;
import org.tal.webrtc.steps.ScreenRecordStep;
import org.tal.webrtc.steps.remote.RemoteJoinRoomStep;
import org.webrtc.kite.stats.GetStatsStep;
import org.webrtc.kite.tests.TestRunner;


public class WebRtc1v1Test extends TalTest {

    @Override
    protected void populateTestSteps(TestRunner runner) {

        LocalJoinRoomStep localJoinRoomStep = new LocalJoinRoomStep(runner);
        RemoteJoinRoomStep remoteJoinRoomStep = new RemoteJoinRoomStep(runner);

        remoteJoinRoomStep.setRoomId("23982308");
        remoteJoinRoomStep.setUserId("239823082");
        remoteJoinRoomStep.setServer(remoteServer);
        runner.addStep(remoteJoinRoomStep);

        localJoinRoomStep.setRoomId("23982308");
        localJoinRoomStep.setUserId("239823081");
        localJoinRoomStep.setServer(localServer);
        runner.addStep(localJoinRoomStep);

        runner.addStep(new PeerConnectionCheck(runner));
        runner.addStep(new RemotePublishVideoDisplayCheck(runner));
        runner.addStep(new LocalSubscribeVideoDisplayCheck(runner));

        if (this.getStats()) {
            runner.addStep(new GetStatsStep(runner, this.getStatsConfig));
        }

        if (this.takeScreenshotForEachTest()) {
            runner.addStep(new ScreenRecordStep(runner));
        }

        if (WebDriverUtils.isChrome(runner.getWebDriver())) {
            runner.addStep(new WebRTCInternalsStep(runner));
        }

    }
}