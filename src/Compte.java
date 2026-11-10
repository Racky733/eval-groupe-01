public abstract class Compte {
    private String numero;
    private String titulaire;
    private long solde = 0;
    
    public Compte(String numero, String titulaire){
        this.numero = numero;
        this.titulaire = titulaire;
        this.solde = 0;
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

    public abstract long frais(long montant);
    public abstract String type();  
}

