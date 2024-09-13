import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.Serializable;

public class Player implements Serializable {
    private String id;
    protected Stats<Integer, Integer> stats;
    protected int currentFuel;
    private int lastFuel;
    private int maxFuel;
    private int shipLevel;
    private int radarLevel;
    private int suitLevel;
    private ArrayList<Achievement> achievements;
    private ArrayList<Boost> boosts;
    private int lastScoreMoney;
    private int chanceRandom;
    private int chanceOnPlanet;

    public Player(String id) {
        this.id = id;
        this.stats = new Stats<Integer, Integer>(0, 0);
        this.lastScoreMoney = 0;
        this.currentFuel = 1000;
        this.maxFuel = 1000;
        this.shipLevel = 0;
        this.lastFuel = 0;
        this.achievements = new ArrayList<Achievement>();
        this.boosts = new ArrayList<Boost>();
        this.chanceRandom = 1;
        this.chanceOnPlanet = 60;

        this.addAchievement();
    }

    /**
     * Réinitialise le player
     */
    public void restart() {
        this.stats = new Stats<Integer, Integer>(0, 0);
        this.lastScoreMoney = 0;
        this.currentFuel = 1000;
        this.maxFuel = 1000;
        this.shipLevel = 0;
        this.lastFuel = 0;
        this.achievements = new ArrayList<Achievement>();
        this.boosts = new ArrayList<Boost>();
        this.chanceRandom = 1;
        this.chanceOnPlanet = 60;

        this.addAchievement();
    }

    /**
     * Diminue le niveau de carburant du joueur.
     * 
     * @param i la quantité de carburant à diminuer
     */
    public void decreaseFuel(int i) {
        this.currentFuel -= i;
        if (this.currentFuel < 0) {
            this.currentFuel = 0;
        }
        this.lastFuel = 0;
    }

    /**
     * Ajoute du carburant au joueur.
     * 
     * @param fuel la quantité de carburant à ajouter
     * @return la quantité de carburant actuelle après l'ajout
     */
    public int addFuel(int fuel) {
        this.currentFuel += fuel;
        if (this.currentFuel > this.maxFuel) {
            this.currentFuel = this.maxFuel;
        }
        return this.currentFuel;
    }

    /**
     * Ajoute un montant de pièces au joueur.
     *
     * @param coins Le nombre de pièces à ajouter.
     * @return Le nombre total de pièces du joueur après l'ajout.
     */
    public int addCoins(int coins) {
        this.stats.setCoins(this.getCoins() + coins);
        return this.getCoins();
    }

    /**
     * Renvoie la chance du joueur sur la planète.
     *
     * @return la chance du joueur sur la planète.
     */
    public int getChanceOnPlanet() {
        return this.chanceOnPlanet;
    }

    /**
     * Définit la valeur de chance aléatoire.
     *
     * @param i La valeur de chance aléatoire à définir.
     */
    public void setChanceRandom(int i) {
        this.chanceRandom = i;
    }

    /**
     * Renvoie la liste des réalisations du joueur.
     *
     * @return la liste des réalisations du joueur
     */
    public ArrayList<Achievement> getAchievements() {
        return this.achievements;
    }

    /**
     * Renvoie la liste des boosts du joueur.
     *
     * @return la liste des boosts du joueur.
     */
    public ArrayList<Boost> getBoosts() {
        return boosts;
    }

    /**
     * Ajoute un boost au joueur.
     *
     * @param boost Le boost à ajouter.
     */
    public void addBoost(Boost boost) {
        this.boosts.add(boost);
    }

    /**
     * Renvoie l'identifiant du joueur.
     *
     * @return l'identifiant du joueur
     */
    public String getId() {
        return this.id;
    }

    /**
     * Renvoie le nombre de pièces du joueur.
     *
     * @return Le nombre de pièces du joueur.
     */
    public int getCoins() {
        return this.stats.getCoins();
    }

