package com.ebusiness.ebusiness.repository;

import com.ebusiness.ebusiness.config.TransportOrderStatus;
import com.ebusiness.ebusiness.entity.Client;
import com.ebusiness.ebusiness.entity.Driver;
import com.ebusiness.ebusiness.entity.TransportOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransportOrderRepository extends JpaRepository<TransportOrder, Integer> {
    List<TransportOrder> findAllByClientAndStatusNotIn(Client client, List<TransportOrderStatus> statuses);
    List<TransportOrder> findAllByClientAndStatusIn(Client client, List<TransportOrderStatus> statuses);
    List<TransportOrder> findAllByDriverAndStatusNotIn(Driver driver, List<TransportOrderStatus> statuses);
}
