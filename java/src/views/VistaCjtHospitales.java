package views;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Vista principal del Conjunto de Hospitales
 * @author Alex Morral
 */

// ID VISTA 1

public class VistaCjtHospitales extends Vista {
	/* Atributos y metodos privados */
//	private CtrlPresentacion ctrlPresentacion;
	private ArrayList<String> hospitales;
	

	
	//-- Components --//
//	private JFrame frameView;
//	private JPanel panelContents = new JPanel();
	private JPanel topPanel = new JPanel();
	private final JButton btnCrearHospital = new JButton("<html><CENTER>Crear Hospital</html></CENTER>");
	private final JButton btnEliminarHospital = new JButton("<html><CENTER>Eliminar Hospital</html></CENTER>");
	private final JButton btnImportarHospital = new JButton("<html><CENTER>Importar Hospital</html></CENTER>");
	private final JButton btnSeleccionarHospital = new JButton("<html><CENTER>Seleccionar Hospital</html></CENTER>");
	private final JButton btnmodificarHospital = new JButton("<html><CENTER>Modificar Hospital</html></CENTER>");
	private final JList<String> list = new JList<String>();
	private final JScrollPane scrollPanel = new JScrollPane();
	private final JLabel titleLabel = new JLabel("Conjunto de Hospitales");
	private final JPanel mediumPanel = new JPanel();
	private JPanel midPanel;
	private final JPanel panelInfo = new JPanel();
	private final JLabel lblInformacion = new JLabel("INFORMACION");
	private final JLabel lblFactm = new JLabel("FactM:");
	private final JLabel lblFactt = new JLabel("FactT:");
	private final JLabel lblFactn = new JLabel("FactN:");
	private final JLabel nDocField = new JLabel("");
	private final JLabel turnosField = new JLabel("");
	private final JLabel factMField = new JLabel("");
	private final JLabel factTField = new JLabel("");
	private final JLabel factNField = new JLabel("");
	private final JLabel solField = new JLabel("");

	//-- Metodos privados --//
	/*private void init_frameView1() {
		/*** DESCOMENTAR PARA EDITAR *
		frameView =  new JFrame("Programador Guardias");
		frameView.setMinimumSize(new Dimension(700, 400));
		frameView.setPreferredSize(frameView.getMinimumSize());
		frameView.setResizable(false);
		frameView.setLocationRelativeTo(null);
		frameView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameView.getContentPane().setBackground(Color.WHITE);
		
		frameView = ctrlPresentacion.getFrame();
		JPanel contentPane = (JPanel) frameView.getContentPane();
		contentPane.setLayout(null);
		panelContents.setBounds(0,0,700,378);
		contentPane.add(panelContents);
		/*** END DESCOMENTAR PARA EDITAR */
	//}

	protected void init_panelContents() {
		panelContents.setLayout(null);
		topPanel.setBounds(0, 0, 700, 60);
		topPanel.setMinimumSize(new Dimension(0, 10));
		topPanel.setPreferredSize(new Dimension(10, 5));
		// Components
		panelContents.add(topPanel);
		topPanel.setLayout(null);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		titleLabel.setBounds(275, 18, 161, 16);
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setHorizontalTextPosition(SwingConstants.LEADING);
		
		topPanel.add(titleLabel);
		
		
		btnmodificarHospital.setBounds(547, 14, 142, 29);
		topPanel.add(btnmodificarHospital);
		btnmodificarHospital.setFont(new Font("Arial", Font.PLAIN, 12));
		
		midPanel = new JPanel();
		midPanel.setBounds(0, 60, 700, 251);
		midPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panelContents.add(midPanel);
		midPanel.setLayout(null);
		scrollPanel.setBounds(20, 0, 234, 251);
		scrollPanel.setPreferredSize(new Dimension(150, 10));
		scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		midPanel.add(scrollPanel);
		list.setFont(new Font("Arial", Font.PLAIN, 13));
		scrollPanel.setViewportView(list);
		mediumPanel.setBounds(254, 125, 150, 0);
		mediumPanel.setPreferredSize(new Dimension(150, 0));
		
		midPanel.add(mediumPanel);
		mediumPanel.setLayout(new BoxLayout(mediumPanel, BoxLayout.X_AXIS));
		
		
		
	}
	private void init_panelTopButtons() {
		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
	}

	
	
	/** Asignacion de listeners **/
	
