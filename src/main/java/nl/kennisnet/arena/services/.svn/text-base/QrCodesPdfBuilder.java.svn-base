package nl.kennisnet.arena.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;

import nl.kennisnet.arena.client.domain.TeamDTO;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

/** 
 * Builder for PDF with QR Codes
 *  Needs Internet acces because the QR codes are generated from a website.
 *  
 *
 * @author Rob van de Meulengraaf
 *
 */
public class QrCodesPdfBuilder {
    
    private String url;
    private float fontSize = 20F;
    private long questId;
    private Collection<TeamDTO>teams;
    
    public QrCodesPdfBuilder(String url,Collection<TeamDTO> teams) {
        try {
            this.url = URLEncoder.encode(url, "UTF-8");
            this.teams=teams;
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
//    public static void main(String[] args) throws Exception {
//        // example usages
//        FileOutputStream out = new FileOutputStream("/tmp/arena.pdf");
//        QrCodesPdfBuilder gen = new QrCodesPdfBuilder("http://t-arena.finalist.com/arena?p=");
//        gen.setFontSize(40F);
//        gen.setNumberOfTeams(10);
//        gen.build(out);
//    }
    
    public void build(OutputStream out) {
        try {
        Document doc = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(doc, out);
        doc.open();
        doc.addTitle("ARena");
        for (TeamDTO team : teams) {
         
            doc.newPage();
            Paragraph header = new Paragraph("Team " + team.getName());
//            header.getFont().setColor(new Color(teams.get(i).getColor()));
            header.getFont().setSize(fontSize);
            doc.add(header);
            Paragraph qrCode = new Paragraph();
            Image image = Image.getInstance(new URL("http://qrcode.kaywa.com/img.php?s=8&d=" + url + "?player=" + team.getName()));
            qrCode.add(image);
            doc.add(qrCode);
        }
        doc.close();
        writer.close();
        } catch (DocumentException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public float getFontSize() {
        return fontSize;
    }


    public void setQuestId(long questId) {
        this.questId = questId;
    }

    public long getQuestId() {
        return questId;
    }
}
