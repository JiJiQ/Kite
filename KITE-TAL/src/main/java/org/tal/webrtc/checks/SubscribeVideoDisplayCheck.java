package org.tal.webrtc.checks;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.report.Status;
import io.cosmosoftware.kite.steps.TestCheck;
import io.cosmosoftware.kite.util.TestUtils;
import org.tal.webrtc.pages.O2oRTCPage;

public class SubscribeVideoDisplayCheck extends TestCheck {
    protected O2oRTCPage o2oRTCPage;

    public SubscribeVideoDisplayCheck(Runner runner) {
        super(runner);
        o2oRTCPage = new O2oRTCPage(runner);
    }

    @Override
    public String stepDescription() {
        return "验证拉流视频是否正常播放";
    }

    @Override
    protected void step() throws KiteTestException {
        String videoCheck = "uninit";
        String audioLevel = "uninit";
        for (int elapsedTime = 0; elapsedTime < this.checkTimeout; elapsedTime += this.checkInterval) {
            logger.info("获取订阅视频播放控件");
            videoCheck = o2oRTCPage.subscribeVideoCheck(1);
            audioLevel = o2oRTCPage.getAudioLevel(1);
            if (!"video".equalsIgnoreCase(videoCheck)) {
                TestUtils.waitAround(2000);
                throw new KiteTestException("订阅视频状态为：" + videoCheck, Status.FAILED);
            } else {
                return;
            }
        }
        reporter.textAttachment(report, "订阅视频状态为：", videoCheck, "plain");
    }

}
