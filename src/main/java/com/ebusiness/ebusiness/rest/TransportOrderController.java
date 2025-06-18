package com.ebusiness.ebusiness.rest;

import com.ebusiness.ebusiness.dto.TransportOrderCostDto;
import com.ebusiness.ebusiness.dto.TransportOrderCreateDto;
import com.ebusiness.ebusiness.dto.TransportOrderResponseDto;
import com.ebusiness.ebusiness.entity.TransportOrder;
import com.ebusiness.ebusiness.service.service.TransportOrderService;
import com.ebusiness.ebusiness.service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class TransportOrderController {

    private final TransportOrderService transportOrderService;

    public TransportOrderController(TransportOrderService transportOrderService, UserService userService) {
        this.transportOrderService = transportOrderService;
    }

    @Operation(
            summary = "Create a new transport order",
            description = """
            Creates a transport order with packages (dimensions in centimeters, weight in grams).
            Access restricted to users with role CLIENT.
            """,
            security = @SecurityRequirement(name = "bearerAuth")
    )

    @PostMapping("/client/order")
    public ResponseEntity<TransportOrderResponseDto> createOrder(
            @RequestBody TransportOrderCreateDto transportOrderCreateDto,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            TransportOrder order = transportOrderService.createTransportOrder(email, transportOrderCreateDto);
            TransportOrderResponseDto responseDto = new TransportOrderResponseDto(order);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Returns predicted cost of the delivery",
            description = """
            Packages (dimensions in centimeters, weight in grams).
            """
    )


    @PostMapping("/order/cost")
    public ResponseEntity<Double> getCost(
            @RequestBody TransportOrderCostDto transportOrderCostDto) {
        try {
            double cost = transportOrderService.calculateCost(transportOrderCostDto.getPackages());
            return ResponseEntity.ok(cost);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }


}