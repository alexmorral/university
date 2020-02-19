package views;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.border.EmptyBorder;
/**
 * Vista para la creacion de un hospital
 * @author Alex Morral
 */
public class VistaCrearHospital extends Vista {
	
//	private CtrlPresentacion ctrlPresentacion;
	private int editing;
	//ID VISTA 13
	
	//Componentes interficie
//	private JFrame frameView;
//	private JPanel panelContents = new JPanel();
	private JPanel panelCenterButtons = new JPanel();
	//CENTER
	private JLabel labelPanel1 = new JLabel("<html><u><b>Crear Hospital</b></u>");
	JButton btnCrearHospital = new JButton("Crear Hospital");
	private JButton btnCancel = new JButton("Cancelar");
	private JTextField nameHospTextField;
	private JTextField mTextField;
	private JTextField tTextField;
	private JTextField nTextField;
	
	
	//METODOS PRIVADOS
//	private void inicializar_frameView() {
//		/*** DESCOMENTAR PARA EDITAR *
//		frameView =  new JFrame("Programador Guardias");
//		frameView.setMinimumSize(new Dimension(700, 400));
//		frameView.setPreferredSize(frameView.getMinimumSize());
//		frameView.setResizable(false);
//		frameView.setLocationRelativeTo(null);
//		frameView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frameView.getContentPane().setBackground(Color.WHITE);
//		/*** END DESCOMENTAR PARA EDITAR */
//		frameView = ctrlPresentacion.getFrame();
//		JPanel contentPane = (JPanel) frameView.getContentPane();
//		contentPane.setLayout(null);
//		panelContents.setBounds(0,0,700,378);
//		contentPane.add(panelContents);
//	}
	
	protected void init_panelContents() {
		panelContents.setLayout(new BorderLayout());
		// Componentes
		panelContents.add(panelCenterButtons, BorderLayout.CENTER);
	}
	
	private void inicializar_panelCenterButtons() {
		////// START: GESTIONADO POR EL BUILDER NO TOCAR
		panelCenterButtons.setBorder(new EmptyBorder(70, 20, 20, 0));
		btnCancel.setFont(new Font("Arial", Font.PLAIN, 12));
		btnCancel.setBounds(187, 322, 140, 25);
		labelPanel1.setFont(new Font("Arial", Font.PLAIN, 14));
		labelPanel1.setBounds(66, 30, 112, 15);
		panelCenterButtons.setLayout(null);
		/// END: GESTIONADO POR EL BUILDER NO TOCAR
		// Components
		panelCenterButtons.add(labelPanel1);
		panelCenterButtons.add(btnCancel);
		
		JLabel lblNombreDelHospital = new JLabel("Nombre del Hospital: ");
		lblNombreDelHospital.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNombreDelHospital.setBounds(205, 90, 118, 16);
		panelCenterButtons.add(lblNombreDelHospital);
		
		nameHospTextField = new JTextField();
		nameHospTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		nameHospTextField.setToolTipText("");
		nameHospTextField.setBounds(335, 84, 145, 28);
		panelCenterButtons.add(nameHospTextField);
		nameHospTextField.setColumns(10);
		
		JLabel lblFactorDeTurno = new JLabel("Factor de Turno: ");
		lblFactorDeTurno.setFont(new Font("Arial", Font.PLAIN, 12));
		lblFactorDeTurno.setBounds(231, 149, 92, 16);
		panelCenterButtons.add(lblFactorDeTurno);
		
		mTextField = new JTextField();
		mTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		mTextField.setText("1.0");
		mTextField.setBounds(335, 143, 40, 28);
		panelCenterButtons.add(mTextField);
		mTextField.setColumns(10);
		
		tTextField = new JTextField();
		tTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		tTextField.setText("1.0");
		tTextField.setColumns(10);
		tTextField.setBounds(388, 143, 40, 28);
		panelCenterButtons.add(tTextField);
		
		nTextField = new JTextField();
		nTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		nTextField.setText("1.0");
		nTextField.setColumns(10);
		nTextField.setBounds(440, 143, 40, 28);
		panelCenterButtons.add(nTextField);
		
		JLabel lblM = new JLabel("M");
		lblM.setFont(new Font("Arial", Font.PLAIN, 12));
		lblM.setHorizontalAlignment(SwingConstants.CENTER);
		lblM.setBounds(341, 124, 24, 16);
		panelCenterButtons.add(lblM);
		
		JLabel lblT = new JLabel("T");
		lblT.setFont(new Font("Arial", Font.PLAIN, 12));
		lblT.setHorizontalAlignment(SwingConstants.CENTER);
		lblT.setBounds(393, 124, 24, 16);
		panelCenterButtons.add(lblT);
		
		JLabel lblN = new JLabel("N");
		lblN.setFont(new Font("Arial", Font.PLAIN, 12));
		lblN.setHorizontalAlignment(SwingConstants.CENTER);
		lblN.setBounds(445, 124, 24, 16);
		panelCenterButtons.add(lblN);
		btnCrearHospital.setFont(new Font("Arial", Font.PLAIN, 12));
		
	
		btnCrearHospital.setBounds(371, 322, 140, 25);
		panelCenterButtons.add(btnCrearHospital);
		
		
		btnCrearHospital.setToolTipText("[CTRL+N]");
		btnCancel.setToolTipText("[ESC]");
		
		
	}
//	private void inicializarComponents() {
//		inicializar_frameView();
//		inicializar_panelContents();
//		inicializar_panelCenterButtons();
//		assignar_listenersComponents();
//	}
	
