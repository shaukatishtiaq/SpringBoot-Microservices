package dev.shaukat.companyms.company.messaging;

import dev.shaukat.companyms.company.CompanyService;
import dev.shaukat.companyms.constants.MessagingConstants;
import dev.shaukat.companyms.company.dto.ReviewMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReviewMessageConsumer {
    private final CompanyService companyService;

    public ReviewMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }


    @RabbitListener(queues = MessagingConstants.COMPANY_RATING_QUEUE_NAME)
    public void consumeMessage(ReviewMessage reviewMessage){
        companyService.updateCompanyRating(reviewMessage);
    }
}
