package nl.kennisnet.arena.client.widget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.UIObject;

public class ExtendedToggleButton extends ToggleButton {

   private static final String STYLENAME_HTML_FACE = "html-face";
   
   private String label;
   private String text;

   public ExtendedToggleButton(String label, String text, String imageUrl) {
      super();

      this.label=label;
      this.text=text;
      
      Element alternativeUpFace = DOM.createDiv();
      Image buttonImage = new Image(imageUrl);

      Element buttonImageFace = DOM.createDiv();
      UIObject.setStyleName(buttonImageFace, STYLENAME_HTML_FACE + "-image", true);
      buttonImageFace.appendChild(buttonImage.getElement());

      Element buttonTextFace = DOM.createDiv();
      UIObject.setStyleName(buttonTextFace, STYLENAME_HTML_FACE + "-text", true);
      DOM.setInnerText(buttonTextFace, createInnerHTML());

      alternativeUpFace.appendChild(buttonImageFace);
      alternativeUpFace.appendChild(buttonTextFace);

      getUpFace().setHTML(alternativeUpFace.getInnerHTML());
   }

   protected String createInnerHTML() {
      String innerHTML = label;
      if (text != null) {
         innerHTML += "<br>" + text;
      }
      return innerHTML;
   }

   public String getLabel() {
      return label;
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public String getText() {
      return text;
   }

   public void setText(String text) {
      this.text = text;
   }
   
   
   
}
