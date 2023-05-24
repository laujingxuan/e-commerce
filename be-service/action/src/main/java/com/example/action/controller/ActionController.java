package com.example.action.controller;

import com.example.action.DTO.UserActionDTO;
import com.example.action.common.JwtTokenService;
import com.example.action.service.ActionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actions")
public class ActionController {
    private ActionService actionService;

    private JwtTokenService jwtTokenService;

    @Autowired
    public ActionController(ActionService actionService, JwtTokenService jwtTokenService){
        this.actionService = actionService;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUserAction(HttpServletRequest httpServletRequest, @Valid @RequestBody UserActionDTO userActionDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            System.out.println("Error: " + bindingResult.getAllErrors());
            System.out.println("Failed binding result");
            return ResponseEntity.badRequest().build();
        }

        String jwtToken = jwtTokenService.extractJwtTokenFromRequest(httpServletRequest);
        if (!jwtTokenService.validateToken(jwtToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserActionDTO createdUserAction = actionService.create(userActionDTO);
        if (createdUserAction == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list/{pathUuid}")
    public ResponseEntity<?> getUserActionList(@PathVariable String pathUuid, HttpServletRequest httpServletRequest){
        String jwtToken = jwtTokenService.extractJwtTokenFromRequest(httpServletRequest);
        if (!jwtTokenService.validateToken(jwtToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userUuid = jwtTokenService.extractUserUuid(jwtToken);
        String authority = jwtTokenService.extractAuthority(jwtToken);

        List<UserActionDTO> userActionList = actionService.getUserActionList(pathUuid, userUuid, authority);
        if (userActionList == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().body(userActionList);
    }
}
