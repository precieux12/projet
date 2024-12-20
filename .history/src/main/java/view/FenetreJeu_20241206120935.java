package main.java.view;

import main.java.controller.Jeu;
import main.java.model.Player;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class FenetreJeu extends JFrame {
    private JButton[] boutonsColonnes;
    private JLabel[][] casesGrille;
    private JLabel message;
    private Jeu jeu;
    private int scoreJoueur1 = 0;
    private int scoreJoueur2 = 0;
    private JLabel scoreLabel;

    public FenetreJeu() {
        //Nom de Joueur et choix de couleur
        String nomJoueur1 = JOptionPane.showInputDialog("Nom du Joueur 1 :");
        String nomJoueur2 = JOptionPane.showInputDialog("Nom du Joueur 2 :");
        String couleur1 = (String) JOptionPane.showInputDialog(null,"Choisisser une couleur, Rouge/Jaune", "Choisir une couleur", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Rouge" , "Jaune"}, "Rouge");
        String couleur2 = couleur1.equals("Rouge") ? "Jaune" : "Rouge";
        jeu = new Jeu(nomJoueur1, nomJoueur2, couleur1, couleur2);
        
        setTitle("Puissance 4");
        setSize(500, 500);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Zone de message
        JPanel panneauMessage = new JPanel();
        message = new JLabel("Bienvenue !" + jeu.getJoueurActuel().getNom(), SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 18));
        panneauMessage.add(message);
        add(panneauMessage , BorderLayout.NORTH);

        //Score
        JPanel panneauScore = new JPanel();
        scoreLabel = new JLabel("Score -" +jeu.getJoueur1().getNom() + ":" + scoreJoueur1 + " | " + jeu.getJoueur2().getNom() + ":" + scoreJoueur2);
        panneauScore.add(scoreLabel); add(panneauScore, BorderLayout.EAST);

        //Grille de jeu
        JPanel panneauGrille = new JPanel(new GridLayout(6, 7));

        panneauGrille.setBackground(Color.BLUE);//Couleur de fond

        casesGrille = new JLabel[6][7];
        for  (int i = 0; i < 6; i++){
            for (int j = 0; j < 7; j++){
                casesGrille[i][j] = new JLabel();
                casesGrille[i][j].setOpaque(true);
                casesGrille[i][j].setBackground(Color.WHITE);
                casesGrille[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                panneauGrille.add(casesGrille[i][j]);
            }
        }
        add(panneauGrille, BorderLayout.CENTER);

        //Bouton des colonnes
        JPanel panneauBoutons = new JPanel(new GridLayout(1, 1));
        boutonsColonnes = new JButton[7];
        for (int i = 0; i<7; i++) {
            final int colonne = i;
            boutonsColonnes[i] = new JButton("Cliquer");
            boutonsColonnes[i].setFont(new Font("Arial",Font.BOLD, 10));
            boutonsColonnes[i].addActionListener((ActionEvent e) -> jouerColonne(colonne));

            panneauBoutons.add(boutonsColonnes[i]);
        }
        add(panneauBoutons , BorderLayout.SOUTH);

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
        } else{
            message.setText("Tour de" + jeu.getJoueurActuel().getNom());
            message.setForeground(Color.BLACK);
        }
    }

    public void jouerSon(String cheminFichier) {
        try {
            File soundFile = new File("src/ressources/" + cheminFichier);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private void ajouterAnimationPion(int colonne) {
        //Simuler la chute du pion
        int row = 0;
        while (row < 6 &&  casesGrille[row][colonne].getBackground() != Color.RED && casesGrille[row][colonne].getBackground() != Color.YELLOW) {
            row++;
        }
        row--;

        JLabel pion = new JLabel();
        pion.setOpaque(true);

        pion.setBackground(jeu.getJoueurActuel().getCouleur().equals("Rouge") ? Color.RED : Color.YELLOW);
        pion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        casesGrille[row][colonne].add(pion);
        repaint();
    }

    private void mettreAJourGrille() {
        String[][] grille = jeu.getGrille().getGrille();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (grille[i][j].equals("Rouge")) {
                    casesGrille[i][j].setBackground(Color.RED);
                } else if (grille[i][j].equals("Jaune")) {
                    casesGrille[i][j].setBackground(Color.YELLOW);
                }
            }
        }
    }

    private void desactiverBoutons() {
        for (JButton bouton : boutonsColonnes) {
            bouton.setEnabled(false);
        }
    }

    private void ajouterBoutonRecommencer() {
        JButton boutonRecommencer = new JButton("Recommencer le Jeu");

        boutonRecommencer.setFont(new Font("Arial", Font.BOLD, 14));
        boutonRecommencer.addActionListener(e -> redemarrerJeu());
        JPanel panneauRecommencer = new JPanel();

        panneauRecommencer.add(boutonRecommencer);
        add(panneauRecommencer, BorderLayout.EAST);
        validate();
    }

    private void redemarrerJeu() {
        dispose();
        new FenetreJeu();
    }

    private void updateScore() {
        if (jeu.getJoueurActuel().getCouleur().equals("Rouge")) {
            scoreJoueur1++;
        } else {
            scoreJoueur2++;
        }
        scoreLabel.setText("Score -" + jeu.getJoueur1().getNom() + " : " + scoreJoueur1 + " | " + jeu.getJoueur2().getNom() + " : " + scoreJoueur2);
    }
}
