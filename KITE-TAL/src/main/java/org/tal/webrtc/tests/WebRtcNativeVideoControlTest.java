package org.tal.webrtc.tests;

import io.cosmosoftware.kite.steps.WebRTCInternalsStep;
import io.cosmosoftware.kite.util.WebDriverUtils;
import net.sf.cglib.core.Local;
import org.tal.webrtc.checks.local.LocalPeerConnectionCheck;
import org.tal.webrtc.checks.local.LocalSubscribeVideoDisplayCheck;
import org.tal.webrtc.checks.local.LocalSubscribeVideoMutedCheck;
import org.tal.webrtc.checks.local.LocalSubscribeVideoUnmutedCheck;
import org.tal.webrtc.steps.ScreenRecordStep;
import org.tal.webrtc.steps.local.*;
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

        //local先进，remote后进，否则会导致streamMap混乱

        localJoinRoomWaitStep.setRoomId(roomId);
        localJoinRoomWaitStep.setUserId(localUserId);
        localJoinRoomWaitStep.setServer(localServer);
        localJoinRoomWaitStep.setRemoteUserId(rtnUserId);
        runner.addStep(localJoinRoomWaitStep);

        runner.addStep(new LocalPeerConnectionCheck(runner));
        runner.addStep(new LocalSubscribeVideoDisplayCheck(runner,rtnUserId));

        runner.addStep(localWaitNativeMuteVideoStep);

        runner.addStep(new LocalSubscribeVideoMutedCheck(runner,1));

        runner.addStep(localWaitNativeUnMuteVideoStep);

        runner.addStep(new LocalSubscribeVideoUnmutedCheck(runner,1));

        if (WebDriverUtils.isChrome(runner.getWebDriver())) {
            runner.addStep(new WebRTCInternalsStep(runner));
        }

        runner.addStep(new ScreenRecordStep(runner,"测试应该通过了，demo截图。"));
    }
}
