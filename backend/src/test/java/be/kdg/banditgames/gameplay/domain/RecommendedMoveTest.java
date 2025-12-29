package be.kdg.banditgames.gameplay.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecommendedMoveTest {

    @Test
    void recordStoresValues() {
        RecommendedMove rm = new RecommendedMove("b2b3", 0.33);
        assertEquals("b2b3", rm.move());
        assertEquals(0.33, rm.confidenceScore(), 1e-9);
    }
}

