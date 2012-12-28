package nl.kennisnet.arena.client.dialog;

import nl.kennisnet.arena.client.domain.PoiDTO;
import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.elements.Element;
import nl.kennisnet.arena.client.elements.ImageElement;
import nl.kennisnet.arena.client.elements.QuestionElement;
import nl.kennisnet.arena.client.elements.StoryElement;
import nl.kennisnet.arena.client.elements.VideoElement;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.LogEvent;
import nl.kennisnet.arena.client.event.RefreshQuestEvent;
import nl.kennisnet.arena.client.event.UpdateQuestItemEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainDialog extends DialogBox implements
		UpdateQuestItemEvent.Handler {
	private VerticalPanel verticalPanel;
	private HorizontalPanel hoofdPaneel;
	private VerticalPanel bodem;
	private ListBox items;
	private TextBox txtbxNaam;
	private TextBox textBoxZichtbaar;
	private TextBox textBoxVindbaar;
	private PoiDTO itemDTO;
	private final boolean readOnlyDialog;
	private final boolean create;

	private final int IMAGE_DIALOG = 0;
	private final int STORY_DIALOG = 1;
	private final int VIDEO_DIALOG = 2;
	private final int OBJECT3D_DIALOG = 4;
	private final int QUESTION_DIALOG = 3;

	public MainDialog(final PoiDTO itemDTO, boolean readOnlyDialog,
			boolean create) {
		super(false, true);
		this.readOnlyDialog = readOnlyDialog;
		this.create = create;
		EventBus.get().addHandler(UpdateQuestItemEvent.TYPE, this);
		EventBus.get().fireEvent(
				new LogEvent("Dialog is called with :" + readOnlyDialog
						+ " and opened readonly :" + this.readOnlyDialog));
		this.itemDTO = itemDTO;
		createForm();
		fillFormFromItem(itemDTO);
		txtbxNaam.setFocus(true);

	}

	private void createForm() {
		setSize("100%", "100%");
		setHTML("Nieuwe marker");

		verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");

		hoofdPaneel = new HorizontalPanel();
		hoofdPaneel.setSpacing(25);
		hoofdPaneel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(hoofdPaneel);
		hoofdPaneel.setSize("100%", "70%");

		VerticalPanel labels = new VerticalPanel();
		labels.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		labels.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		labels.setSpacing(20);
		hoofdPaneel.add(labels);

		Label lblPlaatje = new Label("Plaatje");
		labels.add(lblPlaatje);
		lblPlaatje.setHeight("25");

		Label label = new Label("Verhaal");
		labels.add(label);
		label.setHeight("25");

		Label lblVideo = new Label("Video");
		lblVideo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		labels.add(lblVideo);
		lblVideo.setHeight("25");

		Label lslVraag = new Label("Vraag");
		labels.add(lslVraag);
		lslVraag.setHeight("25");

		Label lbldObject = new Label("3D Object");
		labels.add(lbldObject);
		lbldObject.setHeight("25");

		VerticalPanel knoppen = new VerticalPanel();
		knoppen.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		knoppen.setSpacing(20);
		hoofdPaneel.add(knoppen);

		Button addPlaatje = new Button("Voeg toe");
		addPlaatje.addClickHandler(new AddHandler(IMAGE_DIALOG, itemDTO));
		knoppen.add(addPlaatje);

		Button addVerhaal = new Button("Voeg toe");
		addVerhaal.addClickHandler(new AddHandler(STORY_DIALOG, itemDTO));
		knoppen.add(addVerhaal);

		Button addVideo = new Button("Voeg toe");
		addVideo.addClickHandler(new AddHandler(VIDEO_DIALOG, itemDTO));
		knoppen.add(addVideo);

		Button addInfo = new Button("Voeg toe");
		addInfo.addClickHandler(new AddHandler(QUESTION_DIALOG, itemDTO));
		knoppen.add(addInfo);

		Button add3D = new Button("Voeg toe");
		add3D.addClickHandler(new AddHandler(OBJECT3D_DIALOG, itemDTO));
		knoppen.add(add3D);

		items = new ListBox();
		hoofdPaneel.add(items);
		items.setSize("125", "240");
		items.setVisibleItemCount(10);
		items.addDoubleClickHandler(new editHandler());

		bodem = new VerticalPanel();
		bodem.setSpacing(5);
		verticalPanel.add(bodem);
		bodem.setSize("100%", "100%");

		HorizontalPanel naam = new HorizontalPanel();
		bodem.add(naam);
		naam.setWidth("100%");

		Label lblNaam = new Label("Naam");
		naam.add(lblNaam);

		txtbxNaam = new TextBox();
		naam.add(txtbxNaam);
		txtbxNaam.setWidth("120");
		naam.setCellHorizontalAlignment(txtbxNaam,
				HasHorizontalAlignment.ALIGN_RIGHT);

		HorizontalPanel zichtbaar = new HorizontalPanel();
		bodem.add(zichtbaar);
		zichtbaar.setWidth("100%");

		Label lblZichtbaarRadius = new Label("Zichtbaar Radius");
		zichtbaar.add(lblZichtbaarRadius);

		textBoxZichtbaar = new TextBox();
		textBoxZichtbaar.setAlignment(TextAlignment.CENTER);
		textBoxZichtbaar.setText("100");
		zichtbaar.add(textBoxZichtbaar);
		textBoxZichtbaar.setWidth("40");
		zichtbaar.setCellHorizontalAlignment(textBoxZichtbaar,
				HasHorizontalAlignment.ALIGN_RIGHT);

		HorizontalPanel vindbaar = new HorizontalPanel();
		bodem.add(vindbaar);
		vindbaar.setWidth("100%");

		Label lblVindbaarRadius = new Label("Vindbaar Radius");
		vindbaar.add(lblVindbaarRadius);

		textBoxVindbaar = new TextBox();
		textBoxVindbaar.setAlignment(TextAlignment.CENTER);
		textBoxVindbaar.setText("250");
		vindbaar.add(textBoxVindbaar);
		textBoxVindbaar.setWidth("40");
		vindbaar.setCellHorizontalAlignment(textBoxVindbaar,
				HasHorizontalAlignment.ALIGN_RIGHT);

		HorizontalPanel bodem_knoppen = new HorizontalPanel();
		bodem_knoppen
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		bodem.add(bodem_knoppen);
		bodem.setCellHorizontalAlignment(bodem_knoppen,
				HasHorizontalAlignment.ALIGN_CENTER);
		bodem_knoppen.setWidth("");

		Button btnOpslaan = new Button("Opslaan");
		bodem_knoppen.add(btnOpslaan);
		bodem_knoppen.setCellHorizontalAlignment(btnOpslaan,
				HasHorizontalAlignment.ALIGN_CENTER);

		btnOpslaan.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				fillItemFromForm(itemDTO);
				EventBus.get().fireEvent(new UpdateQuestItemEvent());
				hide();
			}
		});

		Button btnVerwijderen = new Button("Verwijderen");
		bodem_knoppen.add(btnVerwijderen);
		bodem_knoppen.setCellHorizontalAlignment(btnVerwijderen,
				HasHorizontalAlignment.ALIGN_CENTER);

		btnVerwijderen.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (create) {
					QuestState.getInstance().getState().removeItem(itemDTO);
					EventBus.get().fireEvent(new RefreshQuestEvent());
				}
				hide();
			}
		});

		Button btnAnnuleren = new Button("Annuleren");
		bodem_knoppen.add(btnAnnuleren);
		bodem_knoppen.setCellHorizontalAlignment(btnAnnuleren,
				HasHorizontalAlignment.ALIGN_CENTER);

		btnAnnuleren.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();

			}
		});
	}

	private void fillFormFromItem(PoiDTO itemDTO) {
		txtbxNaam.setText(itemDTO.getName());
		textBoxVindbaar.setText("" + itemDTO.getRadius());
		textBoxZichtbaar.setText("" + itemDTO.getVisibleRadius());
		
		refresh();
	}

	public PoiDTO fillItemFromForm(PoiDTO itemDTO) {
		itemDTO.setName(txtbxNaam.getText());
		itemDTO.setRadius(Double.valueOf(textBoxZichtbaar.getText()));
		itemDTO.setVisibleRadius(Double.valueOf(textBoxZichtbaar.getText()));
		return itemDTO;
	}

	public PoiDTO getPoiDTO() {
		return itemDTO;
	}

	public boolean isReadOnly() {
		return readOnlyDialog;
	}

	public void refresh() {
		items.clear();

		for (Element item : itemDTO.getElements()) {
			if (item instanceof ImageElement) {
				items.addItem("Image");
			} else if (item instanceof QuestionElement) {
				items.addItem("Question");
			} else if (item instanceof VideoElement) {
				items.addItem("Video");
			} else if (item instanceof StoryElement) {
				items.addItem("Story");
			}
		}
	}

	class AddHandler implements ClickHandler {

		private int type;
		private PoiDTO itemDTO;

		public AddHandler(int type, PoiDTO itemDTO) {
			this.type = type;
			this.itemDTO = itemDTO;
		}

		@Override
		public void onClick(ClickEvent event) {
			switch (type) {
			case 0:
				new ImageQuestItemDialog(itemDTO, null, false, true).center();
				break;
			case 1:
				new StoryQuestItemDialog(itemDTO, null, false, true).center();
				break;
			case 2:
				new VideoQuestItemDialog(itemDTO, null, false, true).center();
				break;
			case 3:
				new QuestionQuestItemDialog(itemDTO, null, false, true)
						.center();
				break;
			case 4:
				new Object3DDialog(itemDTO, false, true).center();
				break;
			}
		}

	}

	class editHandler implements DoubleClickHandler {

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			int index = items.getSelectedIndex();
			Object o = itemDTO.getElements().get(index);

			String name = items.getItemText(index);

			if (name.startsWith("Image")) {
				new ImageQuestItemDialog(itemDTO,
						((ImageElement) o), true, false)
						.center();
			} else if (name.startsWith("Question")) {
				new QuestionQuestItemDialog(itemDTO,
						((QuestionElement) o), true, false)
						.center();
			} else if (name.startsWith("Story")) {
				new StoryQuestItemDialog(itemDTO,
						((StoryElement) o), true, false)
						.center();
			} else if (name.startsWith("Video")) {
				new VideoQuestItemDialog(itemDTO,
						((VideoElement) o), true, false)
						.center();
			}
		}
	}

	@Override
	public void onUpdateQuestItem(UpdateQuestItemEvent p) {
		refresh();

	}
}
