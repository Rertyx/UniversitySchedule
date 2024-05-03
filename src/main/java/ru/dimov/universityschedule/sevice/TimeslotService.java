package ru.dimov.universityschedule.sevice;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.proxy.EntityNotFoundDelegate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.dimov.universityschedule.model.Timeslot;
import ru.dimov.universityschedule.repository.TeacherRepository;
import ru.dimov.universityschedule.repository.TimeslotRepository;
import one.util.streamex.StreamEx;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import java.util.function.Function;

@Service
public class TimeslotService {
    private final TimeslotRepository timeslotRepository;
    private final LoadingCache<TimeslotKey, Timeslot> cache;


    public TimeslotService(TimeslotRepository timeslotRepository) {
        this.timeslotRepository = timeslotRepository;
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .build(new CacheLoader<>() {
                    @Override
                    public Timeslot load(TimeslotKey key) {
                        return timeslotRepository.findByDayOfWeekAndTimeAndUpper(key.dayOfWeek, key.time, key.upper).orElseGet(
                                () -> timeslotRepository.save(new Timeslot(null, key.dayOfWeek, key.time, key.upper))
                        );
                    }
                });
    }

    @PostConstruct
    private void initCache() {
        Map<TimeslotKey, Timeslot> map = StreamEx.of(timeslotRepository.findAll().spliterator())
                .mapToEntry(x -> new TimeslotKey(x.getTime(), x.isUpper(), x.getDayOfWeek()), Function.identity()).toMap();
        cache.putAll(map);
    }


    @SneakyThrows
    public Timeslot getTimeSlot(Timeslot timeslot) {
        return cache.get(new TimeslotKey(timeslot.getTime(), timeslot.isUpper(), timeslot.getDayOfWeek()));
    }


    record TimeslotKey(LocalTime time, boolean upper, DayOfWeek dayOfWeek) {
    }
}
