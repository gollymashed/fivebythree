package com.studiomashed.fivebythree.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studiomashed.fivebythree.engine.SlotEngine;
import com.studiomashed.fivebythree.model.SpinResult;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class SpinController {

    private final SlotEngine slotEngine;

    public SpinController(SlotEngine slotEngine) {
        this.slotEngine = slotEngine;
    }

    @PostMapping("/spin")
    public SpinResult spin(
            @RequestParam(defaultValue = "20") long stakePerLineInPence,
            @RequestParam(defaultValue = "20") int numberOfPaylines) {
        return slotEngine.spin(stakePerLineInPence, numberOfPaylines);
    }
}