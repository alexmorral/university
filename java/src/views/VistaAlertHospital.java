package views;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * Vista principal de la gestion de un hospital
 * @author Axel Pelaez
 */
	
	public class VistaAlertHospital {
		
		//Componentes interficie
		private JFrame frameView = new JFrame("WARNING");
		private JPanel panelContents = new JPanel();
		
		private JPanel panelCenterButtons = new JPanel();
		
		
		
		
		//CENTER
		
		private JLabel labelPanel1 = new JLabel("<html>Ya existe un Calendario para: <br/> ''NOMBRE DEL HOSPITAL");
		private JLabel labelPanel2 = new JLabel("<html><CENTER>¿Que desea hacer?</CENTER>"); 
		private JButton buttonCrearCal = new JButton("<html><CENTER>Crear nuevo <br/> Calendario</CENTER>");
		private JButton buttonImportCal = new JButton("<html><CENTER>Importar nuevo <br/> Calendario</CENTER>");
		private JButton buttonCancel = new JButton("<html>Cancelar");
		
		
		
		
		//METODOS PRIVADOS
		
	
		
		private void inicializar_frameView() {
			// Tamanyo
		    frameView.setMinimumSize(new Dimension(700,400));
		    frameView.setPreferredSize(frameView.getMinimumSize());
		    frameView.setResizable(false);
		    // Posicion y operaciones por defecto
		    frameView.setLocationRelativeTo(null);
		    frameView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		    JPanel contentPane = (JPanel) frameView.getContentPane();
		    contentPane.add(panelContents);
		}
		
	
		
		private void inicializar_panelContents() {
			panelContents.setLayout(null);
			panelCenterButtons.setBounds(0, 0, 700, 372);
			// Componentes
			
			panelContents.add(panelCenterButtons);
						
		}
		
		
		
	
		

	
		
		private void inicializar_panelCenterButtons() {
			
			////// START: GESTIONADO POR EL BUILDER NO TOCAR
			panelCenterButtons.setBorder(new EmptyBorder(70, 20, 20, 0));
		
			buttonCrearCal.setBounds(50, 271, 177, 50);;
			
			buttonImportCal.setBounds(251, 271, 177, 50);
			
			buttonCancel.setBounds(462, 271, 177, 50);
			
			labelPanel1.setBounds(78, 47, 226, 44);
			labelPanel2.setBounds(267, 172, 191, 38);
			
			panelCenterButtons.setLayout(null);
			/// END: GESTIONADO POR EL BUILDER NO TOCAR
			
			// Components
			
			panelCenterButtons.add(labelPanel1);
			panelCenterButtons.add(labelPanel2);
			panelCenterButtons.add(buttonImportCal);
			panelCenterButtons.add(buttonCrearCal);
			panelCenterButtons.add(buttonCancel);
			
		}
		
		
		private void inicializarComponents() {
		    inicializar_frameView();
		    inicializar_panelContents();
		    inicializar_panelCenterButtons();
		    assignar_listenersComponents();
		  }


		private void assignar_listenersComponents() {
		}
		
		
		//METODOS PUBLICOS
		/**
		 * @wbp.parser.entryPoint
		 */
		public VistaAlertHospital() {
			inicializarComponents();
		}
	
		public void showView() {
			frameView.setVisible(true);
		}

		public void enableView() {
			frameView.setEnabled(true);
		}

		public void disableView() {
			frameView.setEnabled(false);
		}
		
		/*public static void main(String[] args) {
			VistaGestion v = new VistaGestion();
			v.showView();
		}*/
	}


