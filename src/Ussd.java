public class Ussd {
    public static boolean numeroValide(String n) {
        if (n == null || n.length() != 9) {
            return false;
        }
        if (n.charAt(0) != '7') {
            return false;
        }
        char c1 = n.charAt(1);
        if (c1 != '0' && c1 != '5' && c1 != '6' && c1 != '7' && c1 != '8') {
            return false;
        }
        for (int i = 2; i < 9; i++) {
            char c = n.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    
    public static String normaliser(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ' ' && c != '.' && c != '-') {
                sb.append(c);
            }
        }
        String propre = sb.toString();
        if (propre.startsWith("+221")) {
            propre = propre.substring(4);
        } else if (propre.startsWith("00221")) {
            propre = propre.substring(5);
        }
        if (numeroValide(propre)) {
            return propre;
        }
        return null;
    }

    
    public static String masquer(String n) {
        if (n == null) {
            return null;
        }
        if (n.length() < 4) {
            return n;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(n.substring(0, 2));
        for (int i = 2; i < n.length() - 2; i++) {
            sb.append('*');
        }
        sb.append(n.substring(n.length() - 2));
        return sb.toString();
    }

    
    public static long lireMontant(String s) {
        if (s == null || s.length() < 1 || s.length() > 9) {
            return -1L;
        }
        long val = 0L;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                return -1L;
            }
            val = val * 10 + (c - '0');
        }
        return val;
    }

    
    public static String formaterMontant(long m) {
        String s = "" + m;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && (s.length() - i) % 3 == 0) {
                sb.append(' ');
            }
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    
    public static String[] decouper(String code) {
        if (code == null || code.length() < 3) {
            return null;
        }
        if (code.charAt(0) != '*' || code.charAt(code.length() - 1) != '#') {
            return null;
        }
        String inner = code.substring(1, code.length() - 1);
        if (inner.length() == 0 || inner.charAt(0) == '*' || inner.charAt(inner.length() - 1) == '*') {
            return null;
        }
        int nbEtoiles = 0;
        for (int i = 0; i < inner.length(); i++) {
            char c = inner.charAt(i);
            if (c == '#') {
                return null;
            }
            if (c == '*') {
                if (i > 0 && inner.charAt(i - 1) == '*') {
                    return null;
                }
                nbEtoiles++;
            }
        }
        String[] champs = new String[nbEtoiles + 1];
        int start = 0;
        int idx = 0;
        for (int i = 0; i <= inner.length(); i++) {
            if (i == inner.length() || inner.charAt(i) == '*') {
                champs[idx++] = inner.substring(start, i);
                start = i + 1;
            }
        }
        return champs;
    }
    
    public static String executer(Plateforme p, String appelant, String code) {
        String[] champs = decouper(code);
        if (champs == null) {
            return "Code invalide";
        }
        if (!champs[0].equals("144")) {
            return "Service inconnu";
        }
        if (champs.length < 2) {
            return "Code invalide";
        }
        if (!champs[1].equals("1") && !champs[1].equals("2")) {
            return "Service inconnu";
        }
        if (champs[1].equals("1") && champs.length != 4) {
            return "Code invalide";
        }
        if (champs[1].equals("2") && champs.length != 2) {
            return "Code invalide";
        }
        if (p == null || appelant == null) {
            return "Compte inconnu";
        }
        Compte cAppelant = p.rechercher(appelant);
        if (cAppelant == null) {
            return "Compte inconnu";
        }

        if (champs[1].equals("2")) {
            return "Solde : " + formaterMontant(cAppelant.getSolde()) + " FCFA";
        }

        String destNormalise = normaliser(champs[2]);
        if (destNormalise == null) {
            return "Numero invalide";
        }
        long montant = lireMontant(champs[3]);
        if (montant <= 0) {
            return "Montant invalide";
        }
        long frais = cAppelant.frais(montant);
        boolean transfertOk = p.transferer(appelant, destNormalise, montant);
        if (!transfertOk) {
            return "Transfert refuse";
        }
        return "Transfert de " + formaterMontant(montant) + " FCFA vers " + masquer(destNormalise) + " effectue. Frais : " + formaterMontant(frais) + " FCFA";
    }
}