package com.libraryplusplus.controller;

import com.libraryplusplus.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/analytics")
//@CrossOrigin(origins = "")
@RequiredArgsConstructor
public class AnalyticsController {
    @Autowired
    AnalyticsService analyticsService;

    @PostMapping("/genre")
    public ResponseEntity<?> getGenreReportByPeriod(@RequestBody Map<String, String> body) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(body.get("start"));
            Date end = format.parse(body.get("end"));

            List<Map<String, Integer>> report = analyticsService.getGenreReport(start, end);
            System.out.println(report);
            return ResponseEntity.ok(report);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/genre-csv")
    public ResponseEntity<byte[]> getGenreReportCSV(@RequestBody Map<String, String> body) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(body.get("start"));
            Date end = format.parse(body.get("end"));

            List<Map<String, Integer>> genreReport = analyticsService.getGenreReport(start, end);
            byte[] cvsDate = analyticsService.generateGenreReportCSV(genreReport);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "genre_report.csv");

            return new ResponseEntity<>(cvsDate, headers, HttpStatus.OK);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage().getBytes());
        }
    }
}
