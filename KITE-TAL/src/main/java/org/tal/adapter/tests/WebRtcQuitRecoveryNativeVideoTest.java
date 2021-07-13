package org.tal.adapter.tests;

import io.cosmosoftware.kite.steps.WebRTCInternalsStep;
import io.cosmosoftware.kite.util.WebDriverUtils;
import org.tal.adapter.checks.local.*;
import org.tal.adapter.steps.ScreenRecordStep;
import org.tal.adapter.steps.local.LocalJoinRoomStep;
import org.tal.adapter.steps.local.LocalJoinRoomWaitStep;
import org.tal.adapter.steps.local.LocalRejoinWaitNativeUnMuteVideoStep;
import org.tal.adapter.steps.local.LocalWaitNativeMuteVideoStep;
import org.webrtc.kite.tests.TestRunner;

public class WebRtcQuitRecoveryNativeVideoTest extends TalTest {

    @Override
    protected void populateTestSteps(TestRunner runner) {

        LocalJoinRoomStep localJoinRoomStep=new LocalJoinRoomStep(runner);
        LocalJoinRoomWaitStep localJoinRoomWaitStep =
                new LocalJoinRoomWaitStep(runner);
        LocalWaitNativeMuteVideoStep localWaitNativeMuteVideoStep =
                new LocalWaitNativeMuteVideoStep(runner);
        LocalRejoinWaitNativeUnMuteVideoStep localRejoinWaitNativeUnMuteVideoStep =
                new LocalRejoinWaitNativeUnMuteVideoStep(runner);

        localJoinRoomStep.setRoomId(roomId);
        localJoinRoomStep.setUserId(localUserId);
        localJoinRoomWaitStep.setRoomId(roomId);
        localJoinRoomWaitStep.setUserId(localUserId);
        localJoinRoomWaitStep.setRemoteUserId(rtnUserId);
        runner.addStep(localJoinRoomWaitStep);

        runner.addStep(new LocalPeerConnectionCheck(runner));
        runner.addStep(new LocalSubscribeVideoDisplayCheck(runner,rtnUserId));

        runner.addStep(localWaitNativeMuteVideoStep);
        runner.addStep(new LocalSubscribeVideoMutedCheck(runner,rtnUserId));
        runner.addStep(new ScreenRecordStep(runner,"remote操作了mute video，demo截图"));

        runner.addStep(localJoinRoomStep);
        runner.addStep(new LocalSubscribeVideoMutedRejoinCheck(runner,rtnUserId));
        runner.addStep(new ScreenRecordStep(runner,"退出重进后看不到订阅视频测试通过，demo截图。"));

        runner.addStep(localRejoinWaitNativeUnMuteVideoStep);
        runner.addStep(new LocalSubscribeVideoUnmutedCheck(runner,rtnUserId));//local stream刷新后remote stream的index为0
        runner.addStep(new ScreenRecordStep(runner,"remote操作了unmute video，demo截图"));

        if (WebDriverUtils.isChrome(runner.getWebDriver())) {
            runner.addStep(new WebRTCInternalsStep(runner));
        }

        runner.addStep(new ScreenRecordStep(runner,"测试应该通过了，demo截图。"));
    }
}
