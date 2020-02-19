import views.CtrlPresentacion;

public class MainProgram {
	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CtrlPresentacion mainWindow = new CtrlPresentacion();
				mainWindow.initPresentation();
			}
		});

	}
}
