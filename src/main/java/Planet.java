import java.util.List;
import java.util.Random;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.io.IOException;

public class Planet {
    private String chemin;
    private Boost tempBoost;
    private Random randomInt = new Random();

    /**
     * Classe représentant une planète.
     * 
     * Cette classe représente une planète dans le jeu. Chaque planète possède un chemin vers une image, 
     * un boost temporaire et une chance aléatoire d'obtenir ce boost.
     */
    public Planet(){
        this.chemin="planets/planet"+randomInt.nextInt(4);
        int randomChance = (int) (Math.random() * 100);
        double multiplier = (Math.round(Math.random()*100.00))/100.00;
        LocalDateTime endBoost = LocalDateTime.now().plusSeconds(30);
        if(Game.player.getChanceOnPlanet()>=randomChance){
            this.tempBoost=new TempBoost("Boost Temporaire x"+(multiplier+1), (multiplier+1), 1, endBoost);
        } else {
            this.tempBoost=new TempBoost("Boost Temporaire x"+multiplier, multiplier, 1, endBoost);
        }
    }

    public String getChemin(){
        return this.chemin;
    }

    public Boost getTempBoost(){
        return this.tempBoost;
    }

    public String getTempBoostName(){
        return this.tempBoost.getName();
    }

    /**
     * Lit le contenu d'un fichier texte et retourne une liste de chaînes de caractères.
     *
     * @param path le chemin du fichier à lire
     * @return une liste de chaînes de caractères représentant les lignes du fichier
     */
    public static List<String> readFile(String path) {
        try {
			return Files.readAllLines(Paths.get("src/main/resources/ascii/"+path+".txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
    }

    /**
     * Affiche les informations de la planète.
     *
     * @return une chaîne de caractères contenant les informations de la planète
     */
    public String afficherplanet(){
        String result="";
        List<String> planet = readFile(this.getChemin());
        for (int i = 0; i < planet.size(); i += 1) {
            result+=planet.get(i)+'\n';
        }
        return result;
    }
}
