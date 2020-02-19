package views;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.lang.NumberFormatException;
import java.io.File;
import java.io.IOException;

import com.toedter.calendar.JDateChooser;

/**
 * Vista gestion de calendario
 * @author Felix Fernando Ramos Velazquez
 */
public class VistaCalendario extends Vista {

	//-- Containers --//
	private JPanel panelTop = new JPanel();
	private JPanel panelVacationList = new JPanel();
	private JPanel panelVacationHandler = new JPanel();
	private JPanel panelBottom = new JPanel();
	private JPanel panelTxtFieldsDialog = new JPanel();
	//-- Buttons --//
	private JButton buttonCreateCal = new JButton("Crear calendario");
	private JButton buttonImportCal = new JButton("Importar calendario");
	private JButton buttonDeleteCal = new JButton("Eliminar calendario");
	private JButton buttonAddVacation = new JButton("Anadir dia");	
	private JButton buttonModVacation = new JButton("Modificar dia");
	private JButton buttonDelVacation = new JButton("Eliminar dia");
	private JButton buttonGoBack = new JButton("Volver");
	private JButton buttonHelp = new JButton("Ayuda");
	//-- Labels --//
	private JLabel labelCalendar, labelVacationHeaders;
	private JLabel labelMorningDrs, labelEveningDrs, labelNightDrs, labelSpecialDate;
	//-- Text fields --//
	private JTextField textMorningDrs = new JTextField(4);
	private JTextField textEveningDrs = new JTextField(4);
	private JTextField textNightDrs = new JTextField(4);
	private JComboBox<String> comboSpecialDate;

	//-- Others private attributes--//
	DefaultListModel<String> dlm = new DefaultListModel<String>();
	private JList<String> listVacations;
	private JScrollPane scrollPanel;
	private JDateChooser dateChooser = new JDateChooser("dd/MM/yyyy", "##/##/##", '_');
	private int calendarYear;
	private final String pattern = "%7s%11s%9s%9s%-7s%s";	// patron formato lista dias vacacionales

	/* Private Methods */
	private void init_panelTop() {
		// panel
		panelTop.setLayout(null);
		panelTop.setBounds(0, 0, width, 95);
		// components
		buttonCreateCal.setBounds(325, 10, 170, 30);
		buttonCreateCal.setFont(new Font("Arial", Font.BOLD, 11));
		buttonDeleteCal.setBounds(325, 10, 170, 30);
		buttonDeleteCal.setFont(new Font("Arial", Font.BOLD, 11));
		buttonImportCal.setBounds(505, 10, 170, 30);
		buttonImportCal.setFont(new Font("Arial", Font.BOLD, 11));
		labelCalendar = new JLabel();
		labelCalendar.setBounds(20, 10, 325, 30);
		labelVacationHeaders = new JLabel(String.format("%7s%9s%9s%9s%-7s%s", " Dia vac.","M","T","N","","Fecha espec."));
		labelVacationHeaders.setFont(new Font("Courier", Font.BOLD,14));
		labelVacationHeaders.setBounds(20, 75, 495, 15);
		// tooltips
		buttonCreateCal.setToolTipText("[CTRL+C]");
		buttonDeleteCal.setToolTipText("[CTRL+E]");
		buttonImportCal.setToolTipText("[CTRL+I]");
		
		panelTop.add(labelCalendar);
		panelTop.add(buttonCreateCal);
		panelTop.add(buttonImportCal);
		panelTop.add(buttonDeleteCal);
		panelTop.add(labelVacationHeaders);

		// tooltips
		buttonCreateCal.setToolTipText("Crear nuevo calendario");
		buttonImportCal.setToolTipText("Importar calendario existente");
		buttonDeleteCal.setToolTipText("Eliminar calendario existente");
	}

	private void init_panelVacationInfo() {
		// panel
		panelVacationList.setLayout(null);
		panelVacationList.setBounds(0, 50, 525, 265);
		// components
		listVacations = new JList<String>(dlm);
		listVacations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
		listVacations.setFont(new Font("Courier", Font.PLAIN,14));
		scrollPanel = new JScrollPane(listVacations);
		scrollPanel.setBounds(20, 45, 495, 200);

		panelVacationList.add(scrollPanel);
	}

