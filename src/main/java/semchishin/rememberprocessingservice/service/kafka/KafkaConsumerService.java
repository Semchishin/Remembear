package semchishin.rememberprocessingservice.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import semchishin.rememberprocessingservice.model.Remind;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(topics = "#{@environment.getProperty('spring.kafka.topic.remind-name')}",
                   groupId = "#{@environment.getProperty('spring.kafka.consumer.group-id')}")
    public void onRemindReceived(Remind remind) {
        log.info("Received remind from Kafka: id={}, userId={}, title={}",
                remind.getRemindId(), remind.getUserId(), remind.getTitle());
    }
}
