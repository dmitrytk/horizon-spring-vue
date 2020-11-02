package com.takkand.horizon.controller;

import com.takkand.horizon.domain.view.IncView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RestController
@RequestMapping("/api/sandbox")
public class SandboxController {

    private final EntityManager manager;

    public SandboxController(EntityManager manager) {
        this.manager = manager;
    }

    @GetMapping
    List<IncView> sandbox() {
        Query q = manager.createNativeQuery("SELECT * FROM inclinometry_view i" +
                " where i.well_name in (select w.name from wells w where w.field_id = 1)", IncView.class);
        return q.getResultList();
    }

}