	private void init_panelVacationHandler() {
		// panel
		panelVacationHandler.setLayout(null);
		panelVacationHandler.setBounds(525, 95, 175, 220);
		// components
		dateChooser.setBounds(0, 0, 150, 25);
		dateChooser.setEnabled(false);
		dateChooser.setLocale(new Locale("es","ES"));
		buttonAddVacation.setBounds(0, 35, 150, 40);
		buttonAddVacation.setEnabled(false);
		buttonModVacation.setBounds(0, 95, 150, 40);
		buttonModVacation.setEnabled(false);
		buttonDelVacation.setBounds(0, 155, 150, 40);
		buttonDelVacation.setEnabled(false);
		// tooltips
		buttonAddVacation.setToolTipText("[CTRL+A]");
		buttonModVacation.setToolTipText("[CTRL+M]");
		buttonDelVacation.setToolTipText("[CTRL+D]");
		
		panelVacationHandler.add(dateChooser);
		panelVacationHandler.add(buttonAddVacation);
		panelVacationHandler.add(buttonModVacation);
		panelVacationHandler.add(buttonDelVacation);
	}

	private void init_panelBottom() {
		// panel
		panelBottom.setLayout(null);
		panelBottom.setBounds(0, 315, width, 60);
		// components
		buttonGoBack.setBounds(20, 10, 150, 30);
		buttonHelp.setBounds(365, 10, 150, 30);
		// tooltips
		buttonGoBack.setToolTipText("[ESC]");
		buttonHelp.setToolTipText("[CTRL+H]");
		
		panelBottom.add(buttonGoBack);
		panelBottom.add(buttonHelp);
	}

	private void init_panelTxtFieldsDialog() {
		labelMorningDrs = new JLabel("Drs. manana:");
		labelEveningDrs = new JLabel("Drs. tarde:");
		labelNightDrs = new JLabel("Drs. noche:");
		labelSpecialDate = new JLabel("Fecha especial:");
		textMorningDrs.setHorizontalAlignment(JTextField.RIGHT);
		textEveningDrs.setHorizontalAlignment(JTextField.RIGHT);
		textNightDrs.setHorizontalAlignment(JTextField.RIGHT);
		String[] specials = {"-","navidad","noche_buena", "noche_vieja", "semana_santa"};
		comboSpecialDate = new JComboBox<String>(specials);
		comboSpecialDate.setBackground(Color.white);

		panelTxtFieldsDialog.add(labelMorningDrs);
		panelTxtFieldsDialog.add(textMorningDrs);
		panelTxtFieldsDialog.add(Box.createHorizontalStrut(15));
		panelTxtFieldsDialog.add(labelEveningDrs);
		panelTxtFieldsDialog.add(textEveningDrs);
		panelTxtFieldsDialog.add(Box.createHorizontalStrut(15));
		panelTxtFieldsDialog.add(labelNightDrs);
		panelTxtFieldsDialog.add(textNightDrs);
		panelTxtFieldsDialog.add(Box.createHorizontalStrut(15));
		panelTxtFieldsDialog.add(labelSpecialDate);
		panelTxtFieldsDialog.add(comboSpecialDate);
		
	}

	/**
	 * Actualiza el contenido de la lista de dias vacacionales
	 */
	private void update_listVacations() {
		dlm.clear();
		try {
		// llamada a dominio
		ArrayList<ArrayList<String>> vacations = ctrlPresentacion.getALLVacations();
		if (!vacations.isEmpty()) {
			for (ArrayList<String> vacationDay : vacations) {
				String date = vacationDay.get(0);
				String morningDrs = vacationDay.get(1);
				String eveningDrs = vacationDay.get(2);
				String nightDrs = vacationDay.get(3);
				String especialDate = (vacationDay.get(4).equals("-")) ? "" : vacationDay.get(4).replace('_', ' ');
				dlm.addElement(String.format(pattern, date, morningDrs, eveningDrs, nightDrs,"", especialDate));
			}
		}
		}
		catch(IOException e) {rejectedOperationDialog("Se ha producido el siguiente error al actualizar la lista de dias vacacionales:\n" + e.getMessage());}
	}

