package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.domain.model.Prophet;
import com.lcarvalho.isaid.api.domain.service.ProphetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProphetResource {

    @Autowired
    ProphetService prophetService;

    @GetMapping("/prophet/{login}")
    public Prophet getProphet(@PathVariable(value = "login") String login) {
        return prophetService.retrieveProphetBy(login);
    }

    @PostMapping("/prophet")
    public Prophet createProphet(@RequestBody String login) {
        return prophetService.createProphet(login);
    }
}
