/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.util.Timer;
import server.ServerSemaphore;

/**
 * Responsable to create the Server's Interface Graphic
 * @author Leo
 */
public class GUIServer extends javax.swing.JFrame {

    private final ServerSemaphore modificationController;
    /**
     * Creates new form GUIServer
     */
    public GUIServer() {
        initComponents();
        modificationController = new ServerSemaphore(this);
        modificationController.initializeLog();
        Timer timer = new Timer();
        int period = 2000;
        timer.scheduleAtFixedRate(modificationController, 0, period);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        clients = new javax.swing.JPanel();
        log = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        logArea = new javax.swing.JTextArea();
        footer = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        optionExit = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        optionHelp = new javax.swing.JMenuItem();
        optionAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        clients.setBackground(new java.awt.Color(255, 204, 204));
        clients.setMinimumSize(new java.awt.Dimension(130, 100));

        javax.swing.GroupLayout clientsLayout = new javax.swing.GroupLayout(clients);
        clients.setLayout(clientsLayout);
        clientsLayout.setHorizontalGroup(
            clientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        clientsLayout.setVerticalGroup(
            clientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 153, Short.MAX_VALUE)
        );

        log.setBackground(new java.awt.Color(204, 255, 204));

        logArea.setEditable(false);
        logArea.setColumns(20);
        logArea.setRows(5);
        jScrollPane1.setViewportView(logArea);

        javax.swing.GroupLayout logLayout = new javax.swing.GroupLayout(log);
        log.setLayout(logLayout);
        logLayout.setHorizontalGroup(
            logLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                .addContainerGap())
        );
        logLayout.setVerticalGroup(
            logLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        footer.setBackground(new java.awt.Color(0, 204, 204));

        javax.swing.GroupLayout footerLayout = new javax.swing.GroupLayout(footer);
        footer.setLayout(footerLayout);
        footerLayout.setHorizontalGroup(
            footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        footerLayout.setVerticalGroup(
            footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        fileMenu.setText("File");

        optionExit.setText("Exit");
        fileMenu.add(optionExit);

        menuBar.add(fileMenu);

        helpMenu.setText("Help");

        optionHelp.setText("Help");
        optionHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionHelpActionPerformed(evt);
            }
        });
        helpMenu.add(optionHelp);

        optionAbout.setText("About");
        optionAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionAboutActionPerformed(evt);
            }
        });
        helpMenu.add(optionAbout);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(clients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(log, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(footer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(log, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(clients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(175, 175, 175)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(footer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Responsable to manage the "Help" Event
     * @param evt Help Event
     */
    private void optionHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionHelpActionPerformed
        DialogMessages dialog = new DialogMessages(this, true, "Help");
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_optionHelpActionPerformed
    /**
     * Responsable to manage the "About" Event
     * @param evt About Event
     */
    private void optionAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionAboutActionPerformed
        DialogMessages dialog = new DialogMessages(this, true, "About");
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_optionAboutActionPerformed
    
    /**
     * Responsable to write in the Space of Log 
     * @param sb Show in the area the transactions 
     */
    public void writeOnLog(StringBuilder sb) {
        logArea.append(sb.toString());
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel clients;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JPanel footer;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel log;
    private javax.swing.JTextArea logArea;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem optionAbout;
    private javax.swing.JMenuItem optionExit;
    private javax.swing.JMenuItem optionHelp;
    // End of variables declaration//GEN-END:variables
}
