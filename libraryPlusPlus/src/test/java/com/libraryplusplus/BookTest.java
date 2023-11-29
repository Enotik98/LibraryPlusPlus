package com.libraryplusplus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryplusplus.dto.AuthDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllBooks() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/book")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }
    @Test
    public void testGetBook() throws Exception {
        int bookId = 5;
        ResultActions resultActions = mockMvc.perform(get("/book/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }
}
