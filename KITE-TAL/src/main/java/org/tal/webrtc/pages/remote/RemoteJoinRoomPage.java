package org.tal.webrtc.pages.remote;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.tal.webrtc.pages.RemoteBasePage;
import org.tal.webrtc.tests.TalTest;

import java.net.MalformedURLException;

import static io.cosmosoftware.kite.util.TestUtils.waitAround;
import static org.webrtc.kite.config.client.RemoteClient.remoteWebDriver;

public class RemoteJoinRoomPage extends RemoteBasePage {

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


    public RemoteJoinRoomPage(Runner runner) throws MalformedURLException {
        super(runner);
    }

    public void remoteJoinRoom(String roomId, String userId,String serverUrl,String debugOption) throws KiteTestException {
        remoteWebDriver.get(TalTest.apprtcURL);
        waitAround(500);
        this.sendKeys(this.roomIdTextBox, roomId);
        this.sendKeys(this.userIdTextBox,userId);
        this.sendKeys(this.serverTextBox,serverUrl);
        waitAround(2000);
        this.click(this.joinButton);
        waitAround(2000);
        this.click(this.createLStreamButton);
        waitAround(2000);
        this.click(this.publishButton);
        logger.info("remote 推流成功");
        waitAround(1000);
    }
}
