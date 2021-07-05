package org.tal.adapter.checks.local;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.report.Status;
import io.cosmosoftware.kite.steps.TestCheck;
import io.cosmosoftware.kite.util.TestUtils;
import org.tal.adapter.pages.local.LocalO2oRTCPage;

import static io.cosmosoftware.kite.util.ReportUtils.saveScreenshotPNG;

public class LocalSubscribeVideoMutedCheck extends TestCheck {
    protected LocalO2oRTCPage localO2oRTCPage;
    String remoteId;
    public LocalSubscribeVideoMutedCheck(Runner runner, String remoteId) {
        super(runner);
        localO2oRTCPage = new LocalO2oRTCPage(runner);
        this.remoteId=remoteId;
    }

    @Override
    public String stepDescription() {
        return "验证拉流视频的enabled是否为false";
    }

    @Override
    protected void step() {
        try {
            String videoCheck = "uninit";
            String VideoEnabled = "uninit";
            for (int elapsedTime = 0; elapsedTime < this.checkTimeout; elapsedTime += this.checkInterval) {
                logger.info("获取订阅流视频状态和muted");
                videoCheck = localO2oRTCPage.subscribeVideoCheck(1);
                VideoEnabled = localO2oRTCPage.getVideoState(remoteId);
                if (!"false".equalsIgnoreCase(VideoEnabled) &&
                        !"freeze".equalsIgnoreCase(videoCheck)) {
                    TestUtils.waitAround(this.checkInterval);
                } else {
                    logger.info("订阅流视频状态为：" + videoCheck);
                    logger.info("订阅流视频muted为：" + VideoEnabled);
                    reporter.textAttachment(report, "订阅视频状态为：",videoCheck, "plain");
                    reporter.textAttachment(report, "订阅视频muted为：",VideoEnabled, "plain");
                    return;
                }
            }
            if(!"freeze".equalsIgnoreCase(videoCheck)){
                throw new KiteTestException("订阅流视频状态为：" + videoCheck, Status.FAILED);
            }
            if(!"false".equalsIgnoreCase(VideoEnabled)){
                throw new KiteTestException("订阅视频muted为："+VideoEnabled,Status.FAILED);
            }
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