	/**
	 * Obtiene la fecha del dia vacacional del objeto string pasado como parametro
	 * @param strDate String con la info del dia vacacional
	 * @return GregorianCalendar con la fecha del dia vacacional
	 */
	private GregorianCalendar getSelectedDate(String strDate) throws NumberFormatException, ParseException {
		try {
			int hyphenIndex = strDate.indexOf('-');
			int beginIndex = (strDate.charAt(hyphenIndex-2) != ' ') ? hyphenIndex-2 : hyphenIndex-1;

			int d = Integer.parseInt(strDate.substring(beginIndex, hyphenIndex));
			int M;
			switch (strDate.substring(hyphenIndex+1, hyphenIndex+4)) {
			case "ene": {M = 0;break;}
			case "feb": {M = 1;break;}
			case "mar": {M = 2;break;}
			case "abr": {M = 3;break;}
			case "may": {M = 4;break;}
			case "jun": {M = 5;break;}
			case "jul": {M = 6;break;}
			case "ago": {M = 7;break;}
			case "sep": {M = 8;break;}
			case "oct": {M = 9;break;}
			case "nov": {M = 10;break;}
			case "dic": {M = 11;break;}
			default: {throw new ParseException("El mes de la fecha seleccionada no es correcto ",0);}
			}
			GregorianCalendar date = new GregorianCalendar(calendarYear,M,d);
			return date;
		}
		catch (NumberFormatException e) {throw new NumberFormatException("La fecha seleccionada no es correcta ");}
	}

	protected void init_panelContents() {
		panelContents.setLayout(null);
		panelContents.add(panelTop);
		panelContents.add(panelVacationList);
		panelContents.add(panelVacationHandler);
		panelContents.add(panelBottom);
	}

	//******************************************************************************//
	/* Methods of the listener interfaces */
	public void actionPerformed_buttonCreateCal(ActionEvent event) {
		JTextField textYear = new JTextField(6);
		JPanel options = new JPanel();
		options.add(new JLabel("Introducir anyo del calendario:"));
		options.add(textYear);
		if (JOptionPane.showConfirmDialog(null, options, "Crear calendario", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
				try {
					int year = Integer.parseInt(textYear.getText());
					GregorianCalendar currentDate = new GregorianCalendar();
					int minYear = currentDate.get(GregorianCalendar.YEAR);
					int maxYear = currentDate.get(GregorianCalendar.YEAR) + 5;
					if (year < minYear || year > maxYear) {
						rejectedOperationDialog("El anyo ha de estar dentro del rango " + minYear + " - " + maxYear + " ");
					}
					else {
						ctrlPresentacion.createCalendar(year);
						ctrlPresentacion.saveCalendar();
						update_view(true);
						successfulOperationDialog("Se ha creado el calendario del anyo " + year + " ! ");
					}
				}
				catch(NumberFormatException e) {rejectedOperationDialog("El anyo introducido no es correcto ");}
				catch(IOException e) {rejectedOperationDialog("Se ha producido el siguiente error al guardar el calendario:\n" + e.getMessage());}
				return;

		}
		canceledOperationDialog("No se ha creado el calendario !");
	}

	public void actionPerformed_buttonDeleteCal(ActionEvent event) {
		if (confirmationDialog("Eliminar el calendario actual ? ", "Eliminar calendario") == JOptionPane.YES_OPTION) {
			try {
				int year = calendarYear;
				ctrlPresentacion.deleteCalendar();
				ctrlPresentacion.saveCalendar();
				update_view(false);
				successfulOperationDialog("Se ha eliminado el calendario vacacional " + year + " ! ");
			}
			catch(IOException e) {rejectedOperationDialog("Se ha producido el siguiente error al guardar el calendario:\n" + e.getMessage());}
		}	
	}

	public void actionPerformed_buttonImportCal(ActionEvent event) throws ParseException {
		if (!ctrlPresentacion.existsCalendar() ||
				confirmationDialog("Importar un nuevo calendario y reemplazar el calendario actual ? ", "Importar calendario") == JOptionPane.OK_OPTION) {
			JFileChooser chooser = new JFileChooser();
			/*FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "Text File (.txt)", "txt");
			  //  chooser.setFileFilter(filter);*/
			int returnVal = chooser.showOpenDialog(frameView);
			File f = chooser.getSelectedFile();
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				try{
			       ctrlPresentacion.importCalendar((f.getAbsolutePath()));
			       ctrlPresentacion.cargarCale();
				} catch(IOException eX) {
					rejectedOperationDialog(eX);
//					JOptionPane.showMessageDialog(null, eX, "Error", JOptionPane.ERROR_MESSAGE); 

				}
			 }
		}
	}
	//--------------------------------------------------------------------------//
	public void actionPerformed_listVacations(ListSelectionEvent event) {
		if (!listVacations.isSelectionEmpty()) {
			buttonModVacation.setEnabled(true);
			buttonDelVacation.setEnabled(true);
		}
		else {
			buttonModVacation.setEnabled(false);
			buttonDelVacation.setEnabled(false);
		}
	}


