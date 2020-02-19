package views;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Vista principal de la gestion de restricciones
 * @author Sergi Orra Genero
 */


//ID VISTA 9

public class VistaRestriccion extends Vista {
	
//	private CtrlPresentacion ctrlPresentacion;
	
	//Componentes interficie
//	private JFrame frameView;
//	private JPanel panelContents = new JPanel();
	private JPanel panelButtons = new JPanel();
	private JPanel panelInfo = new JPanel();
	
	//Etiquetas
	  private JLabel labelPanel = new JLabel("<html>Elije el tipo de restriccion para crear:");
	
	//Botones
	private JButton buttonNOTFecha = new JButton("NOT Fecha");
	private JButton buttonNOTTurno = new JButton("NOT Turno");
	private JButton buttonNOTEspecial = new JButton("NOT Especial");
	private JButton buttonNOTDiaMes = new JButton("NOT Dia Mes");
	private JButton buttonNOTDiaSemana = new JButton("NOT Dia Semana");
	private JButton buttonMAXTurnosRango = new JButton("MAX Turnos Rango");
	private JButton buttonMAXTurnosDia = new JButton("MAX Turnos por Dia");
	private JButton buttonXOR = new JButton("XOR");
	private JButton buttonVolver = new JButton("Volver");
	
	
	protected void init_panelContents() {
	    panelContents.setLayout(new BorderLayout());
	    panelContents.add(panelInfo,BorderLayout.NORTH);
	    panelContents.add(panelButtons,BorderLayout.CENTER);
	}
	
	private void inicializar_panelInfo() {
		panelInfo.setBorder(BorderFactory.createEmptyBorder(25, 50, 0, 300));
		labelPanel.setFont(new Font("Arial", Font.PLAIN, 18));
		panelInfo.add(labelPanel);
	}
	
	private void inicializar_panelButtons() {
		
		panelButtons.setBorder(new EmptyBorder(70, 20, 20, 0));
		buttonNOTFecha.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonNOTFecha.setBounds(101, 33, 249, 40);
		buttonNOTTurno.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonNOTTurno.setBounds(362, 33, 249, 40);
		buttonNOTEspecial.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonNOTEspecial.setBounds(101, 85, 249, 40);
		buttonNOTDiaMes.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonNOTDiaMes.setBounds(362, 85, 249, 40);
		buttonNOTDiaSemana.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonNOTDiaSemana.setBounds(101, 137, 249, 40);
		buttonMAXTurnosRango.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonMAXTurnosRango.setBounds(362, 137, 249, 40);
		buttonMAXTurnosDia.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonMAXTurnosDia.setBounds(101, 189, 249, 40);
		buttonXOR.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonXOR.setBounds(362, 189, 249, 40);
		buttonVolver.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonVolver.setBounds(49, 257, 221, 40);
		panelButtons.setLayout(null);
		
	    // Botones
	    panelButtons.add(buttonNOTFecha);
	    panelButtons.add(buttonNOTTurno);
	    panelButtons.add(buttonNOTEspecial);
	    panelButtons.add(buttonNOTDiaMes);
	    panelButtons.add(buttonNOTDiaSemana);
	    panelButtons.add(buttonMAXTurnosRango);
	    panelButtons.add(buttonMAXTurnosDia);
	    panelButtons.add(buttonXOR);
	    panelButtons.add(buttonVolver);
	    // Tooltips
	    buttonNOTFecha.setToolTipText("[CTRL+F]");
	    buttonNOTTurno.setToolTipText("[CTRL+T]");
	    buttonNOTEspecial.setToolTipText("[CTRL+E]");
	    buttonNOTDiaMes.setToolTipText("[CTRL+M]");
	    buttonNOTDiaSemana.setToolTipText("[CTRL+S]");
	    buttonMAXTurnosRango.setToolTipText("[CTRL+R]");
	    buttonMAXTurnosDia.setToolTipText("[CTRL+D]");
	    buttonXOR.setToolTipText("[CTRL+X]");
	    buttonVolver.setToolTipText("[CTRL+ESC]");
	}

	@SuppressWarnings("serial")
	protected void assign_listenersComponents() {
		buttonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPresentacion.changeView("vistaDoctor", panelContents);
			}
		});
		
	
		
		buttonNOTTurno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPresentacion.changeView("vistaNOTTurno", panelContents);
			}
		});
		
		buttonNOTEspecial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPresentacion.changeView("vistaNOTEspecial", panelContents);
			}
		});
		
		buttonNOTDiaMes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPresentacion.changeView("vistaNOTDiaMes", panelContents);
			}
		});
		
		buttonNOTDiaSemana.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPresentacion.changeView("vistaNOTDiaSemana", panelContents);
			}
		});
		
		
		
		buttonMAXTurnosDia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPresentacion.changeView("vistaMAXTurnosporDia", panelContents);
			}
		});
		
		buttonMAXTurnosRango.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPresentacion.changeView("vistaMAXTurnosRango", panelContents);
			}
		});
		
		
		buttonXOR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPresentacion.changeView("vistaXOR", panelContents);
			}
		});
		
		
		buttonNOTFecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPresentacion.changeView("vistaNOTFecha", panelContents);
			}
		});
		
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "buttonVolver");
		 panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonNOTFecha");
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonXOR");
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonNOTEspecial");
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonNOTTurno");
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonNOTDiaMes");
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonNOTDiaSemana");
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonMAXTurnosRango");
	     panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonMAXTurnosDia");

	     panelContents.getActionMap().put("buttonNOTFecha", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonNOTFecha.doClick();
	            }
	        });
	        panelContents.getActionMap().put("buttonXOR", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonXOR.doClick();
	            }
	        });
	        panelContents.getActionMap().put("buttonNOTTurno", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonNOTTurno.doClick();
	            }
	        });
	        panelContents.getActionMap().put("buttonNOTDiaSemana", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonNOTDiaSemana.doClick();
	            }
	        });
	        panelContents.getActionMap().put("buttonNOTEspecial", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonNOTEspecial.doClick();
	            }
	        });
	        panelContents.getActionMap().put("buttonNOTDiaMes", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonNOTDiaMes.doClick();
	            }
	        });
	        panelContents.getActionMap().put("buttonMAXTurnosRango", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonMAXTurnosRango.doClick();
	            }
	        });
	        panelContents.getActionMap().put("buttonMAXTurnosDia", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonMAXTurnosDia.doClick();
	            }
	        });
	        panelContents.getActionMap().put("buttonVolver", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	buttonVolver.doClick();
	            }
	        });
		
	}
	
	
	//METODOS PUBLICOS
	
	public VistaRestriccion(CtrlPresentacion pCtrlPresentacion) {
		super(pCtrlPresentacion);

	}
	
	public void init() {
		init_frameView();
	    init_panelContents();
	    inicializar_panelInfo();
	    inicializar_panelButtons();
	    assign_listenersComponents();
	}
	
}
