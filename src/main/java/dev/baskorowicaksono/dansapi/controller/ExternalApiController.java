package dev.baskorowicaksono.dansapi.controller;

import dev.baskorowicaksono.dansapi.payload.response.JobDetailsResponse;
import dev.baskorowicaksono.dansapi.payload.response.JobListResponse;
import dev.baskorowicaksono.dansapi.service.CsvExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/")
public class ExternalApiController {

    @Autowired
    private CsvExportService csvExportService;

    @GetMapping("/job_list")
    public ResponseEntity<JobListResponse> getJobLists() {
        String uri = "http://dev3.dansmultipro.co.id/api/recruitment/positions.json";
        RestTemplate restTemplate = new RestTemplate();
        JobListResponse jobListResponse = new JobListResponse();

        try{
            List<JobDetailsResponse> result = restTemplate.getForObject(uri, ArrayList.class);
            jobListResponse.setSuccess(true);
            jobListResponse.setData(result);

            return new ResponseEntity<>(jobListResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new JobListResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/job_details/{id}")
    public ResponseEntity<JobDetailsResponse> getJobDetails(@PathVariable(value = "id", required = true ) String id) {
        String uri = "http://dev3.dansmultipro.co.id/api/recruitment/positions/" + id;
        RestTemplate restTemplate = new RestTemplate();
        try{
            JobDetailsResponse result = restTemplate.getForObject(uri, JobDetailsResponse.class);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new JobDetailsResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/job_list/csv")
    public void getJobListsInCsv(HttpServletResponse servletResponse) throws IOException {

        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"jobList.csv\"");
        try{
            String uri = "http://dev3.dansmultipro.co.id/api/recruitment/positions.json";
            RestTemplate restTemplate = new RestTemplate();
            List<JobDetailsResponse> result = restTemplate.getForObject(uri, ArrayList.class);

            this.csvExportService.writeToCsv(servletResponse.getWriter(), result);
        } catch (Exception e) {
            System.out.println("Error While writing CSV: " + e);
        }
    }
}
