import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class PlayerTest{
    
    @Test
    public void testScoreMoney(){
        Player player = new Player(null);
        player.addScore(40);
        assertEquals(2, player.getCoins());
        player.addScore(100);
        assertEquals(7, player.getCoins());
        player.addScore(1000);
        assertEquals(57, player.getCoins());
    }

    @Test
    public void testDecreaseFuel(){
        Player player = new Player(null);
        player.setLastFuel(50);
        player.decreaseFuel();
        assertTrue(player.getCurrentFuel()<1000);
    }
}