	public void actionPerformed_dateChooser(ActionEvent event) {
		if (dateChooser.getDate() == null)
			dateChooser.setCalendar(new GregorianCalendar(calendarYear,0,1,0,0,0));
	}

	public void actionPerformed_buttonAddVacation(ActionEvent event) {
		Date selectedDate = dateChooser.getDate();
		if (selectedDate != null && !(selectedDate.before(dateChooser.getMinSelectableDate())) && !(selectedDate.after(dateChooser.getMaxSelectableDate()))) {
			textMorningDrs.setForeground(Color.black);
			textEveningDrs.setForeground(Color.black);
			textNightDrs.setForeground(Color.black);
			comboSpecialDate.setForeground(Color.black);
			textMorningDrs.setText("0");
			textEveningDrs.setText("0");
			textNightDrs.setText("0");
			comboSpecialDate.setSelectedIndex(0);
			Object[] options = {new JLabel("Anadir el dia vacacional " + simpleDateFormat.format(selectedDate) + " ? "), Box.createVerticalStrut(20), panelTxtFieldsDialog,Box.createVerticalStrut(20)};
			if (JOptionPane.showConfirmDialog(null, options, "Anadir dia", JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
				try{
					int morningDrs = Integer.parseInt(textMorningDrs.getText());
					int eveningDrs = Integer.parseInt(textEveningDrs.getText());
					int nightDrs = Integer.parseInt(textNightDrs.getText());
					String especialDate = comboSpecialDate.getSelectedItem().toString();
					GregorianCalendar date = new GregorianCalendar();
					date.setTime(selectedDate);
					// llamada a dominio
					if (ctrlPresentacion.addVacation(date, morningDrs, eveningDrs, nightDrs, especialDate)) {
						update_listVacations();
						ctrlPresentacion.saveCalendar();
						dateChooser.setDate(null);
						successfulOperationDialog("Se ha anadido el dia vacacional " + simpleDateFormat.format(selectedDate) + " ! ");
					}
				}
				catch(NumberFormatException e) {rejectedOperationDialog("El numero de doctores de alguno de los turnos no es correcto ");}
				catch(IOException e) {rejectedOperationDialog(e.getMessage());}
				catch(Exception e) {rejectedOperationDialog("Se ha producido el siguiente error:\n" + e.getMessage());}
			}
		}
		else {
			rejectedOperationDialog("No se ha introducido una fecha o la fecha introducida no es correcta ");
		}
	}

	public void actionPerformed_ModVacation() {
		if (!listVacations.isSelectionEmpty()) {
			try {
				GregorianCalendar date = getSelectedDate(listVacations.getSelectedValue()); // llamada a dominio
				if (confirmationDialog("Modificar el dia vacacional " + simpleDateFormat.format(date.getTime()) + " ? ", "Modificar dia") == JOptionPane.YES_OPTION) {
					ArrayList<String> vacation = ctrlPresentacion.getVacationDay(date);
					textMorningDrs.setForeground(Color.red);
					textEveningDrs.setForeground(Color.red);
					textNightDrs.setForeground(Color.red);
					comboSpecialDate.setForeground(Color.red);
					textMorningDrs.setText(vacation.get(0));
					textEveningDrs.setText(vacation.get(1));
					textNightDrs.setText(vacation.get(2));
					comboSpecialDate.setSelectedItem(vacation.get(3));
//					System.out.println("especial: " + vacation.get(3));
//					if (!vacation.equals("-")) comboSpecialDate.setSelectedItem(vacation.get(3));				
//					else comboSpecialDate.setSelectedIndex(0);
					Object[] options = {new JLabel("Introducir los nuevos datos para el dia vacacional " + simpleDateFormat.format(date.getTime()) + ": "), Box.createVerticalStrut(20), panelTxtFieldsDialog,Box.createVerticalStrut(20)};
					if (JOptionPane.showConfirmDialog(null, options, "Modificar dia", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
						int morningDrs = Integer.parseInt(textMorningDrs.getText());
						int eveningDrs = Integer.parseInt(textEveningDrs.getText());
						int nightDrs = Integer.parseInt(textNightDrs.getText());
						String especialDate = comboSpecialDate.getSelectedItem().toString();
						// llamada a dominio
						if (ctrlPresentacion.modifyVacation(date, morningDrs, eveningDrs, nightDrs, especialDate)) {
							update_listVacations();
							ctrlPresentacion.saveCalendar();
							successfulOperationDialog("Se ha modificado el dia vacacional !");
						}
					}
					else canceledOperationDialog("No se ha realizado ningun cambio !");
				}

			}
			catch(NumberFormatException e) {rejectedOperationDialog("El numero de doctores de alguno de los turnos no es correcto ");}
			catch(ParseException e) {rejectedOperationDialog(e.getMessage());}
			catch(IOException e) {rejectedOperationDialog(e.getMessage());}
			catch(Exception e) {rejectedOperationDialog("Se ha producido el siguiente error:\n" + e.getMessage());}
		}
	}

	public void actionPerformed_buttonDelVacation(ActionEvent event) {
		if (!listVacations.isSelectionEmpty()) {
			try {
				GregorianCalendar date = getSelectedDate(listVacations.getSelectedValue());
				if (confirmationDialog("Eliminar el dia vacacional " + simpleDateFormat.format(date.getTime()) + " ? ", "Eliminar dia vacacional") == JOptionPane.YES_OPTION) {
					// llamada a dominio
					if (ctrlPresentacion.deleteVacationDay(date)) {
						update_listVacations();
						ctrlPresentacion.saveCalendar();
						successfulOperationDialog("Se ha eliminado el dia vacacional !");
					}	
				}
			}
			catch(NumberFormatException e) {rejectedOperationDialog(e.getMessage());}
			catch(ParseException e) {rejectedOperationDialog(e.getMessage());}
			catch(IOException e) {rejectedOperationDialog(e.getMessage());}
			catch(Exception e) {rejectedOperationDialog("Se ha producido el siguiente error:\n" + e.getMessage());}
		}
		else rejectedOperationDialog("No se ha seleccionado ningun dia vacacional ");
	}
	//--------------------------------------------------------------------------//
	public void actionPerformed_buttonGoBack(ActionEvent event) {
		ctrlPresentacion.changeView("vistaGestion", panelContents);
		listVacations.clearSelection();
	}
	
	
	public void actionPerformed_buttonHelp (ActionEvent event) {
		Object[] options = {new JLabel("M:  Numero de Drs. del turno de manana"),
				new JLabel("T:  Numero de Drs. del turno de tarde"),
				new JLabel("N:  Numero de Drs. del turno de noche"),
				new JLabel("Para anadir un dia vacacional: introducir la fecha o utilizar el selector de fechas y presionar 'Anadir dia'"),
				new JLabel("Para modificar un dia vacacional: seleccionar la fecha de la lista y presionar 'Modificar dia' (o doble clic)"),
				new JLabel("Para eliminar un dia vacacional: seleccionar la fecha de la lista y presionar 'Eliminar dia'")};
		JOptionPane.showMessageDialog(null, options, "Ayuda",JOptionPane.INFORMATION_MESSAGE);
		
	}
	//--------------------------------------------------------------------------//
	/* Assigning listeners */
	//--------------------------------------------------------------------------//
	protected void assign_listenersComponents() {

		buttonCreateCal.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				actionPerformed_buttonCreateCal(event);
			}
		});

