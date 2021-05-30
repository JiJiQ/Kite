package org.tal.webrtc.checks;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.report.Status;
import io.cosmosoftware.kite.steps.TestCheck;
import io.cosmosoftware.kite.util.TestUtils;
import org.tal.webrtc.pages.local.LocalO2oRTCPage;
import org.tal.webrtc.pages.remote.RemoteO2oRTCPage;

import java.net.MalformedURLException;

import static io.cosmosoftware.kite.util.ReportUtils.saveScreenshotPNG;
import static org.webrtc.kite.config.client.RemoteClient.remoteWebDriver;

public class PeerConnectionCheck extends TestCheck {
    private LocalO2oRTCPage localO2oRTCPage;
    private RemoteO2oRTCPage remoteO2oRTCPage;

    public PeerConnectionCheck(Runner runner) {
        super(runner);
        localO2oRTCPage = new LocalO2oRTCPage(runner);
        try {
            remoteO2oRTCPage = new RemoteO2oRTCPage(runner);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String stepDescription() {
        return "验证本端和对端与服务器的ICE连接状态是否为 'connected'";
    }

    @Override
    protected void step() throws KiteTestException {
        try {
            String localIceState = "";
            String remoteIceState = "";
            for (int elapsedTime = 0; elapsedTime < this.checkTimeout; elapsedTime += this.checkInterval) {
                localIceState = localO2oRTCPage.getIceConnectionState();
                remoteIceState = remoteO2oRTCPage.getIceConnectionState();
                if (localIceState.equalsIgnoreCase("failed")) {
                    throw new KiteTestException("local ice状态已经变为：'failed'", Status.FAILED);
                }

                if (remoteIceState.equalsIgnoreCase("failed")) {
                    throw new KiteTestException("remote ice状态已经变为：'failed'", Status.FAILED);
                }

                boolean iceCheckValue=localIceState.equalsIgnoreCase("connected") || localIceState.equalsIgnoreCase("completed");
                iceCheckValue=iceCheckValue && (remoteIceState.equalsIgnoreCase("connected") || remoteIceState.equalsIgnoreCase("completed"));
                if (iceCheckValue) {
                    return;
                }
                TestUtils.waitAround(this.checkInterval);
            }
            throw new KiteTestException("获取ice状态超时：" + this.checkTimeout + ",local ice状态一直为：" + localIceState+",remote ice状态一直为："+remoteIceState, Status.FAILED);
        } catch (Exception e) {
            remoteWebDriver.close();
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
