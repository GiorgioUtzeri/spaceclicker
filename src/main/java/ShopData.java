import java.util.Arrays;

public class ShopData {
    public static String[] upgradeNames = new String[]{"amelioration","amélioration","amelioration vaisseau","amélioration vaisseau","vaisseau","radar","combinaison","combi","combinaison spatiale","carburant"};
    public static int[] shipUpgradesCost = new int[]{10, 50, 100, 200, 400, 600};
    public static int[] shipUpgradesBonus = new int[]{1, 1, 2, 5, 10, 30};
    public static int[] radarUpgradesCost = new int[]{10, 50, 100, 200};
    public static int[] radarUpgradesBonus = new int[]{2, 3, 5, 10};
    public static int[] suitUpgradesCost = new int[]{10, 20, 50, 100, 200, 300, 400, 500, 750};
    public static int[] suitUpgradesBonus = new int[]{2, 3, 5, 10, 20, 50};

    /**
     * Vérifie si l'entrée spécifiée est une mise à niveau.
     *
     * @param input L'entrée à vérifier.
     * @return true si l'entrée est une mise à niveau, sinon false.
     */
    public static boolean isAnUpgrade(String input) {
        return Arrays.asList(upgradeNames).contains(input);
    }

    /**
     * Vérifie si le joueur peut acheter l'élément spécifié.
     *
     * @param input l'élément à vérifier
     * @return true si le joueur peut acheter l'élément, sinon false
     */
    public static boolean canBuy(String input) {
        if (getLevel(input) < getMaxLevel(input)) {
            return Game.player.getCoins() >= getCost(input,getLevel(input));
        }
        return false;
    }

    /**
     * Méthode pour effectuer un achat dans le jeu.
     * 
     * @param input L'élément à acheter.
     *              Les valeurs possibles sont :
     *              - "amelioration" ou "amélioration" : pour améliorer le niveau du vaisseau du joueur.
     *              - "amelioration vaisseau" ou "amélioration vaisseau" : pour améliorer le niveau du vaisseau du joueur.
     *              - "vaisseau" : pour améliorer le niveau du vaisseau du joueur.
     *              - "radar" : pour améliorer le niveau du radar du joueur.
     *              - "combinaison" ou "combi" ou "combinaison spatiale" : pour améliorer le niveau de la combinaison spatiale du joueur.
     *              - "carburant" : pour augmenter le carburant du joueur de 500 unités.
     * 
     * @throws IllegalArgumentException si l'élément à acheter n'est pas valide.
     */
    public static void buy(String input) {
        if (!input.equals("carburant")) {
            if (getLevel(input) < getMaxLevel(input)) {
                Game.player.setCoins(Game.player.getCoins()-getCost(input,getLevel(input)));
                switch (input) {
                    case "amelioration":
                        Game.player.shipLevelUp();
                        break;
                        case "amélioration":
                            Game.player.shipLevelUp();
                            break;
                        case "amelioration vaisseau":
                            Game.player.shipLevelUp();
                            break;
                        case "amélioration vaisseau":
                            Game.player.shipLevelUp();
                            break;
                        case "vaisseau":
                            Game.player.shipLevelUp();
                            break;
                    case "radar":
                        Game.player.radarLevelUp();
                        break;
                    case "combinaison":
                        Game.player.suitLevelUp();
                        break;
                        case "combi":
                            Game.player.suitLevelUp();
                            break;
                        case "combinaison spatiale":
                            Game.player.suitLevelUp();
                            break;
                }
            }
        } else {
            Game.player.setCurrentFuel(Game.player.getCurrentFuel()+500);
            Game.player.setCoins(Game.player.getCoins()-20);
        }
    }

    /**
     * Renvoie le niveau correspondant à l'élément spécifié.
     *
     * @param input le nom de l'élément
     * @return le niveau de l'élément spécifié
     */
    public static int getLevel(String input) {
        switch (input) {
            case "amelioration":
                return Game.player.getShipLevel();
                case "amélioration":
                    return Game.player.getShipLevel();
                case "amelioration vaisseau":
                    return Game.player.getShipLevel();
                case "amélioration vaisseau":
                    return Game.player.getShipLevel();
                case "vaisseau":
                    return Game.player.getShipLevel();
            case "radar":
                return Game.player.getRadarLevel();
            case "combinaison":
                return Game.player.getSuitLevel();
                case "combi":
                    return Game.player.getSuitLevel();
                case "combinaison spatiale":
                    return Game.player.getSuitLevel();
            default:
                return 0;
        }
    }

