package main.java.model;

public class Player {
    private String nom;
    private String couleur;
    private int score; // Ajout d'un attribut pour le score

    public Player(String nom, String couleur) {
        this.nom = nom;
        this.couleur = couleur;
        this.score = 0; // Initialisation du score à 0
    }

    public String getNom() {
        return nom;
    }

    public String getCouleur() {
        return couleur;
    }

    public int getScore() {
        return score; // Retourne le score actuel du joueur
    }

    public void incrementerScore() {
        score++; // Méthode pour incrémenter le score
    }
}