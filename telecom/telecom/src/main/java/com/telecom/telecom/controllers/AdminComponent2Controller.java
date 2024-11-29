package com.telecom.telecom.controllers;


import com.telecom.telecom.repositories.FunctionsRepository;
import com.telecom.telecom.repositories.ProceduresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/admin")
public class AdminComponent2Controller {
    @Autowired
    private FunctionsRepository functionsRepository;

    @Autowired
    private ProceduresRepository proceduresRepository;

}
