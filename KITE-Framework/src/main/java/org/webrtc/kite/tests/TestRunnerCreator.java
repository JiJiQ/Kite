package org.webrtc.kite.tests;


import io.cosmosoftware.kite.report.KiteLogger;
import org.webrtc.kite.config.client.Client;
import org.webrtc.kite.config.client.RemoteClient;

import java.io.IOException;
import java.util.concurrent.Callable;

import static io.cosmosoftware.kite.util.TestUtils.waitAround;

public class TestRunnerCreator implements Callable<TestRunner> {

  private final int interval;
  private final int id;
  private final Client client;
  private final RemoteClient remoteClient;
  private final KiteBaseTest test;
  protected final KiteLogger logger;
  
  
  TestRunnerCreator(Client client, RemoteClient remoteClient,KiteBaseTest test, int id) throws IOException {
    this.interval = test.getInterval(id);
    this.id = id;
    this.test = test;
    this.client = client;
    this.remoteClient = remoteClient;
    this.logger = test.testConfig.getLogger();
  }
  
  @Override
  public TestRunner call() throws Exception {
    logger.info("Start creating the TestRunner id " + id + " in " + interval + "ms");
    waitAround(interval);
    return new TestRunner(client,remoteClient, test, id);
  }
  
}
