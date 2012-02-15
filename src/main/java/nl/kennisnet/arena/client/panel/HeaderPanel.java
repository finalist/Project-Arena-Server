package nl.kennisnet.arena.client.panel;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class HeaderPanel extends FlowPanel {

	public HeaderPanel() {
		this.getElement().setId("header");
		this.add(createPanel("arenaLogo"));
		this.add(new MenuPanel());
		this.add(createPanel("kennisnetLogo"));
	}

	private Panel createPanel(String id){
		Panel result=new SimplePanel();
		result.getElement().setId(id);
		return result;
	}
	
}
