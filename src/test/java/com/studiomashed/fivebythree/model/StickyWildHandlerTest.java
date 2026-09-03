package com.studiomashed.fivebythree.model;

import com.studiomashed.fivebythree.feature.StickyWildHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StickyWildHandlerTest {

    private final StickyWildHandler handler =
            new StickyWildHandler();

    @Test
    void shouldKeepPreviousWildsSticky() {

        SpinGrid previousGrid = new SpinGrid(List.of(
                new ReelWindow(List.of(Symbol.WILD, Symbol.LP1, Symbol.LP2)),
                new ReelWindow(List.of(Symbol.LP1, Symbol.WILD, Symbol.LP2)),
                new ReelWindow(List.of(Symbol.LP1, Symbol.LP2, Symbol.WILD)),
                new ReelWindow(List.of(Symbol.WILD, Symbol.LP1, Symbol.LP2)),
                new ReelWindow(List.of(Symbol.LP1, Symbol.WILD, Symbol.LP2))
        ));

        SpinGrid newGrid = new SpinGrid(List.of(
                new ReelWindow(List.of(Symbol.LP1, Symbol.LP1, Symbol.LP1)),
                new ReelWindow(List.of(Symbol.LP2, Symbol.LP2, Symbol.LP2)),
                new ReelWindow(List.of(Symbol.LP3, Symbol.LP3, Symbol.LP3)),
                new ReelWindow(List.of(Symbol.LP4, Symbol.LP4, Symbol.LP4)),
                new ReelWindow(List.of(Symbol.MP1, Symbol.MP1, Symbol.MP1))
        ));

        SpinGrid result = handler.apply(
                newGrid,
                previousGrid
        );

        assertEquals(Symbol.WILD, result.reels().get(0).symbolAt(0));
        assertEquals(Symbol.WILD, result.reels().get(1).symbolAt(1));
        assertEquals(Symbol.WILD, result.reels().get(2).symbolAt(2));
        assertEquals(Symbol.WILD, result.reels().get(3).symbolAt(0));
        assertEquals(Symbol.WILD, result.reels().get(4).symbolAt(1));
    }

    @Test
    void shouldKeepPreviousWildsStickyAndLeaveOtherSymbolsUnchanged() {

        SpinGrid previousGrid = new SpinGrid(List.of(
                new ReelWindow(List.of(Symbol.WILD, Symbol.LP1, Symbol.LP2)),
                new ReelWindow(List.of(Symbol.LP1, Symbol.WILD, Symbol.LP2)),
                new ReelWindow(List.of(Symbol.LP1, Symbol.LP2, Symbol.WILD)),
                new ReelWindow(List.of(Symbol.WILD, Symbol.LP1, Symbol.LP2)),
                new ReelWindow(List.of(Symbol.LP1, Symbol.WILD, Symbol.LP2))
        ));

        SpinGrid newGrid = new SpinGrid(List.of(
                new ReelWindow(List.of(Symbol.LP1, Symbol.LP1, Symbol.LP1)),
                new ReelWindow(List.of(Symbol.LP2, Symbol.LP2, Symbol.LP2)),
                new ReelWindow(List.of(Symbol.LP3, Symbol.WILD, Symbol.LP3)),
                new ReelWindow(List.of(Symbol.LP4, Symbol.LP4, Symbol.LP4)),
                new ReelWindow(List.of(Symbol.MP1, Symbol.MP1, Symbol.MP1))
        ));

        SpinGrid expectedGrid = new SpinGrid(List.of(
                new ReelWindow(List.of(Symbol.WILD, Symbol.LP1, Symbol.LP1)),
                new ReelWindow(List.of(Symbol.LP2, Symbol.WILD, Symbol.LP2)),
                new ReelWindow(List.of(Symbol.LP3, Symbol.WILD, Symbol.WILD)),
                new ReelWindow(List.of(Symbol.WILD, Symbol.LP4, Symbol.LP4)),
                new ReelWindow(List.of(Symbol.MP1, Symbol.WILD, Symbol.MP1))
        ));

        SpinGrid result = handler.apply(newGrid, previousGrid);

        assertEquals(expectedGrid, result);
    }
}