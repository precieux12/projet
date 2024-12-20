package main.java.model;

public class Grille {
    private String[][] grille;

    public Grille() {
        grille = new String[6][7]; // 6 lignes et 7 colonnes
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                grille[i][j] = ""; // Initialisation des cases à vide
            }
        }
    }

    public boolean ajouterPion(int colonne, String couleur) {
        // Ajoute un pion dans la colonne spécifiée
        for (int i = 5; i >= 0; i--) { // Commence par la dernière ligne
            if (grille[i][colonne].isEmpty()) {
                grille[i][colonne] = couleur; // Place le pion
                return true; // Pion ajouté avec succès
            }
        }
        return false; // La colonne est pleine
    }

    public boolean verifierVictoire(String couleur) {
        // Vérification Horizontale
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (grille[i][j].equals(couleur) && 
                    grille[i][j + 1].equals(couleur) && 
                    grille[i][j + 2].equals(couleur) && 
                    grille[i][j + 3].equals(couleur)) {
                    return true; // Victoire horizontale
                }
            }
        }

        // Vérification Verticale
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (grille[i][j].equals(couleur) && 
                    grille[i + 1][j].equals(couleur) && 
                    grille[i + 2][j].equals(couleur) && 
                    grille[i + 3][j].equals(couleur)) {
                    return true; // Victoire verticale
                }
            }
        }

        // Vérification Diagonale (de gauche à droite)
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (grille[i][j].equals(couleur) && 
                    grille[i - 1][j + 1].equals(couleur) && 
                    grille[i - 2][j + 2].equals(couleur) && 
                    grille[i - 3][j + 3].equals(couleur)) {
                    return true; // Victoire diagonale
                }
            }
        }

        // Vérification Diagonale (de droite à gauche)
        for (int i = 3; i < 6; i++) {
            for (int j = 3; j < 7; j++) {
                if (grille[i][j].equals(couleur) && 
                    grille[i - 1][j - 1].equals(couleur) && 
                    grille[i - 2][j - 2].equals(couleur) && 
                    grille[i - 3][j - 3].equals(couleur)) {
                    return true; // Victoire diagonale
                }
            }
        }

        return false; // Pas de victoire
    }

    public String getCase(int ligne, int colonne) {
        // Retourne la couleur de la case à la position spécifiée
        return grille[ligne][colonne];
    }

    public String[][] getGrille() {
        return grille; // Retourne l'état actuel de la grille
    }

    public void reinitialiser() {
        // Réinitialise la grille à l'état vide
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                grille[i][j] = ""; // Réinitialisation des cases
            }
        }
    }
}