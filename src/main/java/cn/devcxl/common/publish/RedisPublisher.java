package cn.devcxl.common.publish;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author devcxl
 */
@Slf4j
public class RedisPublisher<T> {


    private RedisTemplate<String, Object> redisPublisherTemplate;

    public RedisPublisher(RedisTemplate<String, Object> redisPublisherTemplate) {
        this.redisPublisherTemplate = redisPublisherTemplate;
    }

    public void push(String streamName, String group, T data) {
        Long size = redisPublisherTemplate.opsForStream().size(streamName);
        if (size <= 0) {
            try {
                redisPublisherTemplate.opsForStream().createGroup(streamName, group);
            } catch (RedisSystemException ex) {
                log.error(ex.getMessage());
            }
        }
        ObjectRecord<String, T> record = StreamRecords.newRecord().in(streamName).ofObject(data).withId(RecordId.autoGenerate());
        RecordId recordId = redisPublisherTemplate.opsForStream().add(record);
        log.info("publish: stream:{} group:{} recordId:{}", streamName, group, recordId);
    }
}