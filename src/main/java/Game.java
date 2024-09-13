import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Game {
    static PlayerCheat player;
    static int state = 10; //0:game 1:shop 2:achievements 8:win screen 9:game over 10:title screen
    static String sub = "                                        ";
    static boolean shipAnim = true;

    /**
     * Méthode qui affiche l'écran de fin de partie (lorsqu'on a perdu).
     * Cette méthode lit le contenu du fichier "game over" et l'affiche à l'écran.
     * Elle affiche également le score et le nombre de pièces du joueur.
     * 
     * @throws FileNotFoundException si le fichier "game over" n'est pas trouvé.
     * @throws Exception si une erreur se produit lors de la lecture du fichier.
     */
    public static void gameOver() throws FileNotFoundException, Exception{
        List<String> gameOver = readFile("game over");
        for (int i = 0; i < 23; i += 1) {
            System.out.print(gameOver.get(i));
        }
        System.out.print(formatScoreCoins(player.getScore()));
        System.out.print(gameOver.get(23));
        System.out.print(gameOver.get(24));
        System.out.print(formatScoreCoins(player.getCoins()));
        for (int i = 25; i < 40; i += 1) {
            System.out.print(gameOver.get(i));
        }
    }

    /**
     * Méthode qui affiche l'écran de fin de partie (lorsqu'on a gagné).
     * Cette méthode lit le contenu du fichier "win" et l'affiche à l'écran.
     * 
     * @throws FileNotFoundException si le fichier "win" n'est pas trouvé.
     * @throws Exception si une erreur se produit lors de la lecture du fichier.
     */
    public static void win() throws FileNotFoundException, Exception{
        List<String> win = readFile("win");
        for (int i = 0; i < 38; i += 1) {
            System.out.print(win.get(i));
        }
    }

    /**
     * Génère des événements aléatoires pour le jeu.
     * Les événements peuvent inclure la découverte d'un bonus de carburant, d'un bonus de pièces, un jackpot ou une perte de carburant pour le vaisseau du joueur.
     */
    public static void randomEvents() {
        sub = "                                        ";
        int fuelBonus = (int) (Math.random() * 10) + 1;
        int random = (int) (Math.random() * 1000);
        if (random<=6) {
            sub = "Vous avez trouvé un bonus de carburant !";
            player.addFuel(fuelBonus);
        } else if (random>6  && random<=12) {
            sub = " Vous avez trouvé un bonus de pièces !  ";
            player.addCoins(50);
        } else if (random>12 && random<=14) {
            sub = "               JACKPOT !                ";
            player.addFuel(player.getMaxFuel()/2);
            player.addCoins(100);
        } else if (random > 14 && random <=16) {
            sub = " Votre vaisseau a perdu du carburant... ";
            player.setCurrentFuel(player.getCurrentFuel()/2);
        }
    }


    /**
     * Méthode qui gère le clic de l'utilisateur dans le jeu.
     * 
     * @param scanner Le scanner utilisé pour lire l'entrée de l'utilisateur.
     * @param player Le joueur actuel.
     * @throws NullPointerException Si une exception de type NullPointerException est levée.
     * @throws NumberFormatException Si une exception de type NumberFormatException est levée.
     * @throws EntryException Si l'état actuel est invalide.
     * @throws Exception Si une exception non prévue est levée.
     */
    public static void Clic(Scanner scanner, Player player) throws NullPointerException, NumberFormatException, EntryException, Exception {
        
        String input = "";
        try{
            drawTitle();
        }
        catch(NullPointerException npe){System.err.println(npe);}
        catch(Exception e){System.err.println("Exception non prévu.");}
        boolean end = false;
        while (scanner.hasNextLine() && !end) {
            input = scanner.nextLine().toLowerCase();
            switch (state) {
                case 0:
                    if (input.equals("")) {
                        randomEvents();
                        player.addScore();
                        player.decreaseFuel();
                        player.randomPlanet(input, scanner);
                        try{
                            drawGame();
                        }
                        catch(NullPointerException npe){System.err.println(npe);}
                        catch(Exception e){System.err.println("Exception non prévu.");}
                    } else if (input.equals("shop")) {
                        state = 1;
                        try{
                            drawShop();
                        }
                        catch(NullPointerException npe){System.err.println(npe);}
                        catch(Exception e){System.err.println("Exception non prévu.");}
                    } else if (input.equals("succes")) {
                        state = 2;
                        drawAchievements();
                    } else if (input.equals("prestige") && player instanceof PlayerCheat) {
                        player.prestige();
                        try{
                            drawGame();
                        }
                        catch(NullPointerException npe){System.err.println(npe);}
                        catch(Exception e){System.err.println("Exception non prévu.");}
                    } else if (input.equals("save")) {
                        try{
                            DonneesCSV.save( (PlayerCheat)player, "src/main/resources/"+"PlayerSave"+".csv");
                        }
                        catch(NullPointerException npe){System.err.println(npe);}
                        catch(Exception e){System.err.println("Exception non prévu.");}
                        drawGame();
                    }else if (input.equals("restart")) {
                        player.restart();
                        try{
                            DonneesCSV.save( (PlayerCheat)player, "src/main/resources/"+"PlayerSave"+".csv");
                        }
                        catch(NullPointerException npe){System.err.println(npe);}
                        catch(Exception e){System.err.println("Exception non prévu.");}
                        drawGame();
                    } else if (input.equals("help")) {
                        state = 3;
                        drawHelp();
                    } 
                    else if (input.equals("exit")) {
                        //state = 4;
                        end = true;
                    } else if (input.equals("no fuel") && player instanceof PlayerCheat playerCheat) {
                        playerCheat.noFuel();
                    } else if (input.equals("motherload") && player instanceof PlayerCheat playerCheat) {
                        playerCheat.maxCoins();
                        drawGame();
                    } else if (input.equals("win") && player instanceof PlayerCheat playerCheat) {
                        playerCheat.maxPoints();
                        drawGame();
                    } else {
                        try{
                            drawGame();
                        }
                        catch(NullPointerException npe){System.err.println(npe);}
                        catch(Exception e){System.err.println("Exception non prévu.");}
                    }
                    break;
                case 1:
                    if (input.equals("")) {
                        try{
                            drawShop();
                        }
                        catch(NullPointerException npe){System.err.println(npe);}
                        catch(Exception e){System.err.println("Exception non prévu.");}
                    } else if (input.equals("exit")) {
                        state = 0;
                        try{
                            drawGame();
                        }
                        catch(NullPointerException npe){System.err.println(npe);}
                        catch(Exception e){System.err.println("Exception non prévu.");}
                    } else if (input.equals("a")) {
                        try{
                            drawShop();
                        }
                        catch(NullPointerException npe){System.err.println(npe);}
                        catch(Exception e){System.err.println("Exception non prévu.");}
                        System.out.println(player.getShipLevel()+" "+player.getRadarLevel()+" "+player.getSuitLevel());
                    } else if (ShopData.isAnUpgrade(input)) {
                        try{
                            if (ShopData.canBuy(input)) {
                                ShopData.buy(input);
                            }
                            drawShop();
                        }
                        catch(NullPointerException npe){System.err.println(npe);}
                        catch(NumberFormatException nfe){System.err.println(nfe);}
                        catch(Exception e){System.err.println("Exception non prévu.");}
                    } else {
                        try{
                            drawShop();
                        }
                        catch(NullPointerException npe){System.err.println(npe);}
                        catch(Exception e){System.err.println("Exception non prévu.");}
                    }
                    break;
                case 2:
                    if (input.equals("")) {
                        drawAchievements();
                    } else if (input.equals("exit")) {
                        state = 0;
                        try{
                            drawGame();
                        }
                        catch(NullPointerException npe){System.err.println(npe);}
                        catch(Exception e){System.err.println("Exception non prévu.");}
                    } else {
                        drawAchievements();
                    }
                    break;
                case 3:
                    if (input.equals("")) {
                        drawHelp();
                    } else if (input.equals("exit")) {
                        state = 0;
                        try{
                            drawGame();
                        }
                        catch(NullPointerException npe){System.err.println(npe);}
                        catch(Exception e){System.err.println("Exception non prévu.");}
                    } else {
                        drawHelp();
                    }
                    break;
                case 8:
                    if (input.equals("")) {
                        win();
                    } else if (input.equals("exit")) {
                        end = true;
                    } else if (input.equals("prestige")) {
                        player.prestige();
                        state = 0;
                        try{
                            drawGame();
                        }
                        catch(NullPointerException npe){System.err.println(npe);}
                        catch(Exception e){System.err.println("Exception non prévu.");}
                    } else {
                        win();
                    }
                    break;
                case 9:
                    gameOver();
                    if (input.equals("exit")) {
                        end = true;
                    }
                    break;
                case 10:
                    state = 0;
                    try{
                        drawGame();
                    }
                    catch(NullPointerException npe){System.err.println(npe);}
                    catch(Exception e){System.err.println("Exception non prévu.");}
                    break;
                default:
                    throw new EntryException("Invalid state: " + state);
            }
            if(player.getCurrentFuel()==0 && state != 9){
                //end=true;
                state = 9;
                gameOver();
            }
            if(player.getScore() >= 30000 && state != 8){
                //end=true;
                state = 8;
                win();
            }
        }
        for (int i = 0; i < 50; i += 1) {
            System.out.println();
        }
    }

    /**
     * Méthode privée qui affiche l'aide du jeu.
     * 
     * @throws FileNotFoundException si le fichier d'aide n'est pas trouvé.
     * @throws Exception si une erreur se produit lors de la lecture du fichier d'aide.
     */
    private static void drawHelp() throws FileNotFoundException, Exception {
        List<String> help = readFile("help");
        for (int i = 0; i < 38; i += 1) {
            System.out.print(help.get(i));
        }
    }

    /**
     * Lit le contenu d'un fichier texte.
     *
     * @param path le chemin du fichier à lire
     * @return une liste de chaînes de caractères représentant les lignes du fichier
     * @throws FileNotFoundException si le fichier spécifié n'est pas trouvé
     * @throws Exception si une erreur se produit lors de la lecture du fichier
     */
    public static List<String> readFile(String path) throws FileNotFoundException, Exception {
        try {
			return Files.readAllLines(Paths.get("src/main/resources/ascii/"+path+".txt"));
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
        return null;
    }

    /**
     * Méthode pour afficher le jeu.
     * 
     * @throws NullPointerException si une référence d'objet est nulle.
     * @throws Exception si une exception non prévue se produit.
     */
    public static void drawGame() throws NullPointerException, Exception {
        List<String> gameBG;
        List<String> ship;
        if (shipAnim) {
            ship = readFile("fusées/"+player.getShipLevel());
            gameBG = readFile("game1");
        } else {
            ship = readFile("fusées2/"+player.getShipLevel());
            gameBG = readFile("game2");
        }
        shipAnim = !shipAnim;
        for (int i = 0; i < 4; i += 1) {
            System.out.print(gameBG.get(i));
        }
        try{
            System.out.print(formatScoreCoins(player.getScore()));
            System.out.print(gameBG.get(4));
            System.out.print(gameBG.get(5));
            System.out.print(formatScoreCoins(player.getCoins()));
        }
        catch(NumberFormatException nfe){System.err.println(nfe);}
        catch(Exception e){System.err.println("Exception non prévu.");}
        for (int i = 6; i < 11; i += 1) {
            System.out.print(gameBG.get(i));
        }
        int bgLine = 11;
        int shipLine = 0;
        for (int i = 0; i < 19; i += 1) {
            System.out.print(gameBG.get(bgLine));
            bgLine += 1;
            System.out.print(ship.get(shipLine));
            shipLine += 1;
            System.out.print(gameBG.get(bgLine));
            bgLine += 1;
        }
        for (int i = 49; i < 52; i += 1) {
            System.out.print(gameBG.get(i));
        }
        System.out.print(sub);
        for (int i = 52; i < 56; i += 1) {
            System.out.print(gameBG.get(i));
        }
        try{
            System.out.print(getFuelBar());
            System.out.print(gameBG.get(56));
            System.out.print(gameBG.get(57));
            System.out.print(getFuelBar());
        }
        catch(NullPointerException npe){System.err.println(npe);}
        catch(NumberFormatException nfe){System.err.println(nfe);}
        catch(Exception e){System.err.println("Exception non prévu.");}
        for (int i = 58; i < 62; i += 1) {
            System.out.print(gameBG.get(i));
        }
    }

    /**
     * Méthode pour afficher le titre du jeu.
     * 
     * @throws NullPointerException si le fichier "title" est introuvable.
     * @throws Exception si une erreur se produit lors de la lecture du fichier.
     */
    public static void drawTitle() throws NullPointerException, Exception{
        List<String> title = readFile("title");
        for (int i = 0; i < 38; i += 1) {
            System.out.print(title.get(i));
        }
    }

    /**
     * Formate le score ou le nombre de pièces en une chaîne de caractères.
     *
     * @param scoreOrCoins Le score ou le nombre de pièces à formater.
     * @return La chaîne de caractères formatée.
     * @throws NumberFormatException Si le score ou le nombre de pièces n'est pas un nombre valide.
     * @throws Exception Si une erreur inattendue se produit.
     */
    public static String formatScoreCoins(int scoreOrCoins) throws NumberFormatException, Exception {
        String res = "" + scoreOrCoins;
        if (scoreOrCoins < 10) {
            res = "    " + res;
        } else if (scoreOrCoins < 100) {
            res = "   " + res;
        } else if (scoreOrCoins < 1000) {
            res = "  " + res;
        } else if (scoreOrCoins < 10000) {
            res = " " + res;
        }
        return res;
    }

    /**
     * Retourne une chaîne de caractères représentant la barre de carburant du joueur.
     * 
     * @return La barre de carburant du joueur sous forme de chaîne de caractères.
     * @throws NullPointerException si player est null.
     * @throws NumberFormatException si player.getCurrentFuel() ou player.getMaxFuel() ne peuvent pas être convertis en double.
     * @throws Exception si une exception non spécifiée se produit.
     */
    public static String getFuelBar() throws NullPointerException, NumberFormatException, Exception{
        int symbolNumber = (int) Math.round((double) ((double) player.getCurrentFuel() / (double) player.getMaxFuel() * 186));
        String fuelBar = "";
        for (int i = 0; i < 186; i += 1) {
            if (i > symbolNumber) {
                fuelBar += " ";
            } else {
                fuelBar += "#";
            }
        }
        return fuelBar;
    }

    /**
     * Affiche le contenu du magasin.
     *
     * @throws NullPointerException si le fichier "shop" est introuvable.
     * @throws Exception si une erreur se produit lors de la lecture du fichier "shop".
     */
    public static void drawShop() throws NullPointerException, Exception{
        List<String> shop = readFile("shop");
        System.out.print(shop.get(0));
        System.out.print(shop.get(1));
        System.out.print(formatScoreCoins(player.getCoins()));
        for (int i = 2; i < 30; i += 1) {
            System.out.print(shop.get(i));
        }
        System.out.print(player.getShipLevelFormat());
        System.out.print(shop.get(30));
        System.out.print(player.getRadarLevelFormat());
        System.out.print(shop.get(31));
        System.out.print(player.getSuitLevelFormat());
        System.out.print(shop.get(32));
        System.out.print(shop.get(33));
        System.out.print(ShopData.getCostFormat("vaisseau",player.getShipLevel()));
        System.out.print(shop.get(34));
        System.out.print(ShopData.getCostFormat("radar",player.getRadarLevel()));
        System.out.print(shop.get(35));
        System.out.print(ShopData.getCostFormat("combi",player.getSuitLevel()));
        System.out.print(shop.get(36));
        System.out.print(ShopData.getCostFormat("carburant",player.getShipLevel()));
        for (int i = 37; i < 46; i += 1) {
            System.out.print(shop.get(i));
        }
    }

    public static void main(String[] args) {

        try{

            File f = new File("src/main/resources/"+"PlayerSave"+".csv");
            if(f.exists()) { 
                player = DonneesCSV.load("src/main/resources/"+"PlayerSave"+".csv");
            }else{
                player = new PlayerCheat("1");
            }

            if(player == null){
                player = new PlayerCheat("1");
            }
        }
        catch(Exception e){System.err.println(e);}


        Scanner scanner = new Scanner(System.in);
        

        try{
            Clic(scanner, player);
        }
        catch(NullPointerException npe){System.err.println(npe);}
        catch(NumberFormatException nfe){System.err.println(nfe);}
        catch(EntryException ee){System.err.println(ee);}
        catch(Exception e){System.err.println("Exception non prévu.");}
        finally{
            scanner.close();
        }
    }


    public static void drawAchievements() throws FileNotFoundException, Exception{
        List<String> ach = readFile("achievements");
        for (int i = 0; i < 12; i += 1) {
            System.out.print(ach.get(i));
        }
        int achLine = 12;
        for(Achievement a : player.getAchievements()){
            System.out.print(ach.get(achLine));
            achLine += 1;
            System.out.print(a);
            System.out.print(ach.get(achLine));
            achLine += 1;
            System.out.print(ach.get(achLine));
            achLine += 1;
        }
        for (int i = 42; i < 48; i += 1) {
            System.out.print(ach.get(i));
        }
    }
}