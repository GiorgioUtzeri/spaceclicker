import java.io.Serializable;

/**
 * Classe représentant un trophée dans le jeu.
 */
public class Achievement implements Serializable{

    /**
     * Description du trophée.
     */
    private String description;

    /**
     * Indique si le trophée a été obtenu ou non.
     */
    private boolean done;

    /**
     * Score objectif à atteindre pour obténir un trophée.
     */
    private int scoreObjectif;

    /**
     * Nombre de pièces objectif à atteindre pour obténir un trophée.
     */
    private int coinsObjectif;

    /**
     * Constructeur de la classe Achievement.
     * 
     * @param description   La description du trophée.
     * @param scoreObjectif Le score objectif à atteindre.
     * @param coinsObjectif Le nombre de pièces objectif à atteindre.
     */
    public Achievement(String description, int scoreObjectif, int coinsObjectif) {
        this.description = description;
        this.done = false;
        this.scoreObjectif = scoreObjectif;
        this.coinsObjectif = coinsObjectif;
    }

    /**
     * Retourne la description du trophée.
     * 
     * @return La description du trophée.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Vérifie si le trophée a été réalisé.
     * 
     * @return true si le trophée a été réalisé, false sinon.
     */
    public boolean isDone() {
        return this.done;
    }

    /**
     * Retourne le score objectif à atteindre pour obténir le trophée.
     * 
     * @return Le score objectif à atteindre.
     */
    public int getScoreObjectif() {
        return this.scoreObjectif;
    }

    /**
     * Retourne le nombre de pièces objectif à atteindre pour obtenir un trophée.
     * 
     * @return Le nombre de pièces objectif à atteindre.
     */
    public int getCoinsObjectif() {
        return this.coinsObjectif;
    }

    /**
     * Vérifie si le trophée a été obtenu en fonction du score et du nombre de
     * pièces du joueur.
     * 
     * @param player Le joueur dont on vérifie les scores.
     * @throws NullPointerException  Si le joueur est null.
     * @throws NumberFormatException Si le score ou le nombre de pièces du joueur
     *                               n'est pas un nombre valide.
     */
    public void achievementsDone(Player player) throws NullPointerException, NumberFormatException {
        if (player.getScore() >= this.getScoreObjectif() && player.getCoins() >= this.getCoinsObjectif()) {
            this.done = true;
        }
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères le trophée.
     * 
     * @return La représentation du trophée.
     */
    public String toString() {
        if (this.isDone()) {
            return this.getDescription() + ": " + "✅";
        }

        return this.getDescription() + ": " + "❌";
    }
}