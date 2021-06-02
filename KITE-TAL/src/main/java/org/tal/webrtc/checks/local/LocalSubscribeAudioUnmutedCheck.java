package org.tal.webrtc.checks.local;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.report.Status;
import io.cosmosoftware.kite.steps.TestCheck;
import io.cosmosoftware.kite.util.TestUtils;
import org.tal.webrtc.pages.local.LocalO2oRTCPage;

import static io.cosmosoftware.kite.util.ReportUtils.saveScreenshotPNG;

public class LocalSubscribeAudioUnmutedCheck extends TestCheck {
    protected LocalO2oRTCPage localO2oRTCPage;

    public LocalSubscribeAudioUnmutedCheck(Runner runner) {
        super(runner);
        localO2oRTCPage = new LocalO2oRTCPage(runner);
    }

    @Override
    public String stepDescription() {
        return "验证拉流端音频是否是unmuted状态";
    }

    @Override
    protected void step() {
        try {
            String audioPaused = "uninit";
            for (int elapsedTime = 0; elapsedTime < this.checkTimeout; elapsedTime += this.checkInterval) {
                logger.info("获取音频muted状态");
                audioPaused = localO2oRTCPage.getAudioState(1);

                if (!audioPaused.equalsIgnoreCase("false" )) {
                    TestUtils.waitAround(this.checkInterval);
                } else {
                    reporter.textAttachment(report, "订阅音频muted状态为：",audioPaused, "plain");
                    return;
                }
            }
            throw new KiteTestException("订阅流音频状态为：" + audioPaused, Status.FAILED);
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
