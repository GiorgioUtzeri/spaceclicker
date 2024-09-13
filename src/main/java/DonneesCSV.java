import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DonneesCSV {
    /**
     * Charge un objet PlayerCheat à partir d'un emplacement spécifié.
     *
     * @param location l'emplacement du fichier à charger
     * @return l'objet PlayerCheat chargé à partir du fichier
     * @throws Exception si une erreur se produit lors de la lecture du fichier
     */
    public static PlayerCheat load(String location) throws Exception {
        try(ObjectInputStream ois = new ObjectInputStream( new FileInputStream(new File(location)))) {
            PlayerCheat player = (PlayerCheat)ois.readObject();
            return player;
        }catch(Exception e) {e.printStackTrace();}
        return null;
    }


    /**
     * Sauvegarde les données d'un joueur dans un fichier.
     * 
     * @param player le joueur dont les données doivent être sauvegardées
     * @param location l'emplacement du fichier de sauvegarde
     * @throws Exception si une erreur se produit lors de la sauvegarde
     */
    public static void save(PlayerCheat player, String location) throws  Exception {
        try(ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream(new File(location)))) {
            oos.writeObject(player);
        } catch(Exception e) {e.printStackTrace();}
    
    }


}
