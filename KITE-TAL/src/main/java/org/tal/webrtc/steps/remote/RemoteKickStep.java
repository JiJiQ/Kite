package org.tal.webrtc.steps.remote;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.steps.TestStep;
import org.tal.webrtc.pages.remote.AnotherRemoteJoinRoomPage;
import org.tal.webrtc.pages.remote.RemoteJoinRoomPage;
import org.webrtc.kite.tests.TestRunner;

import java.net.MalformedURLException;

public class RemoteKickStep extends TestStep {
    protected String roomId;
    protected String remoteUserId;
    protected String remoteServerUrl;
    protected AnotherRemoteJoinRoomPage anotherRemoteJoinRoomPage;
    private String debugOption;

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setUserId(String remoteUserId){
        this.remoteUserId=remoteUserId;
    }

    public void setServer(String remoteServerUrl){
        this.remoteServerUrl=remoteServerUrl;
    }

    public void setDebugOption(String debugOption) {
        this.debugOption = debugOption;
    }

    public RemoteKickStep(TestRunner runner)   {
        super(runner);
        try {
            this.anotherRemoteJoinRoomPage = new AnotherRemoteJoinRoomPage(runner);
        }catch (MalformedURLException e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public String stepDescription() {
        return "another remote踢人 打开demo，进入房间并推流：" + this.roomId;
    }

    @Override
    protected void step() throws KiteTestException {
        anotherRemoteJoinRoomPage.remoteJoinRoom(this.roomId != null ? this.roomId : "23982308",
                this.remoteUserId!=null?this.remoteUserId:"239823082",
                this.remoteServerUrl, this.debugOption != null ? this.debugOption : "");
       }
}
