package com.example.action.controller;

import com.example.action.common.JwtTokenService;
import com.example.action.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;

@RestController
public class ActionController {
    private ActionService actionService;

    private JwtTokenService jwtTokenService;

    @Autowired
    public ActionController(ActionService actionService, JwtTokenService jwtTokenService){
        this.actionService = actionService;
        this.jwtTokenService = jwtTokenService;
    }

}
