package dev.baskorowicaksono.dansapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.baskorowicaksono.dansapi.payload.response.JobDetailsResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
public class CsvExportService {

    public void writeToCsv(Writer writer, List<JobDetailsResponse> result) {
        ObjectMapper mapper = new ObjectMapper();

        try{
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
            csvPrinter.printRecord("id", "type", "url", "created_at", "company", "company_url", "location", "title", "description", "how_to_apply", "company_logo");

            for (int i = 0; i < result.size(); i++) {
                JobDetailsResponse response = mapper.convertValue(result.get(i), JobDetailsResponse.class);
                csvPrinter.printRecord(response.getId(),
                        response.getType(),
                        response.getUrl(),
                        response.getCreated_at(),
                        response.getCompany(),
                        response.getCompany_url(),
                        response.getLocation(),
                        response.getTitle(),
                        response.getDescription(),
                        response.getHow_to_apply(),
                        response.getCompany_logo());
            }
        } catch (IOException | NullPointerException e) {
            System.out.println("Error While writing CSV: " + e);
        }
    }

}
