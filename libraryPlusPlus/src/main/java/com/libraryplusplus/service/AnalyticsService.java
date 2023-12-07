package com.libraryplusplus.service;

import com.libraryplusplus.entity.Book;
import com.libraryplusplus.repository.BookRepository;
import com.libraryplusplus.repository.LostBookRepository;
import com.libraryplusplus.repository.OrderRepository;
import com.libraryplusplus.repository.UserRepository;
import com.libraryplusplus.utils.CustomException;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.Styler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    LostBookRepository lostBookRepository;

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

    public ByteArrayOutputStream generateGenreReportExcel(List<Map<String, Integer>> genreReport){
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            XSSFSheet sheet = workbook.createSheet("Genre Report");
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Genre");
            headerRow.createCell(1).setCellValue("Order Count");

            int rowNum = 1;
            List<String> genres = new ArrayList<>();
            List<Integer> orders = new ArrayList<>();
            for (Map<String, Integer> genreEntry : genreReport) {
                XSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(String.valueOf(genreEntry.get("book_genre")));
                row.createCell(1).setCellValue(String.valueOf(genreEntry.get("orders_count")));
                genres.add(String.valueOf(genreEntry.get("book_genre")));
                orders.add(Integer.parseInt(String.valueOf(genreEntry.get("orders_count"))));
            }
            //circle diagram
            PieChart chart = new PieChart(800, 500);
            chart.setTitle("Genre Report");

            for (int i = 0; i < genres.size(); i++) {
                chart.addSeries(genres.get(i), orders.get(i));
            }
            //column diagram
//            CategoryChart chart = new CategoryChart(800, 500);
//            chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
//            chart.getStyler().setHasAnnotations(true);
//
//            chart.addSeries("Orders Count", genres, orders);
//
            BufferedImage chartImage = BitmapEncoder.getBufferedImage(chart);

            ByteArrayOutputStream chartOutputStream = new ByteArrayOutputStream();
            ImageIO.write(chartImage, "png", chartOutputStream);

            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 2, 1, 10, 15);
            int pictureIndex = workbook.addPicture(chartOutputStream.toByteArray(), Workbook.PICTURE_TYPE_PNG);
            drawing.createPicture(anchor, pictureIndex);

            workbook.write(outputStream);
            return outputStream;

        }catch (Exception e){
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    public ByteArrayOutputStream restrictionsUsersReportExcel(){
        try{
            List<Map<String, Object>> userReport = userRepository.getRestrictionsUserReport();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Restrictions Users Report");
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("User Id");
            headerRow.createCell(1).setCellValue("Email");
            headerRow.createCell(2).setCellValue("First Name");
            headerRow.createCell(3).setCellValue("Last Name");
            headerRow.createCell(4).setCellValue("Phone");
            headerRow.createCell(5).setCellValue("Sanctions");
            headerRow.createCell(6).setCellValue("Blocked");
            headerRow.createCell(7).setCellValue("Total Orders");

            int rowNum = 1;
            for (Map<String, Object> reportEntry : userReport){
                XSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(String.valueOf(reportEntry.get("user_id")));
                row.createCell(1).setCellValue(String.valueOf(reportEntry.get("email")));
                row.createCell(2).setCellValue(String.valueOf(reportEntry.get("first_name")));
                row.createCell(3).setCellValue(String.valueOf(reportEntry.get("last_name")));
                row.createCell(4).setCellValue(String.valueOf(reportEntry.get("phone")));
                row.createCell(5).setCellValue(String.valueOf(reportEntry.get("sanctions")));
                row.createCell(6).setCellValue(String.valueOf(reportEntry.get("blocked")));
                row.createCell(7).setCellValue(String.valueOf(reportEntry.get("total_orders")));
            }

            workbook.write(outputStream);
            return outputStream;

        }catch (Exception e){
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    public ByteArrayOutputStream lostBookReportExcel() {
        try {
            List<Map<String, Object>> lostBookReport = lostBookRepository.getLostBookReport();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Lost Book Report");
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Title");
            headerRow.createCell(1).setCellValue("Author");
            headerRow.createCell(2).setCellValue("Quantity");
            headerRow.createCell(3).setCellValue("Lost Copies");

            int rowNum = 1;
            for (Map<String, Object> reportEntry : lostBookReport){
                XSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(String.valueOf(reportEntry.get("title")));
                row.createCell(1).setCellValue(String.valueOf(reportEntry.get("author")));
                row.createCell(2).setCellValue(String.valueOf(reportEntry.get("quantity")));
                row.createCell(3).setCellValue(String.valueOf(reportEntry.get("lost_copies")));
            }

            List<Book> fullLostBookReport = bookRepository.findAllByQuantity(0);
            XSSFSheet sheet2 = workbook.createSheet("List All LostBook Report");
            XSSFRow headerRow2 = sheet2.createRow(0);
            headerRow2.createCell(0).setCellValue("Title");
            headerRow2.createCell(1).setCellValue("Author");
            headerRow2.createCell(2).setCellValue("Genre");
            headerRow2.createCell(3).setCellValue("ISBN");
            headerRow2.createCell(4).setCellValue("Year");
            headerRow2.createCell(5).setCellValue("Quantity");
            for (Book book : fullLostBookReport){
                XSSFRow row = sheet2.createRow(rowNum++);
                row.createCell(0).setCellValue(book.getTitle());
                row.createCell(1).setCellValue(book.getAuthor());
                row.createCell(2).setCellValue(book.getGenre().getName());
                row.createCell(3).setCellValue(book.getISBN());
                row.createCell(4).setCellValue(book.getPublication_year());
                row.createCell(5).setCellValue(String.valueOf(book.getQuantity()));
            }
            workbook.write(outputStream);
            return outputStream;

        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
