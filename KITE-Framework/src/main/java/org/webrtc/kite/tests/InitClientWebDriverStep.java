package org.webrtc.kite.tests;

import static io.cosmosoftware.kite.util.ReportUtils.getStackTrace;
import static org.webrtc.kite.config.client.RemoteClient.remoteWebDriver;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.interfaces.Runner;
import io.cosmosoftware.kite.report.StatusDetails;
import io.cosmosoftware.kite.steps.TestStep;

import java.net.MalformedURLException;
import java.rmi.Remote;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.webrtc.kite.config.client.Client;
import org.webrtc.kite.config.client.RemoteClient;
import org.webrtc.kite.config.paas.Paas;
import org.webrtc.kite.exception.KiteGridException;

public class InitClientWebDriverStep extends TestStep {
  private final int id;
  private final Client client;
  private final RemoteClient remoteClient;
  private final Map<WebDriver, Map<String, Object>> sessionData;

  public InitClientWebDriverStep(Runner runner, int id, Client client, RemoteClient remoteClient, Map<WebDriver, Map<String, Object>> sessionData) {
    super(runner);
    this.id = id;
    this.client = client;
    this.remoteClient=remoteClient;
    this.sessionData = sessionData;
    this.setGetConsoleLog(false);
  }


  @Override
  protected void step() throws KiteTestException {
    try {
      this.client.setName(this.client.getName() == null ? ("" + id) : ( id + "_" + this.client.getName())) ;
      logger.info("Creating web driver for " + client);
      remoteClient.createWebDriver();
      this.webDriver = client.createWebDriver(sessionData);
      if (sessionData != null && sessionData.containsKey(this.webDriver)) {
        Map<String, Object> clientSessionData = sessionData.get(this.webDriver);
        if (clientSessionData.containsKey("node_host")) {
          logger.debug("created " + client + " on node: " + clientSessionData.get("node_host"));
        }
      }
    } catch (KiteGridException | MalformedURLException e) {
      this.webDriver = null;
      logger.error("Exception while populating webdriver: " + client.getName() + "\r\n" + getStackTrace(e));
      reporter.textAttachment(this.report, "KiteGridException", getStackTrace(e), "plain");
      StatusDetails details = new StatusDetails();
      details.setCode(1);
      details.setMessage("Exception while populating webdrivers: \r\n" + getStackTrace(e));
      this.report.setDetails(details);
    }
  }

  public WebDriver getWebDriver() {
    return this.webDriver;
  }

  @Override
  public String stepDescription() {
    return "Initiate webDriver for client";
  }
}
