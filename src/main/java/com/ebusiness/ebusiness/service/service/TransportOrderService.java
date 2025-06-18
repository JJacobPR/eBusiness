package com.ebusiness.ebusiness.service.service;

import com.ebusiness.ebusiness.dto.PackageCreateDto;
import com.ebusiness.ebusiness.dto.TransportOrderCreateDto;
import com.ebusiness.ebusiness.entity.TransportOrder;

import java.util.List;
import java.util.Optional;

public interface TransportOrderService {
    List<TransportOrder> getAllTransportOrders();
    Optional<TransportOrder> getTransportOrderById(Integer id);
    TransportOrder createTransportOrder(TransportOrder transportOrder);
    TransportOrder createTransportOrder(String email, TransportOrderCreateDto dto);
    TransportOrder updateTransportOrder(Integer id, TransportOrder updatedTransportOrder);
    double calculateCost(List<PackageCreateDto> packages);
    void deleteTransportOrder(Integer id);
}
