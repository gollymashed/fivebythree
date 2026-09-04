package com.studiomashed.fivebythree.api;

import com.studiomashed.fivebythree.model.GameState;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studiomashed.fivebythree.engine.SlotEngine;
import com.studiomashed.fivebythree.model.SpinResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class SpinController {

    private final SlotEngine slotEngine;
    private final Map<String, GameState> gameStates = new ConcurrentHashMap<>();

    public SpinController(SlotEngine slotEngine) {
        this.slotEngine = slotEngine;
    }

    @PostMapping("/spin")
    public SpinResult spin(
            @RequestParam String playerId,
            @RequestParam(defaultValue = "10") long stakeInCoins) {

        GameState gameState = gameStates.computeIfAbsent(
                playerId,
                id -> new GameState()
        );

        return slotEngine.spin(stakeInCoins, gameState);
    }
}