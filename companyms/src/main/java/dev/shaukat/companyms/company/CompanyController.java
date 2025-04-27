package dev.shaukat.companyms.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<String> addCompany(@RequestBody Company company){
        companyService.createCompany(company);

        return new ResponseEntity<>("Company Created successfully.", HttpStatus.CREATED);
    }

    @GetMapping
    public List<Company> getAllCompanies(){
        return companyService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable("id") Long id, @RequestBody Company company){
        if(companyService.updateCompanyRating(id, company)){
            return new ResponseEntity<>("Update successful", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") Long id){
        Company company = companyService.findById(id);

        if(Objects.nonNull(company)){
            return new ResponseEntity<>(company, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable("id") Long id) {
        boolean deleted = companyService.deleteById(id);

        String response = deleted ? "Deleted" : "Company not found";
        HttpStatus httpStatus = deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(response, httpStatus);
    }
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
}
