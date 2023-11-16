package com.libraryplusplus.service;

import com.libraryplusplus.repository.BookRepository;
import com.libraryplusplus.repository.OrderRepository;
import com.libraryplusplus.utils.CustomException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    OrderRepository orderRepository;

    public List<Map<String, Integer>> getGenreReport(Date startDate, Date endDate){
        return orderRepository.getGenreReportByPeriod(startDate,endDate);
    }
    public ByteArrayOutputStream getPopularityBook(Date start, Date end){
        try{
            List<Map<String, Integer>> bookReport = orderRepository.getPopularityBook(start, end);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Book Report");
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Title");
            headerRow.createCell(1).setCellValue("Order Count");

            int rowNum = 1;
            for (Map<String, Integer> reportEntry : bookReport){
                XSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(String.valueOf(reportEntry.get("book_title")));
                row.createCell(1).setCellValue(String.valueOf(reportEntry.get("orders_count")));
            }
            workbook.write(outputStream);
            return outputStream;

        }catch (Exception e){
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public byte[] generateGenreReportCSV(List<Map<String, Integer>> genreReport){
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            outputStream.write("Genre, OrdersCount\n".getBytes());
            for (Map<String, Integer> genreEntry : genreReport){
                String orderCount = String.valueOf(genreEntry.get("orders_count"));
                String genre = String.valueOf(genreEntry.get("book_genre"));
                outputStream.write((genre + ": " + orderCount + "\n").getBytes());
            }
            return outputStream.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
            return new byte[0];
        }
    }

    public ByteArrayOutputStream generateGenreReportExcel(List<Map<String, Integer>> genreReport){
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            XSSFSheet sheet = workbook.createSheet("Genre Report");
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Genre");
            headerRow.createCell(1).setCellValue("Order Count");

            int rowNum = 1;
            for (Map<String, Integer> genreEntry : genreReport) {
                XSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(String.valueOf(genreEntry.get("book_genre")));
                row.createCell(1).setCellValue(String.valueOf(genreEntry.get("orders_count")));
            }
            workbook.write(outputStream);
            return outputStream;

        }catch (Exception e){
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


}
