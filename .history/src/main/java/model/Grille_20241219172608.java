package main.java.model;

import java.awt.Color;

public class Grille {
    private String[][] grille;

    public Grille() {
        grille = new String[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                grille[i][j] = "";
            }
        }
    }

    public boolean ajouterPion(int colonne, String couleur) {
        for (int i = 5; i >= 0; i--) {
            if (grille[i][colonne].isEmpty()) {
                grille[i][colonne] = couleur;
                return true;
            }
        }
        return false;
    }

    public boolean verifierVictoire(String couleur) {
        // Vérification Horizontale
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (grille[i][j].equals(couleur) && grille[i][j + 1].equals(couleur) && grille[i][j + 2].equals(couleur) && grille[i][j + 3].equals(couleur)) {
                    return true;
                }
            }
        }

        // Vérification Verticale
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (grille[i][j].equals(couleur) && grille[i + 1][j].equals(couleur) && grille[i + 2][j].equals(couleur) && grille[i + 3][j].equals(couleur)) {
                    return true;
                }
            }
        }

        // Vérification Diagonale
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (grille[i][j].equals(couleur) && grille[i - 1][j + 1].equals(couleur) && grille[i - 2][j + 2].equals(couleur) && grille[i - 3][j + 3].equals(couleur)) {
                    return true;
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (grille[i][j].equals(couleur) && grille[i + 1][j + 1].equals(couleur) && grille[i + 2][j + 2].equals(couleur) && grille[i + 3][j + 3].equals(couleur)) {
                    return true;
                }
            }
        }

        return false;
    }

    public String getCouleur(int ligne, int colonne) {
        return grille[ligne][colonne].isEmpty() ? Color.WHITE : grille[ligne][colonne];
    }

    public Object getCase(int i, int j) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCase'");
    }
}