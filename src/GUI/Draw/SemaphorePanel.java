/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Draw;

import GUI.GUIServer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import resources.StageSemaphore;

/**
 *
 * @author silva
 */
public class SemaphorePanel extends javax.swing.JPanel {

    private Timer timer;
    private StageSemaphore currentStage;
    private final ActionListener action;
    private final int SECOND = 1000;
    private int semaphoreTime = GUIServer.PERIOD / SECOND;

    /**
     * Creates new form SemaphorePanel
     */
    public SemaphorePanel() {
        initComponents();

        red.setEnabled(true); // comeca no vermelho
        yellow.setEnabled(false);
        green.setEnabled(false);

        action = (ActionEvent evt) -> {
            semaphoreTime--;
            showTime();
        };
    }

    public void startTextShowTimer() {
        this.semaphoreTime = GUIServer.PERIOD / SECOND;
        
        
        try {
            timer.stop();
        } catch (NullPointerException e) {
        }
        timer = new Timer(SECOND, action);
        timer.start();
    }

    private void showTime() {

        clearText();

        switch (currentStage) {
            case RED -> {
                red.setText(String.valueOf(semaphoreTime));
            }

            case YELLOW -> {
                yellow.setText(String.valueOf(semaphoreTime));
            }

            case GREEN -> {
                green.setText(String.valueOf(semaphoreTime));
            }
        }
    }

    private void clearText() {
        red.setText(null);
        yellow.setText(null);
        green.setText(null);
    }

    public void changeState(StageSemaphore stage) {
        this.currentStage = stage;

        switch (stage) {
            case RED -> {
                changeRed();
            }
            case YELLOW -> {
                changeYellow();
            }
            case GREEN -> {
                changeGreen();
            }
        }
        startTextShowTimer();
    }

    private void changeRed() {
        red.setEnabled(true);
        yellow.setEnabled(false);
        green.setEnabled(false);
    }

    private void changeYellow() {
        red.setText(null);
        yellow.setEnabled(true);
        red.setEnabled(false);
        green.setEnabled(false);
    }

    private void changeGreen() {
        green.setEnabled(true);
        yellow.setEnabled(false);
        red.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        red = new javax.swing.JLabel();
        yellow = new javax.swing.JLabel();
        green = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 51));

        red.setBackground(new java.awt.Color(0, 0, 0));
        red.setFont(new java.awt.Font("Ebrima", 0, 36)); // NOI18N
        red.setForeground(new java.awt.Color(255, 255, 51));
        red.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Draw/red-80.png"))); // NOI18N
        red.setOpaque(true);

        yellow.setBackground(new java.awt.Color(0, 0, 0));
        yellow.setFont(new java.awt.Font("Ebrima", 0, 36)); // NOI18N
        yellow.setForeground(new java.awt.Color(255, 255, 51));
        yellow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Draw/yellow-80.png"))); // NOI18N
        yellow.setOpaque(true);

        green.setBackground(new java.awt.Color(0, 0, 0));
        green.setFont(new java.awt.Font("Ebrima", 0, 36)); // NOI18N
        green.setForeground(new java.awt.Color(255, 255, 51));
        green.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Draw/green-80.png"))); // NOI18N
        green.setText("0");
        green.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(red, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(yellow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(green, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(red, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(yellow, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(green, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel green;
    private javax.swing.JLabel red;
    private javax.swing.JLabel yellow;
    // End of variables declaration//GEN-END:variables

}