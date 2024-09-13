import java.io.Serializable;

/**
 * Classe représentant les statistiques d'un joueur.
 * 
 * @param <C> le type de données pour les pièces
 * @param <S> le type de données pour le score
 */
public class Stats<C,S> implements Serializable {

    private C coins;
    private S score;

    public Stats(C coins, S score){
        this.coins = coins;
        this.score = score;
    }

    public C getCoins() {
        return this.coins;
    }

    public S getScore() {
        return this.score;
    }

    public void setCoins(C coins) {
        this.coins = coins;
    }

    public void setScore(S score) {
        this.score = score;
    }

    
    
}