	protected void assign_listenersComponents() {
		
		btnCrearHospital.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nameHosp = nameHospTextField.getText();
				nameHosp=nameHosp.replaceAll(" ", "%");
				try {
					Double factM = Double.parseDouble(mTextField.getText());
					Double factT = Double.parseDouble(tTextField.getText());
					Double factN = Double.parseDouble(nTextField.getText());
					if(nameHosp.isEmpty()) throw new IOException("No le has puesto un nombre al hospital");
					if(editing==1){
						ctrlPresentacion.modificarHospital(nameHosp, factM, factT, factN);
					}else {
						ctrlPresentacion.crearHospital(nameHosp, factM, factT, factN);
					}
				} catch (IOException eX) {
					JOptionPane.showMessageDialog(null, eX, "Error",JOptionPane.ERROR_MESSAGE); 
				} catch (NumberFormatException nE){
					JOptionPane.showMessageDialog(null, "Alguno de los factores no es un valor correcto", "Error", JOptionPane.ERROR_MESSAGE); 
				}
				ctrlPresentacion.changeView("vistaCjtHospitales", panelContents);
			}
		});
		
		
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPresentacion.changeView("vistaCjtHospitales", panelContents);
			}
		});
		
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK), "btnCrearHospital");
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "btnCancel");
	     panelContents.getActionMap().put("btnCrearHospital", new AbstractAction() {
			private static final long serialVersionUID = 1L;

				@Override
	            public void actionPerformed(ActionEvent e) {
	            	btnCrearHospital.doClick();
	            }
	        });
	       
	        panelContents.getActionMap().put("btnCancel", new AbstractAction() {
	        	private static final long serialVersionUID = 1L;
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	btnCancel.doClick();
	            }
	        });
		
		
		
	}
	
	
	//METODOS PUBLICOS
	public VistaCrearHospital(CtrlPresentacion pCtrlPresentacion) {
//		ctrlPresentacion = pCtrlPresentacion;
		super(pCtrlPresentacion);
		/** DESCOMENTAR PARA EDITAR *
		 inicializarComponents();
		 /** END DESCOMENTAR PARA EDITAR*/
	}
	
	public void init() {
//		inicializarComponents();
		init_frameView();
		init_panelContents();
		inicializar_panelCenterButtons();
		assign_listenersComponents();
	}
	
	public void clearData() {
		nameHospTextField.setText("");
		mTextField.setText("1.0");
		tTextField.setText("1.0");
		nTextField.setText("1.0");
	}
	
	public void setEditing(int newVal) {
		editing = newVal;
	}
	
	public void funcEditing() {
		if(editing == 1) {
			btnCrearHospital.setText("Modificar Hospital");
			try {
				ArrayList<String> info = ctrlPresentacion.getInfoHosp();
				nameHospTextField.setText(info.get(1).replace("%", " "));
				mTextField.setText(info.get(2));
				tTextField.setText(info.get(3));
				nTextField.setText(info.get(4));
			} catch(IOException eX) {
				JOptionPane.showMessageDialog(null, eX, "Error",JOptionPane.ERROR_MESSAGE); 
			}
		} else {
			btnCrearHospital.setText("Crear Hospital");
		}
	}
//	public JPanel getPanel() {
//		return panelContents;
//	}
//
//	public void showView() {
//		frameView.setVisible(true);
//	}
//	
//	public void hidePanel() {
//		panelContents.setVisible(false);
//	}
	public void showPanel() {
		clearData();
		funcEditing();
		panelContents.setVisible(true);
	}
	
//	public void enableView() {
//		frameView.setEnabled(true);
//	}
//	
//	public void disableView() {
//		frameView.setEnabled(false);
//	}
}
