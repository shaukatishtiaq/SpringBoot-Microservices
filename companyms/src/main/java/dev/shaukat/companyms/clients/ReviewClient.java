package dev.shaukat.companyms.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "REVIEW-SERVICE")
public interface ReviewClient {
    @GetMapping("/reviews/average-rating")
    Double getAverageRating(@RequestParam("company-id") Long companyId);

}
