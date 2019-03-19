package org.nico.yasso.pipeline.impl;

import org.nico.yasso.consts.BuildState;
import org.nico.yasso.entity.Mail;
import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.templator.Templator;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.email.Recipient;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailPipeline extends AbstractPipeline{

    private final static Logger LOGGER = LoggerFactory.getLogger(MailPipeline.class);
    
    private final static Templator TEMPLATOR = new Templator("${", "}");
    
    @Override
    public BuildState pipeline(YassoJob job) {
        
        if(job.getMail() == null) {
            LOGGER.error("Missing mail configuration.");
        }else {
            Mail.Config config = job.getMail().getConfig();
            Mail.Template template = job.getMail().getTemplate();
            
            if(config == null && template == null) {
                LOGGER.error("Missing mail send or receive configuration.");
            }else {
                Mailer mailer = MailerBuilder.withSMTPServer(config.getSmtp(), config.getPort(), config.getUser(), config.getPwd())
                        .withTransportStrategy(config.getStrategy())
                        .withSessionTimeout(config.getTimeout())
                        .withDebugLogging(config.isDebug())
                        .buildMailer();
                
                String[] fromInfo = template.getAddresser().split("[:]", 2);
                Recipient from = new Recipient(fromInfo[0], fromInfo.length > 0 ? fromInfo[1] : fromInfo[0], null);
                
                String[] toList = template.getRecipients().toArray(new String[] {});
                
                template.setTitle(TEMPLATOR.render(template.getTitle().trim(), job.getContext().getDatas()));
                template.setContent(TEMPLATOR.render(template.getContent(), job.getContext().getDatas()));
                
                Email email = EmailBuilder.startingBlank()
                        .from(from)
                        .to("YASSO", toList)
                        .withSubject(template.getTitle())
                        .withHTMLText(template.getType().equalsIgnoreCase("TXT") ? null : template.getContent())
                        .withPlainText(template.getType().equalsIgnoreCase("TXT") ? template.getContent() : null)
                        .withHeader("X-Priority", 5)
                        .buildEmail();
                
                mailer.sendMail(email);
                
                return then(job);
            }
        }
        
        return BuildState.SUCCESS;
        
    }

}
