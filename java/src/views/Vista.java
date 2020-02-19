package views;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Superclase abstracta Vista
 * @author Felix Fernando Ramos Velazquez
 */
public class Vista {
	/* Protected attributes */
	protected CtrlPresentacion ctrlPresentacion;
	//-- Containers --//
	protected JFrame frameView = new JFrame("Programador de guardias");
	protected JPanel panelContents = new JPanel();

	/* Protected static attributes */
	protected static final int width = 700;		// panelContents width
	protected static final int height = 378;	// panelContents height
	protected static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", new Locale("es","ES"));
	
	/* Constructors & protected methods */
	protected Vista(CtrlPresentacion pCtrlPresentacion) {
		ctrlPresentacion = pCtrlPresentacion;
	}

	protected void init() {}

	protected void init_frameView() {
		frameView = ctrlPresentacion.getFrame();
		JPanel contentPane = (JPanel) frameView.getContentPane();
		contentPane.setLayout(null);
		panelContents.setBounds(0,0,width,height);
		contentPane.add(panelContents);
	}

	protected void init_panelContents() {}

	protected void assign_listenersComponents() {}
	
	//dialogs
	protected static void successfulOperationDialog(Object message) {
		JOptionPane.showMessageDialog(null, message, "Operacion realizada", JOptionPane.INFORMATION_MESSAGE);
	}

	protected static void canceledOperationDialog(Object message) {
		JOptionPane.showMessageDialog(null, message, "Operacion cancelada", JOptionPane.WARNING_MESSAGE);
	}
	
	protected static void rejectedOperationDialog(Object message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	protected static int confirmationDialog(Object message, String title) {
		return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
	}
	
	/* Public methods */
	public JPanel getPanel() {
		return panelContents;
	}

	public void showPanel() {
		panelContents.setVisible(true);
	}

	public void hidePanel() {
		panelContents.setVisible(false);
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
}
