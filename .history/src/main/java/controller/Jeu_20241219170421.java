package main.java.controller;

import main.java.model.Grille;
import main.java.model.Player;

public class Jeu {
    private Grille grille;
    private Player joueur1, joueur2;
    private Player joueurActuel;

    public Jeu(String nomJoueur1, String nomJoueur2, String couleur1, String couleur2) {
        grille = new Grille();
        joueur1 = new Player(nomJoueur1, couleur1);
        joueur2 = new Player(nomJoueur2, couleur2);
        joueurActuel = joueur1;
    }

    public String jouerTour(int colonne) {
        if (grille.ajouterPion(colonne, joueurActuel.getCouleur())) {
            if (grille.verifierVictoire(joueurActuel.getCouleur())) {
                return joueurActuel.getNom() + " a gagné!";
            }
            alternerJoueur();
        } else {
            return "Colonne pleine!";
        }
        return null;
    }

    private void alternerJoueur() {
        joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
    }

    public Grille getGrille() {
        return grille;
    }

    public Player getJoueur1() {
        return joueur1;
    }

    public Player getJoueur2() {
        return joueur2;
    }

    public Player getJoueurActuel() {
        return joueurActuel;
    }

    public int getScoreJoueur1() {
        return joueur1.getScore(); // Ajoutez une méthode getScore() dans Player
    }

    public int getScoreJoueur2() {
        return joueur2.getScore(); // Ajoutez une méthode getScore() dans Player
    }

    public Player get() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }
}