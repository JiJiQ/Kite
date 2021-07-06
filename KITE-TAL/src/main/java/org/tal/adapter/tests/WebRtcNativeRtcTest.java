package org.tal.adapter.tests;

import io.cosmosoftware.kite.steps.WebRTCInternalsStep;
import io.cosmosoftware.kite.util.WebDriverUtils;
import org.tal.adapter.checks.local.LocalPeerConnectionCheck;
import org.tal.adapter.checks.local.LocalSubscribeVideoDisplayCheck;
import org.tal.adapter.steps.ScreenRecordStep;
import org.tal.adapter.steps.local.LocalMuteAudioStep;
import org.tal.adapter.steps.local.LocalMuteVideoStep;
import org.tal.adapter.steps.local.LocalUnmuteAudioStep;
import org.tal.adapter.steps.local.LocalUnmuteVideoStep;
import org.tal.webrtc.steps.local.LocalJoinRoomWaitStep;
import org.webrtc.kite.tests.TestRunner;


public class WebRtcNativeRtcTest extends TalTest {

    @Override
    protected void populateTestSteps(TestRunner runner) {

        LocalJoinRoomWaitStep localJoinRoomWaitStep = new LocalJoinRoomWaitStep(runner);

        localJoinRoomWaitStep.setRoomId(roomId);
        localJoinRoomWaitStep.setUserId(localUserId);
        localJoinRoomWaitStep.setRemoteUserId(rtnUserId);
        runner.addStep(localJoinRoomWaitStep);

        runner.addStep(new LocalMuteAudioStep(runner));
        runner.addStep(new LocalMuteVideoStep(runner));

        runner.addStep(new LocalPeerConnectionCheck(runner));
        runner.addStep(new LocalSubscribeVideoDisplayCheck(runner,rtnUserId));

        runner.addStep(new LocalUnmuteAudioStep(runner));
        runner.addStep(new LocalUnmuteVideoStep(runner));

        if (WebDriverUtils.isChrome(runner.getWebDriver())) {
            runner.addStep(new WebRTCInternalsStep(runner));
        }
        runner.addStep(new ScreenRecordStep(runner,"测试应该通过了，demo截图。"));
    }
}
