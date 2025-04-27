package dev.shaukat.reviewms.messaging;

import dev.shaukat.reviewms.constants.MessagingConstants;
import dev.shaukat.reviewms.dto.ReviewMessage;
import dev.shaukat.reviewms.review.Review;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReviewMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public ReviewMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Review review) {
        ReviewMessage reviewMessage = ReviewMessage.mapToReviewMessage(review);

        rabbitTemplate.convertAndSend(MessagingConstants.COMPANY_RATING_QUEUE_NAME,reviewMessage);
    }
}
