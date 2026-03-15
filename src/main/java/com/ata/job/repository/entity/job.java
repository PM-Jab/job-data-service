package com.ata.job.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "merchant")
@Table(name = "merchant")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "employer")
    private String employer;

    @Column(name = "location")
    private String location;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "years_at_employer")
    private String yearsAtEmployer;

    @Column(name = "years_of_experience")
    private String yearsOfExperience;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "signing_bonus")
    private Double signingBonus;

    @Column(name = "annual_bonus")
    private Double annualBonus;

    @Column(name = "annual_stock_value_bonus")
    private Double annualStockValueBonus;

    @Column(name = "gender")
    private String gender;

    @Column(name = "additional_comments")
    private String additionalComments;
}
