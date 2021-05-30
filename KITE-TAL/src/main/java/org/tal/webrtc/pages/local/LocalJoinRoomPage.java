package org.tal.webrtc.pages.local;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.tal.webrtc.tests.TalTest;

import static io.cosmosoftware.kite.util.TestUtils.executeJsScript;
import static io.cosmosoftware.kite.util.TestUtils.waitAround;

public class LocalJoinRoomPage extends BasePage {

    @FindBy(id = "roomId")
    WebElement roomIdTextBox;

    @FindBy(id="userId")
    WebElement userIdTextBox;

    @FindBy(id="server")
    WebElement serverTextBox;

    @FindBy(id = "joinRoom")
    WebElement joinButton;

    @FindBy(id = "createLocalStream")
    WebElement createLStreamButton;

    @FindBy(id = "publish")
    WebElement publishButton;

    public LocalJoinRoomPage(Runner runner) {
        super(runner);
    }

    public void localJoinRoom(String roomId, String userId,String serverUrl,String debugOption) throws KiteTestException {
        this.webDriver.get(TalTest.apprtcURL);
        logger.info(this.webDriver);
        waitAround(2000);
        webDriver.manage().window().maximize();
        this.sendKeys(roomIdTextBox,roomId);
        this.sendKeys(userIdTextBox,userId);
        this.sendKeys(serverTextBox,serverUrl);

        waitAround(4000);
        this.click(joinButton);
        waitAround(1000);
        this.webDriver.findElement(By.id("joinRoom")).click();
        waitAround(2000);
        this.click(createLStreamButton);
        waitAround(2000);
        this.click(publishButton);
        waitAround(2000);
        executeJsScript(this.webDriver, "window.scrollTo(0, document.body.scrollHeight)");
    }
}
