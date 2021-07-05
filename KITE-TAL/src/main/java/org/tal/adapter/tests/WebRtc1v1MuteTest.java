package org.tal.adapter.tests;

import org.tal.adapter.checks.local.*;
import org.tal.adapter.checks.remote.RemotePeerConnectionCheck;
import org.tal.adapter.checks.remote.RemoteSubscribeVideoDisplayCheck;
import org.tal.adapter.steps.ScreenRecordStep;
import org.tal.adapter.steps.local.LocalJoinRoomStep;
import org.tal.adapter.steps.remote.*;
import org.webrtc.kite.tests.TestRunner;

public class WebRtc1v1MuteTest extends TalTest {
    @Override
    protected void populateTestSteps(TestRunner runner) {
        LocalJoinRoomStep localJoinRoomStep = new LocalJoinRoomStep(runner);
        RemoteJoinRoomStep remoteJoinRoomStep = new RemoteJoinRoomStep(runner);

        //local先进，remote后进，否则会导致streamMap混乱
        localJoinRoomStep.setRoomId(roomId);
        localJoinRoomStep.setUserId(localUserId);
        runner.addStep(localJoinRoomStep);

        remoteJoinRoomStep.setRoomId(roomId);
        remoteJoinRoomStep.setUserId(remoteUserId);
        runner.addStep(remoteJoinRoomStep);

        runner.addStep(new LocalPeerConnectionCheck(runner));
        runner.addStep(new LocalSubscribeVideoDisplayCheck(runner,remoteUserId));
        runner.addStep(new RemotePeerConnectionCheck(runner));
        runner.addStep(new RemoteSubscribeVideoDisplayCheck(runner));

        runner.addStep(new RemoteMuteAudioStep(runner));
        runner.addStep(new ScreenRecordStep(runner,"remote操作了mute audio，demo截图"));
        runner.addStep(new LocalSubscribeAudioMutedCheck(runner,TalTest.remoteUserId));

        runner.addStep(new RemoteUnmuteAudioStep(runner));
        runner.addStep(new ScreenRecordStep(runner,"remote操作了unmute audio，demo截图"));
        runner.addStep(new LocalSubscribeAudioUnmutedCheck(runner,TalTest.remoteUserId));

        runner.addStep(new RemoteMuteVideoStep(runner));
        runner.addStep(new ScreenRecordStep(runner,"remote操作了mute video，demo截图。"));
        runner.addStep(new LocalSubscribeVideoMutedCheck(runner,remoteUserId));

        runner.addStep(new ScreenRecordStep(runner,"看不到订阅视频测试通过，demo截图。"));

        runner.addStep(new RemoteUnmuteVideoStep(runner));
        runner.addStep(new ScreenRecordStep(runner,"remote操作了unmute video，demo截图。"));
        runner.addStep(new LocalSubscribeVideoUnmutedCheck(runner,remoteUserId));
        runner.addStep(new ScreenRecordStep(runner,"能订阅视频测试通过，demo截图。"));
    }
}
