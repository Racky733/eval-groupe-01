/**
 * Paramètres de l'équipe. Remplissez GROUPE et MEMBRES au checkpoint 0,
 * puis ne modifiez plus rien : les autres valeurs sont calculées à partir de GROUPE.
 */
public final class Parametres {
    public static final int GROUPE = 01;                                   // numéro donné par l'enseignant
    public static final String[] MEMBRES = {
        "A :Rokhaya Diouf (ISEPAT202603002) ",
        "B :Mouhamed Marone (ISEPAT47468202619)",
        "C :Mouhamed Top (ISEPAT47489202619)"
    };

    public static final int TAUX_FRAIS = 10 + GROUPE % 6;                 // pour mille (10 = 1 %)
    public static final long PLAFOND_RETRAIT = 150_000 + 10_000 * (GROUPE % 8);   // FCFA
    public static final long SEUIL_PREMIUM = 50_000;                      // FCFA
    public static final int CAPACITE = 20;                                // comptes

    private Parametres() { }
}
