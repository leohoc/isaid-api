package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.infrastructure.model.Prophet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProphetResource {

    @GetMapping("/prophet/{id}")
    public Prophet getProphet(@PathVariable(value = "id") long id) {
        return new Prophet(id, "lcarvalho");
    }

}