	protected void assign_listenersComponents() {
		btnCrearHospital.setFont(new Font("Arial", Font.PLAIN, 12));
		btnCrearHospital.setBounds(266, 161, 150, 42);
		midPanel.add(btnCrearHospital);
		btnSeleccionarHospital.setFont(new Font("Arial", Font.PLAIN, 12));
		btnSeleccionarHospital.setBounds(530, 208, 161, 42);
		midPanel.add(btnSeleccionarHospital);
		btnImportarHospital.setFont(new Font("Arial", Font.PLAIN, 12));
		btnImportarHospital.setBounds(266, 208, 150, 42);
		midPanel.add(btnImportarHospital);
		
		btnCrearHospital.setToolTipText("[CTRL+N]");
		btnEliminarHospital.setToolTipText("[CTRL+D]");
        btnImportarHospital.setToolTipText("[CTRL+I]");
        btnSeleccionarHospital.setToolTipText("[ENTER]");
        btnmodificarHospital.setToolTipText("[CTRL+M]");
		
		panelInfo.setBackground(Color.WHITE);
		panelInfo.setBounds(263, 0, 428, 123);
		midPanel.add(panelInfo);
		panelInfo.setLayout(null);
		
		JLabel labelDoc = new JLabel("N de doctores:");
		labelDoc.setFont(new Font("Arial", Font.BOLD, 12));
		labelDoc.setBounds(23, 45, 88, 16);
		panelInfo.add(labelDoc);
		
		JLabel lblNmeroDeTurnos = new JLabel("N de Turnos:");
		lblNmeroDeTurnos.setFont(new Font("Arial", Font.BOLD, 12));
		lblNmeroDeTurnos.setBounds(168, 45, 77, 16);
		panelInfo.add(lblNmeroDeTurnos);
		
		JLabel lblHaySolucionGuardada = new JLabel("Hay solucion guardada:");
		lblHaySolucionGuardada.setFont(new Font("Arial", Font.BOLD, 12));
		lblHaySolucionGuardada.setBounds(72, 86, 140, 16);
		panelInfo.add(lblHaySolucionGuardada);
		lblInformacion.setFont(new Font("Arial", Font.BOLD, 12));
		lblInformacion.setBounds(168, 6, 97, 16);
		
		panelInfo.add(lblInformacion);
		lblFactm.setFont(new Font("Arial", Font.BOLD, 12));
		lblFactm.setBounds(325, 30, 42, 16);
		
		panelInfo.add(lblFactm);
		lblFactt.setFont(new Font("Arial", Font.BOLD, 12));
		lblFactt.setBounds(326, 58, 41, 16);
		
		panelInfo.add(lblFactt);
		lblFactn.setFont(new Font("Arial", Font.BOLD, 12));
		lblFactn.setBounds(326, 86, 41, 16);
		
		panelInfo.add(lblFactn);
		nDocField.setFont(new Font("Arial", Font.PLAIN, 12));
		nDocField.setBounds(115, 45, 22, 16);
		
		panelInfo.add(nDocField);
		turnosField.setFont(new Font("Arial", Font.PLAIN, 12));
		turnosField.setBounds(250, 45, 20, 16);
		
		panelInfo.add(turnosField);
		factMField.setFont(new Font("Arial", Font.PLAIN, 12));
		factMField.setBounds(370, 30, 22, 16);
		
		panelInfo.add(factMField);
		factTField.setFont(new Font("Arial", Font.PLAIN, 12));
		factTField.setBounds(370, 58, 22, 16);
		
		panelInfo.add(factTField);
		factNField.setFont(new Font("Arial", Font.PLAIN, 12));
		factNField.setBounds(370, 86, 22, 16);
		
		panelInfo.add(factNField);
		solField.setFont(new Font("Arial", Font.PLAIN, 12));
		solField.setBounds(210, 86, 22, 16);
		
		panelInfo.add(solField);
		
		btnImportarHospital.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
					/*FileNameExtensionFilter filter = new FileNameExtensionFilter(
					        "Text File (.txt)", "txt");
					  //  chooser.setFileFilter(filter);*/
					int returnVal = chooser.showOpenDialog(frameView);
					File f = chooser.getSelectedFile();
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						try{
					       ctrlPresentacion.importarHospital(f.getAbsolutePath());
					       loadHospitals();
						} catch(IOException eX) {
							rejectedOperationDialog(eX);
//							JOptionPane.showMessageDialog(null, eX, "Error", JOptionPane.ERROR_MESSAGE); 

						}
					 }

				
			}
		});
		btnEliminarHospital.setFont(new Font("Arial", Font.PLAIN, 12));
		btnEliminarHospital.setBounds(20, 323, 150, 42);
		panelContents.add(btnEliminarHospital);
		
		btnEliminarHospital.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
					Object[] options = {"Aceptar", "Cancelar"};
					int returnVal = JOptionPane.showOptionDialog(null,
							"Esta seguro de que quiere eliminar el Hospital?",
						    "Alert",
						    JOptionPane.YES_NO_CANCEL_OPTION,
						    JOptionPane.WARNING_MESSAGE,
						    null,
						    options,
						    options[1]);
						if(returnVal == JOptionPane.YES_OPTION) {
							try {
							if(list.isSelectionEmpty()) throw new IOException("Debe seleccionar un hospital");
							
							String[] parts = (list.getSelectedValue()).split("-");
							ctrlPresentacion.deleteHospital(Integer.parseInt(parts[0]));
							loadHospitals();
							} catch (IOException eX) {
								rejectedOperationDialog(eX);
//								JOptionPane.showMessageDialog(null, eX, "Error", JOptionPane.ERROR_MESSAGE); 
							}
						}
				}
				});
		
		btnSeleccionarHospital.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(list.isSelectionEmpty()) throw new IOException("Debe seleccionar un hospital");
					String[] parts = (list.getSelectedValue()).split("-");
					String idHosp = parts[0];
					ctrlPresentacion.cargarHospital(Integer.parseInt(idHosp));
					ctrlPresentacion.changeView("vistaGestion", panelContents);
				} catch (IOException eX){
					rejectedOperationDialog(eX);
//					JOptionPane.showMessageDialog(null, eX, "Error", JOptionPane.ERROR_MESSAGE); 
				} catch (ParseException eX){
					rejectedOperationDialog(eX);
//					JOptionPane.showMessageDialog(null, eX, "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
			
		});
		
		
		btnCrearHospital.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPresentacion.setHospEdit(0);
				ctrlPresentacion.changeView("vistaCrearHospital", panelContents);
			}
		});
		
		btnmodificarHospital.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(list.isSelectionEmpty()) throw new IOException("Debe seleccionar un hospital");
					String[] parts = (list.getSelectedValue()).split("-");
					String idHosp = parts[0];
					ctrlPresentacion.cargarHospital(Integer.parseInt(idHosp));
					ctrlPresentacion.setHospEdit(1);
					ctrlPresentacion.changeView("vistaCrearHospital", panelContents);
				} catch (IOException eX){
					rejectedOperationDialog(eX);
//					JOptionPane.showMessageDialog(null, eX, "Error", JOptionPane.ERROR_MESSAGE); 
				} catch (ParseException eX){
					rejectedOperationDialog(eX);
//					JOptionPane.showMessageDialog(null, eX, "Error", JOptionPane.ERROR_MESSAGE); 
				}
				
			}
		});
	
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!list.isSelectionEmpty()){
					if(!panelInfo.isVisible()) panelInfo.setVisible(true);
					boolean isAdjusting = e.getValueIsAdjusting();
					if(!isAdjusting) {
						String[] parts = (list.getSelectedValue()).split("-");
						try {
							ArrayList<String> info = ctrlPresentacion.getInfoHospital(Integer.parseInt(parts[0]));
							//0-> ID //1-> Name //2-> FM //3-> FT //4-> FN //5-> nDoc //6-> nDias //
							nDocField.setText(info.get(5));
							turnosField.setText(new Integer(Integer.parseInt(info.get(6))*3).toString());//dias * 3 = turnos
							factMField.setText(info.get(2));
							factTField.setText(info.get(3));
							factNField.setText(info.get(4));
							solField.setText(info.get(7));
						} catch(IOException eX) {
							
						}
					}
				}
				
			}
		});
		
		// HERE ARE THE KEY BINDINGS
        panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK), "newHospital");
        panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_DOWN_MASK), "importHospital");
        panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK), "delHospital");
        panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_DOWN_MASK), "modHospital");
        panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "selHospital");
        
        panelContents.getActionMap().put("newHospital", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
            public void actionPerformed(ActionEvent e) {
               	btnCrearHospital.doClick();
            }
        });
        panelContents.getActionMap().put("importHospital", new AbstractAction() {
        	private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent e) {
               	btnImportarHospital.doClick();
            }
        });
        panelContents.getActionMap().put("delHospital", new AbstractAction() {
        	private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent e) {
               	btnEliminarHospital.doClick();
            }
        });
        panelContents.getActionMap().put("selHospital", new AbstractAction() {
        	private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent e) {
               	btnSeleccionarHospital.doClick();
            }
        });
        panelContents.getActionMap().put("modHospital", new AbstractAction() {
        	private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent e) {
               	btnmodificarHospital.doClick();
            }
        });
        // END OF KEY BINDINGS
		
		
		
	}
	
	/*private void initComponents() {
		init_frameView1();
		init_panelContents();
		init_panelTopButtons();
		assign_listenersComponents();
	}
	*/

	private void loadHospitals() {
		panelInfo.setVisible(false);
		list.clearSelection();
		hospitales = new ArrayList<String>();
		try {
			hospitales = ctrlPresentacion.loadHospitals();
		} catch (IOException e) {
			hospitales.add("No hay hospitales disponibles");
		}
		
		DefaultListModel<String> model = new DefaultListModel<String>();
	    for(String st : hospitales){
	    	st = st.replace("%", " ");
	         model.addElement(st);
	    }    
	    list.setModel(model);  
	    list.revalidate();
		list.repaint();
		nDocField.setText("0");
		turnosField.setText("0");
		factMField.setText("0");
		factTField.setText("0");
		factNField.setText("0");
	}
	
	/* Constructoras y metodos publicos */
	public VistaCjtHospitales(CtrlPresentacion pCtrlVistaPrincipal) {
//		ctrlPresentacion = pCtrlVistaPrincipal;
		super(pCtrlVistaPrincipal);
		/*** DESCOMENTAR PARA EDITAR *
		 initComponents();
		 /*** END DESCOMENTAR PARA EDITAR */
	}
	
	public void init() {
		loadHospitals();
		init_frameView();
		init_panelContents();
		init_panelTopButtons();
		assign_listenersComponents();
	}

	
	public void showPanel() {
		loadHospitals();
		panelContents.setVisible(true);
	}
}