    /**
     * Définit le nombre de pièces du joueur.
     * La valeur est limitée entre 0 et 99999.
     * 
     * @param coins le nombre de pièces à définir
     */
    public void setCoins(int coins) {
        if (coins > 99999) {
            coins = 99999;
        } else if (coins < 0) {
            coins = 0;
        }
        this.stats.setCoins(coins);
    }

    /**
     * Renvoie le score du joueur.
     *
     * @return Le score du joueur.
     */
    public int getScore() {
        return this.stats.getScore();
    }

    /**
     * Définit la dernière quantité de carburant du joueur.
     *
     * @param lastFuel la dernière quantité de carburant à définir
     */
    public void setLastFuel(int lastFuel) {
        this.lastFuel = lastFuel;
    }

    public void decreaseFuel() {
        if (this.lastFuel > 48) {
            int fuelDecrease = (int) (Math.random() * 100) + 1;
            this.currentFuel -= fuelDecrease;
            if (this.currentFuel < 0) this.currentFuel = 0;
            this.lastFuel = 0;
        } else {
            this.lastFuel += 1;
        }
    }

    /**
     * Ajoute le score du joueur en prenant en compte les boosts.
     * Si le nouveau score est égal à l'ancien score, incrémente le score de 1.
     * Sinon, met à jour le score avec le nouveau score calculé.
     * Ensuite, vérifie si le score atteint un multiple de 20 supérieur ou égal à lastScoreMoney.
     * Si c'est le cas, incrémente lastScoreMoney de 20, ajoute une pièce au joueur et vérifie les achievements.
     */
    public void addScore() {
        int newScore = (int) (getScore() + getBoostAddition() + 1 * getBoostMultiplier());
        if (newScore == getScore()) {
            this.stats.setScore(getScore()+1);
        } else {
            this.stats.setScore(newScore);
        }
        while (this.getScore() >= this.lastScoreMoney + 20) {
            this.lastScoreMoney += 20;
            this.setCoins(this.getCoins() + 1);
            this.achievements = this.checkAchievements();
        }
    }

    /**
     * Retourne le multiplicateur de boost.
     * 
     * @return Le multiplicateur de boost.
     */
    public double getBoostMultiplier() {
        double totalMultiplier = 1;
        for (Boost boost : this.boosts) {
            if (!boost.isAddition()) {
                totalMultiplier *= boost.getMultiplier();
            }
        }
        return totalMultiplier;
    }

    /**
     * Calcule et retourne l'addition totale des boosts pour le joueur.
     *
     * @return L'addition totale des boosts pour le joueur.
     */
    public double getBoostAddition() {
        double totalAddition = 0;
        for (Boost boost : this.boosts) {
            if (boost.isAddition()) {
                totalAddition += boost.getMultiplier();
            }
        }
        return totalAddition;
    }

    /**
     * Ajoute le score spécifié à celui du joueur.
     * Met également à jour le nombre de pièces en fonction du score.
     * 
     * @param value la valeur du score à ajouter
     */
    public void addScore(int value) {
        this.stats.setScore(getScore() + value);
        while (this.getScore() >= this.lastScoreMoney + 20) {
            this.lastScoreMoney += 20;
            this.setCoins(this.getCoins() + 1);
        }
    }

    /**
     * Renvoie le niveau de carburant actuel du joueur.
     *
     * @return Le niveau de carburant actuel du joueur.
     */
    public int getCurrentFuel() {
        return this.currentFuel;
    }

    /**
     * Définit le niveau actuel de carburant du joueur.
     *
     * @param currentFuel le niveau actuel de carburant à définir
     */
    public void setCurrentFuel(int currentFuel) {
        this.currentFuel = currentFuel;
    }

    /**
     * Renvoie la valeur maximale de carburant.
     *
     * @return La valeur maximale de carburant.
     */
    public int getMaxFuel() {
        return this.maxFuel;
    }

    /**
     * Définit la valeur maximale du carburant.
     * 
     * @param maxFuel La valeur maximale du carburant à définir.
     */
    public void setMaxFuel(int maxFuel) {
        this.maxFuel = maxFuel;
    }

