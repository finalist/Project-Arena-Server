package nl.kennisnet.arena.client.dialog;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.LogEvent;
import nl.kennisnet.arena.client.event.RefreshQuestEvent;
import nl.kennisnet.arena.client.event.UpdateQuestItemEvent;
import nl.kennisnet.arena.client.widget.FormTablePanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class QuestItemDialog extends DialogBox {

   private TextBox nameTextBox = new TextBox();
   private TextBox radiusTextBox = new TextBox();
   private QuestItemDTO itemDTO;
   private final boolean readOnlyDialog;
   private final boolean create;

   public QuestItemDialog(final QuestItemDTO itemDTO, boolean readOnlyDialog,boolean create) {
      super(false, true);
      this.readOnlyDialog = readOnlyDialog;
      this.create = create;
      EventBus.get().fireEvent(
            new LogEvent("Dialog is called with :" + readOnlyDialog + " and opened readonly :" + this.readOnlyDialog));
      this.itemDTO = itemDTO;
      setWidget(createPanel(itemDTO));
      fillFormFromItem(itemDTO);
      nameTextBox.setFocus(true);
   }

   protected Panel createPanel(final QuestItemDTO itemDTO) {
      VerticalPanel result = new VerticalPanel();
      FormTablePanel formPanel = new FormTablePanel();

      for (FormTablePanel.Element attributePanel : createFormPanels()) {
         if (readOnlyDialog) {
            setFormPanelToReadOnly(attributePanel.getField());
         }
         formPanel.addField(attributePanel);
      }
      result.add(formPanel);
      result.setHorizontalAlignment(HasAlignment.ALIGN_CENTER);
      result.add(createButtonPanel());

      return result;
   }

   private Panel createButtonPanel() {
      HorizontalPanel buttonPanel = new HorizontalPanel();
      if (!readOnlyDialog) {
         Button ok = new Button("OK");
         buttonPanel.add(ok);
         ok.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
               fillItemFromForm(itemDTO);
               EventBus.get().fireEvent(new UpdateQuestItemEvent());
               hide();
            }
         });
      }

      Button cancel = new Button();
      if (readOnlyDialog) {
         cancel.setText("OK");
      } else {
         cancel.setText("Anuleren");
      }
      buttonPanel.add(cancel);
      cancel.addClickHandler(new ClickHandler() {

         @Override
         public void onClick(ClickEvent event) {
            if (create){
               QuestState.getInstance().getState().removeItem(itemDTO);
               EventBus.get().fireEvent(new RefreshQuestEvent());
            }
            hide();
         }
      });
      return buttonPanel;
   }

   private void setFormPanelToReadOnly(Widget widget) {
      if (widget instanceof Panel) {
         for (Widget partWidget : ((Panel) widget)) {
            setFormPanelToReadOnly(partWidget);
         }
      } else {
         if (widget instanceof TextBoxBase) {
            ((TextBoxBase) widget).setReadOnly(true);
         } else if (widget instanceof CheckBox) {
            ((CheckBox) widget).setEnabled(false);
         } else if (widget instanceof FocusWidget) {
            ((FocusWidget) widget).setEnabled(false);
         }
      }
      ;
   }

   protected List<FormTablePanel.Element> createFormPanels() {
      List<FormTablePanel.Element> result = new ArrayList<FormTablePanel.Element>();
      result.add(createNamePanel());
      result.add(createRadiusPanel());
      return result;

   }

   protected void fillFormFromItem(QuestItemDTO itemDTO) {
      nameTextBox.setText(itemDTO.getName());
      radiusTextBox.setText("" + itemDTO.getRadius());
   }

   protected QuestItemDTO fillItemFromForm(QuestItemDTO itemDTO) {
      itemDTO.setName(nameTextBox.getText().trim());
      itemDTO.setRadius(Double.valueOf(radiusTextBox.getText()));
      return itemDTO;
   }

   protected FormTablePanel.Element createNamePanel() {
      return new FormTablePanel.Element("Naam", nameTextBox);
   }

   protected FormTablePanel.Element createRadiusPanel() {
      return new FormTablePanel.Element("Radius", radiusTextBox);
   }

   public QuestItemDTO getQuestItemDTO() {
      return itemDTO;
   }

   public boolean isReadOnly() {
      return readOnlyDialog;
   }

}
