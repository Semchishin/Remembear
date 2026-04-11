package semchishin.rememberprocessingservice.service;

import semchishin.rememberprocessingservice.model.Remind;

import java.util.List;

public interface RemindService {

    Remind createReminder(Remind remind);

    Remind findRemindById(Long id);

    List<Remind> getAllRemindsByUserId(Long id);

    void UpdateRemind(Remind remind);

    void deleteById(Long id);

}
