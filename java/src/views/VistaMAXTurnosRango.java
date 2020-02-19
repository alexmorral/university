package views;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Vista secundaria de gestion de restricciones tipo MAXTurnosRango
 * @author Sergi Orra Genero
 */
public class VistaMAXTurnosRango extends Vista {

	private JPanel panelButtons = new JPanel();
	private JPanel panelInfo = new JPanel();
	
	private JLabel labelPanel1 = new JLabel("<html><u>Crear Restriccion:</u> Tipo MAX Turnos Rango");
	private JLabel labelPanel2 = new JLabel("<html><CENTER>Seleccione fecha inicial:</CENTER>");
	private JLabel labelPanel3 = new JLabel("<html><CENTER>Seleccione fecha final:</CENTER>");
	private JLabel labelPanel4 = new JLabel("<html><CENTER>Escribe numero de turnos:</CENTER>");
	private JDateChooser dataChooser1 = new JDateChooser("dd/MM/yyyy", "##/##/##", '_');
	private JDateChooser dataChooser2 = new JDateChooser("dd/MM/yyyy", "##/##/##", '_');
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
		labelPanel2.setFont(new Font("Arial", Font.PLAIN, 15));
		labelPanel2.setBounds(75, 72, 175, 30);
		labelPanel3.setFont(new Font("Arial", Font.PLAIN, 15));
		labelPanel3.setBounds(75, 107, 175, 30);
		labelPanel4.setFont(new Font("Arial", Font.PLAIN, 15));
		labelPanel4.setBounds(75, 142, 175, 30);
		dataChooser1.setBounds(300, 75, 150, 30);
		dataChooser2.setBounds(300, 110, 150, 30);
		textArea.setBounds(300, 152, 50, 16);
		button.setFont(new Font("Arial", Font.PLAIN, 12));
		button.setBounds(470, 110, 150, 30);
		buttonVolver.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonVolver.setBounds(40, 230, 150, 40);
		panelButtons.setLayout(null);
	    panelButtons.add(labelPanel2);
	    panelButtons.add(labelPanel3);
	    panelButtons.add(labelPanel4);
	    panelButtons.add(dataChooser1);
	    panelButtons.add(dataChooser2);
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
				dataChooser1.setCalendar(null);
				dataChooser2.setCalendar(null);
				ctrlPresentacion.changeView("vistaRestriccion", panelContents);
			}
		});
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date data1 = dataChooser1.getDate();
				Date data2 = dataChooser2.getDate();
				Scanner sc1 = new Scanner(textArea.getText());
				if (data1 != null && data2 != null && sc1.hasNextInt()) {
					GregorianCalendar cal1 = new GregorianCalendar();
				    cal1.setTime(data1);	
					int dia1 = cal1.get(GregorianCalendar.DAY_OF_MONTH);
					int mes1 = cal1.get(GregorianCalendar.MONTH)+1;
					int year1 = cal1.get(GregorianCalendar.YEAR);
					GregorianCalendar cal2 = new GregorianCalendar();
				    cal2.setTime(data2);	
				    int dia2 = cal2.get(GregorianCalendar.DAY_OF_MONTH);
					int mes2 = cal2.get(GregorianCalendar.MONTH)+1;
					int year2 = cal2.get(GregorianCalendar.YEAR);
					int num = Integer.parseInt(textArea.getText());
					try {
						ctrlPresentacion.addResMAX_Turnos_Rango(dia1, mes1, year1, dia2, mes2, year2, num);
						successfulOperationDialog("Restriccion creada correctamente");
						textArea.setText(null);
						dataChooser1.setCalendar(null);
						dataChooser2.setCalendar(null);
						ctrlPresentacion.changeView("vistaRestriccion", panelContents);
					} catch (IOException eX) {
						rejectedOperationDialog("Restriccion no creada");
						textArea.setText(null);
						dataChooser1.setCalendar(null);
						dataChooser2.setCalendar(null);
						ctrlPresentacion.changeView("vistaRestriccion", panelContents);
					}
				}
				else {
					rejectedOperationDialog("Valores introducidos incorrectamente");
					textArea.setText(null);
					dataChooser1.setCalendar(null);
					dataChooser2.setCalendar(null);
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
	
	public VistaMAXTurnosRango(CtrlPresentacion pCtrlPresentacion) {
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