package org.tal.adapter.tests;

import io.cosmosoftware.kite.steps.WebRTCInternalsStep;
import io.cosmosoftware.kite.util.WebDriverUtils;
import org.tal.adapter.checks.local.LocalPeerConnectionCheck;
import org.tal.adapter.checks.local.LocalSubscribeVideoDisplayCheck;
import org.tal.adapter.checks.local.LocalSubscribeVideoMutedCheck;
import org.tal.adapter.checks.local.LocalSubscribeVideoUnmutedCheck;
import org.tal.adapter.steps.ScreenRecordStep;
import org.tal.adapter.steps.local.LocalJoinRoomWaitStep;
import org.tal.adapter.steps.local.LocalWaitNativeMuteVideoStep;
import org.tal.adapter.steps.local.LocalWaitNativeUnMuteVideoStep;
import org.webrtc.kite.tests.TestRunner;


public class WebRtcNativeVideoControlTest extends TalTest {

    @Override
    protected void populateTestSteps(TestRunner runner) {

        LocalJoinRoomWaitStep localJoinRoomWaitStep =
                new LocalJoinRoomWaitStep(runner);
        LocalWaitNativeMuteVideoStep localWaitNativeMuteVideoStep =
                new LocalWaitNativeMuteVideoStep(runner);
        LocalWaitNativeUnMuteVideoStep localWaitNativeUnMuteVideoStep =
                new LocalWaitNativeUnMuteVideoStep(runner);

        localJoinRoomWaitStep.setRoomId(roomId);
        localJoinRoomWaitStep.setUserId(localUserId);
        localJoinRoomWaitStep.setRemoteUserId(rtnUserId);
        runner.addStep(localJoinRoomWaitStep);

        runner.addStep(new LocalPeerConnectionCheck(runner));
        runner.addStep(new LocalSubscribeVideoDisplayCheck(runner,rtnUserId));

        runner.addStep(localWaitNativeMuteVideoStep);

        runner.addStep(new LocalSubscribeVideoMutedCheck(runner,roomId));
        runner.addStep(new ScreenRecordStep(runner,"remote操作了mute audio，demo截图"));
        runner.addStep(localWaitNativeUnMuteVideoStep);

        runner.addStep(new LocalSubscribeVideoUnmutedCheck(runner,roomId));
        runner.addStep(new ScreenRecordStep(runner,"remote操作了unmute audio，demo截图"));

        if (WebDriverUtils.isChrome(runner.getWebDriver())) {
            runner.addStep(new WebRTCInternalsStep(runner));
        }

        runner.addStep(new ScreenRecordStep(runner,"测试应该通过了，demo截图。"));
    }
}
