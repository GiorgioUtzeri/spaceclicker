import java.io.Serializable;

public class PermaBoost implements Boost, Serializable {
    String name;
    double multiplier;
    int type; // 1 pr la combo, 2 pr la chance
    boolean addition;

    PermaBoost(String name, double multiplier, int type) {
        this.name = name;
        this.multiplier = multiplier;
        this.type = type;
        this.addition = false;
    }

    PermaBoost(String name, double multiplier, int type, boolean addition) {
        this.name = name;
        this.multiplier = multiplier;
        this.type = type;
        this.addition = addition;
    }

    public String getName() {
        return this.name;
    }

    public double getMultiplier() {
        return this.multiplier;
    }

    public int getType() {
        return this.type;
    }

    public boolean isValid() {
        return true;
    }

    public boolean isAddition() {
        return this.addition;
    }
}