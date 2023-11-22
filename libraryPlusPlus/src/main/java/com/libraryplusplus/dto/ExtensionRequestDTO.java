package com.libraryplusplus.dto;

import com.libraryplusplus.entity.ExtensionRequest;
import com.libraryplusplus.entity.Order;
import com.libraryplusplus.entity.RequestStatus;
import com.libraryplusplus.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
@NoArgsConstructor
public class ExtensionRequestDTO {
    private int id;
    @NotNull(message = "Order id is empty")
    @Min(value = 1, message = "Order id mustn't be empty.")
    private int order_id;
    private int user_id;
    private Date request_date;
    private RequestStatus status;
    @NotNull
    @Future(message = "Field new return date was filled incorrect.")
    @Temporal(TemporalType.DATE)
    private Date new_return_date;
    private String message;

    public static ExtensionRequestDTO ConvertToDTO(ExtensionRequest request){
        ExtensionRequestDTO dto = new ExtensionRequestDTO();
        dto.setId(request.getId());
        dto.setOrder_id(request.getOrder().getId());
        dto.setUser_id(request.getUser().getId());
        dto.setRequest_date(request.getRequest_date());
        dto.setStatus(request.getStatus());
        dto.setNew_return_date(request.getNew_return_date());
        dto.setMessage(request.getMessage());
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
        extensionRequest.setMessage(this.getMessage());
        return extensionRequest;
    }
}
