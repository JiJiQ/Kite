package org.tal.adapter.pages.remote;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.tal.adapter.pages.RemoteBasePage;
import org.tal.adapter.tests.TalTest;

import java.net.MalformedURLException;

import static io.cosmosoftware.kite.util.TestUtils.waitAround;
import static org.webrtc.kite.config.client.RemoteClient.remoteWebDriver;

public class RemoteJoinRoomPage extends RemoteBasePage {

    @FindBy(id = "txroomid")
    WebElement roomIdTextBox;

    @FindBy(id="userid")
    WebElement userIdTextBox;

    @FindBy(id = "btnJoinRoom")
    WebElement joinButton;

    @FindBy(id = "createStream")
    WebElement createLStreamButton;

    @FindBy(id = "publishStream")
    WebElement publishButton;

    @FindBy(id = "selectPreOnline")
    WebElement selectPreOnline;
    public RemoteJoinRoomPage(Runner runner) throws MalformedURLException {
        super(runner);
    }

    public void remoteJoinRoom(String roomId, String userId,String serverUrl,String debugOption) throws KiteTestException {
        PageFactory.initElements(remoteWebDriver, this);
        remoteWebDriver.get(TalTest.apprtcURL);
        waitAround(500);
        this.click(selectPreOnline);
        this.sendKeys(this.roomIdTextBox, roomId);
        this.sendKeys(this.userIdTextBox,userId);
        waitAround(2000);
        this.click(this.joinButton);
        waitAround(2000);
        this.click(this.createLStreamButton);
        waitAround(2000);
        this.click(this.publishButton);
        logger.info("remote 推流成功");
        waitAround(2000);
    }
}
