import javax.swing.*;
import java.util.*;

public class VencimientoMVC {
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				List<VencimientoModel> model = new ArrayList<VencimientoModel>();
				VencimientoView view = new VencimientoView(model);
				view.setVisible(true);
			}
		});
	}
}