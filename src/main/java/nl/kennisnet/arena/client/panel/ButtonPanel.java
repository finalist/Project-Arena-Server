package nl.kennisnet.arena.client.panel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ButtonPanel extends VerticalPanel implements ResizablePanel {

	public ButtonPanel() {
		super();
		getElement().setPropertyInt("cellSpacing", 3);
		add(new QuestItemTypeButtonPanel());
		add(new TypeFilterPanel());
		add(new WallTogglePanel());
		add(new ZoomToFitPanel());
		add(createAppLink());
	}

	public Widget createAppLink() {
		Anchor a = new Anchor("Download applicaties");
		a.setStyleName("paneltext");
		a.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Window.open("/arena-server/apks.html", "_blank", "");

			}
		});

		return a;
	}

	@Override
	public void resize(int x, int y) {
		setHeight(y + "px");
		setWidth(x + "px");
	}

}
