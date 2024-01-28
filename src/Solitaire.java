
public class Solitaire {

    /*rappelle les différentes regles du jeu.*/
    public static void début() {
        System.out.println("Bienvenue dans le jeu du solitaire !");
        System.out.println("But du jeu : réaliser 4 piles de 7 cartes chacune en ordre croissant donc de l'as au roi et par famille de couleur.");
        System.out.println("Règle du jeu : Les 4 piles doivent être faites avec les 13 cartes restantes qui sont réparties en 7 colonnes avec une carte retournée face visible.");
        System.out.println("A chaque tour, vous pouvez déplacer une ou plusieurs cartes en prenant en compte la couleur et la valeur des cartes.");
        System.out.println("Vous pouvez également déplacer des colonnes entières si elles sont bine formées et si elles respectent les règles du jeu.");
        System.out.println("Une fois que toutes les piles sont complétées avec leurs as, 2, 3, 4...jusqu'au roi, le jeu est gagné !");
    }

    /*permet à l'utilisateur de personnaliser ou non son nom de joueur.*/
    public static void utilisateur() {
        System.out.println("Voulez-vous personnaliser votre nom : [o/n] ?");
        char choix = Console.lireChar();
        if (choix == 'n') {
            System.out.println("Bienvenue utilisateur");
        } else {
            System.out.println("Quel est votre nom ?");
            String nom = Console.lireString();
            System.out.println("Bienvenue " + nom);
        }
    }

    /*permet d'afficher les cartes d'un tableau donne en parametres en completant avec des espaces jusqu'a 20 caracteres
Les chaînes de caractères des cartes ont donc toutes la même taille.*/
    public static void afficherTableauString(String[] tableau) {
        for (int i = 0; i < tableau.length; i++) {
            System.out.println(affichageCarte(tableau[i], 20));
        }
    }

    /*permet d'afficher les différentes cartes d'un tableau à deux dimensions donné en paramètres. 
La fonction s'assure qu'on ne dépasse pas le tableau et affiche des cartes avec une taille donnée pour
respecter l'alignement des colonnes ou affiche des espaces en cas de donnée "null".*/

    public static void afficherTableauStringDouble(String[][] tableau) {
        for (int i = 0; i != tableau.length; i++) {
            for (int j = 0; j != tableau[i].length; j++) {
                if (j >= tableau.length || i >= tableau[j].length) {
                    continue;
                }
                if (tableau[j][i] == null) {
                    if (j < 7) {
                        System.out.print("|" + affichageCarte(null, 16));
                    }
                } else {
                    System.out.print("|" + affichageCarte(tableau[j][i], 16));
                }
            }
            System.out.println("");
        }
        System.out.println("_____________________________________________________________________________________________________________________________________________");
        System.out.println("_____________________________________________________________________________________________________________________________________________");
    }

    /*met à "null" la derniere carte de la pioche quand le joueur decide de piocher. La carte
n'est donc plus dans la pioche.*/
    public static String[] reduireTaillePiocheCartes(String[] piocheCartes) {
        //reduit la taille du tableau en mettant null les éléments à supprimer
        int reduitPioche = tailleSansNull(piocheCartes);
        if (reduitPioche == 0) {
            return null;
        }
        piocheCartes[reduitPioche - 1] = null;

        return piocheCartes;
    }

    /*met à "null" les dernieres cartes du tableau specifiee par le nombre et la colonne. Cette fonction
est utilise lors du deplacement des cartes.*/
    public static String[][] enleverCartes(String[][] tableau, int nombre, int colonne) {
        //enleve les elements en les remplaçant par null
        for (int i = tailleSansNull(tableau[colonne]) - nombre; i < 13; i++) {
            tableau[colonne][i] = null;
        }

        return tableau;
    }

    /*permet de copier les cartes d'une colonne depart vers une colonne d'arrivee.*/
    public static String[][] mettreCartes(String[][] tableau, int nombre, int colonneDepart, int colonneArriveeCartes) {

        int ligneDepart = tailleSansNull(tableau[colonneDepart]) - (nombre);
        int colonneArrivee = tailleSansNull(tableau[colonneArriveeCartes]);

        for (int i = 0; i < nombre; i++) {
            tableau[colonneArriveeCartes][colonneArrivee + i] = tableau[colonneDepart][ligneDepart + i];

        }

        return tableau;
    }

