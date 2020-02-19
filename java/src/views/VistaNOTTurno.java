package views;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Vista secundaria de gestion de restricciones tipo NOT Turno
 * @author Sergi Orra Genero
 */
public class VistaNOTTurno extends Vista {
	
	private JPanel panelButtons = new JPanel();
	private JPanel panelInfo = new JPanel();
	
	private JLabel labelPanel1 = new JLabel("<html><u>Crear Restriccion:</u> Tipo NOT Turno");
	
	private JLabel labelPanel3 = new JLabel("<html><CENTER>Seleccione el tipo de turno:</CENTER>");
	private JCheckBox checkBox1 = new JCheckBox("manana");
	private JCheckBox checkBox2 = new JCheckBox("tarde");
	private JCheckBox checkBox3 = new JCheckBox("noche");
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
	
	private void inicializar_panelButtons() {
		panelButtons.setBorder(new EmptyBorder(70, 20, 20, 0));
		labelPanel3.setFont(new Font("Arial", Font.PLAIN, 15));
		labelPanel3.setBounds(110, 95, 200, 30);
		checkBox1.setFont(new Font("Arial", Font.PLAIN, 12));
		checkBox1.setBounds(305, 80, 100, 20);
		checkBox2.setFont(new Font("Arial", Font.PLAIN, 12));
		checkBox2.setBounds(305, 100, 100, 20);
		checkBox3.setFont(new Font("Arial", Font.PLAIN, 12));
		checkBox3.setBounds(305, 120, 100, 20);
		button.setFont(new Font("Arial", Font.PLAIN, 12));
		button.setBounds(410, 95, 150, 30);
		buttonVolver.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonVolver.setBounds(40, 230, 150, 40);
		panelButtons.setLayout(null);
	    panelButtons.add(labelPanel3);
	    panelButtons.add(checkBox1);
	    panelButtons.add(checkBox2);
	    panelButtons.add(checkBox3);
	    panelButtons.add(button);
	    panelButtons.add(buttonVolver);
	    button.setToolTipText("[CTRL+ENTER]");
	    buttonVolver.setToolTipText("[ESC]");
	}

	@SuppressWarnings("serial")
	protected void assign_listenersComponents() {
		
		buttonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBox1.isSelected()) checkBox1.setSelected(false);
				if(checkBox2.isSelected()) checkBox2.setSelected(false);
				if(checkBox3.isSelected()) checkBox3.setSelected(false);
				ctrlPresentacion.changeView("vistaRestriccion", panelContents);
			}
		});
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((checkBox1.isSelected() && !checkBox2.isSelected() && !checkBox3.isSelected()) ||
					(!checkBox1.isSelected() && checkBox2.isSelected() && !checkBox3.isSelected()) ||
					(!checkBox1.isSelected() && !checkBox2.isSelected() && checkBox3.isSelected())) {
					try {
						if(checkBox1.isSelected()) ctrlPresentacion.addResNOT_Turno("manana");
						else if (checkBox2.isSelected()) ctrlPresentacion.addResNOT_Turno("tarde");
						else if (checkBox3.isSelected()) ctrlPresentacion.addResNOT_Turno("noche");
						successfulOperationDialog("Restriccion creada correctamente");
						if(checkBox1.isSelected()) checkBox1.setSelected(false);
						else if(checkBox2.isSelected()) checkBox2.setSelected(false);
						else if(checkBox3.isSelected()) checkBox3.setSelected(false);
						ctrlPresentacion.changeView("vistaRestriccion", panelContents);
					} catch (IOException eX) {
						rejectedOperationDialog("Restriccion no creada"); 
						if(checkBox1.isSelected()) checkBox1.setSelected(false);
						else if(checkBox2.isSelected()) checkBox2.setSelected(false);
						else if(checkBox3.isSelected()) checkBox3.setSelected(false);
						ctrlPresentacion.changeView("vistaRestriccion", panelContents);
					}
				}
				else {
					rejectedOperationDialog("Turno seleccionado incorrecto");
					if(checkBox1.isSelected()) checkBox1.setSelected(false);
					if(checkBox2.isSelected()) checkBox2.setSelected(false);
					if(checkBox3.isSelected()) checkBox3.setSelected(false);
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
	
	public VistaNOTTurno(CtrlPresentacion pCtrlPresentacion) {
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