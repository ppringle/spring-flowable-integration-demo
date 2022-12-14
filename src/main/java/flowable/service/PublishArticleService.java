package flowable.service;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

@Slf4j
public class PublishArticleService implements JavaDelegate {

    public void execute(DelegateExecution execution) {
        log.info("Publishing the approved article !");
    }

}
