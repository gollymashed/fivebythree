package com.studiomashed.threebyone.api;

import com.studiomashed.threebyone.engine.SlotEngine;
import com.studiomashed.threebyone.model.SpinResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpinController {

    private final SlotEngine slotEngine;

    public SpinController(SlotEngine slotEngine) {
        this.slotEngine = slotEngine;
    }

    @PostMapping("/spin")
    public SpinResult spin(
            @RequestParam(defaultValue = "100") long stakeInPence) {
        return slotEngine.spin(stakeInPence);
    }
}