    /**
     * Renvoie le niveau du vaisseau du joueur.
     *
     * @return le niveau du vaisseau du joueur.
     */
    public int getShipLevel() {
        return this.shipLevel;
    }

    /**
     * Retourne le format du niveau du vaisseau.
     * 
     * @return le format du niveau du vaisseau. Si le niveau du vaisseau est différent de 6, retourne le niveau du vaisseau entouré d'espaces. Sinon, retourne "MAX".
     */
    public String getShipLevelFormat() {
        if (this.shipLevel != 6) {
            return " " + this.shipLevel + " ";
        }
        return "MAX";
    }

    /**
     * Augmente le niveau du vaisseau du joueur.
     */
    public void shipLevelUp() {
        this.shipLevel += 1;
        addBoost(new PermaBoost("Ship Upgrade " + this.shipLevel, ShopData.getBonus("vaisseau", shipLevel - 1), 1, true));
    }

    /**
     * Renvoie le niveau du radar du joueur.
     *
     * @return le niveau du radar du joueur.
     */
    public int getRadarLevel() {
        return this.radarLevel;
    }

    /**
     * Retourne le format du niveau de radar.
     * 
     * @return Le format du niveau de radar. Si le niveau de radar n'est pas égal à 4, retourne le niveau de radar entouré d'espaces. Sinon, retourne "MAX".
     */
    public String getRadarLevelFormat() {
        if (this.radarLevel != 4) {
            return " " + this.radarLevel + " ";
        }
        return "MAX";
    }

    /**
     * Augmente le niveau du radar du joueur et augmente également les chances du joueur de trouver une planète.
     */
    public void radarLevelUp() {
        this.radarLevel += 1;
        this.chanceRandom += 1;
    }

    /**
     * Renvoie le niveau de la tenue du joueur.
     *
     * @return le niveau de la tenue du joueur.
     */
    public int getSuitLevel() {
        return this.suitLevel;
    }

    /**
     * Retourne une représentation formatée du niveau de costume.
     *
     * @return une chaîne de caractères représentant le niveau de costume formaté.
     */
    public String getSuitLevelFormat() {
        if (this.suitLevel != 9) {
            return " " + this.suitLevel + " ";
        }
        return "MAX";
    }

    /**
     * Augmente le niveau de la combinaison du joueur.
     * Augmente également les chances du joueur de trouver une planète.
     */
    public void suitLevelUp() {
        this.suitLevel += 1;
        this.chanceOnPlanet += 2;
    }

    /**
     * Réinitialise les statistiques du joueur et remet à zéro certains attributs.
     */
    public void prestige() {
        this.stats = new Stats<Integer, Integer>(0, 0);
        this.lastScoreMoney = 0;
        this.currentFuel = 1000;
        this.maxFuel = 1000;
        this.shipLevel = 0;
    }

    /**
     * Ajoute des réalisations au joueur.
     */
    public void addAchievement() {
        achievements.add(new Achievement("Atteindre les 100 points", 100, 0));
        achievements.add(new Achievement("Atteindre les 200 points", 200, 0));
        achievements.add(new Achievement("Atteindre les 300 points", 300, 0));
        achievements.add(new Achievement("Atteindre les 400 points", 400, 0));
        achievements.add(new Achievement("Atteindre les 500 points", 500, 0));
        achievements.add(new Achievement(" Atteindre les 100 fonds", 0, 100));
        achievements.add(new Achievement(" Atteindre les 200 fonds", 0, 200));
        achievements.add(new Achievement(" Atteindre les 300 fonds", 0, 300));
        achievements.add(new Achievement(" Atteindre les 400 fonds", 0, 400));
        achievements.add(new Achievement(" Atteindre les 500 fonds", 0, 500));
    }

    /**
     * Vérifie les réalisations du joueur.
     * 
     * @return Une liste d'objets Achievement représentant les réalisations vérifiées.
     */
    public ArrayList<Achievement> checkAchievements() {
        ArrayList<Achievement> tempList = new ArrayList<Achievement>();

        for (Achievement achievement : this.getAchievements()) {
            achievement.achievementsDone(this);
            tempList.add(achievement);
        }

        return tempList;
    }

