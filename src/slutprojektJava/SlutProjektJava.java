package slutprojektJava;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SlutProjektJava {
	public static void main(String[] args)throws SQLException {
		//skapar en JFrame
	JFrame frame = new JFrame("Login");
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel panel = new JPanel();
    JLabel label = new JLabel("Username");
    JTextField textField = new JTextField(20);
    JLabel label2 = new JLabel("Password");
    JPasswordField passwordField = new JPasswordField(20);
    JButton button = new JButton("Login");
    panel.add(label);
    panel.add(textField);
    panel.add(label2);
    panel.add(passwordField);
    panel.add(button);
    frame.add(panel);
   
    frame.revalidate();
    frame.repaint(0, 0, 0, 0, 0);
    //connectar till min mysql databas
    String url = "jdbc:mysql://localhost/userdb?useSSL=false";
    String username = "root";
    String password = "123";
    //skapar en connection till min databas
    Connection connection = DriverManager.getConnection(url, username, password);
    Statement statement = connection.createStatement();
    //efter successfully logged in, kommmer en ny JFrame window upp
    JFrame frame2 = new JFrame("My First GUI");
    frame2.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //button för att generera random picture från min api
    JButton button2 = new JButton("Click Here For A Random Picture");

    frame2.add(button2, BorderLayout.CENTER);
    frame2.add(button2);
    //skapar en actionlistener till min login button
    button.addActionListener(new  ActionListener() {
    	
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		//om rätt user + pass, loggar in
    		String user = textField.getText();
    		String pass = String.valueOf(passwordField.getPassword());
    		ResultSet resultSet = null;
    		boolean unique = false;
    		//try-catch som tittar på alla möjliga users + pass som finns i min databas
			try {
				String query = "SELECT * FROM users where username = '"+user+"' and pass = '"+pass+"'";
				resultSet = statement.executeQuery(query);
				unique = resultSet.next() && !resultSet.next();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			//try-catch som öppnar min nya JFrame window om successfull login samt displayar text
    		try {
				if(unique) {
					JOptionPane.showMessageDialog(null, "Login Successful");
					frame2.setVisible(true);
					frame.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Login Unsuccessful");
				}
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			}
    	}
    });
    //min nya button på min nya JFrame window som har en actionperformed funktion och hämtar en random bild från min api
    button2.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                URL url = new URL("https://source.unsplash.com/random");
                BufferedImage image = ImageIO.read(url);
                JLabel label3 = new JLabel(new ImageIcon(image));
                frame2.add(label3);
                label.setSize(frame2.getSize());
                frame2.setResizable(true);
                frame2.setMinimumSize(new Dimension(500, 500));
                
                
                //method to minimize button when resized to a smaller window
                createComponents(frame2.getContentPane());
                frame2.addComponentListener((ComponentListener) new ComponentAdapter(){
                	public void componentResized(ComponentEvent ev) {
                		button2.setSize(100, 50);
                		button2.setText("Click :)");
                }
                	
            });
                
                
                button2.revalidate();
	            button2.repaint();
                
                
	            button2.addActionListener(new ActionListener() {
	                @Override
	                //actionperformed funktion som tar bort min gamla bild och tillsätter ny bild på dens plats
	                public void actionPerformed(ActionEvent e) {
	                    frame2.remove(label3);
	                }
	            });
                } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            //efter man tryckt på hämta bild, ändra på knappens plats samt storlek och text så den inte är i vägen för bilden
            button2.setText("Click Me Again For A New Picture");
            button2.setLocation(0, 0);
            button2.setSize(230, 50);
            button2.revalidate();
            button2.repaint();
        }

        
		private void createComponents(Container contentPane) {
			// TODO Auto-generated method stub
			
		}
    });
    
	}
}        