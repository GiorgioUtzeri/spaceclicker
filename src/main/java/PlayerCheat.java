public class PlayerCheat extends Player {
    public PlayerCheat(String id){
        super(id);
    }

    /**
     * Met à jour les statistiques du joueur en attribuant le nombre maximum de pièces.
     */
    public void maxCoins(){
        this.stats = new Stats<Integer,Integer>(99999, this.stats.getScore());
    }

    /**
     * Met à jour les points du joueur avec la valeur maximale.
     */
    public void maxPoints(){
        this.stats = new Stats<Integer,Integer>(this.stats.getCoins(), 99999);
    }

    /**
     * Réinitialise le carburant actuel du joueur à la valeur maximale.
     */
    public void maxFuel(){
        this.currentFuel = this.getMaxFuel();
    }

    /**
     * Méthode permettant de définir le carburant actuel du joueur à 0.
     */
    public void noFuel(){
        this.currentFuel = 0;
    }
}
