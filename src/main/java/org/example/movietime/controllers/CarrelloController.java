package org.example.movietime.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.movietime.services.CarrelloService;

@RestController
@RequestMapping("carrello")
@Validated
public class CarrelloController {

    private final CarrelloService carrelloService;

    public CarrelloController(CarrelloService carrelloService) {
        this.carrelloService = carrelloService;
    }

    @PostMapping("{}")
    public ResponseEntity<?> aggiungiAlCarrello(

    ){
        //TODO
        return null;
    }

}
