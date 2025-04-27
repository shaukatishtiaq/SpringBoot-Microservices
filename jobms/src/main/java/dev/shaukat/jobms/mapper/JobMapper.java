package dev.shaukat.jobms.mapper;

import dev.shaukat.jobms.job.Job;
import dev.shaukat.jobms.job.dto.JobDTO;
import dev.shaukat.jobms.job.external.Company;
import dev.shaukat.jobms.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobCompanyDto(Job job, Company company, List<Review> reviews) {
        JobDTO jobDTO = new JobDTO();

        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setCompany(company);

        jobDTO.setReview(reviews);

        return jobDTO;
    }
}
