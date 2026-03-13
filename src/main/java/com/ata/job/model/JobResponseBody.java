package com.ata.job.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobResponseBody {

    @JsonProperty("Timestamp")
    private String timestamp;

    @JsonProperty("Employer")
    private String employer;

    @JsonProperty("Location")
    private String location;

    @JsonProperty("Job Title")
    private String jobTitle;

    @JsonProperty("Years at Employer")
    private String yearsAtEmployer;

    @JsonProperty("Years of Experience")
    private String yearsOfExperience;

    @JsonProperty("Salary")
    private String salary;

    @JsonProperty("Signing Bonus")
    private String signingBonus;

    @JsonProperty("Annual Bonus")
    private String annualBonus;

    @JsonProperty("Annual Stock Value/Bonus")
    private String annualStockValueBonus;

    @JsonProperty("Gender")
    private String gender;

    @JsonProperty("Additional Comments")
    private String additionalComments;
}
