package org.tal.adapter.checks.local;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.report.Status;
import io.cosmosoftware.kite.steps.TestCheck;
import io.cosmosoftware.kite.util.TestUtils;
import org.tal.adapter.pages.local.LocalO2oRTCPage;

import static io.cosmosoftware.kite.util.ReportUtils.saveScreenshotPNG;

public class LocalPeerConnectionCheck extends TestCheck {
    private LocalO2oRTCPage localO2oRTCPage;

    public LocalPeerConnectionCheck(Runner runner) {
        super(runner);
        localO2oRTCPage = new LocalO2oRTCPage(runner);
    }

    @Override
    public String stepDescription() {
        return "验证local与服务器的ICE连接状态是否为 'connected'";
    }

    @Override
    protected void step() throws KiteTestException {
        try {
            String localIceState = "";
            for (int elapsedTime = 0; elapsedTime < this.checkTimeout; elapsedTime += this.checkInterval) {
                localIceState = localO2oRTCPage.getIceConnectionState();
                if (localIceState.equalsIgnoreCase("joined")) {
                    return;
                }
                TestUtils.waitAround(this.checkInterval);
            }
            throw new KiteTestException("获取ice状态超时：" + this.checkTimeout + ",local ice状态一直为：" + localIceState, Status.FAILED);
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
