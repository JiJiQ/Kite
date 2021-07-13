package org.tal.adapter.tests;

import org.tal.adapter.checks.local.*;
import org.tal.adapter.checks.remote.RemotePeerConnectionCheck;
import org.tal.adapter.checks.remote.RemoteSubscribeVideoDisplayCheck;
import org.tal.adapter.steps.ScreenRecordStep;
import org.tal.adapter.steps.local.LocalJoinRoomStep;
import org.tal.adapter.steps.remote.RemoteJoinRoomStep;
import org.tal.adapter.steps.remote.RemoteMuteVideoStep;
import org.tal.adapter.steps.remote.RemoteUnmuteVideoStep;
import org.webrtc.kite.tests.TestRunner;

public class WebRtcQuitRecoveryVideoTest extends TalTest {
    @Override
    protected void populateTestSteps(TestRunner runner) {
        LocalJoinRoomStep localJoinRoomStep = new LocalJoinRoomStep(runner);
        RemoteJoinRoomStep remoteJoinRoomStep = new RemoteJoinRoomStep(runner);

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

        runner.addStep(new RemoteMuteVideoStep(runner));
        runner.addStep(new ScreenRecordStep(runner,"remote操作了mute video，demo截图。"));
        runner.addStep(new LocalSubscribeVideoMutedCheck(runner,remoteUserId));
        runner.addStep(new ScreenRecordStep(runner,"看不到订阅视频测试通过，demo截图。"));

        runner.addStep(localJoinRoomStep);//local退出重进房间
        runner.addStep(new LocalSubscribeVideoMutedRejoinCheck(runner,remoteUserId));//local stream刷新后remote stream的index为0
        runner.addStep(new ScreenRecordStep(runner,"退出重进后看不到订阅视频测试通过，demo截图。"));

        runner.addStep(new RemoteUnmuteVideoStep(runner));
        runner.addStep(new ScreenRecordStep(runner,"remote操作了unmute video，demo截图。"));
        runner.addStep(new LocalSubscribeVideoUnmutedCheck(runner,remoteUserId));//local stream刷新后remote stream的index为0
        runner.addStep(new ScreenRecordStep(runner,"能看到订阅视频测试通过，demo截图。"));
    }
}
