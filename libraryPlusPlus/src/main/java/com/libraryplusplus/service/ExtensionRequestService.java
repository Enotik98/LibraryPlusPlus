package com.libraryplusplus.service;

import com.libraryplusplus.dto.ExtensionRequestDTO;
import com.libraryplusplus.entity.ExtensionRequest;
import com.libraryplusplus.entity.Order;
import com.libraryplusplus.entity.RequestStatus;
import com.libraryplusplus.entity.User;
import com.libraryplusplus.repository.ExtensionRequestRepository;
import com.libraryplusplus.repository.OrderRepository;
import com.libraryplusplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExtensionRequestService {
    @Autowired
    ExtensionRequestRepository extensionRequestRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    public List<ExtensionRequestDTO> findAllRequest(){
        List<ExtensionRequest> requests = extensionRequestRepository.findAll();
        List<ExtensionRequestDTO> result = new ArrayList<>();
        for(ExtensionRequest request : requests){
            result.add(ExtensionRequestDTO.ConvertToDTO(request));
        }
        return result;
    }

    public boolean addExtensionRequest(ExtensionRequestDTO extensionRequestDTO){
        User user = userRepository.findById(extensionRequestDTO.getUser_id());
        Order order = orderRepository.findById(extensionRequestDTO.getOrder_id());
        if (user == null || order == null){
            return false;
        }else {
            ExtensionRequest request = extensionRequestDTO.ConvertToExtensionRequest(order, user);
            extensionRequestRepository.save(request);
            return true;
        }
    }
    public boolean updateExtensionRequest(ExtensionRequestDTO extensionRequestDTO){
        ExtensionRequest exRequest = extensionRequestRepository.findById(extensionRequestDTO.getId());
        if (exRequest == null && exRequest.getOrder() != null && exRequest.getUser() != null){
            return false;
        }else {
            if (exRequest.getStatus().equals(extensionRequestDTO.getStatus())){
                return true;
            }else {
                exRequest.setStatus(extensionRequestDTO.getStatus());
                exRequest.setNew_return_date(extensionRequestDTO.getNew_return_date());
                exRequest.setMessage(extensionRequestDTO.getResponse_message());
                if (extensionRequestDTO.getStatus().equals(RequestStatus.APPROVED)) {
                    Order order = orderRepository.findById(extensionRequestDTO.getOrder_id());
                    order.setReturn_date(extensionRequestDTO.getNew_return_date());
                    orderRepository.save(order);
                }
            }
        }
        return true;
    }

}
