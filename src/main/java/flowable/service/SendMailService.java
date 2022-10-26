package flowable.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("sendMailService")
@NoArgsConstructor
@AllArgsConstructor
public class SendMailService implements JavaDelegate {

    private String GENERIC_EMAIL_CONTENT = "Thank you for your submission, but unfortunately we have decided to " +
            "to pursue another option at this time.";
    @Autowired
    private EmailUtil emailUtil;

    public void execute(DelegateExecution execution) {

        log.info("Sending rejection mail to author !");
        emailUtil.sendEmail("test@example.com", GENERIC_EMAIL_CONTENT);

    }

}