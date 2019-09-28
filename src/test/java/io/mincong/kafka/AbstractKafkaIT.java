package io.mincong.kafka;

import io.debezium.kafka.KafkaCluster;
import io.debezium.util.Testing;
import java.io.File;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * @author Mincong Huang
 * @since 0.1.0
 */
public abstract class AbstractKafkaIT {

  private static File dataDir;
  protected static KafkaCluster kafkaCluster;

  private static KafkaCluster kafkaCluster() {
    if (kafkaCluster != null) {
      throw new IllegalStateException();
    }
    dataDir = Testing.Files.createTestingDirectory("cluster");
    kafkaCluster = new KafkaCluster().usingDirectory(dataDir).withPorts(2181, 9092);
    return kafkaCluster;
  }

  @BeforeClass
  public static void setUpBeforeClass() throws IOException {
    kafkaCluster = kafkaCluster().deleteDataPriorToStartup(true).addBrokers(1).startup();
  }

  @AfterClass
  public static void tearDownAfterClass() {
    if (kafkaCluster != null) {
      kafkaCluster.shutdown();
      kafkaCluster = null;
      boolean delete = dataDir.delete();
      // If files are still locked and a test fails: delete on exit to allow subsequent test
      // execution
      if (!delete) {
        dataDir.deleteOnExit();
      }
    }
  }
}
