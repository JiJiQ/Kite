package org.tal.webrtc.steps.local;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.steps.TestStep;
import org.tal.webrtc.pages.local.LocalJoinRoomPage;
import org.webrtc.kite.tests.TestRunner;


public class LocalJoinRoomWaitStep extends TestStep {
    protected String roomId;
    protected String localUserId;
    protected String remoteUserId;
    protected String localServerUrl;
    protected LocalJoinRoomPage localJoinRoomPage;
    private String debugOption;

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setUserId(String localUserId){
        this.localUserId=localUserId;
    }

    public void setRemoteUserId(String remoteUserId){this.remoteUserId=remoteUserId;};

    public void setServer(String localServerUrl){
        this.localServerUrl=localServerUrl;
    }


    public void setDebugOption(String debugOption) {
        this.debugOption = debugOption;
    }

    public LocalJoinRoomWaitStep(TestRunner runner)   {
        super(runner);
        this.localJoinRoomPage = new LocalJoinRoomPage(runner);
    }

    @Override
    public String stepDescription() {
        return "local 打开demo，进入房间并推流：" + this.roomId;
    }

    @Override
    protected void step() throws KiteTestException {
        localJoinRoomPage.localJoinRoomNoPush(this.roomId != null ? this.roomId : "23982308",
            this.localUserId!=null?this.localUserId:"239823082",
            this.localServerUrl, this.debugOption != null ? this.debugOption : "");
        localJoinRoomPage.waitNoRemoteVideo(this.remoteUserId!=null?this.remoteUserId:"1001");

        localJoinRoomPage.localJoinRoom(this.roomId != null ? this.roomId : "23982308",
                this.localUserId!=null?this.localUserId:"239823082",
                this.localServerUrl, this.debugOption != null ? this.debugOption : "");
        localJoinRoomPage.waitRemoteVideo(this.remoteUserId!=null?this.remoteUserId:"1001");
    }
}
