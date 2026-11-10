public class ComptePremium extends Compte {
    public ComptePremium(String numero, String titulaire) {
        super(numero, titulaire);
    }

    @Override
    public long frais(long montant) {
        if (montant <= Parametres.SEUIL_PREMIUM){
            return 0;
        }else{
            return montant * Parametres.TAUX_FRAIS / 2;
        }
    }

    @Override
    public long plafond() { 
        return 2 * super.plafond(); 
    }

    @Override
    public String type() { 
        return "PRM"; 
    }
}