    /**
     * Retourne le niveau maximum correspondant à une certaine catégorie.
     *
     * @param input la catégorie pour laquelle on souhaite obtenir le niveau maximum
     * @return le niveau maximum correspondant à la catégorie spécifiée
     */
    public static int getMaxLevel(String input) {
        switch (input) {
            case "amelioration":
                return 6;
                case "amélioration":
                    return 6;
                case "amelioration vaisseau":
                    return 6;
                case "amélioration vaisseau":
                    return 6;
                case "vaisseau":
                    return 6;
            case "radar":
                return 4;
            case "combinaison":
                return 9;
                case "combi":
                    return 9;
                case "combinaison spatiale":
                    return 9;
            default:
                return 69;
        }
    }

    /**
     * Renvoie le coût associé au type et au niveau spécifiés.
     *
     * @param type le type d'amélioration ou d'objet
     * @param level le niveau de l'amélioration ou de l'objet
     * @return le coût associé au type et au niveau spécifiés
     * @throws IllegalArgumentException si le type spécifié est invalide
     */
    public static int getCost(String type, int level) {
        switch (type) {
            case "amelioration":
                return shipUpgradesCost[level];
                case "amélioration":
                    return shipUpgradesCost[level];
                case "amelioration vaisseau":
                    return shipUpgradesCost[level];
                case "amélioration vaisseau":
                    return shipUpgradesCost[level];
                case "vaisseau":
                    return shipUpgradesCost[level];
            case "radar":
                return radarUpgradesCost[level];
            case "combinaison":
                return suitUpgradesCost[level];
                case "combi":
                    return suitUpgradesCost[level];
                case "combinaison spatiale":
                    return suitUpgradesCost[level];
            case "carburant":
                return 20;
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

    /**
     * Retourne le format du coût pour un type donné et un niveau donné.
     *
     * @param type le type d'amélioration ou d'équipement
     * @param level le niveau de l'amélioration ou de l'équipement
     * @return le format du coût correspondant au type et au niveau spécifiés
     * @throws IllegalArgumentException si le type spécifié est invalide
     */
    public static String getCostFormat(String type, int level) {
        if (getLevel(type) < getMaxLevel(type)) { 
            switch (type) {
                case "amelioration":
                    return formatCost(shipUpgradesCost[level]);
                    case "amélioration":
                        return formatCost(shipUpgradesCost[level]);
                    case "amelioration vaisseau":
                        return formatCost(shipUpgradesCost[level]);
                    case "amélioration vaisseau":
                        return formatCost(shipUpgradesCost[level]);
                    case "vaisseau":
                        return formatCost(shipUpgradesCost[level]);
                case "radar":
                    return formatCost(radarUpgradesCost[level]);
                case "combinaison":
                    return formatCost(suitUpgradesCost[level]);
                    case "combi":
                        return formatCost(suitUpgradesCost[level]);
                    case "combinaison spatiale":
                        return formatCost(suitUpgradesCost[level]);
                case "carburant":
                    return formatCost(20);
                default:
                    throw new IllegalArgumentException("Invalid type: " + type);
            }
        }
        return "----";
    }

    /**
     * Formate le coût donné en une chaîne de caractères avec un certain nombre d'espaces devant.
     *
     * @param cost le coût à formater
     * @return la chaîne de caractères formatée
     */
    public static String formatCost(int cost) {
        String res = "" + cost;
        if (cost < 10) {
            res = "   " + res;
        } else if (cost < 100) {
            res = "  " + res;
        } else if (cost < 1000) {
            res = " " + res;
        }
        return res;
    }

    /**
     * Récupère le bonus correspondant au type et au niveau spécifiés.
     *
     * @param type le type de bonus (amelioration, amélioration, amelioration vaisseau, amélioration vaisseau, vaisseau, radar, combinaison, combi, combinaison spatiale)
     * @param level le niveau du bonus
     * @return le bonus correspondant au type et au niveau spécifiés
     * @throws IllegalArgumentException si le type spécifié est invalide
     */
    public static int getBonus(String type, int level) {
        switch (type) {
            case "amelioration":
                return shipUpgradesBonus[level];
                case "amélioration":
                    return shipUpgradesBonus[level];
                case "amelioration vaisseau":
                    return shipUpgradesBonus[level];
                case "amélioration vaisseau":
                    return shipUpgradesBonus[level];
                case "vaisseau":
                    return shipUpgradesBonus[level];
            case "radar":
                return radarUpgradesBonus[level];
            case "combinaison":
                return suitUpgradesBonus[level];
                case "combi":
                    return suitUpgradesBonus[level];
                case "combinaison spatiale":
                    return suitUpgradesBonus[level];
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }
}