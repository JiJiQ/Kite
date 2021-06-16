package org.tal.webrtc.tests;

import io.cosmosoftware.kite.steps.WebRTCInternalsStep;
import io.cosmosoftware.kite.util.WebDriverUtils;
import org.tal.webrtc.checks.local.LocalPeerConnectionCheck;
import org.tal.webrtc.checks.local.LocalSubscribeVideoDisplayCheck;
import org.tal.webrtc.checks.local.RtcKeep5MCheck;
import org.tal.webrtc.checks.remote.RemotePeerConnectionCheck;
import org.tal.webrtc.checks.remote.RemoteSubscribeVideoDisplayCheck;
import org.tal.webrtc.steps.ScreenRecordStep;
import org.tal.webrtc.steps.local.LocalJoinRoomStep;
import org.tal.webrtc.steps.remote.RemoteJoinRoomStep;
import org.webrtc.kite.stats.GetStatsStep;
import org.webrtc.kite.tests.TestRunner;


public class WebRtc1v1Keep5MTest extends TalTest {

    @Override
    protected void populateTestSteps(TestRunner runner) {

        LocalJoinRoomStep localJoinRoomStep = new LocalJoinRoomStep(runner);
        RemoteJoinRoomStep remoteJoinRoomStep = new RemoteJoinRoomStep(runner);

        remoteJoinRoomStep.setRoomId(roomId);
        remoteJoinRoomStep.setUserId(remoteUserId);
        remoteJoinRoomStep.setServer(remoteServer);
        runner.addStep(remoteJoinRoomStep);

        localJoinRoomStep.setRoomId(roomId);
        localJoinRoomStep.setUserId(localUserId);
        localJoinRoomStep.setServer(localServer);
        runner.addStep(localJoinRoomStep);

        runner.addStep(new RtcKeep5MCheck(runner,remoteUserId));

        if (this.getStats()) {
            runner.addStep(new GetStatsStep(runner, this.getStatsConfig));
        }

        runner.addStep(new ScreenRecordStep(runner,"测试应该通过了，demo截图。"));

        if (WebDriverUtils.isChrome(runner.getWebDriver())) {
            runner.addStep(new WebRTCInternalsStep(runner));
        }

    }
}