package views;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Vista gestion dia del calendario
 * @author Felix Fernando Ramos Velazquez
 */
public class VistaDiaCalendario extends Vista {

	/* Private attributes */

	//-- Containers --//
	private JPanel panelTop = new JPanel();
	private JPanel panelBottom = new JPanel();
	private JPanel panelCentral = new JPanel();

	//-- Buttons --//
	private JButton buttonDelVacation = new JButton("Eliminar dia");
	private JButton buttonAddVacation = new JButton("Anadir dia");
	private JButton buttonGoBack = new JButton("Volver");
	private JButton buttonHelp = new JButton("Ayuda");
	private JButton buttonMod = new JButton("Modificar dia");
	private JButton buttonDel = new JButton("Eliminar dia");

	//-- Labels --//
	private JLabel labelCalendar, labelVacationList1, labelVacationList2;


	//-- Others private atributes--//

	/* Private Methods */

	private void init_panelTop() {
		// panel
		panelTop.setLayout(null);
		panelTop.setBounds(0, 0, width, 50);

		// components
		
//		buttonCreateCal.setBounds(325, 10, 170, 30);
//		buttonCreateCal.setFont(new Font("Arial", Font.BOLD, 11));
//		buttonDeleteCal.setBounds(325, 10, 170, 30);
//		buttonDeleteCal.setFont(new Font("Arial", Font.BOLD, 11));
//		buttonDeleteCal.setVisible(false);
//		buttonImportCal.setBounds(505, 10, 170, 30);
//		buttonImportCal.setFont(new Font("Arial", Font.BOLD, 11));
//		labelCalendar = new JLabel(" (No hay calendario)");
		labelCalendar = new JLabel("Calendario vacacional 2014");
//		labelCalendar.setHorizontalAlignment(SwingConstants.LEFT);
//		labelCalendar.setText("(No hay calendario)");
//		labelCalendar.setOpaque(true);
//		labelCalendar.setBackground(Color.cyan);
//		labelCalendar.setFont(new Font("Arial Italic", Font.TRUETYPE_FONT,16));
		labelCalendar.setFont(new Font("Arial", Font.BOLD, 16));
		//		labelCalendar.setForeground(Color.BLUE);
		labelCalendar.setBounds(20, 10, 325, 30);

		panelTop.add(labelCalendar);
//		panelTop.add(buttonCreateCal);
//		panelTop.add(buttonImportCal);
//		panelTop.add(buttonDeleteCal);


		// tooltips
//		buttonCreateCal.setToolTipText("Crear nuevo calendario");
//		buttonImportCal.setToolTipText("Importar calendario existente");
//		buttonDeleteCal.setToolTipText("Eliminar calendario existente");
	}

	private void init_panelCentral() {
		// panel
		panelCentral.setLayout(null);
		panelCentral.setBounds(0, 50, width, 265);

		// components
		labelVacationList1 = new JLabel(String.format("%62s", "Numero de Drs. por turno:"));
//		labelVacationList1.setOpaque(true);
//		labelVacationList1.setBackground(Color.white);
//		labelVacationList1.setBorder(BorderFactory.createLineBorder(Color.cyan, 1));
		labelVacationList1.setFont(new Font("Arial", Font.BOLD, 12));
		labelVacationList1.setBounds(20, 10, 495, 20);

		labelVacationList2 = new JLabel(String.format(" %s%12s%20s%21s%23s", "Dia vacacional","M","T","N","Fecha especial"));
//		labelVacationList2.setOpaque(true);
//		labelVacationList2.setBackground(Color.cyan);
		labelVacationList2.setFont(new Font("Arial", Font.BOLD, 12));
		labelVacationList2.setBounds(20, 30, 495, 20);
		

		
		buttonAddVacation.setBounds(525, 80, 150, 30);
		buttonAddVacation.setEnabled(true);


		panelCentral.add(buttonAddVacation);
	}

	private void init_panelBottom() {
		// panel
		panelBottom.setLayout(null);
		panelBottom.setBounds(0, 315, width, 60);
//		panelBottom.setBackground(Color.cyan);

		// components
		buttonGoBack.setBounds(20, 10, 150, 30);
		buttonHelp.setBounds(365, 10, 150, 30);

		panelBottom.add(buttonGoBack);
//		panelBottom.add(buttonHelp);
	}

	protected void init_panelContents() {
		panelContents.setLayout(null);
		// Componentes
		panelContents.add(panelTop);
		panelContents.add(panelCentral);
		panelContents.add(panelBottom);
	}
	
	//	//************************************************//
	//	/* Methods of the listener interfaces */
	public void actionPerformed_buttonGoBack (ActionEvent event) {

	}

	/* Assigning listeners */	
	protected void assign_listenersComponents() {

		buttonGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPresentacion.changeView("vistaCalendario", panelContents);
			}
		});
	}

	//************************************************//

	/* Constructors & public methods */
	public VistaDiaCalendario(CtrlPresentacion pCtrlPresentacion) {
		super(pCtrlPresentacion);
	}
	
	public void init() {
		init_panelTop();
		init_panelCentral();
		init_panelBottom();
		init_frameView();
		init_panelContents();
		assign_listenersComponents();
	}
}
