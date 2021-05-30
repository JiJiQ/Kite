package org.tal.webrtc.tests;

import io.cosmosoftware.kite.steps.WebRTCInternalsStep;
import io.cosmosoftware.kite.util.WebDriverUtils;
import org.tal.webrtc.checks.remote.AnotherRemotePeerConnectionCheck;
import org.tal.webrtc.checks.remote.AnotherRemotePublishVideoDisplayCheck;
import org.tal.webrtc.checks.local.LocalPeerConnectionCheck;
import org.tal.webrtc.checks.local.LocalSubscribeVideoDisplayCheck;
import org.tal.webrtc.checks.remote.RemotePeerConnectionCheck;
import org.tal.webrtc.checks.remote.RemotePublishVideoDisplayCheck;
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

        remoteJoinRoomStep.setRoomId("23982308");
        remoteJoinRoomStep.setUserId("239823082");
        remoteJoinRoomStep.setServer(remoteServer);
        runner.addStep(remoteJoinRoomStep);

        localJoinRoomStep.setRoomId("23982308");
        localJoinRoomStep.setUserId("239823081");
        localJoinRoomStep.setServer(localServer);
        runner.addStep(localJoinRoomStep);

        runner.addStep(new LocalPeerConnectionCheck(runner));
        runner.addStep(new RemotePeerConnectionCheck(runner));
        runner.addStep(new RemotePublishVideoDisplayCheck(runner));
        runner.addStep(new LocalSubscribeVideoDisplayCheck(runner));

        remoteKickStep.setRoomId("23982308");
        remoteKickStep.setUserId("239823082");
        remoteKickStep.setServer(remoteServer);
        runner.addStep(remoteKickStep);

        ScreenRecordStep kickScreen=new ScreenRecordStep(runner,"刚完成了互踢操作，demo截图。");
        runner.addStep(kickScreen);

        runner.addStep(new LocalPeerConnectionCheck(runner));
        runner.addStep(new AnotherRemotePeerConnectionCheck(runner));
        runner.addStep(new AnotherRemotePublishVideoDisplayCheck(runner));
        runner.addStep(new LocalSubscribeVideoDisplayCheck(runner));

        if (this.getStats()) {
            runner.addStep(new GetStatsStep(runner, this.getStatsConfig));
        }

        ScreenRecordStep endScreen=new ScreenRecordStep(runner,"测试应该通过了，demo截图。");
        runner.addStep(endScreen);

        if (WebDriverUtils.isChrome(runner.getWebDriver())) {
            runner.addStep(new WebRTCInternalsStep(runner));
        }

    }
}
