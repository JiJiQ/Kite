package org.tal.webrtc.pages;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.tal.webrtc.tests.MediasoupTest;

import static io.cosmosoftware.kite.util.TestUtils.executeJsScript;
import static io.cosmosoftware.kite.util.TestUtils.waitAround;

public class JoinRoomPageAndStream extends BasePage {

    @FindBy(id="roomId")
    WebElement roomIdTextBox;

    @FindBy(id="joinRoom")
    WebElement joinButton;

    @FindBy(id="createLocalStream")
    WebElement createLStreamButton;

    @FindBy(id="publish")
    WebElement publishButton;

    public JoinRoomPageAndStream(Runner runner) {
        super(runner);
    }
    public void joinRoom(String roomId,String debugOption)throws KiteTestException {
        this.webDriver.get(MediasoupTest.apprtcURL);
        this.sendKeys(this.roomIdTextBox,roomId);
        waitAround(2000);
        this.click(this.joinButton);
        waitAround(2000);
        this.click(this.createLStreamButton);
        waitAround(2000);
        this.click(this.publishButton);
        logger.info("本地推流成功");
        waitAround(1000);
        this.maximizeCurrentWindow();
        executeJsScript(this.webDriver,"window.scrollTo(0, document.body.scrollHeight)");
    }
}
