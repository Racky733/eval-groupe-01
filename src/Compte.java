public abstract class Compte {
    private String numero;
    private String titulaire;
    private long solde = 0;
    private static int nombreCrees = 0;
    
    public Compte(String numero, String titulaire){
        this.numero = numero;
        this.titulaire = titulaire;
        this.solde = 0;
        nombreCrees++;
    }

    public String getNumero(){
        return numero;
    }
    public String titulaire(){
        return titulaire;
    }
    public long getSolde(){
        return solde;
    }
    
    public static int getNombreCrees() {
        return nombreCrees; 
    }

    public abstract long frais(long montant);
    public abstract String type();

    public boolean deposer(long montant){
        if (montant <= 0){
            return false;
        }else{
            solde += montant;
            return true;
        }
    }

    public boolean retirer(long montant){
        if (montant <= 0 || montant > plafond() || (montant + frais(montant)) > solde){
            return false;
        }else{
            solde = montant + frais(montant);
            return true;
        }
    }
    
    public long plafond() {
        return Parametres.PLAFOND_RETRAIT;
    }

    @Override
    public String toString() {
        return "[" + type() + "] " + numero + " " + titulaire + " " + solde + " FCFA";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) 
            return true;
        if (!(o instanceof Compte)) 
            return false;
        return numero.equals(((Compte) o).numero);
}

    @Override
    public int hashCode() {
        return numero.hashCode();
    }
}

