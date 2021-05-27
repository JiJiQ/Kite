package org.tal.webrtc.steps;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.steps.StepPhase;
import io.cosmosoftware.kite.steps.TestStep;

import static io.cosmosoftware.kite.util.ReportUtils.saveScreenshotPNG;
import static io.cosmosoftware.kite.util.ReportUtils.timestamp;

public class ScreenRecordStep extends TestStep {

    public ScreenRecordStep(Runner runner) {
        super(runner);
        setStepPhase(StepPhase.ALL);
        setOptional(true);
    }

    @Override
    public String stepDescription() {
        return "应该通过了,demo截图";
    }

    @Override
    protected void step() throws KiteTestException {
        reporter.screenshotAttachment(report,"ScreenshotStep_"+timestamp(),saveScreenshotPNG(webDriver));
    }

}
