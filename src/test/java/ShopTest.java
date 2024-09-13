import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class ShopTest{

    @Test
    public void testBuyingShipItem(){
        Player player = new Player(null);
        Game.player = (PlayerCheat) player;
        player.setCoins(1000);
        ShopData.buy("amelioration");
        assertEquals(990, player.getCoins());
        ShopData.buy("amelioration");
        assertEquals(940, player.getCoins());
        ShopData.buy("amelioration");
        assertEquals(840, player.getCoins());
        ShopData.buy("amelioration");
        assertEquals(640, player.getCoins());
    }

    @Test
    public void testBuyingFuelItem(){
        Player player = new Player(null);
        Game.player = (PlayerCheat) player;
        player.setCoins(1000);
        ShopData.buy("carburant");
        assertEquals(980, player.getCoins());
        ShopData.buy("carburant");
        assertEquals(960, player.getCoins());
    }

    @Test
    public void testBuyingRadarItem(){
        Player player = new Player(null);
        Game.player = (PlayerCheat) player;
        player.setCoins(1000);
        ShopData.buy("radar");
        assertEquals(990, player.getCoins());
        ShopData.buy("radar");
        assertEquals(940, player.getCoins());
        ShopData.buy("radar");
        assertEquals(840, player.getCoins());
        ShopData.buy("radar");
        assertEquals(640, player.getCoins());
        ShopData.buy("radar");
        assertEquals(640, player.getCoins()); // l'argent ne bouge pas car on a atteint le level max pour le radar
        
    }
    @Test
    public void testBuyingSpacesuitItem(){
        Player player = new Player(null);
        Game.player = (PlayerCheat) player;
        player.setCoins(1000);
        ShopData.buy("combi");
        assertEquals(990, player.getCoins());
        ShopData.buy("combi");
        assertEquals(940, player.getCoins());
        ShopData.buy("combi");
        assertEquals(840, player.getCoins());
        ShopData.buy("combi");
        assertEquals(640, player.getCoins());
    }
}