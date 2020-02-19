package views;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * Vista principal de los datos de un Doctor
 * 
 * @author Axel Pelaez
 */

public class VistaDoctor extends Vista{
	// Componentes interficie


	private JPanel panelCenterButtons = new JPanel();

	

	//SCROLL PANEL
	
	final String[] fila1 ={"<html><LEFT>Id</LEFT></html>","<html><LEFT>Tipo</LEFT></html>","<html><LEFT>Info</LEFT></html>"};
	final Object[][] datos={};
	
	private JTable tabla = new JTable();
	private JScrollPane scrollPanel = new JScrollPane();
	

	// CENTER

	private JLabel labelPanel1 = new JLabel();
	private JLabel labelPanelID = new JLabel("ID:");
	private JLabel labelPanelNombre = new JLabel("Nombre:");
	private JLabel labelPanelMaxTurnos = new JLabel("Max. Turnos:");
	private JLabel labelPanelSueldo = new JLabel("Sueldo:");

	private JTextField textID = new JTextField();
	private JTextField textNombre = new JTextField();
	private JTextField textMaxTurnos = new JTextField();
	private JTextField textSueldo = new JTextField();

	private JButton buttonEliminar = new JButton(
			"<html><CENTER>Eliminar<br/>Restriccion</CENTER>");
	private JButton buttonAnadir = new JButton(
			"<html><CENTER>Anadir<br/>Restriccion</CENTER>");
	private JButton buttonGuardar = new JButton(
			"<html><CENTER>Guardar<br/>cambios</CENTER>");
	private JButton buttonVolver = new JButton("Volver");

	// METODOS PRIVADOS



	protected void init_panelContents() {

		panelContents.setLayout(new BorderLayout());
		// Componentes
		panelContents.add(panelCenterButtons, BorderLayout.CENTER);

	}

	private void inicializar_panelCenterButtons() {

		// //// START: GESTIONADO POR EL BUILDER NO TOCAR

		// BOTONES
		panelCenterButtons.setBorder(new EmptyBorder(70, 20, 20, 0));
		panelCenterButtons.setLayout(null);

		buttonAnadir.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonAnadir.setBounds(449, 128, 173, 40);

		buttonEliminar.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonEliminar.setBounds(449, 180, 173, 40);

		buttonGuardar.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonGuardar.setBounds(449, 233, 173, 40);

		buttonVolver.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonVolver.setBounds(52, 323, 157, 25);

		// labels
		labelPanel1.setFont(new Font("Arial", Font.PLAIN, 12));
		labelPanel1.setBounds(44, 16, 361, 25);

		labelPanelID.setFont(new Font("Arial", Font.PLAIN, 12));
		labelPanelID.setBounds(52, 45, 28, 30);

		labelPanelNombre.setFont(new Font("Arial", Font.PLAIN, 12));
		labelPanelNombre.setBounds(52, 69, 71, 33);

		labelPanelMaxTurnos.setFont(new Font("Arial", Font.PLAIN, 12));
		labelPanelMaxTurnos.setBounds(340, 45, 101, 31);

		labelPanelSueldo.setFont(new Font("Arial", Font.PLAIN, 12));
		labelPanelSueldo.setBounds(340, 70, 88, 31);

		// textArea
		textID.setBounds(124, 53, 166, 17);
		textNombre.setBounds(124, 78, 166, 17);
		textMaxTurnos.setBounds(449, 53, 166, 17);
		textSueldo.setBounds(449, 78, 166, 17);

		// Scroll
		panelCenterButtons.add(scrollPanel);
		
		scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setBounds(52, 107, 377, 191);
		scrollPanel.setViewportView(tabla);
		tabla.setFont(new Font("Arial", Font.PLAIN, 12));
		
		tabla.setForeground(Color.red);
		tabla.setBackground(Color.white);

		// / END: GESTIONADO POR EL BUILDER NO TOCAR


		// Components
		

		panelCenterButtons.add(labelPanel1);
		panelCenterButtons.add(labelPanelID);
		panelCenterButtons.add(labelPanelNombre);
		panelCenterButtons.add(labelPanelMaxTurnos);
		panelCenterButtons.add(labelPanelSueldo);

		panelCenterButtons.add(textID);
		panelCenterButtons.add(textNombre);
		panelCenterButtons.add(textMaxTurnos);
		panelCenterButtons.add(textSueldo);

		panelCenterButtons.add(buttonEliminar);
		panelCenterButtons.add(buttonAnadir);
		panelCenterButtons.add(buttonGuardar);
		panelCenterButtons.add(buttonVolver);
		
		buttonEliminar.setEnabled(false);
		
		buttonVolver.setToolTipText("[ESC]");
		buttonGuardar.setToolTipText("[CTRL+S]");
		buttonAnadir.setToolTipText("[CTRL+N]");
		buttonEliminar.setToolTipText("[CTRL+D]");
	}


