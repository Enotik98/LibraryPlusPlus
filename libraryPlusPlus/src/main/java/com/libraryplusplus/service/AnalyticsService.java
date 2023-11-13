package com.libraryplusplus.service;

import com.libraryplusplus.repository.BookRepository;
import com.libraryplusplus.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

//    public


}
