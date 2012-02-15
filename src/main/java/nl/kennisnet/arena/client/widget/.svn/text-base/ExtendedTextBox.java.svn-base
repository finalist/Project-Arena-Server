package nl.kennisnet.arena.client.widget;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ExtendedTextBox extends VerticalPanel {

   private final TextArea text;
   private final Label counter;
   private final int maxCharacters;
   
   public ExtendedTextBox(int characterWidth,int characterHeight,int maxCharacters){
      super();
      this.maxCharacters=maxCharacters;
      text=new TextArea();
      text.setCharacterWidth(60);
      text.setVisibleLines(4);
      counter=new Label();
      text.addChangeHandler(new ChangeHandler() {
         
         @Override
         public void onChange(ChangeEvent arg0) {
            trimTextBox();
            updateCharacterCounter();
         }
      });
      
      text.addKeyUpHandler(new KeyUpHandler() {
         
         @Override
         public void onKeyUp(KeyUpEvent event) {
            updateCharacterCounter();
            
         }
      });
      this.add(text);
      this.add(counter);
      updateCharacterCounter();
   }
   
   private void trimTextBox(){
      if (text.getText().length()>maxCharacters){
         text.setText(text.getText().substring(0, maxCharacters-1));
      }
   }

   private void updateCharacterCounter(){
      int numberOfCharacters=text.getText().length();
      int remainingCharacters=maxCharacters-numberOfCharacters;
      counter.setText("Resterende karakters :"+remainingCharacters);
      if (remainingCharacters<0){
         counter.setStyleName("toManyCharacters");
      } else {
         counter.setStyleName(null);
      }
   }

   public void setText(String text){
      this.text.setText(text);
      updateCharacterCounter();
   }
   
   public String getText(){
      return this.text.getText();
   }
}
