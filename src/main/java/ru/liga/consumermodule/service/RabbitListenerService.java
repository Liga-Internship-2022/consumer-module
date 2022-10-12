package ru.liga.consumermodule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.consumermodule.api.RabbitService;
import ru.liga.consumermodule.model.QueueNames;

@Service
@RequiredArgsConstructor
public class RabbitListenerService {

    private final RabbitService rabbitService;

    @RabbitListener(queues = {
            QueueNames.DAILY_QUEUE_NAME,
            QueueNames.ALERT_QUEUE_NAME,
            QueueNames.ERROR_QUEUE_NAME
    })
    public void receiveAndLogMessage(String message) {
        rabbitService.logMessage(message);
    }
}
