package main.java.view;

import main.java.controller.Jeu;
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
    private JLabel scoreLabel;

    public FenetreJeu() {
        // Initialisation des joueurs
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

        // Configuration de la fenêtre
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
        scoreLabel = new JLabel("Score - " + jeu.getJoueur1().getNom() + ": " + jeu.getScoreJoueur1() + " | " + jeu.getJoueur2().getNom() + ": " + jeu.getScoreJoueur2());
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
            String Text;
            Text = ("C'est au tour de " + jeu.getJoueurActuel().getNom());
            message.setForeground(Color.BLACK);
        }
    }

    private void ajouterAnimationPion(int colonne) {
        // Logique pour animer le pion qui tombe dans la colonne
        int ligne = 5; // Commence à la dernière ligne
        while (ligne >= 0 && casesGrille[ligne][colonne].getBackground() != Color.WHITE) {
            ligne--; // Trouver la première case vide
        }
        if (ligne >= 0) {
            // Animation simple : changer la couleur de la case
            casesGrille[ligne][colonne].setBackground(jeu.getJoueurActuel().getCouleur().equals("Rouge") ? Color.RED : Color.YELLOW);
        }
    }

    private void mettreAJourGrille() {
        // Mettre à jour l'affichage de la grille
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                casesGrille[i][j].setBackground(jeu.getGrille().getCase(i, j).equals("Rouge") ? Color.RED : 
                                                jeu.getGrille().getCase(i, j).equals("Jaune") ? Color.YELLOW : Color.WHITE);
            }
        }
    }

    private void updateScore() {
        scoreLabel.setText("Score - " + jeu.getJoueur1().getNom() + ": " + jeu.getScoreJoueur1() + " | " + jeu.getJoueur2().getNom() + ": " + jeu.getScoreJoueur2());
    }

    private void desactiverBoutons() {
        for (JButton bouton : boutonsColonnes) {
            bouton.setEnabled(false);
        }
    }

    private void ajouterBoutonRecommencer() {
        JButton boutonRecommencer = new JButton("Recommencer");
        boutonRecommencer.addActionListener(e -> recommencerJeu());
        add(boutonRecommencer, BorderLayout.NORTH);
        revalidate();
        repaint();
    }

    private void recommencerJeu() {
        // Logique pour recommencer le jeu
        jeu.reinitialiser();
        mettreAJourGrille();
        updateScore();
        message.setText("Bienvenue " + jeu.getJoueurActuel().getNom());
        desactiverBoutons();
    }

    private void jouerSon(String nomFichier) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(nomFichier));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}