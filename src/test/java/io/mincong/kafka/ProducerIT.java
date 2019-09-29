package io.mincong.kafka;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.kafka.clients.producer.ProducerConfig.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class ProducerIT extends AbstractKafkaIT {

  private static Logger logger = LoggerFactory.getLogger(ProducerIT.class);

  @Test
  public void sendRecord() throws Exception {
    Properties props = new Properties();
    props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ACKS_CONFIG, "all");
    props.put(RETRIES_CONFIG, 0);
    props.put(BATCH_SIZE_CONFIG, 16384);
    props.put(LINGER_MS_CONFIG, 1);
    props.put(BUFFER_MEMORY_CONFIG, 33554432);
    props.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    props.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

    CountDownLatch completion = new CountDownLatch(1);
    Map<String, String> messages = new ConcurrentHashMap<>();
    kafkaCluster
        .useTo()
        .consumeStrings(
            "my-topic",
            1,
            10,
            TimeUnit.SECONDS,
            completion::countDown,
            (k, v) -> {
              messages.put(k, v);
              return true;
            });

    try (Producer<String, String> producer = new KafkaProducer<>(props)) {
      producer.send(new ProducerRecord<>("my-topic", "k", "v"));
    }

    if (!completion.await(10, TimeUnit.SECONDS)) {
      logger.warn("Timeout: consumer and/or producer did not complete normally");
    }
    assertThat(messages).containsExactly(entry("k", "v"));
  }
}
