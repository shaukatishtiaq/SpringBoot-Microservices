package dev.shaukat.reviewms.review;

import dev.shaukat.reviewms.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMessageProducer reviewMessageProducer;
    public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam("companyId") Long id){
        return new ResponseEntity<>(reviewService.getAllReviews(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createReview(@RequestParam("companyId") Long companyId,@RequestBody Review review){
        if(reviewService.addReview(companyId, review)){
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>("Review Created.", HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable("id") Long id){
        Review review = reviewService.getReviewById(id);

        if(Objects.nonNull(review)){
            return new ResponseEntity<>(review, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateReviewById(@PathVariable("id") Long id, @RequestBody Review review){
        boolean isUpdated = reviewService.updateReview(id,review);
        String response = isUpdated ? "Update successful." : null;
        HttpStatus httpStatus = isUpdated ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(response, httpStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReviewById(@PathVariable("id") Long id){
        boolean isDeleted = reviewService.deleteReviewById(id);
        String response = isDeleted ? "Delete successful." : null;
        HttpStatus httpStatus = isDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/average-rating")
    public ResponseEntity<Double> getAverageReview(@RequestParam(name = "company-id") Long companyId){
        Double result = reviewService.getAllReviews(companyId).stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
