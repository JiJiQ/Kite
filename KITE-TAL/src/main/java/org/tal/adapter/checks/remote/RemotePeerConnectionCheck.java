package org.tal.adapter.checks.remote;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.report.Status;
import io.cosmosoftware.kite.steps.TestCheck;
import io.cosmosoftware.kite.util.TestUtils;
import org.tal.adapter.pages.remote.RemoteO2oRTCPage;

import java.net.MalformedURLException;

import static io.cosmosoftware.kite.util.ReportUtils.saveScreenshotPNG;

public class RemotePeerConnectionCheck extends TestCheck {
    private RemoteO2oRTCPage remoteO2oRTCPage;

    public RemotePeerConnectionCheck(Runner runner) {
        super(runner);
        try {
            remoteO2oRTCPage = new RemoteO2oRTCPage(runner);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String stepDescription() {
        return "验证remote是否为 'joined'状态";
    }

    @Override
    protected void step() throws KiteTestException {
        try {
            String remoteIceState = "";
            for (int elapsedTime = 0; elapsedTime < this.checkTimeout; elapsedTime += this.checkInterval) {
                remoteIceState = remoteO2oRTCPage.getIceConnectionState();
                if (remoteIceState.equalsIgnoreCase("joined")) {
                    return;
                }
                TestUtils.waitAround(this.checkInterval);
            }
            throw new KiteTestException("获取ice状态超时：" + this.checkTimeout +"remote ice状态一直为："+remoteIceState, Status.FAILED);
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
