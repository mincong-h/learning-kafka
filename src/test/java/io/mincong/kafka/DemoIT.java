package io.mincong.kafka;

import org.apache.curator.test.TestingServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DemoIT {

  private TestingServer zooKeeper;

  @Before
  public void setUp() throws Exception {
    zooKeeper = new TestingServer();
  }

  @After
  public void tearDown() throws Exception {
    zooKeeper.close();
  }

  @Test
  public void demo() {
    // do nothing
  }
}
