package org.tal.webrtc.checks.local;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.report.Status;
import io.cosmosoftware.kite.steps.TestCheck;
import io.cosmosoftware.kite.util.TestUtils;
import org.tal.webrtc.pages.local.LocalO2oRTCPage;
import org.tal.webrtc.pages.remote.RemoteO2oRTCPage;

import java.net.MalformedURLException;

import static io.cosmosoftware.kite.util.ReportUtils.saveScreenshotPNG;

public class RtcKeep5MCheck extends TestCheck {
    private LocalO2oRTCPage localO2oRTCPage;
    private RemoteO2oRTCPage remoteO2oRTCPage;
    public String remoteUserId;

    public RtcKeep5MCheck(Runner runner,String remoteUserId) {
        super(runner);
        localO2oRTCPage = new LocalO2oRTCPage(runner);
        this.remoteUserId=remoteUserId;
        try {
            remoteO2oRTCPage = new RemoteO2oRTCPage(runner);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void step() throws KiteTestException {
        int times = 60;
        for (int time=0;time<times;time++){
            try {
                String localIceState = "uninit";
                String remoteIceState = "uninit";
                String localVideoCheck = "uninit";
                String remoteVideoCheck = "uninit";
                for (int elapsedTime = 0; elapsedTime < this.checkTimeout; elapsedTime += this.checkInterval) {

                    localIceState = localO2oRTCPage.getIceConnectionState();
                    remoteIceState = remoteO2oRTCPage.getIceConnectionState();
                    localO2oRTCPage.clickPlay(this.remoteUserId!=null?this.remoteUserId:"2398230802");
                    localVideoCheck = localO2oRTCPage.subscribeVideoCheck(1);
                    remoteVideoCheck = remoteO2oRTCPage.subscribeVideoCheck(1);

                    if (localIceState.equalsIgnoreCase("failed")) {
                        throw new KiteTestException("local ice状态已经变为：'failed'", Status.FAILED);
                    }
                    if (remoteIceState.equalsIgnoreCase("failed")) {
                        throw new KiteTestException("local ice状态已经变为：'failed'", Status.FAILED);
                    }

                    boolean ifKeep5M=
                            (localIceState.equalsIgnoreCase("connected") || localIceState.equalsIgnoreCase("completed"))
                            &&
                            (remoteIceState.equalsIgnoreCase("connected") || remoteIceState.equalsIgnoreCase("completed"))
                            &&
                            ("video".equalsIgnoreCase(localVideoCheck))
                            &&
                            ("video".equalsIgnoreCase(remoteVideoCheck));
                    if(!ifKeep5M){
                        reporter.textAttachment(report, "localIceState：", localIceState, "plain");
                        reporter.textAttachment(report, "remoteIceState：", remoteIceState, "plain");
                        reporter.textAttachment(report, "localVideoCheck：", localVideoCheck, "plain");
                        reporter.textAttachment(report, "remoteVideoCheck：", remoteVideoCheck, "plain");
                    }else{
                        break;
                    }
                    TestUtils.waitAround(this.checkInterval);
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
            TestUtils.waitAround(5000);
            logger.info("time："+time*5+"s");
        }
    }

    @Override
    public String stepDescription() {
        return "local和remote保持5分钟音视频互通检查。";
    }
}
