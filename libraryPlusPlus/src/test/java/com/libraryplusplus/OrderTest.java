package com.libraryplusplus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryplusplus.dto.AuthDTO;
import com.libraryplusplus.dto.OrderDTO;
import com.libraryplusplus.entity.Role;
import com.libraryplusplus.entity.User;
import com.libraryplusplus.service.UserService;
import com.libraryplusplus.utils.TokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    UserService userService;


    @Test
    public void testAddOrder() throws Exception {
        String token = TokenUtils.generateAccessToken(20, Role.USER);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBook_id(5);
        orderDTO.setReturn_date(new Date(2023, 12, 3));
//        orderDTO.setUser_id(20);

        ResultActions resultActions = mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(orderDTO)));
        resultActions.andExpect(status().isOk());
    }
}
