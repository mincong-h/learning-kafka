package io.mincong.kafka;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

public class ProducerIT extends AbstractKafkaIT {

  @Test
  public void sendRecord() {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("acks", "all");
    props.put("retries", 0);
    props.put("batch.size", 16384);
    props.put("linger.ms", 1);
    props.put("buffer.memory", 33554432);
    props.put("key.serializer", StringSerializer.class.getName());
    props.put("value.serializer", StringSerializer.class.getName());

    try (Producer<String, String> producer = new KafkaProducer<>(props)) {
      for (int i = 0; i < 10; i++) {
        producer.send(new ProducerRecord<>("my-topic", Integer.toString(i), Integer.toString(i)));
      }
    }
  }
}
