
package views;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Vista principal del Conjunto soluciones para un Hospital
 * @author Alex Morral
 */

public class VistaGestionSoluciones {	/* Atributos y metodos privados */
	
	private CtrlPresentacion ctrlPresentacion;
	

	//Componentes interficie
	private JFrame frameView;
	private JPanel panelContents = new JPanel();
	private JPanel panelCenterButtons = new JPanel();
	
	//Componentes
	private JLabel labelPanel1 = new JLabel();
	private JButton buttonGenerarFF = new JButton("<html><CENTER>Generar Solucion <br/>[FordFulkerson]</CENTER>");
	private JButton buttonGenerarED = new JButton("<html><CENTER>Generar Solucion <br/>[EdmondsKarp]</CENTER>");
	private JButton buttonGenerarDI = new JButton("<html><CENTER>Generar Solucion Optima <br/>[Dijkstra]</CENTER>");
	private JButton buttonModSol = new JButton("<html><CENTER> Modificar <br/> Solucion</CENTER>");
	private JButton buttonBorrarSol = new JButton("<html><CENTER> Eliminar <br/> Solucion</CENTER>");
	private JButton buttonVolver = new JButton("Volver");
	
	private final JList<String> list = new JList<String>();
	private final JScrollPane scrollPanel = new JScrollPane();
	
	//METODOS PRIVADOS
	private void inicializar_frameView() {
		/** DESCOMENTAR PARA EDITAR *
		frameView =  new JFrame("Programador Guardias");
		frameView.setMinimumSize(new Dimension(700, 400));
		frameView.setPreferredSize(frameView.getMinimumSize());
		frameView.setResizable(false);
		frameView.setLocationRelativeTo(null);
		frameView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameView.getContentPane().setBackground(Color.WHITE);
		/*** END DESCOMENTAR PARA EDITAR */
		frameView = ctrlPresentacion.getFrame();
		JPanel contentPane = (JPanel) frameView.getContentPane();
		contentPane.setLayout(null);
		panelContents.setBounds(0,0,700,378);
		contentPane.add(panelContents);
	}
	
	private void inicializar_panelContents() {
		panelContents.setLayout(new BorderLayout());
		// Componentes
		panelContents.add(panelCenterButtons, BorderLayout.CENTER);
		
		
		
		
	}
	
	private void inicializar_panelCenterButtons() {
		////// START: GESTIONADO POR EL BUILDER NO TOCAR
		panelCenterButtons.setBorder(new EmptyBorder(70, 20, 20, 0));
		
		buttonGenerarFF.setBounds(301, 63, 298, 53);
		buttonGenerarFF.setFont(new Font("Arial", Font.PLAIN, 12));
		
		buttonGenerarED.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonGenerarED.setBounds(301, 128, 298, 53);
		
		buttonGenerarDI.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonGenerarDI.setBounds(301, 193, 298, 53);
		
		buttonModSol.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonModSol.setBounds(301, 257, 138, 53);
		
		buttonBorrarSol.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonBorrarSol.setBounds(461, 257, 138, 53);
		
		buttonVolver.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonVolver.setBounds(40, 324, 157, 25);
		
		labelPanel1.setFont(new Font("Arial", Font.PLAIN, 15));
		labelPanel1.setBounds(40, 26, 361, 25);
		
		panelCenterButtons.setLayout(null);
		/// END: GESTIONADO POR EL BUILDER NO TOCAR
		// Components
		scrollPanel.setBounds(40, 57, 231, 253);
		scrollPanel.setPreferredSize(new Dimension(150, 10));
		scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelCenterButtons.add(scrollPanel);
		list.setFont(new Font("Arial", Font.PLAIN, 13));
		scrollPanel.setViewportView(list);
		panelCenterButtons.setBounds(254, 125, 150, 0);
		panelCenterButtons.setPreferredSize(new Dimension(150, 0));
		
		panelCenterButtons.add(labelPanel1);
		panelCenterButtons.add(buttonVolver);
		panelCenterButtons.add(buttonGenerarFF);
		panelCenterButtons.add(buttonGenerarDI);
		panelCenterButtons.add(buttonGenerarED);
		panelCenterButtons.add(buttonModSol);
		panelCenterButtons.add(buttonBorrarSol);
		
		buttonModSol.setEnabled(false);
		buttonBorrarSol.setEnabled(false);
		
		buttonVolver.setToolTipText("[ESC]");
	    buttonGenerarFF.setToolTipText("[CTRL+F]");
	    buttonGenerarED.setToolTipText("[CTRL+E]");
	    buttonGenerarDI.setToolTipText("[CTRL+O]");
	    buttonBorrarSol.setToolTipText("[CTRL+D]");
	    buttonModSol.setToolTipText("[CTRL+M]");
	}
	private void inicializarComponents() {
		inicializar_frameView();
		inicializar_panelContents();
		inicializar_panelCenterButtons();
		assignar_listenersComponents();
	}
	
