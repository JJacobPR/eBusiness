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

import java.util.List;

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

    @GetMapping("/client/orders")
    public ResponseEntity<List<TransportOrderResponseDto>> getClientOrders(Authentication authentication) {
        String email = authentication.getName();
        List<TransportOrderResponseDto> orders = transportOrderService.getActiveOrdersForClient(email);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/client/orders-history")
    public ResponseEntity<List<TransportOrderResponseDto>> getClientHistory(Authentication authentication) {
        String email = authentication.getName();
        List<TransportOrderResponseDto> orders = transportOrderService.getHistoryForClient(email);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/driver/orders")
    public ResponseEntity<List<TransportOrderResponseDto>> getDriverOrders(Authentication authentication) {
        String email = authentication.getName();
        List<TransportOrderResponseDto> orders = transportOrderService.getActiveOrdersForClient(email);
        return ResponseEntity.ok(orders);
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
        Updates the status of a transport order by status name.
        If no status name is provided, defaults to DELIVERED.
        Allowed status names (case-insensitive):
        CREATED, SCHEDULED, IN_TRANSIT, DELIVERED, CANCELLED, ON_HOLD.
        Access restricted to users with role Drver.
        """,
            security = @SecurityRequirement(name = "bearerAuth")
    )

    @PutMapping("/driver/order/status/{id}")
    public ResponseEntity<String> updateStatusDriver(@PathVariable Integer id,
                                                     @RequestParam(required = false) String statusName) {
        try {
            TransportOrderStatus status;
            if (statusName == null) {
                status = TransportOrderStatus.DELIVERED;
            } else {
                switch (statusName.toUpperCase()) {
                    case "CREATED":
                        status = TransportOrderStatus.CREATED;
                        break;
                    case "SCHEDULED":
                        status = TransportOrderStatus.SCHEDULED;
                        break;
                    case "IN_TRANSIT":
                        status = TransportOrderStatus.IN_TRANSIT;
                        break;
                    case "DELIVERED":
                        status = TransportOrderStatus.DELIVERED;
                        break;
                    case "CANCELLED":
                        status = TransportOrderStatus.CANCELLED;
                        break;
                    case "ON_HOLD":
                        status = TransportOrderStatus.ON_HOLD;
                        break;
                    default:
                        return ResponseEntity.badRequest().body("Invalid status name");
                }
            }
            transportOrderService.updateTransportOrderStatus(id, status);
            return ResponseEntity.ok("Status updated to " + status);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Problem changing status");
        }
    }



    @Operation(
            summary = "Update transport order status by client",
            description = """
        Updates the status of a transport order by status name.
        If no status name is provided, defaults to DELIVERED.
        Allowed status names (case-insensitive):
        CREATED, SCHEDULED, IN_TRANSIT, DELIVERED, CANCELLED, ON_HOLD.
        Access restricted to users with role CLIENT.
        """,
            security = @SecurityRequirement(name = "bearerAuth")
    )

    @PutMapping("/client/order/status/{id}")
    public ResponseEntity<String> updateStatusClient(@PathVariable Integer id,
                                                     @RequestParam(required = false) String statusName) {
        try {
            TransportOrderStatus status;
            if (statusName == null) {
                status = TransportOrderStatus.DELIVERED;  // default
            } else {
                switch (statusName.toUpperCase()) {
                    case "CREATED":
                        status = TransportOrderStatus.CREATED;
                        break;
                    case "SCHEDULED":
                        status = TransportOrderStatus.SCHEDULED;
                        break;
                    case "IN_TRANSIT":
                        status = TransportOrderStatus.IN_TRANSIT;
                        break;
                    case "DELIVERED":
                        status = TransportOrderStatus.DELIVERED;
                        break;
                    case "CANCELLED":
                        status = TransportOrderStatus.CANCELLED;
                        break;
                    case "ON_HOLD":
                        status = TransportOrderStatus.ON_HOLD;
                        break;
                    default:
                        return ResponseEntity.badRequest().body("Invalid status name");
                }
            }
            transportOrderService.updateTransportOrderStatus(id, status);
            return ResponseEntity.ok("Status updated to " + status);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Problem changing status");
        }
    }



}