		buttonDeleteCal.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				actionPerformed_buttonDeleteCal(event);
			}
		});

		buttonImportCal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					actionPerformed_buttonImportCal(event);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		//--------------------------------------------------------------------------//
		listVacations.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				actionPerformed_listVacations(event);
			}
		});

		listVacations.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) actionPerformed_ModVacation ();
			}
		});

		dateChooser.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				actionPerformed_dateChooser (event);
			}
		});

		buttonAddVacation.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				actionPerformed_buttonAddVacation(event);
			}
		});

		buttonModVacation.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				actionPerformed_ModVacation ();
			}
		});

		buttonDelVacation.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				actionPerformed_buttonDelVacation(event);
			}
		});
		//--------------------------------------------------------------------------//
		buttonGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				actionPerformed_buttonGoBack (event);
			}
		});

		buttonHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				actionPerformed_buttonHelp (event);
			}
		});
		
		panelVacationHandler.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonAddVacation");
		panelVacationHandler.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonModVacation");
		panelVacationHandler.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonDelVacation");
	    panelBottom.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "buttonGoBack");
	    panelBottom.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonHelp");
	    panelTop.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonCreateCal");
	    panelTop.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonDeleteCal");
	    panelTop.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_DOWN_MASK), "buttonImportCal");
	    
	    panelVacationHandler.getActionMap().put("buttonAddVacation", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	buttonAddVacation.doClick();
            }
        });
	    
	    panelVacationHandler.getActionMap().put("buttonModVacation", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	buttonModVacation.doClick();
            }
        });
	    
	    panelVacationHandler.getActionMap().put("buttonDelVacation", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	buttonDelVacation.doClick();
            }
        });
	    
	    panelBottom.getActionMap().put("buttonGoBack", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	buttonGoBack.doClick();
            }
        });
	    
	    panelBottom.getActionMap().put("buttonHelp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	buttonHelp.doClick();
            }
        });
	    
	    panelTop.getActionMap().put("buttonCreateCal", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	buttonCreateCal.doClick();
            }
        });
	    
	    panelTop.getActionMap().put("buttonDeleteCal", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	buttonDeleteCal.doClick();
            }
        });
	    
	    panelTop.getActionMap().put("buttonImportCal", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	buttonImportCal.doClick();
            }
        });
	
	}
	//******************************************************************************//

	/* Constructors & public methods */
	public VistaCalendario(CtrlPresentacion pCtrlPresentacion) {
		super(pCtrlPresentacion);
	}

	public void init() {
		init_panelTop();
		init_panelVacationInfo();
		init_panelVacationHandler();
		init_panelBottom();
		init_panelTxtFieldsDialog();
		init_frameView();
		init_panelContents();
		assign_listenersComponents();
		update_view(ctrlPresentacion.existsCalendar());
	}

	/**
	 * Actualiza la vista
	 * @param existsCalendar Indica si existe un calendario vacacional en el hospital actual
	 */
	public void update_view(boolean existsCalendar) {
		if (existsCalendar) {
			calendarYear = ctrlPresentacion.getCalendarYear();
			labelCalendar.setText("Calendario vacacional " + calendarYear);
			labelCalendar.setFont(new Font("Arial", Font.BOLD, 16));
			buttonDeleteCal.setVisible(true);
			buttonCreateCal.setVisible(false);
			GregorianCalendar minSelectDate, maxSelectDate;
			minSelectDate = new GregorianCalendar(calendarYear,0,1,0,0,0);
			maxSelectDate = new GregorianCalendar(calendarYear,11,31,23,59,59);
			dateChooser.setSelectableDateRange(minSelectDate.getTime(), maxSelectDate.getTime());
			dateChooser.setEnabled(true);
			buttonAddVacation.setEnabled(true);
			listVacations.setEnabled(true);
			update_listVacations();
		}
		else {
			calendarYear = -1;
			labelCalendar.setText("(No hay calendario vacacional)");
			labelCalendar.setFont(new Font("Arial", Font.ITALIC, 16));
			dateChooser.setCalendar(null);
			dateChooser.setEnabled(false);
			buttonAddVacation.setEnabled(false);
			buttonCreateCal.setVisible(true);
			buttonDeleteCal.setVisible(false);
			dlm.clear();
			listVacations.setEnabled(false);
		}
	}
}
