package com.libraryplusplus.controller;

import com.libraryplusplus.service.AnalyticsService;
import com.libraryplusplus.utils.CustomException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
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

    @PostMapping("/genre-excel")
    public ResponseEntity<byte[]> getGenreReportExcel(@RequestBody Map<String, String> body) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(body.get("start"));
            Date end = format.parse(body.get("end"));

            List<Map<String, Integer>> genreReport = analyticsService.getGenreReport(start, end);

            ByteArrayOutputStream outputStream = analyticsService.generateGenreReportExcel(genreReport);
            HttpHeaders headers = createHeaderExcel("genre_report.xlsx");
            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);

        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage().getBytes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage().getBytes());
        }
    }

    @PostMapping("/book-excel")
    public ResponseEntity<byte[]> getPopularityBookExcel(@RequestBody Map<String, String> body) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(body.get("start"));
            Date end = format.parse(body.get("end"));
            ByteArrayOutputStream outputStream = analyticsService.getPopularityBook(start, end);
            HttpHeaders headers = createHeaderExcel("book_report.xlsx");
            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);

        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage().getBytes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage().getBytes());
        }

    }
    @GetMapping("/restrictions_excel")
    public ResponseEntity<byte[]> getRestrictionsUserReportExcel(){
        try {
            ByteArrayOutputStream outputStream = analyticsService.restrictionsUsersReportExcel();
            HttpHeaders headers = createHeaderExcel("restrictions_user_report.xlsx");
            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage().getBytes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage().getBytes());
        }
    }
    @GetMapping("/lost_book_excel")
    public ResponseEntity<byte[]> getLostBookReportExcel(){
        try {
            ByteArrayOutputStream outputStream = analyticsService.lostBookReportExcel();
            HttpHeaders headers = createHeaderExcel("lost_book_report.xlsx");
            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage().getBytes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage().getBytes());
        }
    }

    public HttpHeaders createHeaderExcel(String file){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", file);
        return headers;
    }
}

//    public ByteArrayOutputStream generateGenreReportExcel(List<Map<String, Integer>> genreReport){
//        try (XSSFWorkbook workbook = new XSSFWorkbook();
//             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
//            XSSFSheet sheet = workbook.createSheet("Genre Report");
//            XSSFRow headerRow = sheet.createRow(0);
//            headerRow.createCell(0).setCellValue("Genre");
//            headerRow.createCell(1).setCellValue("Order Count");
//
//            int rowNum = 1;
//            for (Map<String, Integer> genreEntry : genreReport) {
//                XSSFRow row = sheet.createRow(rowNum++);
//                row.createCell(0).setCellValue(String.valueOf(genreEntry.get("book_genre")));
//                row.createCell(1).setCellValue(String.valueOf(genreEntry.get("orders_count")));
//            }
//            workbook.write(outputStream);
//            return outputStream;
//
//        }catch (Exception e){
//            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
//        }
//    }
