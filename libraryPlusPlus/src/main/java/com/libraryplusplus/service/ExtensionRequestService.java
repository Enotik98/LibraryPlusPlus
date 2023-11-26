package com.libraryplusplus.service;

import com.libraryplusplus.dto.ExtensionRequestDTO;
import com.libraryplusplus.entity.ExtensionRequest;
import com.libraryplusplus.entity.Order;
import com.libraryplusplus.entity.RequestStatus;
import com.libraryplusplus.entity.User;
import com.libraryplusplus.repository.ExtensionRequestRepository;
import com.libraryplusplus.repository.OrderRepository;
import com.libraryplusplus.repository.UserRepository;
import com.libraryplusplus.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public List<ExtensionRequest> findAllRequest() {
        try {
            return extensionRequestRepository.findAll();
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public List<ExtensionRequest> findAllUserRequest(int userId) {
        try {
            User user = userRepository.findById(userId);
            if (user == null) {
                throw new CustomException(HttpStatus.NOT_FOUND, "Don't found user");
            }
            return extensionRequestRepository.findAllByUser(user);
        }catch (Exception e){
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    public void addExtensionRequest(ExtensionRequestDTO extensionRequestDTO) {
        try {
            User user = userRepository.findById(extensionRequestDTO.getUser_id());
            Order order = orderRepository.findById(extensionRequestDTO.getOrder_id());
            if (user == null || order == null) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "User or Order not found");
            }
            ExtensionRequest exRequest = extensionRequestRepository.findByUserAndOrder(user, order);
            if (exRequest != null){
                throw new CustomException(HttpStatus.CONFLICT, "Such a request already exists");
            }
            ExtensionRequest request = extensionRequestDTO.ConvertToExtensionRequest(order, user);
            extensionRequestRepository.save(request);
        } catch (CustomException customException) {
            throw new CustomException(customException.getStatus(), customException.getMessage());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void updateExtensionRequest(ExtensionRequestDTO extensionRequestDTO) {
        try {
            ExtensionRequest exRequest = extensionRequestRepository.findById(extensionRequestDTO.getId());
            if (exRequest == null) {
                throw new CustomException(HttpStatus.NOT_FOUND, "Request not found!");
            } else {
                if (extensionRequestDTO.getOrder_id() != exRequest.getOrder().getId()) {
                    throw new CustomException(HttpStatus.NOT_FOUND, "Order id doesn't match");
                }
                if (exRequest.getStatus().equals(extensionRequestDTO.getStatus())) {
                    throw new CustomException(HttpStatus.BAD_REQUEST, "The status hasn't changed");
                } else {
                    exRequest.setStatus(extensionRequestDTO.getStatus());
                    exRequest.setNew_return_date(extensionRequestDTO.getNew_return_date());
                    exRequest.setMessage(extensionRequestDTO.getMessage());
                    if (extensionRequestDTO.getStatus().equals(RequestStatus.APPROVED)) {
                        Order order = orderRepository.findById(extensionRequestDTO.getOrder_id());
                        if (exRequest.getNew_return_date().after(order.getReturn_date())) {
                            order.setReturn_date(extensionRequestDTO.getNew_return_date());
                            orderRepository.save(order);
                        } else {
                            throw new CustomException(HttpStatus.NOT_FOUND, "The new date is incorrect");
                        }
                    }
                    extensionRequestRepository.save(exRequest);
                }
            }
        } catch (CustomException e) {
            throw new CustomException(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
