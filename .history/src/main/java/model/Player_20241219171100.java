package main.java.model;

public class Player {
    private String nom;
    private String couleur;

    public Player(String nom, String couleur) {
        this.nom = nom;
        this.couleur = couleur;
    }

    public String getNom(){
        return nom;
    }

    public String getCouleur() {
        return couleur;
    }

    public int getScore() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getScore'");
    }
}
