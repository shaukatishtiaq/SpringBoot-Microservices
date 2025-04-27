package dev.shaukat.companyms.company;

import dev.shaukat.companyms.clients.ReviewClient;
import dev.shaukat.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyService(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }

    public List<Company> findAll(){
        return companyRepository.findAll();
    }

    public boolean updateCompanyRating(Long id, Company company) {
        Optional<Company> companyOptional = companyRepository.findById(id);

        if(companyOptional.isPresent()){
            Company updatedCompany = companyOptional.get();

            updatedCompany.setDescription(company.getDescription());
            updatedCompany.setName(company.getName());

            companyRepository.save(updatedCompany);

            return true;
        }
        return false;
    }

    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    public Company findById(Long id){
        return companyRepository.findById(id).orElse(null);
    }

    public boolean deleteById(Long id) {
        if(companyRepository.existsById(id)){
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void updateCompanyRating(ReviewMessage reviewMessage) {
        System.out.println("Review Consumed from QUEUE " + reviewMessage.toString());


        Company company = companyRepository.findById(reviewMessage.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company Not Found " + reviewMessage.getCompanyId()));

        Double averageCompanyRating = reviewClient.getAverageRating(reviewMessage.getCompanyId());
        System.out.println(averageCompanyRating);

        company.setRating(averageCompanyRating);

        companyRepository.save(company);

    }
}
