package com.takkand.horizon.controller;

import com.takkand.horizon.domain.view.RandomView;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequestMapping("/api/sandbox")
public class SandboxController {

    private final EntityManager manager;
    private final JdbcTemplate template;

    public SandboxController(EntityManager manager, JdbcTemplate template) {
        this.manager = manager;
        this.template = template;
    }

    @GetMapping
    List<RandomView> sandbox() {
        return manager.createNativeQuery("select w.id, w.name from wells w", RandomView.class).getResultList();
    }

}
