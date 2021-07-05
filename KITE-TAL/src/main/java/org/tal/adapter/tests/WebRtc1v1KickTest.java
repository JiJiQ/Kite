package org.tal.adapter.tests;

import io.cosmosoftware.kite.steps.WebRTCInternalsStep;
import io.cosmosoftware.kite.util.WebDriverUtils;
import org.tal.adapter.checks.local.LocalPeerConnectionCheck;
import org.tal.adapter.checks.local.LocalSubscribeVideoDisplayCheck;
import org.tal.adapter.checks.remote.*;
import org.tal.adapter.steps.ScreenRecordStep;
import org.tal.adapter.steps.local.LocalJoinRoomStep;
import org.tal.adapter.steps.remote.RemoteJoinRoomStep;
import org.tal.adapter.steps.remote.RemoteKickStep;
import org.webrtc.kite.stats.GetStatsStep;
import org.webrtc.kite.tests.TestRunner;

public class WebRtc1v1KickTest extends TalTest {

    @Override
    protected void populateTestSteps(TestRunner runner) {
        LocalJoinRoomStep localJoinRoomStep = new LocalJoinRoomStep(runner);
        RemoteJoinRoomStep remoteJoinRoomStep = new RemoteJoinRoomStep(runner);
        RemoteKickStep remoteKickStep = new RemoteKickStep(runner);

        remoteJoinRoomStep.setRoomId(roomId);
        remoteJoinRoomStep.setUserId(remoteUserId);
        runner.addStep(remoteJoinRoomStep);

        localJoinRoomStep.setRoomId(roomId);
        localJoinRoomStep.setUserId(localUserId);
        runner.addStep(localJoinRoomStep);

        runner.addStep(new LocalPeerConnectionCheck(runner));
        runner.addStep(new LocalSubscribeVideoDisplayCheck(runner,remoteUserId));
        runner.addStep(new RemotePeerConnectionCheck(runner));
        runner.addStep(new RemoteSubscribeVideoDisplayCheck(runner));

        remoteKickStep.setRoomId(roomId);
        remoteKickStep.setUserId(remoteUserId);
        runner.addStep(remoteKickStep);

        runner.addStep(new ScreenRecordStep(runner,"刚完成了互踢操作，demo截图。"));

        runner.addStep(new LocalPeerConnectionCheck(runner));
        runner.addStep(new LocalSubscribeVideoDisplayCheck(runner,remoteUserId));
        runner.addStep(new AnotherRemotePeerConnectionCheck(runner));
        runner.addStep(new AnotherRemoteSubscribeVideoDisplayCheck(runner));

        runner.addStep(new RemoteKickedCheck(runner));

        if (this.getStats()) {
            runner.addStep(new GetStatsStep(runner, this.getStatsConfig));
        }

        runner.addStep(new ScreenRecordStep(runner,"互踢后音视频互通，测试应该通过了，demo截图。"));

        if (WebDriverUtils.isChrome(runner.getWebDriver())) {
            runner.addStep(new WebRTCInternalsStep(runner));
        }

    }
}
