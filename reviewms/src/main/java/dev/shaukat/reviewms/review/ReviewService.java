package dev.shaukat.reviewms.review;

import dev.shaukat.reviewms.messaging.ReviewMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAllReviews(Long companyId){
        return reviewRepository.findByCompanyId(companyId);
    }

    public boolean addReview(Long companyId, Review review){

        if(companyId != null && review != null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        } else{
            return false;
        }
    }

    public Review getReviewById(Long reviewId){

        return reviewRepository.findById(reviewId).orElse(null);
    }

    public boolean updateReview(Long reviewId, Review updatedReview){
        Review review = reviewRepository.findById(reviewId).orElse(null);

        if(reviewId != null){
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setCompanyId(updatedReview.getCompanyId());
            review.setRating(updatedReview.getRating());
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    public boolean deleteReviewById(Long reviewId){
        Review review = reviewRepository.findById(reviewId).orElse(null);

        if(review != null) {
            reviewRepository.delete(review);
            return true;
        }else {
            return false;
        }
    }
}
