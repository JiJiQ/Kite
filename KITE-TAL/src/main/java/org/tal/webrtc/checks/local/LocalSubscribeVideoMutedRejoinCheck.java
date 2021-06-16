package org.tal.webrtc.checks.local;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.report.Status;
import io.cosmosoftware.kite.steps.TestCheck;
import io.cosmosoftware.kite.util.TestUtils;
import org.tal.webrtc.pages.local.LocalO2oRTCPage;

import static io.cosmosoftware.kite.util.ReportUtils.saveScreenshotPNG;

public class LocalSubscribeVideoMutedRejoinCheck extends TestCheck {
    protected LocalO2oRTCPage localO2oRTCPage;
    int remoteIndex;
    public LocalSubscribeVideoMutedRejoinCheck(Runner runner, int remoteIndex) {
        super(runner);
        localO2oRTCPage = new LocalO2oRTCPage(runner);
        this.remoteIndex=remoteIndex;
    }

    @Override
    public String stepDescription() {
        return "退出重进后验证拉流视频的muted是否为true";
    }

    @Override
    protected void step() {
        try {
            String VideoMuted = "uninit";
            for (int elapsedTime = 0; elapsedTime < this.checkTimeout; elapsedTime += this.checkInterval) {
                logger.info("获取订阅流视频状态和muted");
                VideoMuted = localO2oRTCPage.getVideoState(1);
                if (!"true".equalsIgnoreCase(VideoMuted)) {
                    TestUtils.waitAround(this.checkInterval);
                } else {
                    logger.info("订阅流视频muted为：" + VideoMuted);
                    reporter.textAttachment(report, "订阅视频muted为：",VideoMuted, "plain");
                    return;
                }
            }
            if(!"true".equalsIgnoreCase(VideoMuted)){
                throw new KiteTestException("订阅视频muted为："+VideoMuted,Status.FAILED);
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
