package flowable.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Slf4j
public class EmailUtil {

    public void sendEmail(String recipient, String emailContent) {
        log.info("Email sent !");
    }

}