	private void assignar_listenersComponents() {
		
		ListSelectionModel listSelectionModel = list.getSelectionModel();
		listSelectionModel.addListSelectionListener(new ListSelectionListener() {
		        public void valueChanged(ListSelectionEvent e) { 
		            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
		            buttonModSol.setEnabled(!lsm.isSelectionEmpty()); 
		            buttonBorrarSol.setEnabled(!lsm.isSelectionEmpty()); 
		       
		        }
		});

		buttonGenerarFF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ctrlPresentacion.findSolution(1);
					ctrlPresentacion.setSolId(-1);
					ctrlPresentacion.changeView("vistaSolucion", panelContents);
				} catch(IOException eX) {
					JOptionPane.showMessageDialog(null, eX, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			
		});
		

		
		buttonGenerarED.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ctrlPresentacion.findSolution(2);
					ctrlPresentacion.setSolId(-1);
					ctrlPresentacion.changeView("vistaSolucion", panelContents);
				} catch(IOException eX) {
					JOptionPane.showMessageDialog(null, eX, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			
		});
		
		buttonGenerarDI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ctrlPresentacion.findSolution(3);
					ctrlPresentacion.setSolId(-1);
					ctrlPresentacion.changeView("vistaSolucion", panelContents);
				} catch(IOException eX) {
					JOptionPane.showMessageDialog(null, eX, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			
		});
		
	
		buttonModSol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(list.isSelectionEmpty()) throw new IOException("Debe seleccionar un hospital");
					String[] parts = list.getSelectedValue().split(" ");
					int idSol = Integer.parseInt(parts[0]);
					ctrlPresentacion.cargarSol(idSol);
					ctrlPresentacion.setSolId(idSol);
					ctrlPresentacion.changeView("vistaSolucion", panelContents);
				} catch (IOException eX){
					JOptionPane.showMessageDialog(null, eX, "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
			
			
		});
		
		buttonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPresentacion.changeView("vistaGestion", panelContents);
				
			}
			
			
		});
		
		buttonBorrarSol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Aceptar", "Cancelar"};
				int returnVal = JOptionPane.showOptionDialog(null,
						"Esta seguro de que desea eliminar la Solucion: ?",
					    "Alert",
					    JOptionPane.YES_NO_CANCEL_OPTION,
					    JOptionPane.WARNING_MESSAGE,
					    null,
					    options,
					    options[1]);
					if(returnVal == JOptionPane.YES_OPTION) {
						try {
						if(list.isSelectionEmpty()) throw new IOException("Debe seleccionar un Solucion!");
						String[] parts = (list.getSelectedValue()).split(" ");
						ctrlPresentacion.deleteSol(Integer.parseInt(parts[0]));
						cargarSoluciones();
						
						} catch (IOException eX) {
							JOptionPane.showMessageDialog(null, eX, "Error", JOptionPane.ERROR_MESSAGE); 
						}
					}
			}
			
		});
		
	      
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonGenerarFF");
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonGenerarED");
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonGenerarDI");
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonModSol");
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonBorrarSol");
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "buttonVolver");
	    
	     panelContents.getActionMap().put("buttonGenerarFF", new AbstractAction() {
			private static final long serialVersionUID = 1L;

				@Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonGenerarFF.doClick();
	            }
	        });
	        panelContents.getActionMap().put("buttonGenerarED", new AbstractAction() {
	        	private static final long serialVersionUID = 1L;
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonGenerarED.doClick();
	            }
	        });
	        panelContents.getActionMap().put("buttonGenerarDI", new AbstractAction() {
	        	private static final long serialVersionUID = 1L;
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonGenerarDI.doClick();
	            }
	        });
	        panelContents.getActionMap().put("buttonModSol", new AbstractAction() {
	        	private static final long serialVersionUID = 1L;
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonModSol.doClick();
	            }
	        });
	        panelContents.getActionMap().put("buttonBorrarSol", new AbstractAction() {
	        	private static final long serialVersionUID = 1L;
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonBorrarSol.doClick();
	            }
	        });
	        panelContents.getActionMap().put("buttonVolver", new AbstractAction() {
	        	private static final long serialVersionUID = 1L;
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonVolver.doClick();
	            }
	        });
	}
		
		
	
	
	
	/* Constructoras y metodos publicos */
	public VistaGestionSoluciones(CtrlPresentacion pCtrlVistaPrincipal) {
		ctrlPresentacion = pCtrlVistaPrincipal;
		/* DESCOMENTAR PARA EDITAR *
		 inicializarComponents();
		/*  END DESCOMENTAR PARA EDITAR */
	}
	
	public void init() {
		 inicializarComponents();
	}
	
	public void cargarHospital() {
		
		String name = new String(ctrlPresentacion.getNameHospital().replaceAll("%", " "));
		//name = name.replace("%", " ");
		name = 	"Soluciones del Hospital: "	+ name;
		
		labelPanel1.setText(name);
		
	}
	
	private void cargarSoluciones() {
		list.clearSelection();
		ArrayList<String> lista = ctrlPresentacion.getAllIdSolutions();
		
		DefaultListModel<String> model = new DefaultListModel<String>();
	    for(String st : lista){
	         model.addElement(st);
	    }    
	    list.setModel(model);  
	    list.revalidate();
		list.repaint();
	}
	
	public JPanel getPanel() {
		return panelContents;
	}
	
	public void hidePanel() {
		panelContents.setVisible(false);
	}
	public void showPanel() {
		cargarHospital();
		cargarSoluciones();
		panelContents.setVisible(true);
	}
	
	public void showView() {
		panelContents.setVisible(true);
	}
	
	public void enableView() {
		frameView.setEnabled(true);
	}
	
	public void disableView() {
		frameView.setEnabled(false);
	}
	
	
	
}