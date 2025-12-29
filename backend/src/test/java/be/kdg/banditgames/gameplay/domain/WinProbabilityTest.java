package be.kdg.banditgames.gameplay.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WinProbabilityTest {

    @Test
    void recordStoresValues() {
        WinProbability wp = new WinProbability(0.2, 0.8, 0.2, List.of(0.2, 0.8));
        assertEquals(0.2, wp.player1WinProbability(), 1e-9);
        assertEquals(0.8, wp.player2WinProbability(), 1e-9);
        assertEquals(2, wp.distribution().size());
    }
}

