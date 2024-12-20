package main.java.view;

import main.java.controller.Jeu;
import main.java.model.Player;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class FenetreJeu extends JFrame {
    private JButton[] boutonsColonnes;
    private JLabel[][] casesGrille;
    private JLabel message;
    private Jeu jeu;
    private int scoreJoueur1 = 0;
    private int scoreJoueur2 = 0;
    private JLabel scoreLabel;

    public FenetreJeu() {
        // Nom de Joueur et choix de couleur
        String nomJoueur1 = JOptionPane.showInputDialog("Nom du Joueur 1 :");
        if (nomJoueur1 == null || nomJoueur1.trim().isEmpty()) {
            nomJoueur1 = "Joueur 1"; // Valeur par défaut
        }

        String nomJoueur2 = JOptionPane.showInputDialog("Nom du Joueur 2 :");
        if (nomJoueur2 == null || nomJoueur2.trim().isEmpty()) {
            nomJoueur2 = "Joueur 2"; // Valeur par défaut
        }

        String couleur1 = (String) JOptionPane.showInputDialog(null, "Choisir une couleur, Rouge/Jaune", "Choisir une couleur", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Rouge", "Jaune"}, "Rouge");
        if (couleur1 == null) {
            couleur1 = "Rouge"; // Valeur par défaut
        }
        String couleur2 = couleur1.equals("Rouge") ? "Jaune" : "Rouge";
        jeu = new Jeu(nomJoueur1, nomJoueur2, couleur1, couleur2);

        setTitle("Puissance 4");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Zone de message
        JPanel panneauMessage = new JPanel();
        message = new JLabel("Bienvenue " + jeu.getJoueurActuel().getNom(), SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 18));
        panneauMessage.add(message);
        add(panneauMessage, BorderLayout.NORTH);

        // Score
        JPanel panneauScore = new JPanel();
        scoreLabel = new JLabel("Score - " + jeu.getJoueur1().getNom() + ": " + scoreJoueur1 + " | " + jeu.getJoueur2().getNom() + ": " + scoreJoueur2);
        panneauScore.add(scoreLabel);
        add(panneauScore, BorderLayout.SOUTH);

        // Grille de jeu
        JPanel panneauGrille = new JPanel(new GridLayout(6, 7));
        panneauGrille.setBackground(Color.BLUE); // Couleur de fond

        casesGrille = new JLabel[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                casesGrille[i][j] = new JLabel();
                casesGrille[i][j].setOpaque(true);
                casesGrille[i][j].setBackground(Color.WHITE);
                casesGrille[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                panneauGrille.add(casesGrille[i][j]);
            }
        }
        add(panneauGrille, BorderLayout.CENTER);

        // Boutons des colonnes
        JPanel panneauBoutons = new JPanel(new GridLayout(1, 7));
        boutonsColonnes = new JButton[7];
        for (int i = 0; i < 7; i++) {
            final int colonne = i;
            boutonsColonnes[i] = new JButton("Jouer dans la colonne " + (colonne + 1));
            boutonsColonnes[i].setFont(new Font("Arial", Font.BOLD, 10));
            boutonsColonnes[i].addActionListener((ActionEvent e) -> jouerColonne(colonne));
            panneauBoutons.add(boutonsColonnes[i]);
        }
        add(panneauBoutons, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * @param colonne
     */
    private void jouerColonne(int colonne) {
        String resultat = jeu.jouerTour(colonne);
        jouerSon("placement.wav");

        ajouterAnimationPion(colonne);
        mettreAJourGrille();
        if (resultat != null) {
            jouerSon("victoire.wav");
            message.setText(resultat);
            message.setForeground(Color.GREEN);
            updateScore();
            desactiverBoutons();
            ajouterBoutonRecommencer();
        } else {
            message.setText("Tour de " + jeu.get JoueurActuel().getNom());
            message.setForeground(Color.BLACK);
        }
    }

    private void mettreAJourGrille() {
        // Vérification si la grille a changé avant de mettre à jour
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                casesGrille[i][j].setBackground(jeu.getCouleur(i, j));
            }
        }
        repaint();
    }

    private void jouerSon(String nomFichier) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(nomFichier));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du son : " + e.getMessage());
        }
    }

    private void updateScore() {
        scoreJoueur1 = jeu.getScoreJoueur1();
        scoreJoueur2 = jeu.getScoreJoueur2();
        scoreLabel.setText("Score - " + jeu.getJoueur1().getNom() + ": " + scoreJoueur1 + " | " + jeu.getJoueur2().getNom() + ": " + scoreJoueur2);
    }

    private void desactiverBoutons() {
        for (JButton bouton : boutonsColonnes) {
            bouton.setEnabled(false);
        }
    }

    private void ajouterBoutonRecommencer() {
        JButton boutonRecommencer = new JButton("Recommencer");
        boutonRecommencer.addActionListener(e -> redemarrerJeu());
        add(boutonRecommencer, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    private void redemarrerJeu() {
        dispose();
        new FenetreJeu();
    }

    private void ajouterAnimationPion(int colonne) {
        // Logique pour ajouter une animation de pion
    }

    public static void main(String[] args) {
        new FenetreJeu();
    }
}