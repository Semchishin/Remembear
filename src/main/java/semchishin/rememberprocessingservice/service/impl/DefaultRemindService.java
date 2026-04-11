package semchishin.rememberprocessingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import semchishin.rememberprocessingservice.exception.RemindNotFoundException;
import semchishin.rememberprocessingservice.model.Remind;
import semchishin.rememberprocessingservice.repository.RemindRepository;
import semchishin.rememberprocessingservice.service.RemindService;

import java.util.List;

import static semchishin.rememberprocessingservice.util.Message.REMIND_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class DefaultRemindService implements RemindService {

    private final RemindRepository remindRepository;

    @Override
    public Remind createReminder(Remind remind) {
        return remindRepository.add(remind);
    }

    @Override
    public Remind findRemindById(Long id) {
        return remindRepository.findById(id).orElseThrow(
                () -> new RemindNotFoundException(
                        String.format(REMIND_NOT_FOUND, id)
                )
        );
    }

    @Override
    public List<Remind> getAllRemindsByUserId(Long id) {
        return remindRepository.findAllByUserId(id);
    }

    @Override
    public void UpdateRemind(Remind remind) {
        remindRepository.update(remind);
    }

    @Override
    public void deleteById(Long id) {
        remindRepository.deleteById(id);
    }
}
