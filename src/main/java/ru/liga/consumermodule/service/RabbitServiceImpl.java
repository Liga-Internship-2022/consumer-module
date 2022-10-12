package ru.liga.consumermodule.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.consumermodule.api.RabbitService;
import ru.liga.consumermodule.model.MessageType;
import ru.liga.consumermodule.model.QueueNames;
import ru.liga.consumermodule.model.RabbitMessageDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitServiceImpl implements RabbitService {

    private final ObjectMapper objectMapper;

    @Override
    public void logMessage(String message) {
        try {
            RabbitMessageDto rabbitMessageDto = objectMapper.readValue(message, RabbitMessageDto.class);
            MessageType messageType = rabbitMessageDto.getMessageType();
            String content = rabbitMessageDto.getContent();

            String fromQueue = "unknown_queue";
            switch (messageType) {
                case DAILY:
                    fromQueue = QueueNames.DAILY_QUEUE_NAME;
                    break;
                case ALERT:
                    fromQueue = QueueNames.ALERT_QUEUE_NAME;
                    break;
                case ERROR:
                    fromQueue = QueueNames.ERROR_QUEUE_NAME;
                    break;
            }
            log.info("Got message [{}] from queue [{}]", content, fromQueue);

        } catch (Exception e) {
            log.error("Error converting message");
        }
    }
}
