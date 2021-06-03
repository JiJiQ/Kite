package org.tal.webrtc.tests;

import io.cosmosoftware.kite.steps.WebRTCInternalsStep;
import io.cosmosoftware.kite.util.WebDriverUtils;
import org.tal.webrtc.checks.local.LocalPeerConnectionCheck;
import org.tal.webrtc.checks.local.LocalSubscribeVideoDisplayCheck;
import org.tal.webrtc.checks.remote.RemotePeerConnectionCheck;
import org.tal.webrtc.checks.remote.RemoteSubscribeVideoDisplayCheck;
import org.tal.webrtc.steps.ScreenRecordStep;
import org.tal.webrtc.steps.local.LocalControlAudioStep;
import org.tal.webrtc.steps.local.LocalControlVideoStep;
import org.tal.webrtc.steps.remote.LocalJoinRoomWaitStep;
import org.tal.webrtc.steps.remote.RemoteControlAudioStep;
import org.tal.webrtc.steps.remote.RemoteControlVideoStep;
import org.webrtc.kite.tests.TestRunner;


public class WebRtcHalfTest extends TalTest {

    @Override
    protected void populateTestSteps(TestRunner runner) {

        LocalJoinRoomWaitStep localJoinRoomWaitStep = new LocalJoinRoomWaitStep(runner);

        localJoinRoomWaitStep.setRoomId(roomId);
        localJoinRoomWaitStep.setUserId(localUserId);
        localJoinRoomWaitStep.setServer(localServer);
        localJoinRoomWaitStep.setRemoteUserId(rtnUserId);
        runner.addStep(localJoinRoomWaitStep);

        runner.addStep(new LocalControlAudioStep(runner));
        runner.addStep(new LocalControlVideoStep(runner));

        runner.addStep(new LocalPeerConnectionCheck(runner));
        runner.addStep(new LocalSubscribeVideoDisplayCheck(runner,rtnUserId));

        runner.addStep(new LocalControlAudioStep(runner));
        runner.addStep(new LocalControlVideoStep(runner));

        if (WebDriverUtils.isChrome(runner.getWebDriver())) {
            runner.addStep(new WebRTCInternalsStep(runner));
        }
        runner.addStep(new ScreenRecordStep(runner,"测试应该通过了，demo截图。"));
    }
}
