package org.tal.webrtc.tests;

import org.tal.webrtc.checks.local.*;
import org.tal.webrtc.checks.remote.RemotePeerConnectionCheck;
import org.tal.webrtc.checks.remote.RemoteSubscribeVideoDisplayCheck;
import org.tal.webrtc.steps.ScreenRecordStep;
import org.tal.webrtc.steps.local.LocalJoinRoomStep;
import org.tal.webrtc.steps.remote.RemoteJoinRoomStep;
import org.tal.webrtc.steps.remote.RemoteControlAudioStep;
import org.tal.webrtc.steps.remote.RemoteControlVideoStep;
import org.webrtc.kite.tests.TestRunner;

public class WebRtc1v1MuteTest extends TalTest{
    @Override
    protected void populateTestSteps(TestRunner runner) {
        LocalJoinRoomStep localJoinRoomStep = new LocalJoinRoomStep(runner);
        RemoteJoinRoomStep remoteJoinRoomStep = new RemoteJoinRoomStep(runner);

        remoteJoinRoomStep.setRoomId("23982308");
        remoteJoinRoomStep.setUserId("239823082");
        remoteJoinRoomStep.setServer(remoteServer);
        runner.addStep(remoteJoinRoomStep);

        localJoinRoomStep.setRoomId("23982308");
        localJoinRoomStep.setUserId("239823081");
        localJoinRoomStep.setServer(localServer);
        runner.addStep(localJoinRoomStep);

        runner.addStep(new LocalPeerConnectionCheck(runner));
        runner.addStep(new LocalSubscribeVideoDisplayCheck(runner));
        runner.addStep(new RemotePeerConnectionCheck(runner));
        runner.addStep(new RemoteSubscribeVideoDisplayCheck(runner));

        runner.addStep(new RemoteControlAudioStep(runner));
        runner.addStep(new ScreenRecordStep(runner,"remote操作了mute audio，demo截图"));
        runner.addStep(new LocalSubscribeAudioMutedCheck(runner));

        runner.addStep(new RemoteControlAudioStep(runner));
        runner.addStep(new ScreenRecordStep(runner,"remote操作了unmute audio，demo截图"));
        runner.addStep(new LocalSubscribeAudioUnmutedCheck(runner));

        runner.addStep(new RemoteControlVideoStep(runner));
        runner.addStep(new ScreenRecordStep(runner,"remote操作了mute video，demo截图。"));
        runner.addStep(new LocalSubscribeVideoMutedCheck(runner));

        runner.addStep(new ScreenRecordStep(runner,"看不到订阅视频测试通过，demo截图。"));

        runner.addStep(new RemoteControlVideoStep(runner));
        runner.addStep(new ScreenRecordStep(runner,"remote操作了unmute video，demo截图。"));
        runner.addStep(new LocalSubscribeVideoUnmutedCheck(runner));
        runner.addStep(new ScreenRecordStep(runner,"能订阅视频测试通过，demo截图。"));
    }
}