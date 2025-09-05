package com.phegondev.InventoryManagementSystem;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phegondev.InventoryManagementSystem.dto.*;
import com.phegondev.InventoryManagementSystem.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerLayerTDDTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String adminEmail;
    private final String adminPassword = "adminpassword";
    private String adminToken;

    @BeforeEach
    void setup() throws Exception {
        if (adminEmail == null) {
            adminEmail = "admin_" + System.currentTimeMillis() + "@example.com";
            RegisterRequest req = new RegisterRequest();
            req.setName("Admin");
            req.setEmail(adminEmail);
            req.setPassword(adminPassword);
            req.setPhoneNumber("123456789");
            req.setRole(UserRole.ADMIN);

            mockMvc.perform(post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
        }
        adminToken = getAdminToken();
    }

    private String getAdminToken() throws Exception {
        LoginRequest loginReq = new LoginRequest();
        loginReq.setEmail(adminEmail);
        loginReq.setPassword(adminPassword);

        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReq)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode json = objectMapper.readTree(response);
        if (json.get("token") == null) {
            throw new IllegalStateException("Respuesta de login sin 'token': " + response);
        }
        return json.get("token").asText();
    }

    @Test
    void testRegisterUser() throws Exception {
        RegisterRequest req = new RegisterRequest();
        req.setName("TDD User");
        req.setEmail("tdduser_" + System.currentTimeMillis() + "@example.com");
        req.setPassword("password");
        req.setPhoneNumber("123456789");
        req.setRole(UserRole.MANAGER);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testCreateCategory() throws Exception {
        CategoryDTO dto = new CategoryDTO();
        dto.setName("TDD Category " + System.currentTimeMillis());

        mockMvc.perform(post("/api/categories/add")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testAddSupplier() throws Exception {
        SupplierDTO dto = new SupplierDTO();
        dto.setName("TDD Supplier " + System.currentTimeMillis());
        dto.setAddress("Test Address");

        mockMvc.perform(post("/api/suppliers/add")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users/all")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testGetAllCategories() throws Exception {
        mockMvc.perform(get("/api/categories/all")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/products/all")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testGetAllSuppliers() throws Exception {
        mockMvc.perform(get("/api/suppliers/all")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testGetAllTransactions() throws Exception {
        mockMvc.perform(get("/api/transactions/all")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }




    // Obtener usuarios: resultado esperado (ya existe)
    @Test
    void testGetAllUsersExpected() throws Exception {
        mockMvc.perform(get("/api/users/all")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    // Obtener categorías: resultado esperado
    @Test
    void testGetAllCategoriesExpected() throws Exception {
        mockMvc.perform(get("/api/categories/all")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    // Obtener proveedores: resultado esperado
    @Test
    void testGetAllSuppliersExpected() throws Exception {
        mockMvc.perform(get("/api/suppliers/all")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testCreateCategoryWithPost() throws Exception {
        CategoryDTO dto = new CategoryDTO();
        dto.setName("Nueva Categoría " + System.currentTimeMillis());

        mockMvc.perform(post("/api/categories/add")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testSimpleCreateCategory() throws Exception {
        CategoryDTO dto = new CategoryDTO();
        dto.setName("CategoriaSimple_" + System.currentTimeMillis());

        mockMvc.perform(post("/api/categories/add")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testAddSupplierUno() throws Exception {
        SupplierDTO dto = new SupplierDTO();
        dto.setName("ProveedorUno_" + System.currentTimeMillis());
        dto.setAddress("Dirección Uno");

        mockMvc.perform(post("/api/suppliers/add")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    // Test POST para crear usuario
    @Test
    void testPostRegisterUserSimple() throws Exception {
        RegisterRequest req = new RegisterRequest();
        req.setName("UsuarioTest");
        req.setEmail("usuario_" + System.currentTimeMillis() + "@test.com");
        req.setPassword("testpass");
        req.setPhoneNumber("987654321");
        req.setRole(UserRole.MANAGER);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }


}
