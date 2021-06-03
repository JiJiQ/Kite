package org.tal.webrtc.tests;

import io.cosmosoftware.kite.steps.WebRTCInternalsStep;
import io.cosmosoftware.kite.util.WebDriverUtils;
import org.tal.webrtc.checks.remote.AnotherRemotePeerConnectionCheck;
import org.tal.webrtc.checks.remote.AnotherRemoteSubscribeVideoDisplayCheck;
import org.tal.webrtc.checks.local.LocalPeerConnectionCheck;
import org.tal.webrtc.checks.local.LocalSubscribeVideoDisplayCheck;
import org.tal.webrtc.checks.remote.RemotePeerConnectionCheck;
import org.tal.webrtc.checks.remote.RemoteSubscribeVideoDisplayCheck;
import org.tal.webrtc.steps.ScreenRecordStep;
import org.tal.webrtc.steps.local.LocalJoinRoomStep;
import org.tal.webrtc.steps.remote.RemoteJoinRoomStep;
import org.tal.webrtc.steps.remote.RemoteKickStep;
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
        remoteJoinRoomStep.setServer(remoteServer);
        runner.addStep(remoteJoinRoomStep);

        localJoinRoomStep.setRoomId(roomId);
        localJoinRoomStep.setUserId(localUserId);
        localJoinRoomStep.setServer(localServer);
        runner.addStep(localJoinRoomStep);

        runner.addStep(new LocalPeerConnectionCheck(runner));
        runner.addStep(new LocalSubscribeVideoDisplayCheck(runner,remoteUserId));
        runner.addStep(new RemotePeerConnectionCheck(runner));
        runner.addStep(new RemoteSubscribeVideoDisplayCheck(runner));

        remoteKickStep.setRoomId(roomId);
        remoteKickStep.setUserId(remoteUserId);
        remoteKickStep.setServer(remoteServer);
        runner.addStep(remoteKickStep);

        runner.addStep(new ScreenRecordStep(runner,"刚完成了互踢操作，demo截图。"));

        runner.addStep(new LocalPeerConnectionCheck(runner));
        runner.addStep(new LocalSubscribeVideoDisplayCheck(runner,remoteUserId));
        runner.addStep(new AnotherRemotePeerConnectionCheck(runner));
        runner.addStep(new AnotherRemoteSubscribeVideoDisplayCheck(runner));

        if (this.getStats()) {
            runner.addStep(new GetStatsStep(runner, this.getStatsConfig));
        }

        runner.addStep(new ScreenRecordStep(runner,"互踢后音视频互通，测试应该通过了，demo截图。"));

        if (WebDriverUtils.isChrome(runner.getWebDriver())) {
            runner.addStep(new WebRTCInternalsStep(runner));
        }

    }
}
