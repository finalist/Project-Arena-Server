package nl.kennisnet.arena.services.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * JavaMailSender implementation that doesn't send email, just records them.
 * 
 * @author Auke van Leeuwen
 * @author Rob van de Meulengraaf
 */
public class JavaMailSenderMockImpl extends JavaMailSenderImpl implements JavaMailSender {

    private Logger log = Logger.getLogger(JavaMailSenderMockImpl.class);

    private List<Object> emails = new ArrayList<Object>();

    /** {@inheritDoc} */
    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
        log.info("Email succesfully send");

        synchronized (this) {
            emails.add(mimeMessage);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void send(SimpleMailMessage simpleMailMessage) throws MailException {
        log.info("Email succesfully send");

        synchronized (this) {
            emails.add(simpleMailMessage);
        }
    }

    /**
     * Clears the list of recorded emails and returns the number of cleared
     * emails.
     * 
     * @return the number of stored email before the clear.
     */
    public int reset() {
        int result = emails.size();

        synchronized (this) {
            emails.clear();
        }

        return result;
    }
    
    /**
     * Returns the stored (send) emails.
     * 
     * @return the emails
     */
    public List<Object> getEmails() {
        return Collections.unmodifiableList(emails);
    }
}