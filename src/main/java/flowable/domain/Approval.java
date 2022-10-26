package flowable.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Approval {

    private String id;

    private boolean status;

}