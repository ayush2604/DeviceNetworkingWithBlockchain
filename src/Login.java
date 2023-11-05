package src;

import javax.swing.*;
import java.awt.*;  
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {
    JButton submitButton, closeButton;  
    JPanel newPanel;  
    JLabel userLabel, passLabel;  
    JTextField  textField1, textField2;  
    String username, password;
    boolean actionPerformed;

    public Login(){
        initialiseLabels();
        initialiseTextFields();
        initialiseButtons();
        initialisePanels();    
        add(newPanel, BorderLayout.CENTER);     
        setTitle("ENTER DATABASE CREDENTIALS");   
        setSize(400,150); 
        setVisible(true); 
        setLocation(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        username = "";
        password = "";
        actionPerformed = false;
    }

    private void initialiseLabels (){
        userLabel = new JLabel();
        passLabel = new JLabel();
        userLabel.setText("Username");
        passLabel.setText("Password");
    }

    private void initialiseTextFields(){
        textField1 = new JTextField(15);
        textField2 = new JPasswordField(15); 
    }

    private void initialiseButtons(){
        submitButton = new JButton("SUBMIT"); 
        closeButton = new JButton("CLOSE");
        submitButton.addActionListener(this);  
        closeButton.addActionListener(this);
    }

    private void initialisePanels(){
        newPanel = new JPanel(new GridLayout(3, 1));  
        newPanel.add(userLabel);    
        newPanel.add(textField1);    
        newPanel.add(passLabel);    
        newPanel.add(textField2);   
        newPanel.add(submitButton);
        newPanel.add(closeButton);
    }

    public void printStatus(){
        System.out.println("Login process initiated.");
    }

    public synchronized void actionPerformed(ActionEvent ae) {  
        if (ae.getSource() == submitButton){
            String userValue = textField1.getText();        
            String passValue = textField2.getText();  
            username = userValue;
            password = passValue;
            actionPerformed = true;
            notifyAll();
            setVisible(false);
            dispose();
        }
        else System.exit(0);
    }
    
    public synchronized String getUsername () throws InterruptedException{
        while ( !actionPerformed ) wait();
        return username;
    }

    public synchronized String getPassword () throws InterruptedException{
        while ( !actionPerformed ) wait();
        return password;
    }
}
