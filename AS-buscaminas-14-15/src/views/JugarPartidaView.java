package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import model.Casella;
import model.Nivell;

/**
*
* @author alexmorral
*/

public class JugarPartidaView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static final int MINA = -1;
	private static final int MARCADA = -2;
	private static final int BLANC = -3;
	
	CtrlJugarPartidaPresentacio presentationController;
	JLogin loginPanel;
	JSelecNivell selecNivellPanel;
	JPartida matchPanel;
	JComboBox<String> cBox_Nivells;
	JLabel loginPanelMessages;
	JLabel matchPanelMessages;
	JLabel nivellPanelMessages;
	JButton jugarPartidaBtn;
	JButton stopPartidaBtn;
	JButton finishPartidaBtn;
	private JButton but[][];
	JPanel gridPanel;
	JLabel lb_nivell;
	JLabel tir = new JLabel("0");
	Timer timer;
	JLabel temps = new JLabel("0");
	private int nF;
	private int nC;
	
	
	ActionListener updateTimerLabel = new ActionListener() {
		public void actionPerformed (ActionEvent e){
			int t = Integer.parseInt(temps.getText());
			presentationController.setTemps(t);
			temps.setText(String.valueOf(t+1));
		}
	};
	
	/**
	 * Actualitza el tauler de mines
	 */
	public void updateBoard(Casella[][] caselles) {
		for(int i = 0; i < nF; ++i)
			for(int j = 0; j < nC; ++j) {
				int val = presentationController.checkCasella(i, j);
				if(val == MARCADA) but[i][j].setText("M");
				else if(val == BLANC) but[i][j].setText("");
				else if(val == MINA) {
					but[i][j].setOpaque(true);
					but[i][j].setBackground(Color.RED);
					but[i][j].setText("X");
				}
				else {
					but[i][j].setText(String.valueOf(val));
					but[i][j].setEnabled(false);
				}
			}
		
		
		
		gridPanel.revalidate();
		gridPanel.repaint();
		
	}
	
	/**
	 * Genera el tauler de mines
	 */
	public void buildBoard(Casella[][] caselles, Nivell n) {
		gridPanel = new JPanel();
		timer = new Timer(1000, updateTimerLabel);
		timer.start();
		lb_nivell.setText(n.getNom());
		nF = n.getNombreCasellesxFila();
		nC = n.getNombreCasellesxColumna();
		int sizeX, sizeY;
		if(nC <= 10) sizeX = sizeY = 250;
		else if (nC <= 16) sizeX = sizeY = 300;
		else {
			sizeX = 500;
			sizeY = 300;
		}
		
		but = new JButton[nF][nC];
		
		Dimension size = new Dimension(sizeX,sizeY);
		
		gridPanel.setLayout(new GridLayout(nF, nC));
		gridPanel.setPreferredSize(size);
		gridPanel.setMaximumSize(size);
		gridPanel.setMinimumSize(size);
		gridPanel.setSize(size);
		gridPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=1;
		c.gridy=2;
		c.gridheight=GridBagConstraints.RELATIVE;
		for(int i = 0; i < nF; ++i)
			for(int j = 0; j < nC; ++j) {
				but[i][j] =  new JButton("");
				but[i][j].addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent e) {
						JButton b = (JButton) e.getSource();
						for(int i = 0; i < nF; ++i) {
							for(int j = 0; j < nC; ++j){
								if(but[i][j].equals(b)) {
									try {
										if(SwingUtilities.isRightMouseButton(e)) {
											presentationController.PrCheckCasella(i, j, 2);
										}
										else if (!b.getText().equals("M")){
											presentationController.PrCheckCasella(i, j, 1);
										}
										
									} catch (IOException eX){
										
									} catch (Exception eX) {
										
									}
								}
							}
						}
					}
					
					@Override
					public void mousePressed(MouseEvent e){}
					@Override
					public void mouseExited(MouseEvent e) {}
					@Override
					public void mouseEntered(MouseEvent e) {}
					@Override
					public void mouseClicked(MouseEvent e) {}
				});
				
				gridPanel.add(but[i][j]);
			}
		matchPanel.add(gridPanel,c);
		
		
		
		gridPanel.revalidate();
		gridPanel.repaint();
		
	}
	
	public class JLogin extends JPanel {
		
		private static final long serialVersionUID = 1L;
		private JTextField userField;
		private JPasswordField passField;
		public JLogin() {
			setLayout(null);
			
			//label usuari
			JLabel userLabel = new JLabel("Usuari:");
			userLabel.setFont(new java.awt.Font("Arial",0,20));
			userLabel.setBounds(160, 100, 150, 30);
			userLabel.setHorizontalAlignment(SwingConstants.CENTER);
			add(userLabel);
			
			//label contrasenya
			JLabel passLabel = new JLabel("Password:");
			passLabel.setFont(new java.awt.Font("Arial",0,20));
			passLabel.setBounds(160, 150, 130, 30);
			passLabel.setHorizontalAlignment(SwingConstants.CENTER);
			add(passLabel);
			
			
			//textField username
			userField = new JTextField();
			userField.setBounds(320, 100, 130, 30);
			userField.setFont(new java.awt.Font("Arial",0,17));
			add(userField);
			userField.setColumns(10);
			
			//textField contrasenya
			passField = new JPasswordField();
			passField.setBounds(320, 150, 130, 30);
			passField.setFont(new java.awt.Font("Arial",0,17));
			add(passField);
			
			//panell missatges
			loginPanelMessages = new JLabel("",JLabel.CENTER);
			loginPanelMessages.setBounds(150, 200, 300, 60);
			loginPanelMessages.setHorizontalAlignment(SwingConstants.CENTER);
			loginPanelMessages.setFont(new java.awt.Font("Arial",0,17));
			add(loginPanelMessages);
			
			//boto de login
			JButton btn_login = new JButton("Entrar");
			btn_login.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String username = userField.getText();
					String pass = passField.getText();
					if(presentationController.prLogin(username,pass)) {
						if(presentationController.jugadorTePartida()) {
							System.out.println("Abans de carregar partida");
							try {
								presentationController.PrLoadPartida();
								setContentPane(matchPanel);
							} catch (Exception eX) {
								mostrarAvis(eX.toString(), 0);
							}
							matchPanel.updateUI();
						} else {
							setContentPane(selecNivellPanel);
							nivellPanelMessages.setText("");
							selecNivellPanel.updateUI();
						}
					}
					userField.setText("");
					passField.setText("");
				}
			});
			btn_login.setBounds(250, 270, 100, 35);
			btn_login.setFont(new java.awt.Font("Arial", Font.BOLD, 15));
			add(btn_login);
			
		}
	}
	
	
	public class JPartida extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public JPartida() {
			setLayout(new GridBagLayout());
			
			
			GridBagConstraints c = new GridBagConstraints();
			
			
			lb_nivell = new JLabel("NIVELL");
			lb_nivell.setFont(new java.awt.Font("Arial",1,18));
			c.gridy=0;
			c.gridx=1;
			add(lb_nivell, c);
			
			JLabel lb_tirades = new JLabel("Tirades:");
			c.gridy=0;
			c.gridx=3;
			add(lb_tirades, c);
			
			
			c.gridx=4;
			add(tir, c);
			
			JLabel lb_temps = new JLabel("Temps:");
			c.gridx=3;
			c.gridy=1;
			add(lb_temps, c);
			
			c.gridx=4;
			add(temps, c);
			
			
			//boto aturar partida
			stopPartidaBtn = new JButton("Aturar");
			stopPartidaBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					timer.stop();
					temps.setText("0");
					try {
						presentationController.prTancar();
					} catch (Exception eX) {
						mostrarAvis(eX.toString(), 0);
					}
					setContentPane(loginPanel);
					loginPanel.updateUI();
					matchPanel.remove(gridPanel);
				}
			});
			
			stopPartidaBtn.setBounds( 150 , 290, 100, 35);
			stopPartidaBtn.setFont(new java.awt.Font("ARIAL", Font.BOLD, 15));
			
			stopPartidaBtn.setVisible(true);
			c.gridy=5;
			c.gridx=1;
			
			add(stopPartidaBtn, c);
			
			
			matchPanelMessages = new JLabel("");
			c.gridy=4;
			c.gridx=1;
			add(matchPanelMessages,c);
			finishPartidaBtn = new JButton("Tancar");
			finishPartidaBtn.setBounds( 250 , 290, 100, 35);
			finishPartidaBtn.setFont(new java.awt.Font("ARIAL", Font.BOLD, 16));
			
			finishPartidaBtn.setVisible(false);
			finishPartidaBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					presentationController.prSortir();
				}
			});
			c.gridy=5;
			c.gridx=1;
			add(finishPartidaBtn,c);
		}
	}
	
	public class JSelecNivell extends JPanel {
		private static final long serialVersionUID = 1L;

		public JSelecNivell() {
			setLayout(null);
			
			
			JLabel lb_SelNivell = new JLabel("Tria un nivell:");
			lb_SelNivell.setFont(new java.awt.Font("ARIAL",0,20));
			lb_SelNivell.setHorizontalAlignment(SwingConstants.CENTER);
			lb_SelNivell.setBounds(200, 60, 200, 30);
			add(lb_SelNivell);
			
			//combobox
			cBox_Nivells = new JComboBox<String>();
			cBox_Nivells.setBorder( BorderFactory.createLineBorder( new Color(160, 160, 160), 2 ));
			cBox_Nivells.setFont(new java.awt.Font("ARIAL",1, 14));
			cBox_Nivells.setBounds(220, 120, 160, 30);
			add(cBox_Nivells);
			
			//label missatge
			nivellPanelMessages = new JLabel("",JLabel.CENTER);
			nivellPanelMessages.setBounds(100, 170, 400, 40 );
			nivellPanelMessages.setHorizontalAlignment(SwingConstants.CENTER);
			nivellPanelMessages.setFont(new java.awt.Font("ARIAL",0,17));
			add(nivellPanelMessages);
			
			//boto sortir
			JButton sortirBtn = new JButton("Sortir");
			sortirBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					loginPanelMessages.setText("");
					setContentPane(loginPanel);
					loginPanel.updateUI();
					jugarPartidaBtn.setEnabled(true);
				}
			});
			sortirBtn.setBounds( 150 , 280, 100, 35);
			sortirBtn.setFont(new java.awt.Font("ARIAL", Font.BOLD, 16));
			add(sortirBtn);
			
			//boto ok
			jugarPartidaBtn = new JButton("Jugar");
			jugarPartidaBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String cat = String.valueOf(cBox_Nivells.getSelectedItem());
					try {
						presentationController.prCrearPartida(cat);
						setContentPane(matchPanel);
						
					} catch (Exception e) {
						mostrarAvis(e.toString(), 1);
					}
					matchPanel.updateUI();
				}
			});

			jugarPartidaBtn.setBounds( 350 , 280, 100, 35);
			jugarPartidaBtn.setFont(new java.awt.Font("ARIAL", Font.BOLD, 16));
			add(jugarPartidaBtn);
			
		}
	}


	public JugarPartidaView(CtrlJugarPartidaPresentacio jpc) {
		presentationController = jpc;
		loginPanel = new JLogin();
		selecNivellPanel = new JSelecNivell();
		matchPanel = new JPartida();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600 , 400);
		this.setTitle("Buscamines");
		setContentPane(loginPanel);
	}
	
        public void mostraNivells(ArrayList<String> nivells) {
                cBox_Nivells.removeAllItems();
                for (String st : nivells) {
                	cBox_Nivells.addItem(st);
                }
        }
	
	public void mostrarAvis(String text,int panel) {
		if(panel==0) {
			//Login
			loginPanelMessages.setText(text);
			loginPanelMessages.setForeground(Color.red);
		}
		else if(panel==1) {
			//Nivells
			nivellPanelMessages.setText(text);
			nivellPanelMessages.setForeground(Color.red);
			jugarPartidaBtn.setEnabled(false);
		}
		else {
			//Partida
			matchPanelMessages.setText(text);
			matchPanelMessages.setForeground(Color.red);
		}		
	}
	
	public void actualitzaTirades(int t) {
		this.tir.setText(String.valueOf(t));
	}
	
	
	public void aturarPartida() {
		/**
		 * Atura la partida i torna al login
		 */
		setContentPane(loginPanel);
		loginPanel.updateUI();
	}
	
	public void close() {
		System.exit(-1);
	}
	
	public void finalitzaPartida(boolean guanyada, int puntuacio) {
		/**
		 * Cridada quan s'ha finalitzat la partida, mostra la puntuacio en cas de Guanyada
		 */
		timer.stop();
		for(JButton[] buttons : but)
			for(JButton b : buttons) {
				for(MouseListener l : b.getMouseListeners()) b.removeMouseListener(l);
				b.setEnabled(false);
			}
		if (guanyada) {
			matchPanelMessages.setForeground( new Color( 0, 113, 0 ) );
			matchPanelMessages.setText("Enhorabona has guanyat la partida! Puntuacio: "+puntuacio);
		}
		else {
			matchPanelMessages.setText("Has trobat una mina i has perdut!");
			matchPanelMessages.setForeground(Color.RED);
		}
		matchPanelMessages.repaint();
		stopPartidaBtn.setVisible(false);
		finishPartidaBtn.setVisible(true);
		
	}
	
}
