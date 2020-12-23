package GUI;

import client.ClientSemaphore;
import resources.StageSemaphore;

/**
 * Graphical Client Interface 
 */
public class GUIClient extends javax.swing.JFrame {

    private final ClientSemaphore semaphoreController;

    /**
     * Creates new form GUIClient
     */
    public GUIClient() {
        initComponents();
        this.semaphoreController = new ClientSemaphore(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        containerPanel = new javax.swing.JPanel();
        trafficLight = new javax.swing.JPanel();
        semaphoreContainer = new javax.swing.JPanel();
        semaphorePanel = new GUI.Draw.SemaphorePanel();
        footer = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        optionExit = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        optionHelp = new javax.swing.JMenuItem();
        optionAbout = new javax.swing.JMenuItem();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(789, 483));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        containerPanel.setBackground(new java.awt.Color(204, 204, 204));

        trafficLight.setBackground(new java.awt.Color(255, 255, 51));

        semaphoreContainer.setBackground(new java.awt.Color(255, 255, 51));

        javax.swing.GroupLayout semaphoreContainerLayout = new javax.swing.GroupLayout(semaphoreContainer);
        semaphoreContainer.setLayout(semaphoreContainerLayout);
        semaphoreContainerLayout.setHorizontalGroup(
            semaphoreContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(semaphoreContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(semaphorePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        semaphoreContainerLayout.setVerticalGroup(
            semaphoreContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, semaphoreContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(semaphorePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout trafficLightLayout = new javax.swing.GroupLayout(trafficLight);
        trafficLight.setLayout(trafficLightLayout);
        trafficLightLayout.setHorizontalGroup(
            trafficLightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, trafficLightLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(semaphoreContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        trafficLightLayout.setVerticalGroup(
            trafficLightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trafficLightLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(semaphoreContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout containerPanelLayout = new javax.swing.GroupLayout(containerPanel);
        containerPanel.setLayout(containerPanelLayout);
        containerPanelLayout.setHorizontalGroup(
            containerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(trafficLight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(42, 42, 42))
        );
        containerPanelLayout.setVerticalGroup(
            containerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(trafficLight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(42, 42, 42))
        );

        footer.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Semaphore client");

        javax.swing.GroupLayout footerLayout = new javax.swing.GroupLayout(footer);
        footer.setLayout(footerLayout);
        footerLayout.setHorizontalGroup(
            footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(footerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        footerLayout.setVerticalGroup(
            footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        fileMenu.setText("File");

        optionExit.setText("Exit");
        optionExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionExitActionPerformed(evt);
            }
        });
        fileMenu.add(optionExit);

        menuBar.add(fileMenu);

        helpMenu.setText("Help");

        optionHelp.setText("Help");
        optionHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayHelp(evt);
            }
        });
        helpMenu.add(optionHelp);

        optionAbout.setText("About");
        optionAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayAbout(evt);
            }
        });
        helpMenu.add(optionAbout);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(containerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(footer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(containerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(footer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * To show Help 
     * @param evt Help 
     */
    private void displayHelp(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayHelp
        DialogMessages dialog = new DialogMessages(this, true, "Help");
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_displayHelp
    /**
     * To show About
     * @param evt About
     */
    private void displayAbout(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayAbout
        DialogMessages dialog = new DialogMessages(this, true, "About");
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_displayAbout
    
    public void changeColorState(StageSemaphore stage) {
        this.semaphorePanel.changeState(stage);
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.semaphoreController.getNetworkClient().sendEndCommand();
    }//GEN-LAST:event_formWindowClosing

    private void optionExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionExitActionPerformed
        this.semaphoreController.getNetworkClient().sendEndCommand();
        close();
    }//GEN-LAST:event_optionExitActionPerformed

    public static void close() {
        System.exit(0);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel containerPanel;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JPanel footer;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem optionAbout;
    private javax.swing.JMenuItem optionExit;
    private javax.swing.JMenuItem optionHelp;
    private javax.swing.JPanel semaphoreContainer;
    private GUI.Draw.SemaphorePanel semaphorePanel;
    private javax.swing.JPanel trafficLight;
    // End of variables declaration//GEN-END:variables
}
