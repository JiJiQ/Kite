package org.tal.webrtc.checks;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.report.Status;
import io.cosmosoftware.kite.steps.TestCheck;
import io.cosmosoftware.kite.util.TestUtils;
import org.tal.webrtc.pages.O2oRTCPage;

public class PeerConnectionCheck extends TestCheck {
    private O2oRTCPage o2oRTCPage;

    public PeerConnectionCheck(Runner runner) {
        super(runner);
        o2oRTCPage = new O2oRTCPage(runner);
    }

    @Override
    public String stepDescription() {
        return "验证ice连接状态是否为：'connected'";
    }

    @Override
    protected void step() throws KiteTestException {
        String iceState = "";
        for (int elapsedTime = 0; elapsedTime < this.checkTimeout; elapsedTime += this.checkInterval) {
            iceState = o2oRTCPage.getIceConnectionState();
            if (iceState.equalsIgnoreCase("failed")) {
                throw new KiteTestException("ice状态已经变为：'failed'", Status.FAILED);
            }
            if (iceState.equalsIgnoreCase("connected") || iceState.equalsIgnoreCase("completed")) {
                return;
            }
            TestUtils.waitAround(this.checkInterval);
        }
        throw new KiteTestException("获取ice状态超时：" + this.checkTimeout + ",ice状态一直为：" + iceState, Status.FAILED);
    }

}
