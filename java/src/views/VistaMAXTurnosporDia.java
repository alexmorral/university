
package views;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Scanner;

/**
 * Vista secundaria de gestion de restricciones tipo MAX Turnos por Dia
 * @author Sergi Orra Genero
 */
public class VistaMAXTurnosporDia extends Vista {
	
	private JPanel panelButtons = new JPanel();
	private JPanel panelInfo = new JPanel();
	
	private JLabel labelPanel1 = new JLabel("<html><u>Crear Restriccion:</u> Tipo MAX Turnos por Dia");
	
	private JLabel labelPanel3 = new JLabel("<html><CENTER>Escribe el numero<br/> de turnos:</CENTER>");
	private JTextField textArea = new JTextField();
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
		labelPanel3.setBounds(150, 95, 150, 30);
		textArea.setBounds(305, 102, 35, 16);
		button.setFont(new Font("Arial", Font.PLAIN, 12));
		button.setBounds(390, 95, 150, 30);
		buttonVolver.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonVolver.setBounds(40, 230, 150, 40);
		panelButtons.setLayout(null);
	    panelButtons.add(labelPanel3);
	    panelButtons.add(textArea);
	    panelButtons.add(button);
	    panelButtons.add(buttonVolver);
	    button.setToolTipText("[CTRL+ENTER]");
	    buttonVolver.setToolTipText("[ESC]");
	}

	@SuppressWarnings("serial")
	protected void assign_listenersComponents() {
		
		buttonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(null);
				ctrlPresentacion.changeView("vistaRestriccion", panelContents);
			}
		});
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Scanner sc1 = new Scanner(textArea.getText());
				int numT;
				if (sc1.hasNextInt()) {
					numT = Integer.parseInt(textArea.getText());
					try {
						ctrlPresentacion.addResMAX_Turnos_por_Dia(numT);
						successfulOperationDialog("Restriccion creada correctamente");
						textArea.setText(null);
						ctrlPresentacion.changeView("vistaRestriccion", panelContents);
					} catch (IOException eX) {
						rejectedOperationDialog("Restriccion no creada");
						textArea.setText(null);
						ctrlPresentacion.changeView("vistaRestriccion", panelContents);
					}
				}
				else {
					rejectedOperationDialog("Valor incorrecto");
					textArea.setText(null);
				}
				sc1.close();
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
	
	
	public VistaMAXTurnosporDia(CtrlPresentacion pCtrlPresentacion) {
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