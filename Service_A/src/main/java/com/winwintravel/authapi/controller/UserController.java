package com.winwintravel.authapi.controller;

import com.winwintravel.authapi.dto.JwtAuthenticationDto;
import com.winwintravel.authapi.dto.ProcessLogDto;
import com.winwintravel.authapi.dto.TextRequestDto;
import com.winwintravel.authapi.dto.UserDTO;
import com.winwintravel.authapi.dto.mapping.ProcessLogMapping;
import com.winwintravel.authapi.dto.mapping.UserMapping;
import com.winwintravel.authapi.entity.ProcessingLog;
import com.winwintravel.authapi.entity.User;
import com.winwintravel.authapi.service.imp.ProcessLogServiceImp;
import com.winwintravel.authapi.service.imp.UserServiceImp;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImp userService;
    private final RestTemplate restTemplate =  new RestTemplate();
    private final UserMapping userMapping;
    private final UserServiceImp userServiceImp;
    private final ProcessLogServiceImp processLogServiceImp;
    private final ProcessLogMapping processLogMapping;

//    private final Dotenv dotenv;

    @Value("${INTERNAL_TOKEN}")
    private String INTERNAL_TOKEN;
    @Value("${SERVICE_B_URL}")
    private String SERVICE_B_URL;



//    @Value("${INTERNAL_TOKEN}")
//    @Value("${service.b.url}")
//    @Value("http://localhost:8081/api/transform")
//    private final String serviceBUrl = dotenv.get("SERVICE_B_URL");
//    @Value("e1ceee07dd32f33302290d9b3eda4da8d600cb2417def53b67d9e9527b707923")
//    private final String serviceBToken = dotenv.get("INTERNAL_TOKEN");

    @PostMapping("/process")
    public ResponseEntity<String> process(@RequestBody TextRequestDto request) {
//        System.out.println(">>> SERVICE_B_URL" +  SERVICE_B_URL);
//        System.out.println(">>> INTERNAL_TOKEN" +  INTERNAL_TOKEN);

//        processLogDto.setId(UUID.randomUUID());


        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Internal-Token", INTERNAL_TOKEN);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<TextRequestDto> entity = new HttpEntity<>(request, headers);

            ResponseEntity<TextRequestDto> response = restTemplate.exchange(SERVICE_B_URL, HttpMethod.POST, entity, TextRequestDto.class);
            ProcessingLog processLog = new ProcessingLog();
            processLog.setInputText(request.getText());
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            processLog.setUserId(userServiceImp.findUuidByEmail(email));
            processLog.setCreatedAt(LocalDateTime.now());
            processLog.setOutputText(response.getBody().getText());
//            System.out.println(processLog.toString());
            processLogServiceImp.save(processLog);
            return ResponseEntity.ok(processLog.getOutputText());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//    @PostMapping("/register")
//    public String register(@RequestBody UserDTO userDTO) {
//        userService.saveUser(userDTO);
//        return "User registered successfully";
//    }
//
//    @GetMapping("/email/{email}")
//    public UserDTO getUserByEmail(@PathVariable String email) {
//        return userService.getUserByEmail(email);
//    }

//    @GetMapping("/get")
//    public List<UserDTO> getUser(Model model) {
//        model.addAttribute("email", userService.findAllUsers());
//        return userService.findAllUsers();
//    }
//    @GetMapping("/get/{email}")
//    public Optional<UserDTO> getOneUser(@PathVariable String email, Model model) {
//        model.addAttribute("email", userService.getUserByEmail(email));
//        return userService.getUserByEmail(email);
//    }
}
