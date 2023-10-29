package com.libraryplusplus.dto;

import com.libraryplusplus.entity.ExtensionRequest;
import com.libraryplusplus.entity.Order;
import com.libraryplusplus.entity.RequestStatus;
import com.libraryplusplus.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class ExtensionRequestDTO {
    private int id;
    private int order_id;
    private int user_id;
    private Date request_date;
    private RequestStatus status;
    private Date new_return_date;
    private String response_message;

    public static ExtensionRequestDTO ConvertToDTO(ExtensionRequest request){
        ExtensionRequestDTO dto = new ExtensionRequestDTO();
        dto.setId(request.getId());
        dto.setOrder_id(request.getOrder().getId());
        dto.setUser_id(request.getUser().getId());
        dto.setRequest_date(request.getRequest_date());
        dto.setStatus(request.getStatus());
        dto.setNew_return_date(request.getNew_return_date());
        dto.setResponse_message(request.getMessage());
        return dto;
    }
    public ExtensionRequest ConvertToExtensionRequest(Order order, User user){
        ExtensionRequest extensionRequest = new ExtensionRequest();
        extensionRequest.setId(this.getId());
        extensionRequest.setOrder(order);
        extensionRequest.setUser(user);
        extensionRequest.setRequest_date(this.getRequest_date());
        extensionRequest.setStatus(this.getStatus());
        extensionRequest.setNew_return_date(this.getNew_return_date());
        extensionRequest.setMessage(this.getResponse_message());
        return extensionRequest;
    }
}
