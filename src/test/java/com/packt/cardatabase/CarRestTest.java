package com.packt.cardatabase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest  // บอกว่านี่คือการทดสอบแอพ Spring Boot
@AutoConfigureMockMvc  // ให้ Spring จัดการการตั้งค่า MockMvc ให้อัตโนมัติ
@DisplayName("Car Management REST API Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) 
public class CarRestTest {

    private static final Logger logger = LoggerFactory.getLogger(CarRestTest.class);
    
    @Autowired
    private MockMvc mockMvc;  // ตัวจำลองการเรียกใช้ API
    
    private String token;  // เก็บ token ที่ได้จากการล็อกอิน

    // เตรียมข้อมูลก่อนการทดสอบแต่ละครั้ง
    @BeforeEach
    @DisplayName("Setup authentication token for testing")
    public void setUp() throws Exception {
        logger.info("=== {} ===", getClass().getAnnotation(DisplayName.class).value());
        logger.info("Running setup: Initialize authentication token");
         // ล็อกอินและเก็บ token
         MvcResult result = mockMvc.perform(post("/login")
         .contentType(MediaType.APPLICATION_JSON)  // ใช้ MediaType จาก Spring
         .content("{\"username\":\"admin\",\"password\":\"admin\"}"))
         .andDo(print())
         .andExpect(status().isOk())
         .andReturn();
     
     token = result.getResponse().getHeader(HttpHeaders.AUTHORIZATION);
     if (token == null) {
         throw new RuntimeException("No token returned from login");
     }
    }

     @Test
     @DisplayName("Should login successfully with valid credentials")
    public void shouldLoginSuccessfullyWithValidCredentials() throws Exception {
        // ทดสอบการล็อกอินด้วยข้อมูลที่ถูกต้อง
        mockMvc.perform(post("/login")
            .content("{\"username\":\"admin\",\"password\":\"admin\"}")  // ส่งข้อมูลล็อกอิน
            .header(HttpHeaders.CONTENT_TYPE, "application/json"))  // บอกว่าส่งข้อมูลเป็น JSON
            .andDo(print())  // แสดงผลลัพธ์ในคอนโซล
            .andExpect(status().isOk());  // ตรวจสอบว่าล็อกอินสำเร็จ
    }

    @Test
    @DisplayName("Should fail login with invalid password")
    public void shouldFailLoginWithInvalidPassword() throws Exception {
        // ทดสอบการล็อกอินด้วยรหัสผ่านผิด
        mockMvc.perform(post("/login")
            .content("{\"username\":\"admin\",\"password\":\"wrongpass\"}")
            .header(HttpHeaders.CONTENT_TYPE, "application/json"))
            .andDo(print())
            .andExpect(status().is4xxClientError());  // ควรได้ error 401 หรือ 403
    }

    @Test
    @DisplayName("Should retrieve cars when authenticated with valid token")
    public void shouldRetrieveCarsWithValidToken() throws Exception {
        // ทดสอบการดึงข้อมูลรถยนต์ทั้งหมด (ต้องใช้ token)
        mockMvc.perform(get("/api/cars")
            .header(HttpHeaders.AUTHORIZATION, token))  // แนบ token เพื่อยืนยันตัวตน
            .andDo(print())
            .andExpect(status().isOk());  // ตรวจสอบว่าดึงข้อมูลสำเร็จ
    }

    @Test
    @DisplayName("Should deny access when requesting cars without token")
    public void shouldDenyAccessWithoutToken() throws Exception {
        // ทดสอบการดึงข้อมูลรถยนต์โดยไม่มี token
        mockMvc.perform(get("/api/cars"))
            .andDo(print())
            .andExpect(status().isUnauthorized());  // ควรได้ error 401
    }

}
