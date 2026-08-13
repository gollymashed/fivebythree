package com.studiomashed.threebythree.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studiomashed.threebythree.engine.SlotEngine;
import com.studiomashed.threebythree.model.SpinResult;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class SpinController {

    private final SlotEngine slotEngine;

    public SpinController(SlotEngine slotEngine) {
        this.slotEngine = slotEngine;
    }

    @PostMapping("/spin")
    public SpinResult spin(
            @RequestParam(defaultValue = "100") long stakePerLineInPence,
            @RequestParam(defaultValue = "5") int numberOfPaylines) {
        return slotEngine.spin(stakePerLineInPence, numberOfPaylines);
    }
}