package org.tal.webrtc.steps;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.steps.StepPhase;
import io.cosmosoftware.kite.steps.TestStep;
import io.cosmosoftware.kite.util.TestUtils;

import static io.cosmosoftware.kite.util.ReportUtils.saveScreenshotPNG;
import static io.cosmosoftware.kite.util.ReportUtils.timestamp;

public class ScreenRecordStep extends TestStep {
    String describe;
    public ScreenRecordStep(Runner runner,String describe) {
        super(runner);
        this.describe=describe;
        setStepPhase(StepPhase.ALL);
        setOptional(true);
    }

    @Override
    public String stepDescription() {
        return this.describe;
    }

    @Override
    protected void step() throws KiteTestException {
        TestUtils.waitAround(500);
        reporter.screenshotAttachment(report, "ScreenshotStep_" + timestamp(), saveScreenshotPNG(webDriver));
        TestUtils.waitAround(3000);
    }

}
