package views;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Vista secundaria de gestion de restricciones tipo XOR
 * @author Sergi Orra Genero
 */
public class VistaXOR extends Vista {
	
	private JPanel panelButtons = new JPanel();
	private JPanel panelInfo = new JPanel();
	
	private JLabel labelPanel1 = new JLabel("<html><u>Crear Restriccion:</u> Tipo XOR");
	private JLabel labelPanel2 = new JLabel("<html><CENTER>Seleccione fecha:</CENTER>");
	private JLabel labelPanel3 = new JLabel("<html><CENTER>Seleccione<br/> tipo del turno:</CENTER>");
	private JDateChooser dataChooser = new JDateChooser("dd/MM/yyyy", "##/##/##", '_');
	private JCheckBox checkBox1 = new JCheckBox("manana");
	private JCheckBox checkBox2 = new JCheckBox("tarde");
	private JCheckBox checkBox3 = new JCheckBox("noche");
	private JButton buttonSiguiente = new JButton("Siguiente fecha");
	private JButton button = new JButton("Aceptar");
	private JButton buttonVolver = new JButton("Volver");

	private ArrayList<Integer> dia = new ArrayList<Integer>();
	private ArrayList<Integer> mes = new ArrayList<Integer>();
	private ArrayList<Integer> year = new ArrayList<Integer>();
	private ArrayList<String> turno = new ArrayList<String>();
	
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
		labelPanel2.setBounds(125, 50, 150, 30);
		dataChooser.setBounds(280, 50, 150, 30);
		labelPanel3.setFont(new Font("Arial", Font.PLAIN, 15));
		labelPanel3.setBounds(125, 100, 150, 30);
		checkBox1.setFont(new Font("Arial", Font.PLAIN, 12));
		checkBox1.setBounds(280, 85, 100, 20);
		checkBox2.setFont(new Font("Arial", Font.PLAIN, 12));
		checkBox2.setBounds(280, 105, 100, 20);
		checkBox3.setFont(new Font("Arial", Font.PLAIN, 12));
		checkBox3.setBounds(280, 125, 100, 20);
		button.setFont(new Font("Arial", Font.PLAIN, 12));
		button.setBounds(510, 80, 150, 30);
		buttonSiguiente.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonSiguiente.setBounds(225, 160, 150, 30);
		buttonVolver.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonVolver.setBounds(40, 230, 150, 40);
		panelButtons.setLayout(null);
	    panelButtons.add(labelPanel2);
	    panelButtons.add(checkBox1);
	    panelButtons.add(checkBox2);
	    panelButtons.add(checkBox3);
	    panelButtons.add(labelPanel3);
	    panelButtons.add(dataChooser);
	    panelButtons.add(button);
	    panelButtons.add(buttonSiguiente);
	    panelButtons.add(buttonVolver);
	    button.setToolTipText("[CTRL+ENTER]");
	    buttonVolver.setToolTipText("[ESC]");
	    buttonSiguiente.setToolTipText("[CTRL+S");
	    
	}

	@SuppressWarnings("serial")
	protected void assign_listenersComponents() {
		buttonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dia.clear();
				mes.clear();
				year.clear();
				turno.clear();
				if(checkBox1.isSelected()) checkBox1.setSelected(false);
				if(checkBox2.isSelected()) checkBox2.setSelected(false);
				if(checkBox3.isSelected()) checkBox3.setSelected(false);
				dataChooser.setCalendar(null);
				ctrlPresentacion.changeView("vistaRestriccion", panelContents);
			}
		});
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date data = dataChooser.getDate();
				if (data != null && ((checkBox1.isSelected() && !checkBox2.isSelected() && !checkBox3.isSelected()) ||
				   (!checkBox1.isSelected() && checkBox2.isSelected() && !checkBox3.isSelected()) ||
				   (!checkBox1.isSelected() && !checkBox2.isSelected() && checkBox3.isSelected()))) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime(data);		
						dia.add(cal.get(GregorianCalendar.DAY_OF_MONTH));
						mes.add(cal.get(GregorianCalendar.MONTH)+1);
						year.add(cal.get(GregorianCalendar.YEAR));
						if(checkBox1.isSelected()) turno.add("manana");
						else if (checkBox2.isSelected()) turno.add("tarde");
						else if (checkBox3.isSelected()) turno.add("noche");
					try {
						 if (dia.size() <= 1) throw new IOException("Solo hay una fecha de XOR");
						 ctrlPresentacion.addResXOR(dia, mes, year, turno);
						 successfulOperationDialog("Restriccion creada correctamente");
						 dia.clear();
						 mes.clear();
						 year.clear();
						 turno.clear();
						 if(checkBox1.isSelected()) checkBox1.setSelected(false);
						 else if(checkBox2.isSelected()) checkBox2.setSelected(false);
						 else if(checkBox3.isSelected()) checkBox3.setSelected(false);
						 dataChooser.setCalendar(null);
						 ctrlPresentacion.changeView("vistaRestriccion", panelContents);
					} catch (IOException eX) {
						rejectedOperationDialog("Restriccion no creada");//						JOptionPane.showMessageDialog(null, "Restriccion no creada", "Error",JOptionPane.ERROR_MESSAGE); 
						dia.clear();
						mes.clear();
						year.clear();
						turno.clear();
						if(checkBox1.isSelected()) checkBox1.setSelected(false);
						else if(checkBox2.isSelected()) checkBox2.setSelected(false);
						else if(checkBox3.isSelected()) checkBox3.setSelected(false);
						dataChooser.setCalendar(null);
						ctrlPresentacion.changeView("vistaRestriccion", panelContents);
					}
				}
				else {
					rejectedOperationDialog("Valores introducidos incorrectamente");
					if(checkBox1.isSelected()) checkBox1.setSelected(false);
					if(checkBox2.isSelected()) checkBox2.setSelected(false);
					if(checkBox3.isSelected()) checkBox3.setSelected(false);
					dataChooser.setCalendar(null);
				}
			}
		});	
		
		buttonSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date data = dataChooser.getDate();
				if (data != null && ((checkBox1.isSelected() && !checkBox2.isSelected() && !checkBox3.isSelected()) ||
				   (!checkBox1.isSelected() && checkBox2.isSelected() && !checkBox3.isSelected()) ||
				   (!checkBox1.isSelected() && !checkBox2.isSelected() && checkBox3.isSelected()))) {
					    GregorianCalendar cal = new GregorianCalendar();
					    cal.setTime(data);		
					    dia.add(cal.get(GregorianCalendar.DAY_OF_MONTH));
					    mes.add(cal.get(GregorianCalendar.MONTH)+1);
					    year.add(cal.get(GregorianCalendar.YEAR));
						if(checkBox1.isSelected()) turno.add("manana");
						else if (checkBox2.isSelected()) turno.add("tarde");
						else if (checkBox3.isSelected()) turno.add("noche");
						JOptionPane.showMessageDialog(null, "Fecha anadida correctamente, seleccione la siguiente", "Operacion realizada", JOptionPane.INFORMATION_MESSAGE);
						if(checkBox1.isSelected()) checkBox1.setSelected(false);
						else if(checkBox2.isSelected()) checkBox2.setSelected(false);
						else if(checkBox3.isSelected()) checkBox3.setSelected(false);
						dataChooser.setCalendar(null);
				}
				else {
					JOptionPane.showMessageDialog(null, "Valores introducidos incorrectamente", "Error", JOptionPane.ERROR_MESSAGE);
					if(checkBox1.isSelected()) checkBox1.setSelected(false);
					if(checkBox2.isSelected()) checkBox2.setSelected(false);
					if(checkBox3.isSelected()) checkBox3.setSelected(false);
					dataChooser.setCalendar(null);
				}
			}
		});	
		
		panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "buttonVolver");
		panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, java.awt.event.InputEvent.CTRL_DOWN_MASK), "button");
		panelContents.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonSiguiente");
		
		panelContents.getActionMap().put("buttonVolver", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	buttonVolver.doClick();
            }
        });
		
		panelContents.getActionMap().put("buttonSiguiente", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	buttonSiguiente.doClick();
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
	
	public VistaXOR(CtrlPresentacion pCtrlPresentacion) {
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