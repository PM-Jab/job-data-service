package com.ata.job.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/job_data")
public class JobController {

    // GET /job_data?salary[gte]=50000
    @GetMapping(params = "salary[gte]")
    public ResponseEntity<List<Map<String, Object>>> getJobsBySalaryGte(
            @RequestParam("salary[gte]") double minSalary) {

        return ResponseEntity.ok();
    }

    // GET /job_data?fields=job_title,gender,salary
    @GetMapping(params = "fields")
    public ResponseEntity<List<Map<String, Object>>> getJobsByFields(
            @RequestParam("fields") String fields) {

        List<String> selectedFields = List.of(fields.split(","));


        return ResponseEntity.ok();
    }

    // GET /job_data?sort=job_title&sort_type=DESC
    @GetMapping(params = "sort")
    public ResponseEntity<List<Map<String, Object>>> getJobsSorted(
            @RequestParam("sort") String sortField,
            @RequestParam(value = "sort_type", defaultValue = "ASC") String sortType) {

        return ResponseEntity.ok();
    }
}
