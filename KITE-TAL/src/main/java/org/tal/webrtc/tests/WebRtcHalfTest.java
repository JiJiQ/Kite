package org.tal.webrtc.tests;

import io.cosmosoftware.kite.steps.WebRTCInternalsStep;
import io.cosmosoftware.kite.util.WebDriverUtils;
import org.tal.webrtc.checks.local.LocalPeerConnectionCheck;
import org.tal.webrtc.checks.local.LocalSubscribeVideoDisplayCheck;
import org.tal.webrtc.checks.remote.RemotePeerConnectionCheck;
import org.tal.webrtc.checks.remote.RemoteSubscribeVideoDisplayCheck;
import org.tal.webrtc.steps.ScreenRecordStep;
import org.tal.webrtc.steps.local.LocalJoinRoomStep;
import org.tal.webrtc.steps.remote.RemoteControlAudioStep;
import org.tal.webrtc.steps.remote.RemoteControlVideoStep;
import org.tal.webrtc.steps.remote.RemoteJoinRoomStep;
import org.tal.webrtc.steps.remote.RemoteJoinRoomWaitStep;
import org.webrtc.kite.stats.GetStatsStep;
import org.webrtc.kite.tests.TestRunner;


public class WebRtcHalfTest extends TalTest {

    @Override
    protected void populateTestSteps(TestRunner runner) {

        RemoteJoinRoomWaitStep remoteJoinRoomWaitStep = new RemoteJoinRoomWaitStep(runner);

        remoteJoinRoomWaitStep.setRoomId("23982308");
        remoteJoinRoomWaitStep.setUserId("239823082");
        remoteJoinRoomWaitStep.setServer(remoteServer);
        runner.addStep(remoteJoinRoomWaitStep);

        runner.addStep(new RemoteControlAudioStep(runner));
        runner.addStep(new RemoteControlVideoStep(runner));

        runner.addStep(new RemotePeerConnectionCheck(runner));
        runner.addStep(new RemoteSubscribeVideoDisplayCheck(runner));

        runner.addStep(new RemoteControlAudioStep(runner));
        runner.addStep(new RemoteControlVideoStep(runner));

        if (WebDriverUtils.isChrome(runner.getWebDriver())) {
            runner.addStep(new WebRTCInternalsStep(runner));
        }
        runner.addStep(new ScreenRecordStep(runner,"测试应该通过了，demo截图。"));
    }
}
