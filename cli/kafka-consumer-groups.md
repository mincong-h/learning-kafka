# kafka-consumer-groups.sh examples

Examples of using the `kafka-consumer-groups.sh` provided in kafka bin directory.

## Describe a consumer group, see the topics (and its partitions) it is consuming and each partition's the current offset (already consumed by the consumer group).

```
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group foo-app --describe

Consumer group 'foo-app' has no active members.

GROUP                    TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID     HOST            CLIENT-ID
foo-app                  foo_topic       3          18              34              16              -               -               -
foo-app                  foo_topic       0          15              31              16              -               -               -
foo-app                  foo_topic       4          16              32              16              -               -               -
foo-app                  foo_topic       5          15              31              16              -               -               -
foo-app                  foo_topic       2          16              32              16              -               -               -
foo-app                  foo_topic       1          17              33              16              -               -               -
```
