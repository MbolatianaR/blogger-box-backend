package com.dauphine.blogger.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(
        name = "Hello World",
        description = "My first 3 endpoints"
)

public class HelloWorldController {

    @GetMapping("/hello-world")
    @Operation(
            summary = "Get a hello world message"
    )
    public String helloWorld() {
        return "Hello World !";
    }

    @GetMapping("/hello")
    public String helloByName(@RequestParam String name) {
        return "Hello " + name;
    }

    @GetMapping("hello/{name}")
    @Operation(
            summary = "Get a hello message with a name",
            description = "Returns 'Hello {name}' by path variable"
    )
    public String hello(
            @Parameter(description = "Name to greet")
            @PathVariable String name
    ) {

        return "Hello " + name;
    }
}
