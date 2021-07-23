package org.tal.webrtc.checks.remote;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.report.Status;
import io.cosmosoftware.kite.steps.TestCheck;
import io.cosmosoftware.kite.util.TestUtils;
import org.tal.webrtc.pages.remote.AnotherRemoteO2oRTCPage;

import java.net.MalformedURLException;

import static io.cosmosoftware.kite.util.ReportUtils.saveScreenshotPNG;
import static org.webrtc.kite.config.client.RemoteClient.anotherRemoteWebDriver;

public class AnotherRemoteSubscribeVideoDisplayCheck extends TestCheck {
    AnotherRemoteO2oRTCPage anotherRemoteO2oRTCPage;

    public AnotherRemoteSubscribeVideoDisplayCheck(Runner runner) {
        super(runner);
        try {
            anotherRemoteO2oRTCPage=new AnotherRemoteO2oRTCPage(runner);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void step() {
        try {
            String videoCheck = "uninit";
            for (int elapsedTime = 0; elapsedTime < this.checkTimeout; elapsedTime += 5*this.checkInterval) {
                logger.info("another remote 获取拉流视频播放控件");
                videoCheck = anotherRemoteO2oRTCPage.subscribeVideoCheck(1);
                if (!"video".equalsIgnoreCase(videoCheck)) {
                    TestUtils.waitAround(this.checkInterval);
                } else {
                    logger.info("another remote 拉流视频状态为：" + videoCheck);
                    reporter.textAttachment(report, "another remote 拉流视频状态为：", videoCheck, "plain");
                    return;
                }
            }
            throw new KiteTestException("another remote 拉流视频状态为：" + videoCheck, Status.FAILED);
        } catch (Exception e) {
            //force silent to false in case of error, so the failure appears in the report in all cases.
            try {
                String screenshotName = "error_screenshot_" + this.getName();
                reporter.screenshotAttachment(this.report, screenshotName, saveScreenshotPNG(anotherRemoteWebDriver));
            } catch (KiteTestException ex) {
                logger.warn("Could not attach screenshot to error of step: " + stepDescription());
            }
            reporter.processException(this.report, e, true);
        }
    }

    @Override
    public String stepDescription() {
        return "验证another remote推流端拉流视频是否正常。";
    }
}
