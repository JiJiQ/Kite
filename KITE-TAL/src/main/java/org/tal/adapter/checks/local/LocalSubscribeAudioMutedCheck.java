package org.tal.adapter.checks.local;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.report.Status;
import io.cosmosoftware.kite.steps.TestCheck;
import io.cosmosoftware.kite.util.TestUtils;
import org.tal.adapter.pages.local.LocalO2oRTCPage;

import static io.cosmosoftware.kite.util.ReportUtils.saveScreenshotPNG;

public class LocalSubscribeAudioMutedCheck extends TestCheck {
    protected LocalO2oRTCPage localO2oRTCPage;
    String remoteId;
    public LocalSubscribeAudioMutedCheck(Runner runner, String remoteId) {
        super(runner);
        localO2oRTCPage = new LocalO2oRTCPage(runner);
        this.remoteId=remoteId;
    }

    @Override
    public String stepDescription() {
        return "验证拉流端音频的enabled是否为false";
    }

    @Override
    protected void step() {
        try {
            String audioEnabled = "uninit";
            for (int elapsedTime = 0; elapsedTime < this.checkTimeout; elapsedTime += this.checkInterval) {
                logger.info("获取订阅流音频enabled");
                audioEnabled = localO2oRTCPage.getAudioState(remoteId);

                if (!"false".equalsIgnoreCase(audioEnabled)) {
                    TestUtils.waitAround(this.checkInterval);
                } else {
                    reporter.textAttachment(report, "订阅流音频enabled为：",audioEnabled, "plain");
                    return;
                }
            }
            throw new KiteTestException("订阅流音频enabled为：" + audioEnabled, Status.FAILED);
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
