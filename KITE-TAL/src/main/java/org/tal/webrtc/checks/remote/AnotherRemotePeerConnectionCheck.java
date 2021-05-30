package org.tal.webrtc.checks.remote;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.report.Status;
import io.cosmosoftware.kite.steps.TestCheck;
import io.cosmosoftware.kite.util.TestUtils;
import org.tal.webrtc.pages.remote.AnotherRemoteO2oRTCPage;

import java.net.MalformedURLException;

import static io.cosmosoftware.kite.util.ReportUtils.saveScreenshotPNG;
import static org.webrtc.kite.config.client.RemoteClient.remoteWebDriver;

public class AnotherRemotePeerConnectionCheck extends TestCheck {
    private AnotherRemoteO2oRTCPage anotherRemoteO2oRTCPage;

    public AnotherRemotePeerConnectionCheck(Runner runner) {
        super(runner);
        try {
            anotherRemoteO2oRTCPage = new AnotherRemoteO2oRTCPage(runner);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String stepDescription() {
        return "验证another remote与服务器的ICE连接状态是否为 'connected'";
    }

    @Override
    protected void step() throws KiteTestException {
        try {
            String remoteIceState = "";
            for (int elapsedTime = 0; elapsedTime < this.checkTimeout; elapsedTime += this.checkInterval) {
                remoteIceState = anotherRemoteO2oRTCPage.getIceConnectionState();
                if (remoteIceState.equalsIgnoreCase("failed")) {
                    throw new KiteTestException("remote ice状态已经变为：'failed'", Status.FAILED);
                }
                if (remoteIceState.equalsIgnoreCase("connected") || remoteIceState.equalsIgnoreCase("completed")) {
                    return;
                }
                TestUtils.waitAround(this.checkInterval);
            }
            throw new KiteTestException("获取ice状态超时：" + this.checkTimeout +"another remote ice状态一直为："+remoteIceState, Status.FAILED);
        } catch (Exception e) {
            //force silent to false in case of error, so the failure appears in the report in all cases.
            try {
                String screenshotName = "error_screenshot_" + this.getName();
                reporter.screenshotAttachment(this.report, screenshotName, saveScreenshotPNG(webDriver));
            } catch (KiteTestException ex) {
                logger.warn("Could not attach screenshot to error of step: " + stepDescription());
            }
            reporter.processException(this.report, e, true);
        }
    }
}
