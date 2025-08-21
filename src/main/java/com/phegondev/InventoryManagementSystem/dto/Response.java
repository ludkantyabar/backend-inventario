package com.phegondev.InventoryManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.phegondev.InventoryManagementSystem.enums.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    //generico
    private int status;
    private String message;

    //español: token de acceso
    private String token;
    private UserRole role;
    private String expirationTime;

    //español: paginación
    private Integer totalPages;
    private Long totalElements;

    //español: datos de salida opcionales
    private UserDTO user;
    private List<UserDTO> users;

    private SupplierDTO supplier;
    private List<SupplierDTO> suppliers;

    private CategoryDTO category;
    private List<CategoryDTO> categories;

    private ProductDTO product;
    private List<ProductDTO> products;

    private TransactionDTO transaction;
    private List<TransactionDTO> transactions;

    private final LocalDateTime timestamp = LocalDateTime.now();








}
