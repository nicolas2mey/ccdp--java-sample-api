package com.adeo.sample.path.controllers;

import com.adeo.sample.path.beans.EchoPath;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Temporary custom path to handle free path as String parameter.
 */
@RestController
@RequestMapping("paths")
public class PathController {

    @GetMapping(value = "/{path}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<EchoPath> echoPath(@PathVariable final String path) {

        return Mono.just(new EchoPath(path));
    }
}
