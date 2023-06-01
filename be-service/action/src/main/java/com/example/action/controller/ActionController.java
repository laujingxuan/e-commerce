package com.example.action.controller;

import com.example.action.DTO.UserActionDTO;
import com.example.action.service.ActionService;
import com.example.shared.utils.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(ActionController.class);

    @Autowired
    public ActionController(ActionService actionService, JwtTokenService jwtTokenService) {
        this.actionService = actionService;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUserAction(HttpServletRequest httpServletRequest, @Valid @RequestBody UserActionDTO userActionDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warn("Error during create user action binding: {}", bindingResult.getAllErrors());
            return ResponseEntity.badRequest().build();
        }

        String jwtToken = jwtTokenService.extractJwtTokenFromRequest(httpServletRequest);
        if (!jwtTokenService.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserActionDTO createdUserAction = actionService.create(userActionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserAction);
    }

    @GetMapping("/list/{pathUuid}")
    public ResponseEntity<?> getUserActionList(@PathVariable String pathUuid, HttpServletRequest httpServletRequest) {
        String jwtToken = jwtTokenService.extractJwtTokenFromRequest(httpServletRequest);
        if (!jwtTokenService.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userUuid = jwtTokenService.extractUserUuid(jwtToken);
        String authority = jwtTokenService.extractAuthority(jwtToken);

        List<UserActionDTO> userActionList = actionService.getUserActionList(pathUuid, userUuid, authority);
        return ResponseEntity.ok().body(userActionList);
    }
}
