package org.tal.webrtc.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.webrtc.kite.WebDriverFactory;
import org.webrtc.kite.config.client.RemoteClient;

import javax.json.JsonObject;
import java.net.URL;

import static io.cosmosoftware.kite.util.TestUtils.readJsonFile;

public class SafariTest {

    public static void main(String[] args) throws Exception {
        JsonObject configObject=readJsonFile("/Users/apple/Kite/KITE-TAL/configs/webrtc.1v1.simple.json");
        URL url=new URL("http://localhost:4444/wd/hub");
        RemoteClient remoteClient=new RemoteClient(configObject.getJsonObject("remoteClients"));

        System.out.println(remoteClient);

        RemoteWebDriver webDriver = new RemoteWebDriver(url, WebDriverFactory.createCapabilities(remoteClient, "", ""));
        webDriver.get("https://112-126-100-194.eaydu.com/index");

        Thread.sleep(2000);
        webDriver.manage().window().maximize();
        webDriver.findElement(By.id("joinRoom")).click();

        Thread.sleep(2000);
        webDriver.findElement(By.id("createLocalStream")).click();
        Thread.sleep(2000);
        webDriver.findElement(By.id("publish")).click();

        Thread.sleep(10000);
        webDriver.quit();
    }
}
