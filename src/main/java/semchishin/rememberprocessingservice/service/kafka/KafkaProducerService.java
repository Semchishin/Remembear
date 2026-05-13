package semchishin.rememberprocessingservice.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import semchishin.rememberprocessingservice.model.Remind;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, Remind> kafkaTemplate;

    @Value("${spring.kafka.topic.remind-name}")
    private String remindTopicName;

    public void sendRemind(Remind remind) {
        kafkaTemplate.send(remindTopicName, String.valueOf(remind.getUserId()), remind)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Remind {} sent to topic {}", remind.getRemindId(), remindTopicName);
                    } else {
                        log.error("Failed to send remind {} to topic {}", remind.getRemindId(), remindTopicName, ex);
                    }
                });
    }
}
