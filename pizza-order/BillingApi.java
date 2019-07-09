package com.example.pizzaorder.api.external;

import com.example.pizzaorder.model.external.OrderEntity;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-07-07T16:47:01.177+03:00")

@Api(value = "billing", description = "the billing API")
public interface BillingApi {

    @ApiOperation(value = "createOrder", notes = "", response = Long.class, tags={ "order-rest", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Long.class),
        @ApiResponse(code = 201, message = "Created", response = Long.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Long.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Long.class),
        @ApiResponse(code = 404, message = "Not Found", response = Long.class) })
    @RequestMapping(value = "/billing/createOrder",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Long> createOrderUsingPOST(@ApiParam(value = "order" ,required=true ) @RequestBody OrderEntity order);


    @ApiOperation(value = "deleteOrder", notes = "", response = String.class, tags={ "order-rest", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = String.class),
        @ApiResponse(code = 201, message = "Created", response = String.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = String.class),
        @ApiResponse(code = 403, message = "Forbidden", response = String.class),
        @ApiResponse(code = 404, message = "Not Found", response = String.class) })
    @RequestMapping(value = "/billing/deleteOrder",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<String> deleteOrderUsingPOST(@ApiParam(value = "orderId" ,required=true ) @RequestBody Long orderId);


    @ApiOperation(value = "getStatus", notes = "", response = String.class, tags={ "order-rest", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = String.class),
        @ApiResponse(code = 201, message = "Created", response = String.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = String.class),
        @ApiResponse(code = 403, message = "Forbidden", response = String.class),
        @ApiResponse(code = 404, message = "Not Found", response = String.class) })
    @RequestMapping(value = "/billing/orderStatus",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<String> getStatusUsingPOST(@ApiParam(value = "orderId" ,required=true ) @RequestBody Long orderId);


    @ApiOperation(value = "submitOrder", notes = "", response = String.class, tags={ "order-rest", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = String.class),
        @ApiResponse(code = 201, message = "Created", response = String.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = String.class),
        @ApiResponse(code = 403, message = "Forbidden", response = String.class),
        @ApiResponse(code = 404, message = "Not Found", response = String.class) })
    @RequestMapping(value = "/billing/submitOrder",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<String> submitOrderUsingPOST(@ApiParam(value = "orderId" ,required=true ) @RequestBody Long orderId);

}
