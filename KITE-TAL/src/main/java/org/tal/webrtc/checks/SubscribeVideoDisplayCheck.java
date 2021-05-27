package org.tal.webrtc.checks;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.report.Status;
import io.cosmosoftware.kite.steps.TestCheck;
import org.tal.webrtc.pages.O2oRTCPage;

public class SubscribeVideoDisplayCheck extends TestCheck {
    protected O2oRTCPage o2oRTCPage;
    public SubscribeVideoDisplayCheck(Runner runner) {
        super(runner);
        o2oRTCPage=new O2oRTCPage(runner);
    }

    @Override
    public String stepDescription() {
        return "验证拉流视频是否正常播放";
    }

    @Override
    protected void step() throws KiteTestException {
        logger.info("获取订阅视频播放控件");
        String videoCheck=o2oRTCPage.subscribeVideoCheck();
        if(!"video".equalsIgnoreCase(videoCheck)){
            reporter.textAttachment(report,"订阅视频",videoCheck,"plain");
            throw new KiteTestException("订阅视频"+videoCheck, Status.FAILED);
        }
    }

}
