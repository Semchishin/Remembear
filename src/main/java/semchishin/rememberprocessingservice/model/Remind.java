package semchishin.rememberprocessingservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Remind {

    private Long remindId;

    private Long userId;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @NonNull
    private LocalDateTime createdAt;

    private LocalDateTime remindAt;

    public static Remind createWithRemindTime(
            String title,
            String description,
            LocalDateTime remindAt
    ) {
        Remind remind = new Remind();
        remind.setTitle(title);
        remind.setDescription(description);
        remind.setCreatedAt(LocalDateTime.now());
        remind.setRemindAt(remindAt);

        return remind;
    }

    public static Remind createWithoutRemindTime(
            String title,
            String description
    ) {
        Remind remind = new Remind();
        remind.setTitle(title);
        remind.setDescription(description);
        remind.setCreatedAt(LocalDateTime.now());

        return remind;
    }


}
