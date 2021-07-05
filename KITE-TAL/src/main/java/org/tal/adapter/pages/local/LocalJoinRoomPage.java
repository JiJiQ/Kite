package org.tal.adapter.pages.local;

import io.cosmosoftware.kite.entities.Timeouts;
import io.cosmosoftware.kite.exception.KiteInteractionException;
import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.pages.BasePage;
import io.cosmosoftware.kite.util.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.tal.adapter.tests.TalTest;

import static io.cosmosoftware.kite.util.TestUtils.executeJsScript;
import static io.cosmosoftware.kite.util.TestUtils.waitAround;
import static org.webrtc.kite.config.client.RemoteClient.remoteWebDriver;

public class LocalJoinRoomPage extends BasePage {

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
    public LocalJoinRoomPage(Runner runner) {
        super(runner);
    }

    public void localJoinRoom(String roomId, String userId,String serverUrl,String debugOption) throws KiteTestException {
        this.webDriver.get(TalTest.apprtcURL);
        waitAround(1000);
        this.click(selectPreOnline);
        webDriver.manage().window().maximize();
        this.sendKeys(roomIdTextBox,roomId);
        this.sendKeys(userIdTextBox,userId);

        waitAround(2000);
        this.click(joinButton);
        waitAround(1000);
        this.click(joinButton);
        waitAround(2000);
        this.click(createLStreamButton);
        waitAround(2000);
        this.click(publishButton);
        waitAround(2000);
        executeJsScript(this.webDriver, "window.scrollTo(0, document.body.scrollHeight)");
    }
    public void localJoinRoomNoPush(String roomId, String userId,String serverUrl,String debugOption) throws KiteTestException {
        this.webDriver.get(TalTest.apprtcURL);
        logger.info(this.webDriver);
        waitAround(2000);
        webDriver.manage().window().maximize();
        this.sendKeys(roomIdTextBox,roomId);
        this.sendKeys(userIdTextBox,userId);

        waitAround(4000);
        this.click(joinButton);
        waitAround(1000);
        this.webDriver.findElement(By.id("joinRoom")).click();
        waitAround(2000);
    }

    public void waitRemoteVideo(String remoteUserId){
        logger.info("看到这句话后remote再推流，不然会导致streamMap不可控。");
        logger.info("等待远程客户端推流，请使用指定userid："+remoteUserId);
        while (true){
            WebElement remoteVideo;
            try {
                remoteVideo=this.webDriver.findElement(By.xpath("//body/div[@id='remote']/div[@id='remote_"+remoteUserId+"']/video[1]"));
                waitUntilVisibilityOf(remoteVideo, Timeouts.TEN_SECOND_INTERVAL_IN_SECONDS);
                logger.info("远程客户端"+remoteUserId+"已推流，结束等待。");
                break;
            }catch (NoSuchElementException e){
                logger.info("远程客户端还未推流，等待2s。。。");
                TestUtils.waitAround(2000);
            }catch (KiteInteractionException e){
                logger.error(e.getMessage());
            }
        }
    }
    public void waitNoRemoteVideo(String remoteUserId){
        logger.info("确保这个用户不在房间，否则会导致stream map紊乱："+remoteUserId);
        while (true){
            WebElement remoteVideo;
            try {
                remoteVideo=this.webDriver.findElement(By.xpath("//body/div[@id='remote']/div[@id='remote_"+remoteUserId+"']/video[1]"));
                waitUntilVisibilityOf(remoteVideo, Timeouts.TEN_SECOND_INTERVAL_IN_SECONDS);
                logger.info("远程客户端"+remoteUserId+"已推流。");
                logger.info("远程客户端还没有退出房间，等待2s。。。");
                TestUtils.waitAround(2000);
            }catch (NoSuchElementException e){
                logger.info("远程客户端"+remoteUserId+"已停止推流。看到提示后再进房间推流。");
                break;
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
    }

}
