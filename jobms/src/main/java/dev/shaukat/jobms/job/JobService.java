package dev.shaukat.jobms.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shaukat.jobms.clients.CompanyClient;
import dev.shaukat.jobms.clients.ReviewClient;
import dev.shaukat.jobms.job.dto.JobDTO;
import dev.shaukat.jobms.job.external.Company;
import dev.shaukat.jobms.job.external.Review;
import dev.shaukat.jobms.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.ws.rs.core.MultivaluedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobService {

    private static final Logger log = LoggerFactory.getLogger(JobService.class);
    private final JobRepository jobRepository;
    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;

//    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "fallback")
//    @Retry(name = "companyBreaker", fallbackMethod = "fallback")
    @RateLimiter(name = "companyBreaker",fallbackMethod = "fallback")
    public List<JobDTO> findAll(){
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                    .map(this::convertToDto).toList();
    }
    private List<String> fallback( Exception e) {
        return Collections.singletonList("fallback");
    }
    private JobDTO convertToDto(Job job){
        Company company = companyClient.getCompany(job.getCompanyId());

        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        return JobMapper.mapToJobCompanyDto(job, company, reviews);
    }
    public String add(Job job) {
        jobRepository.save(job);
        return "Job has been added";
    }

    public JobDTO findById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        if(Objects.isNull(job)){
            return null;
        }
        return convertToDto(job);
    }

    public boolean deleteById(Long id) {
        try{
            jobRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean updateById(Long id, Job jobPayload) {
        Optional<Job> jobOption = jobRepository.findById(id);
            if(jobOption.isPresent()){
                Job job = jobOption.get();

                job.setTitle(jobPayload.getTitle());
                job.setDescription(jobPayload.getDescription());
                job.setMinSalary(jobPayload.getMinSalary());
                job.setMaxSalary(jobPayload.getMaxSalary());
                job.setLocation(jobPayload.getLocation());

                jobRepository.save(job);
                return true;
        }
        return false;
    }

    public JobService(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }
}
