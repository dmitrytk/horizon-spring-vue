package com.takkand.horizon.controller;

import com.takkand.horizon.domain.Rate;
import com.takkand.horizon.repository.RateRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/rates")
public class RateController {

    private final RateRepository rateRepository;

    public RateController(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }


    @GetMapping
    List<Rate> findAll() {
        return rateRepository.findAll();
    }
}
