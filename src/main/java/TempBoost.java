import java.io.Serializable;
import java.time.LocalDateTime;

public class TempBoost implements Boost, Serializable {
    String name;
    double multiplier;
    int type; // 1 pr la combo, 2 pr la chance
    LocalDateTime endDate;
    boolean addition;

    TempBoost(String name, double multiplier, int type, LocalDateTime endDate) {
        this.name = name;
        this.multiplier = multiplier;
        this.type = type;
        this.endDate = endDate;
        this.addition = false;
    }

    TempBoost(String name, double multiplier, int type, LocalDateTime endDateTime, boolean addition) {
        this.name = name;
        this.multiplier = multiplier;
        this.type = type;
        this.endDate = endDateTime;
        this.addition = addition;
    }

    /**
     * Renvoie le nom de l'objet.
     *
     * @return le nom de l'objet
     */
    public String getName() {
        return this.name;
    }

    /**
     * Renvoie le multiplicateur actuel.
     *
     * @return Le multiplicateur actuel.
     */
    public double getMultiplier() {
        return this.multiplier;
    }

    /**
     * Renvoie le type de l'objet.
     *
     * @return le type de l'objet.
     */
    public int getType() {
        return this.type;
    }

    /**
     * Vérifie si la date actuelle est antérieure à la date de fin.
     * 
     * @return true si la date actuelle est antérieure à la date de fin, sinon false.
     */
    public boolean isValid() {
        return LocalDateTime.now().isBefore(this.endDate);
    }

    /**
     * Renvoie si l'opération est une addition.
     *
     * @return true si l'opération est une addition, sinon false.
     */
    public boolean isAddition() {
        return this.addition;
    }
    
}