    /*genere un nombre entier aleatoire permettant de melanger les cartes.*/
    public static int genererUnEntier(int max) {
        int min = 0;
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    /*permet de melanger le paquet de cartes. Cette fonction permute deux cartes differentes à chaque tour
500 fois permettant un mélange different à chaque partie.*/
    public static String[] melangerCartesAleatoire(String[] cartes) {
        String[] cartesAleatoire = new String[26];
        String echangerCartes;

        for (int i = 0; i <= 500; i++) {
            int cartesAleatoire1 = genererUnEntier(cartes.length - 1);
            int cartesAleatoire2 = genererUnEntier(cartes.length - 1);
            echangerCartes = cartes[cartesAleatoire1];
            cartes[cartesAleatoire1] = cartes[cartesAleatoire2];
            cartes[cartesAleatoire2] = echangerCartes;

        }
        for (int j = 0; j < cartesAleatoire.length; j++) { //mettre cartes dans cartesAleatoire
            cartesAleatoire[j] = cartes[j];
        }
        return cartesAleatoire;
    }

    /*permet de mettre dans cartesPossible une carte rouge et une carte noire à chaque tour de boucle.*/
    public static String[] genererCartesAleatoire(String[] cartesRouges, String[] cartesNoirs) {
        String[] cartesPossible = new String[52];
        for (int i = 0; i < 26; i++) {
            cartesPossible[tailleSansNull(cartesPossible)] = cartesRouges[i];
            cartesPossible[tailleSansNull(cartesPossible)] = cartesNoirs[i];
        }
        return cartesPossible;
    }

    /*creer le tableau pioche contenant differentes cartes. La fonction part de la 28e position puisque les 24 premieres 
sont dans le tableau de jeu.*/
    public static String[] piocheTableau(String[] cartesAleatoire) {
        String[] piocheTableau = new String[24];
        int j = 28;
        for (int i = 0; i < piocheTableau.length; i++) {
            piocheTableau[i] = cartesAleatoire[j];
            j++;
        }
        return piocheTableau;
    }


    /*creer le tableau de jeu. Utilise les cartes melangees de cartesAleatoire et les met dans 
les colonnes du tableau de jeu en mettant un nombre decroissant de cartes par colonne.*/
    public static String[][] tableauJeu(String[] cartesAleatoire) {

        int[] taille = {7, 6, 5, 4, 3, 2, 1};

        String[][] tableColonne = new String[13][13];

        int k = 0;
        for (int i = 0; i < taille.length; i++) {

            for (int j = 0; j < taille[i]; j++) {
                tableColonne[i][j] = cartesAleatoire[k];

                k++;
            }
        }
        return tableColonne;
    }


    /*permet de quitter le jeu*/
    public static void quitter() {

        System.out.println("Merci d'avoir joué au Solitaire !");
        System.exit(0);

    }

    /*permet de verifier si une premiere carte est à la suite d'une deuxieme carte selon leur valeur*/
    public static boolean verificationSuiteCartes(String[][] tableau, int colonneArrivee, int nombreCartes, int colonne) {

        int longueurColonneSansNull = tailleSansNull(tableau[colonneArrivee]);

        String derniereCarteColonne = tableau[colonneArrivee][longueurColonneSansNull - 1];

        int emplacementCarteColonne = tailleSansNull(tableau[colonne]) - (nombreCartes);
        if (emplacementCarteColonne < 0) {
            return false;
        }
        String premiereCarteColonne = tableau[colonne][emplacementCarteColonne];

        String derniereCarteCouleur = comparaisonCouleurCarte(typeCarte(derniereCarteColonne));

        String premiereCarteCouleur = comparaisonCouleurCarte(typeCarte(premiereCarteColonne));
        String deplacerRoi = premiereCarteColonne;

        boolean verificationRoi = verificationColonnePossible(stringVersInt(valeurCarte(deplacerRoi)), tableau, colonne);

        return comparaisonSuiteCartes(derniereCarteColonne, premiereCarteColonne) == true && comparaisonSuiteCartesCouleur(derniereCarteCouleur, premiereCarteCouleur) == true || verificationRoi;

    }

    /*permet de verifier si une carte a pour valeur Roi et que la colonne de placement est vide*/
    public static boolean verificationRoi(String placeCarte, String[][] tableau, int colonne) {
        return verificationColonnePossible(stringVersInt(valeurCarte(placeCarte)), tableau, colonne);
    }

    /*invite le joueur à deplacer une carte de la pioche à une colonne du tableau de jeu et appelle les methodes 
    de verification de placement*/
    public static boolean placerCartes(String[][] tableau, String placeCarte) {

        try {
            System.out.println("De quelle colonne voulez-vous déplacer cette carte ?");
            int colonne = Console.lireInt();
            int tailleColonne = tailleSansNull(tableau[colonne - 1]);
            if (tailleColonne == 0) {
                if (verificationRoi(placeCarte, tableau, colonne) == true) {
                    tableau[colonne - 1][tailleColonne] = placeCarte;
                }
            } else if (comparaisonSuiteCartes(tableau[colonne - 1][tailleColonne - 1], placeCarte) == true && comparaisonSuiteCartesCouleur(tableau[colonne - 1][tailleColonne - 1], placeCarte) == true) {
                tableau[colonne - 1][tailleColonne] = placeCarte;
            } else {
                System.out.println("Vous ne pouvez pas placer la carte car elle ne respecte pas les règles du Solitaire.");
                System.out.println();
                return false;
            }

        } catch (ArrayIndexOutOfBoundsException Error) {
            System.out.println("Vous ne pouvez pas placer cette carte ici, cette colonne est complète...");
            System.out.println();
            return false;
        }
        return true;
    }

    /*cette fonction permet au joueur de deplacer une carte d’une colonne à une autre et appelle les methodes de verification de deplacement.*/
    public static void deplacerCartes(String[][] tableau) {
        try {
            System.out.println("Dans quelle colonne prendre les cartes à déplacer ? (1, 2, 3, 4, 5, 6, 7)");
            int colonne = Console.lireInt() - 1;
            if (colonne > 6) {
                System.out.println("Cette colonne n'existe pas");
                return;
            }

            System.out.println("Donnez le nombre de cartes à déplacer : ");
            int nombreCartes = Console.lireInt();

            System.out.println("Dans quelle colonne voulez-vous déplacer les cartes demandées ? (1, 2, 3, 4, 5, 6, 7)");
            int colonneArrivee = Console.lireInt() - 1;

            if (verificationSuiteCartes(tableau, colonneArrivee, nombreCartes, colonne) == true) {
                mettreCartes(tableau, nombreCartes, colonne, colonneArrivee);
                enleverCartes(tableau, nombreCartes, colonne);
            } else {
                System.out.println("Vous ne pouvez pas placer la/les carte(s) car elle(s) ne respectent pas les règles du Solitaire.");
                System.out.println();
            }

        } catch (ArrayIndexOutOfBoundsException Error) {
            System.out.println("Vous ne pouvez pas placer ces/cette carte(s) ici, cette colonne est complète...");
            System.out.println();
        }

    }

    /*compare une carte a sa famille en partant de la fin : exemple : "3 trefles" avec "trefles".*/
    public static boolean comparaisonCarte(String carte, String type) {

        for (int i = 0; i < type.length(); i++) {
            if (carte.charAt(carte.length() - 1 - i) != type.charAt(type.length() - 1 - i)) {
                return false;
            }

        }
        return true;
    }

    /*permet d'avoir la valeur de la carte soit dans "As pique" d'avoir seulement "As".*/
    public static String valeurCarte(String carte) {
        String valeur = "";
        for (int i = 0; i < carte.length() - 1; i++) {
            if (carte.charAt(i) != ' ') {
                valeur = valeur + carte.charAt(i);
            } else {
                break;
            }
        }
        return valeur;
    }

    /*permet d'avoir le type de la carte soit dans "As pique" d'avoir seulement "pique".*/
    public static String typeCarte(String carte) {
        String typeInverse = "";
        String type = "";
        for (int i = carte.length() - 1; i >= 0; i--) {
            if (carte.charAt(i) != ' ') {
                typeInverse = typeInverse + carte.charAt(i);
            } else {
                break;
            }
        }
        for (int i = typeInverse.length() - 1; i >= 0; i--) {
            type = type + typeInverse.charAt(i);
        }
        return type;
    }

    /*change les cartes de type chaines de caractères en type int.*/
    public static int stringVersInt(String carte) {
        switch (carte) {
            case "As" -> {
                return 1;
            }
            case "2" -> {
                return 2;
            }
            case "3" -> {
                return 3;
            }
            case "4" -> {
                return 4;
            }
            case "5" -> {
                return 5;
            }
            case "6" -> {
                return 6;
            }
            case "7" -> {
                return 7;
            }
            case "8" -> {
                return 8;
            }
            case "9" -> {
                return 9;
            }
            case "10" -> {
                return 10;
            }
            case "Valet" -> {
                return 11;
            }
            case "Dame" -> {
                return 12;
            }
            case "Roi" -> {
                return 13;
            }
        }
        return 0;
    }

    /*retourne la couleur de la carte selon sa famille.*/
    public static String comparaisonCouleurCarte(String carte) {
        switch (carte) {
            case "carreaux" -> {
                return "rouge";
            }
            case "coeur" -> {
                return "rouge";
            }
            case "trefles" -> {
                return "noir";
            }
            case "pique" -> {
                return "noir";
            }
        }
        return "";
    }

    /*compare si deux couleurs sont differentes ou non.*/
    public static boolean comparaisonSuiteCartesCouleur(String couleur1, String couleur2) {
        return !typeCarte(couleur1).equals(typeCarte(couleur2));
    }

    /*compare si deux cartes se suivent : par exemple 11 est suivi de 12.*/
    public static boolean comparaisonSuiteCartes(String carte1, String carte2) {
        return stringVersInt(valeurCarte(carte1)) - 1 == stringVersInt(valeurCarte(carte2));
    }

    /*verifie si il est possible de mettre une carte dans une colonne vide. Si la carte a pour valeur 
    roi alors la carte peut être posee sinon la carte ne peut pas être posee.*/
    public static boolean verificationColonnePossible(int carteRoi, String[][] tableau, int colonne) {
        if (carteRoi != 13) {
            return false;
        }
        return carteRoi == 13 && tableau[colonne][0] == null || carteRoi != 13 && tableau[colonne][0] != null;
    }


    /*permet l'alignement des colonnes en ajoutant des espaces au nom de la carte.*/
    public static String affichageCarte(String carte, int taille) {
        if (carte == null) {
            carte = "";
        }

        int longueurCarte = taille - carte.length();
        for (int j = 0; j < longueurCarte; j++) {
            carte = carte + " ";

        }
        return carte;
    }

    /*retourne la longueur d'un tableau sans les donnees "null".*/
    public static int tailleSansNull(String[] tableau) {
        //permet de connaître la taille d'une colonne en éliminant les null présents
        int positionNull = 0;

        for (int i = 0; i < tableau.length; i++) {
            if (tableau[i] == null) {
                positionNull = i;
                break;
            }
            if (tableau[tableau.length - 1] != null) {
                positionNull = tableau.length;
            }
        }
        return positionNull;
    }

    /*permet de replacer une carte non utilisee de la pioche a la fin de la pioche.*/
    public static void mettreCarteDansPioche(String placeCarte, String[] piocheCartes) {
        piocheCartes[tailleSansNull(piocheCartes)] = placeCarte;
    }

    /*retourne la derniere carte de la pioche.*/
    public static String derniereCartesPioche(String[] piocheCartes) {
        if (tailleSansNull(piocheCartes) == 0) {
            return "";
        }
        String pioche = piocheCartes[0];
        for (int i = 1; i < 24; i++) {
            piocheCartes[i - 1] = piocheCartes[i];
        }
        piocheCartes[23] = null;
        return pioche;

    }

    /*verifie que la colonne est complete, met le roi dans le tableauResultat, vide la colonne.*/
    public static void completer(String[][] tableau, String[] tableauResultat) {
        System.out.println("Donnez la colonne complète : ");
        int colonne = Console.lireInt();
        int verificationCarte = stringVersInt(valeurCarte(tableau[colonne - 1][0]));
        if (verificationCarte != 13 || tailleSansNull(tableau[colonne - 1]) != 13) {
            System.out.println("Votre colonne n'est pas complète...");
            return;
        }
        switch (tableau[colonne - 1][0]) {
            case "Roi pique":
                tableauResultat[0] = "Roi pique";
                break;
            case "Roi trefles":
                tableauResultat[1] = "Roi trefles";
                break;
            case "Roi carreaux":
                tableauResultat[2] = "Roi carreaux";
                break;
            case "Roi coeur":
                tableauResultat[3] = "Roi coeur";
                break;
        }
        viderColonne(tableau, colonne);
    }

    /*permet de vider la colonne souhaitee du tableau.*/
    public static void viderColonne(String[][] tableau, int colonne) {
        for (int i = 0; i < tableau[colonne - 1].length; i++) {
            tableau[colonne - 1][i] = null;
        }
    }

    /*verifie si la partie est terminee, si toutes les colonnes comportant toutes les cartes de Roi a As sont completes.*/
    public static void partieTerminee(String[] tableauResultat) {
        int verificationPartieTerminee = 0;
        for (int i = 0; i < 4; i++) {
            if (tableauResultat[i] != null) {
                verificationPartieTerminee++;
            }
        }
        if (verificationPartieTerminee == 4) {
            System.out.println("Bravo, vous avez gagné !");
            quitter();
        }
    }

    /*affiche le tableau de resultat.*/
    public static void afficherTableauResultat(String[] tableauResultat) {
        for (int i = 0; i < tableauResultat.length; i++) {
            System.out.print(affichageCarte(tableauResultat[i], 20));
        }
        System.out.println();
    }

    /*invite le joueur à faire une action.*/
    public static int commandes() {
        System.out.println("1 - Piocher");
        System.out.println("2 - Placer la carte prise dans la pioche");
        System.out.println("3 - Déplacer");
        System.out.println("4 - Compléter");
        System.out.println("0 - Quitter");

        int commande = Console.lireInt();

        return commande;
    }

    /*genere un jeu de cartes melange, genere un tableau grace au jeu melange, genere la pioche, rentre dans la boucle qui : 
affiche le resultat du tableau et demande à l'utilisateur de faire une action.*/
    public static void main(String[] args) {
        début();
        utilisateur();

        String[] cartesNoirs = {"As pique", "2 pique", "3 pique", "4 pique", "5 pique", "6 pique",
            "7 pique", "8 pique", "9 pique", "10 pique", "Valet pique", "Dame pique", "Roi pique",
            "As trefles", "2 trefles", "3 trefles", "4 trefles", "5 trefles", "6 trefles", "7 trefles",
            "8 trefles", "9 trefles", "10 trefles", "Valet trefles", "Dame trefles", "Roi trefles"};

        String[] cartesRouges = {"As carreaux", "2 carreaux", "3 carreaux", "4 carreaux", "5 carreaux", "6 carreaux",
            "7 carreaux", "8 carreaux", "9 carreaux", "10 carreaux", "Valet carreaux", "Dame carreaux", "Roi carreaux",
            "As coeur", "2 coeur", "3 coeur", "4 coeur", "5 coeur", "6 coeur", "7 coeur", "8 coeur",
            "9 coeur", "10 coeur", "Valet coeur", "Dame coeur", "Roi coeur"};

        String[] cartesAleatoireNoirs = melangerCartesAleatoire(cartesNoirs);
        String[] cartesAleatoireRouges = melangerCartesAleatoire(cartesRouges);
        String[] cartesAleatoire = genererCartesAleatoire(cartesAleatoireRouges, cartesAleatoireNoirs);
        String[] piocheCartes = piocheTableau(cartesAleatoire);
        String[] tableauResultat = {null, null, null, null};

        System.out.println();

        String[][] tableau = tableauJeu(cartesAleatoire);
        int score = 0;
        String placeCarte = null;
        while (true) {
            System.out.println();
            afficherTableauResultat(tableauResultat);
            System.out.println("_____________________________________________________________________________________________________________________________________________");
            afficherTableauStringDouble(tableau);

            System.out.println("Score : " + score);
            System.out.println();
            if (placeCarte != null) {
                System.out.println("La carte actuellement dans la pioche est : " + placeCarte);
            }
            int commandeSelect = commandes();

            switch (commandeSelect) {
                case 0:
                    quitter();
                case 1:
                    if (placeCarte != null) {
                        mettreCarteDansPioche(placeCarte, piocheCartes);
                    }
                    placeCarte = derniereCartesPioche(piocheCartes);

                    break;
                case 2:
                    boolean verificationPlacer = placerCartes(tableau, placeCarte);
                    if (verificationPlacer == true) {
                        placeCarte = null;
                    }
                    score = score + 5;
                    break;
                case 3:
                    deplacerCartes(tableau);
                    score = score + 10;
                    break;
                case 4:
                    completer(tableau, tableauResultat);
                    partieTerminee(tableauResultat);
                    break;
            }

        }
    }
}
