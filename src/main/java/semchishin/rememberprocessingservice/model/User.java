package semchishin.rememberprocessingservice.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {

    private final Long id;

    @NonNull
    private String name;

    @NonNull
    private final String login;

    @NonNull
    String password;

}