    /**
     * Lit le contenu d'un fichier texte et retourne une liste de chaînes de caractères.
     *
     * @param path le chemin du fichier à lire
     * @return une liste de chaînes de caractères représentant les lignes du fichier
     */
    public static List<String> readFile(String path) {
        try {
            return Files.readAllLines(Paths.get("src/main/resources/ascii/" + path + ".txt"));
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier (" + e.getLocalizedMessage() + ")");
        }
        return null;
    }

    /**
     * Génère une planète aléatoire.
     * Si la chance aléatoire est supérieure ou égale à un nombre aléatoire généré entre 1 et 100, une planète est créée.
     * La méthode lit les informations de la planète à partir du fichier "planets/ecranPlanet" et les affiche à l'écran.
     * Ensuite, elle affiche les informations spécifiques de la planète créée.
     * Enfin, elle ajoute le bonus temporaire de la planète à l'objet courant.
     */
    public void randomPlanet(String input, Scanner scanner) {
        int randomInt = (int) (Math.random() * 100) + 1;
        if (chanceRandom >= randomInt) {
            Planet p = new Planet();
            boolean decouverte=true;
            
            List<String> planet4 = readFile("planets/ecranPlanet");
            for (int i = 0; i < planet4.size() / 2; i += 1) {
                System.out.println(planet4.get(i) + '\n');
            }
            System.out.println(p.afficherplanet());
            List<String> planet3 = readFile("planets/ecranPlanet");
            for (int i = planet3.size() / 2; i < planet3.size(); i += 1) {
                if (i == planet3.size() - 2) {
                    String space = "";
                    for (int j = 0; j < planet3.get(i).length() / 4+10; j++) {
                        space += " ";
                    }
                    System.out.println(space + "Une planète à été découverte ! Voulez-vous vous rendre dessus ? (oui/non)");
                }
                if(i!=planet3.size()-3){
                    System.out.println(planet3.get(i) + '\n');
                }
            }

            while(decouverte){
                input= scanner.nextLine();
                if(input.equals("oui")){
    
                    List<String> planet = readFile("planets/ecranPlanet");
                    for (int i = 0; i < planet.size() / 2; i += 1) {
                        System.out.println(planet.get(i) + '\n');
                    }
                    System.out.println(p.afficherplanet());
                    List<String> planet2 = readFile("planets/ecranPlanet");
                    for (int i = planet2.size() / 2; i < planet2.size(); i += 1) {
                        if (i == planet2.size() - 2) {
                            String space = "";
                            for (int j = 0; j < planet2.get(i).length() / 2 - 10; j++) {
                                space += " ";
                            }
                            System.out.println(space + p.getTempBoostName() + "" + '\n');
                        }
                        System.out.println(planet2.get(i) + '\n');
                    }
                    try {
                        java.lang.Thread.sleep(2000);
                    } catch (Exception e) {
                        System.err.println("Erreur: " + e.getLocalizedMessage());
                    }
                    this.addBoost(p.getTempBoost());
                    decouverte=false;
                }else if(input.equals("non")){
                    decouverte=false;
                } else {
                    for (int i = 0; i < planet4.size() / 2; i += 1) {
                        System.out.println(planet4.get(i) + '\n');
                    }
                    System.out.println(p.afficherplanet());
                    for (int i = planet3.size() / 2; i < planet3.size(); i += 1) {
                        if (i == planet3.size() - 2) {
                            String space = "";
                            for (int j = 0; j < planet3.get(i).length() / 4+10; j++) {
                                space += " ";
                            }
                            System.out.println(space + "Une planète à été découverte ! Voulez-vous vous rendre dessus ? (oui/non)");
                        }
                        if(i!=planet3.size()-3){
                            System.out.println(planet3.get(i) + '\n');
                        }
                    }
                }
            }
        }
    }
}