	protected void assign_listenersComponents() {

		ListSelectionModel listSelectionModel = tabla.getSelectionModel();
		listSelectionModel.addListSelectionListener(new ListSelectionListener() {
		        public void valueChanged(ListSelectionEvent e) { 
		            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
		            buttonEliminar.setEnabled(!lsm.isSelectionEmpty()); 
		        }
		});
		
		
		
		buttonGuardar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (ctrlPresentacion.isEmptyDocActual()) {// Se a entrado para crear

					
					try {
						
						if (textID.getText().isEmpty())throw new IOException("El doctor no tiene ID!");
						if (textMaxTurnos.getText().isEmpty())throw new IOException("El doctor no tiene Maximo de Turnos!");
						if (textSueldo.getText().isEmpty())throw new IOException("El doctor no tiene Sueldo!");
						if (textNombre.getText().isEmpty())throw new IOException("El doctor no tiene Nombre!");
						
						String nameDoc = textNombre.getText();
						nameDoc = nameDoc.replaceAll(" ", "%");
						int id = Integer.parseInt(textID.getText());
						int maxTurnos = Integer.parseInt(textMaxTurnos.getText());
						Double sueldo = Double.parseDouble(textSueldo.getText());
						
						
						
						ctrlPresentacion.crearDoctor(nameDoc, id, maxTurnos,sueldo);
						ctrlPresentacion.setDocActual(textID.getText(), nameDoc ,
													textSueldo.getText(), textMaxTurnos.getText());
						ctrlPresentacion.clearDocActual();
						ctrlPresentacion.changeView("vistaPlantillaDoctores", panelContents);
						
					} catch (IOException eX) {
						rejectedOperationDialog(eX);
					} catch (NumberFormatException nE) {
						rejectedOperationDialog("Alguno de los factores no es un valor correcto");
					}

				}

				else {// Se a entrado para modificar
					
					String[] docActual = ctrlPresentacion.getDocActual();
					
					if (!docActual.equals(textID.getText())
							|| !docActual.equals(textNombre.getText())
							|| !docActual.equals(textSueldo.getText())
							|| !docActual.equals(textMaxTurnos.getText())) {
					

						try {
							
							if (textID.getText().isEmpty())throw new IOException("El doctor no tiene ID!");
							if (textMaxTurnos.getText().isEmpty())throw new IOException("El doctor no tiene Maximo de Turnos!");
							if (textSueldo.getText().isEmpty())throw new IOException("El doctor no tiene Sueldo!");
							if (textNombre.getText().isEmpty())throw new IOException("El doctor no tiene Nombre!");
							
							String nombre = textNombre.getText();
							
							nombre = nombre.replaceAll(" ", "%");
							
							int idDoc = Integer.parseInt(textID.getText());
							
							int maxTurnos = Integer.parseInt(textMaxTurnos.getText());
							
							Double sueldo = Double.parseDouble(textSueldo.getText());
							
							
							ctrlPresentacion.setDocActual(textID.getText(), nombre ,
															textSueldo.getText(), textMaxTurnos.getText());
							ctrlPresentacion.setDoctor(idDoc, nombre, maxTurnos, sueldo);
							
							ctrlPresentacion.clearDocActual();
							
							ctrlPresentacion.changeView("vistaPlantillaDoctores", panelContents);
							
						} catch (IOException eX) {
							rejectedOperationDialog(eX);
						} catch (NumberFormatException nE) {
							rejectedOperationDialog("Alguno de los factores no es un valor correcto");
						}
					}
				}
					
				
			}

		});

		
		
		buttonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPresentacion.clearDocActual();
				ctrlPresentacion.changeView("vistaPlantillaDoctores", panelContents);
			}
		});

		buttonAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (ctrlPresentacion.isEmptyDocActual()) {// Se a entrado para crear

					try {
						if (textID.getText().isEmpty())throw new IOException("El doctor no tiene ID!");
						if (textMaxTurnos.getText().isEmpty())throw new IOException("El doctor no tiene Maximo de Turnos!");
						if (textSueldo.getText().isEmpty())throw new IOException("El doctor no tiene Sueldo!");
						if (textNombre.getText().isEmpty())throw new IOException("El doctor no tiene Nombre!");
						
						String nameDoc = textNombre.getText();
						nameDoc = nameDoc.replaceAll(" ", "%");
						int id = Integer.parseInt(textID.getText());
						int maxTurnos = Integer.parseInt(textMaxTurnos.getText());
						Double sueldo = Double.parseDouble(textSueldo.getText());
						if (nameDoc.isEmpty())throw new IOException("El doctor no tiene nombre");
						
						ctrlPresentacion.crearDoctor(nameDoc, id, maxTurnos,sueldo);
						
						ctrlPresentacion.setDocActual(textID.getText(), nameDoc , 
													textSueldo.getText(), textMaxTurnos.getText());
						
						ctrlPresentacion.changeView("vistaRestriccion", panelContents);
						
					} catch (IOException eX) {
						rejectedOperationDialog(eX);
					} catch (NumberFormatException nE) {
						rejectedOperationDialog("Alguno de los factores no es un valor correcto");
					}
					

			}
			else{ //He entrado para modificar
				
				
				try {
					
					if (textID.getText().isEmpty())throw new IOException("El doctor no tiene ID!");
					if (textMaxTurnos.getText().isEmpty())throw new IOException("El doctor no tiene Maximo de Turnos!");
					if (textSueldo.getText().isEmpty())throw new IOException("El doctor no tiene Sueldo!");
					if (textNombre.getText().isEmpty())throw new IOException("El doctor no tiene Nombre!");
					
					int id = Integer.parseInt(textID.getText());
					String nameDoc = textNombre.getText();
					nameDoc = nameDoc.replaceAll(" ", "%");
					int maxTurnos = Integer.parseInt(textMaxTurnos.getText());
					Double sueldo = Double.parseDouble(textSueldo.getText());
					
					String[] docActual = ctrlPresentacion.getDocActual();

					if (!docActual.equals(textID.getText())
							|| !docActual.equals(textNombre.getText())
							|| !docActual.equals(textSueldo.getText())
							|| !docActual.equals(textMaxTurnos.getText())) {
						
						ctrlPresentacion.setDocActual(textID.getText(), nameDoc , 
														textSueldo.getText(), textMaxTurnos.getText());
						
						ctrlPresentacion.setDoctor(id, nameDoc, maxTurnos, sueldo);
						

					}
					
				ctrlPresentacion.changeView("vistaRestriccion", panelContents);
				} catch (IOException eX) {
					rejectedOperationDialog(eX);
				} catch (NumberFormatException nE) {
					rejectedOperationDialog("Alguno de los factores no es un valor correcto");
				}
			}
				
				
			}

		});

		buttonEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tabla.getSelectedRow();
				String id = (String) tabla.getValueAt(row, 0);
				
				Object[] options = {"Aceptar", "Cancelar"};
				int option = JOptionPane.showOptionDialog(null,
						"Esta seguro de que quiere eliminar la restriccion de id "
						+ id + " ?",
					    "Alert",
					    JOptionPane.YES_NO_CANCEL_OPTION,
					    JOptionPane.WARNING_MESSAGE,
					    null,
					    options,
					    options[1]);
				
				
				if(option == JOptionPane.YES_OPTION){
					try {
						ctrlPresentacion.eliminarRest(Integer.parseInt(id),ctrlPresentacion.getIdDocAc());
					} catch (IOException e1) {
						//error
						
					}
					loadRest();
				}
			}
			
		});

		 panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonEliminar");
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonGuardar");
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonAnadir");
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "buttonVolver");
	     panelContents.getActionMap().put("buttonEliminar", new AbstractAction() {
	            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

				@Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonEliminar.doClick();
	            }
	        });
	        panelContents.getActionMap().put("buttonGuardar", new AbstractAction() {
	            /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonGuardar.doClick();
	            }
	        });
	        panelContents.getActionMap().put("buttonAnadir", new AbstractAction() {
	            /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonAnadir.doClick();
	            }
	        });
	        panelContents.getActionMap().put("buttonVolver", new AbstractAction() {
	            /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonVolver.doClick();
	            }
	        });

	}
	
	private void loadRest() {
		ArrayList<ArrayList<String>> restricciones = new ArrayList<ArrayList<String>>() ;
		if(! ctrlPresentacion.isEmptyDocActual())
			restricciones = ctrlPresentacion.loadRest();
		
		DefaultTableModel dtm = new DefaultTableModel(datos,fila1){
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		
		}; 
		//Que no se muevan las columnas
		tabla.getTableHeader().setReorderingAllowed(false);
	
		if(!restricciones.isEmpty()){
			for(ArrayList<String> rest : restricciones){
				String[] row = new String[rest.size()];
				row = rest.toArray(row);
				dtm.addRow(row); 
			}
		
		}
		
		
		tabla.setModel(dtm);  
		tabla.revalidate();
		tabla.repaint();
		
		
		tabla.getColumnModel().getColumn(0).setMinWidth(0);
		tabla.getColumnModel().getColumn(0).setMaxWidth(0);
		
		tabla.getColumnModel().getColumn(1).setPreferredWidth(125);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(200);
		
		
		
	}
	
	// METODOS PUBLICOS

	public VistaDoctor(CtrlPresentacion pCtrlPresentacion) {
		super(pCtrlPresentacion);

	}

	public void init() {
		init_frameView();
		init_panelContents();
		inicializar_panelCenterButtons();
		assign_listenersComponents();

	}


	public void showPanel() {

		loadRest();
		
		String[] docAc = ctrlPresentacion.getDocActual();
		
		textID.setText(docAc[0]);
		textNombre.setText(docAc[1]);
		textSueldo.setText(docAc[2]);
		textMaxTurnos.setText(docAc[3]);

		panelContents.setVisible(true);
	}


}
