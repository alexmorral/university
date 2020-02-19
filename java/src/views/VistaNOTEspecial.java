package views;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Vista secundaria de gestion de restricciones tipo NOT Especial
 * @author Sergi Orra Genero
 */
public class VistaNOTEspecial extends Vista {

	private JPanel panelButtons = new JPanel();
	private JPanel panelInfo = new JPanel();
	
	private JLabel labelPanel1 = new JLabel("<html><u>Crear Restriccion:</u> Tipo NOT Especial");
	
	private JLabel labelPanel3 = new JLabel("<html><CENTER>Seleccione dia especial:</CENTER>");
	@SuppressWarnings("rawtypes")
	private JComboBox comboboxInformacion1 = new JComboBox();
	private JButton button = new JButton("Aceptar");
	private JButton buttonVolver = new JButton("Volver");
	
	protected void init_panelContents() {
	    panelContents.setLayout(new BorderLayout());
	    panelContents.add(panelInfo,BorderLayout.NORTH);
	    panelContents.add(panelButtons,BorderLayout.CENTER);
	}
	
	private void inicializar_panelInfo() {
		panelInfo.setBorder(BorderFactory.createEmptyBorder(55, 75, 0, 75));
		labelPanel1.setFont(new Font("Arial", Font.PLAIN, 18));
		panelInfo.add(labelPanel1);
	}
	
	@SuppressWarnings("unchecked")
	private void inicializar_panelButtons() {
		panelButtons.setBorder(new EmptyBorder(70, 20, 20, 0));
		labelPanel3.setFont(new Font("Arial", Font.PLAIN, 15));
		labelPanel3.setBounds(75, 95, 200, 30);
		comboboxInformacion1.setFont(new Font("Arial", Font.PLAIN, 12));
		comboboxInformacion1.setBounds(275, 95, 150, 30);
		button.setFont(new Font("Arial", Font.PLAIN, 12));
		button.setBounds(440, 95, 150, 30);
		buttonVolver.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonVolver.setBounds(40, 230, 150, 40);
		panelButtons.setLayout(null);
	    panelButtons.add(labelPanel3);
	    panelButtons.add(comboboxInformacion1);
	    comboboxInformacion1.addItem("navidad");
	    comboboxInformacion1.addItem("semana_santa");
	    comboboxInformacion1.addItem("noche_buena");
	    comboboxInformacion1.addItem("noche_vieja");
	    panelButtons.add(button);
	    panelButtons.add(buttonVolver);
	    button.setToolTipText("[CTRL+ENTER]");
	    buttonVolver.setToolTipText("[ESC]");
	}

	@SuppressWarnings("serial")
	protected void assign_listenersComponents() {
		
		buttonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboboxInformacion1.setSelectedItem("navidad");
				ctrlPresentacion.changeView("vistaRestriccion", panelContents);
			}
		});
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String especial = comboboxInformacion1.getSelectedItem().toString();
				try {
					ctrlPresentacion.addResNOT_Especial(especial);
					successfulOperationDialog("Restriccion creada correctamente");
					comboboxInformacion1.setSelectedItem("navidad");
					ctrlPresentacion.changeView("vistaRestriccion", panelContents);
				} catch (IOException eX) {
					rejectedOperationDialog("Restriccion no creada");
					comboboxInformacion1.setSelectedItem("navidad");
					ctrlPresentacion.changeView("vistaRestriccion", panelContents);
				}
			}
		});	
		
		panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "buttonVolver");
		panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, java.awt.event.InputEvent.CTRL_DOWN_MASK), "button");
		
		panelContents.getActionMap().put("buttonVolver", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	buttonVolver.doClick();
            }
        });
		
		panelContents.getActionMap().put("button", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	button.doClick();
            }
        });
	}
	
	
	//METODOS PUBLICOS
	
	public VistaNOTEspecial(CtrlPresentacion pCtrlPresentacion) {
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