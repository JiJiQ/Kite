package org.tal.webrtc.steps;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.steps.TestStep;
import org.tal.webrtc.pages.JoinRoomPageAndStream;
import org.webrtc.kite.tests.TestRunner;

public class JoinRoomStep extends TestStep {
    protected String roomId;
    private final JoinRoomPageAndStream joinRoomPageAndStream;
    private String debugOption;

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setDebugOption(String debugOption) {
        this.debugOption = debugOption;
    }

    public JoinRoomStep(TestRunner runner) {
        super(runner);
        this.joinRoomPageAndStream = new JoinRoomPageAndStream(runner);
    }

    @Override
    public String stepDescription() {
        return "打开demo，进入房间：" + this.roomId;
    }

    @Override
    protected void step() throws KiteTestException {
        joinRoomPageAndStream.joinRoom(this.roomId != null ? this.roomId : "23982308", this.debugOption != null ? this.debugOption : "");
    }
}
