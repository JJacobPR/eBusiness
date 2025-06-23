package com.ebusiness.ebusiness.rest;

import com.ebusiness.ebusiness.config.TransportOrderStatus;
import com.ebusiness.ebusiness.dto.TransportOrderCostDto;
import com.ebusiness.ebusiness.dto.TransportOrderCreateDto;
import com.ebusiness.ebusiness.dto.TransportOrderResponseDto;
import com.ebusiness.ebusiness.dto.TransportOrderUpdateDto;
import com.ebusiness.ebusiness.entity.TransportOrder;
import com.ebusiness.ebusiness.service.service.TransportOrderService;
import com.ebusiness.ebusiness.service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransportOrderController {

    private final TransportOrderService transportOrderService;

    public TransportOrderController(TransportOrderService transportOrderService, UserService userService) {
        this.transportOrderService = transportOrderService;
    }


    @GetMapping("/client/order/{orderId}")
    public ResponseEntity<TransportOrderResponseDto> getOrder(
            @PathVariable Integer orderId) {

        TransportOrderResponseDto dto = transportOrderService.getOrderResponseDtoById(orderId);
        return ResponseEntity.ok(dto);
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
            summary = "Updates a transport order",
            description = """
            Updates a transport order with packages (dimensions in centimeters, weight in grams).
            Access restricted to users with role CLIENT.
            """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping("/client/order/{orderId}")
    public ResponseEntity<TransportOrderResponseDto> updateOrder(
            @RequestBody TransportOrderUpdateDto transportOrderUpdateDto, @PathVariable Integer orderId,
            Authentication authentication) {
        try {
            TransportOrder order = transportOrderService.updateTransportOrder(orderId, transportOrderUpdateDto);
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
            double cost = transportOrderService.calculateCost(transportOrderCostDto.getPackages(), transportOrderCostDto.isHelpUnload());
            return ResponseEntity.ok(cost);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }


    @Operation(
            summary = "Create a qr code",
            description = """
            Creates a qr code with the id of order..
            """
    )
    @GetMapping("/order/qr/{orderId}")
    public ResponseEntity<String> getQRCode(@PathVariable Integer orderId) {
        try {
            String qrCode = transportOrderService.createQRCode(orderId);
            return ResponseEntity.ok(qrCode);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Problem generating QR code");
        }
    }

    @Operation(
            summary = "Update transport order status by driver",
            description = """
        Updates the status of a transport order. Default status is IN_TRANSIT.
        Optional status codes:
        0 - ON_HOLD,
        -1 - CANCELLED.
        Access restricted to users with role DRIVER.
        """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping("/driver/order/status/{id}")
    public ResponseEntity<String> updateStatusDriver(@PathVariable Integer id,
                                                     @RequestParam(required = false) Integer statusCode) {
        try {
            TransportOrderStatus status;
            if (statusCode == null) {
                status = TransportOrderStatus.IN_TRANSIT;
            } else if (statusCode == 0) {
                status = TransportOrderStatus.ON_HOLD;
            } else if (statusCode == -1) {
                status = TransportOrderStatus.CANCELLED;
            } else {
                return ResponseEntity.badRequest().body("Invalid status code");
            }
            transportOrderService.updateTransportOrderStatus(id, status);
            return ResponseEntity.ok("Status updated to " + status);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(
            summary = "Update transport order status by client",
            description = """
        Updates the status of a transport order. Default status is DELIVERED.
        Optional status codes:
        0 - ON_HOLD,
        -1 - CANCELLED.
        Access restricted to users with role CLIENT.
        """,
            security = @SecurityRequirement(name = "bearerAuth")
    )

    @PutMapping("/client/order/status/{id}")
    public ResponseEntity<String> updateStatusClient(@PathVariable Integer id,
                                                     @RequestParam(required = false) Integer statusCode) {
        try {
            TransportOrderStatus status;
            if (statusCode == null) {
                status = TransportOrderStatus.DELIVERED;
            } else if (statusCode == 0) {
                status = TransportOrderStatus.ON_HOLD;
            } else if (statusCode == -1) {
                status = TransportOrderStatus.CANCELLED;
            } else {
                return ResponseEntity.badRequest().body("Invalid status code");
            }
            transportOrderService.updateTransportOrderStatus(id, status);
            return ResponseEntity.ok("Status updated to " + status);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Problem changing status");
        }
    }



}