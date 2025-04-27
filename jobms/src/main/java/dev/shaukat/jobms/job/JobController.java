package dev.shaukat.jobms.job;

import dev.shaukat.jobms.job.dto.JobDTO;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobService jobService;

    @GetMapping
    public ResponseEntity<List<JobDTO>> findAll(){

        var result = jobService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> findById(@PathVariable("id") Long id){
        JobDTO result = jobService.findById(id);
        if(Objects.nonNull(result)){
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public String createJob(@RequestBody Job job){
        jobService.add(job);
        return "Job added successfully.";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id){
        boolean deleted = jobService.deleteById(id);
        if(deleted){
            return new ResponseEntity<>("Job has been deleted", HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateById(@PathVariable("id") Long id, @RequestBody Job job){
        boolean updated = jobService.updateById(id, job);

        if(updated){
            return new ResponseEntity<>("Updated successfully.", HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
}
