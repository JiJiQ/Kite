package org.tal.webrtc.tests;

import io.cosmosoftware.kite.steps.WebRTCInternalsStep;
import io.cosmosoftware.kite.util.WebDriverUtils;
import net.sf.cglib.core.Local;
import org.tal.webrtc.checks.local.*;
import org.tal.webrtc.steps.ScreenRecordStep;
import org.tal.webrtc.steps.local.LocalJoinRoomStep;
import org.tal.webrtc.steps.local.LocalJoinRoomWaitStep;
import org.tal.webrtc.steps.local.LocalWaitNativeMuteVideoStep;
import org.tal.webrtc.steps.local.LocalWaitNativeUnMuteVideoStep;
import org.webrtc.kite.tests.TestRunner;

public class WebRtcQuitRecoveryNativeVideoTest extends TalTest {

    @Override
    protected void populateTestSteps(TestRunner runner) {

        LocalJoinRoomStep localJoinRoomStep=new LocalJoinRoomStep(runner);
        LocalJoinRoomWaitStep localJoinRoomWaitStep =
                new LocalJoinRoomWaitStep(runner);
        LocalWaitNativeMuteVideoStep localWaitNativeMuteVideoStep =
                new LocalWaitNativeMuteVideoStep(runner);
        LocalWaitNativeUnMuteVideoStep localWaitNativeUnMuteVideoStep =
                new LocalWaitNativeUnMuteVideoStep(runner);

        //local先进，remote后进，否则会导致streamMap混乱
        localJoinRoomStep.setRoomId(roomId);
        localJoinRoomStep.setUserId(localUserId);
        localJoinRoomStep.setServer(localServer);
        localJoinRoomWaitStep.setRoomId(roomId);
        localJoinRoomWaitStep.setUserId(localUserId);
        localJoinRoomWaitStep.setServer(localServer);
        localJoinRoomWaitStep.setRemoteUserId(rtnUserId);
        runner.addStep(localJoinRoomWaitStep);

        runner.addStep(new LocalPeerConnectionCheck(runner));
        runner.addStep(new LocalSubscribeVideoDisplayCheck(runner,rtnUserId));

        runner.addStep(localWaitNativeMuteVideoStep);
        runner.addStep(new LocalSubscribeVideoMutedCheck(runner,1));
        runner.addStep(new ScreenRecordStep(runner,"remote操作了mute video，demo截图"));

        runner.addStep(localJoinRoomStep);
        runner.addStep(new LocalSubscribeVideoMutedRejoinCheck(runner,0));//local stream刷新后remote stream的index为0
        runner.addStep(new ScreenRecordStep(runner,"退出重进后看不到订阅视频测试通过，demo截图。"));

        runner.addStep(localWaitNativeUnMuteVideoStep);
        runner.addStep(new LocalSubscribeVideoUnmutedCheck(runner,0));//local stream刷新后remote stream的index为0
        runner.addStep(new ScreenRecordStep(runner,"remote操作了unmute video，demo截图"));

        if (WebDriverUtils.isChrome(runner.getWebDriver())) {
            runner.addStep(new WebRTCInternalsStep(runner));
        }

        runner.addStep(new ScreenRecordStep(runner,"测试应该通过了，demo截图。"));
    }
}
