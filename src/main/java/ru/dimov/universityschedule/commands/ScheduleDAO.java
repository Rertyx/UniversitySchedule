package ru.dimov.universityschedule.commands;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ru.dimov.universityschedule.model.Timeslot;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleDAO {
    private final NamedParameterJdbcTemplate jdbcTemplate;


    public Timeslot getNextTimeslot(boolean upper, Integer groupId) {
        return jdbcTemplate.query("""
                with "current_time" as (select (extract(dow from now() at time zone 'Europe/Moscow'))  as dow,
                                               extract(hour from now() at time zone 'Europe/Moscow')   as hour,
                                               extract(minute from now() at time zone 'Europe/Moscow') as minute),
                     asd as (
                         select
                             t.*,
                             case
                                 when :upper_now != t.upper then 1
                                 else 0 end                               as weeks_count,
                             t.day_of_week - cd.dow                  as days_count,
                             extract(hour from t.time) - cd.hour     as hours_count,
                             extract(minute from t.time) - cd.minute as minutes_count
                         from timeslot t
                                  cross join "current_time" cd
                         order by weeks_count, days_count, hours_count, minutes_count),
                    grater_than_zero as (
                    select( weeks_count * 7 * 24 * 60) + (days_count * 24 * 60) + (hours_count * 60) + minutes_count as minutes_to_class,
                      asd.*
                    from asd
                         join class c on asd.timeslot_id = c.timeslot_id
                         join class_group cg on cg.class_id = c.class_id and cg.group_id = :group_id
                    where ( weeks_count * 7 * 24 * 60) + (days_count * 24 * 60) + (hours_count * 60) + minutes_count >0)
                select timeslot_id, day_of_week, time, upper
                from grater_than_zero
                where minutes_to_class = (select min(minutes_to_class)from grater_than_zero);
                """, new MapSqlParameterSource("upper_now", upper).addValue("group_id", groupId),
                (rs, rowNum) -> new Timeslot(rs.getLong("timeslot_id"),
                        DayOfWeek.of(rs.getInt("day_of_week")),
                        rs.getObject("time", LocalTime.class),
                        rs.getBoolean("upper"))).get(0);
    }

    public ClassDTO getNextClass(boolean upper, int groupId){
        return jdbcTemplate.query("""
                with "current_time" as (select (extract(dow from now() at time zone 'Europe/Moscow'))  as dow,
                                               extract(hour from now() at time zone 'Europe/Moscow')   as hour,
                                               extract(minute from now() at time zone 'Europe/Moscow') as minute),
                     asd as (
                         select
                             t.*,
                             case
                                 when :upper_now != t.upper then 1
                                 else 0 end                               as weeks_count,
                             t.day_of_week - cd.dow                  as days_count,
                             extract(hour from t.time) - cd.hour     as hours_count,
                             extract(minute from t.time) - cd.minute as minutes_count
                         from timeslot t
                                  cross join "current_time" cd
                         order by weeks_count, days_count, hours_count, minutes_count),
                    grater_than_zero as (
                    select( weeks_count * 7 * 24 * 60) + (days_count * 24 * 60) + (hours_count * 60) + minutes_count as minutes_to_class,
                          asd.*,
                          c.class_id,
                          c.room_id,
                          c.subject_id,
                          c.teacher_id
                    from asd
                         join class c on asd.timeslot_id = c.timeslot_id
                         join class_group cg on cg.class_id = c.class_id and cg.group_id = :group_id
                    where ( weeks_count * 7 * 24 * 60) + (days_count * 24 * 60) + (hours_count * 60) + minutes_count >0)
                select time, r.room_number, r.building, s.name as subject, s.type,s.additional_data, t.name, t.surname, t.patronymic
                from grater_than_zero
                left join room r on grater_than_zero.room_id = r.room_id
                left join subject s on grater_than_zero.subject_id = s.subject_id
                left join teacher t on grater_than_zero.teacher_id = t.teacher_id
                where minutes_to_class = (select min(minutes_to_class)from grater_than_zero);
                """, new MapSqlParameterSource("upper_now", upper).addValue("group_id", groupId),
                (rs, rowNum) -> new ClassDTO(rs.getObject("time", LocalTime.class),
                        rs.getString("room_number"),
                        rs.getString("building"),
                        rs.getString("subject"),
                        rs.getString("type"),
                        rs.getString("additional_data"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("patronymic"))).get(0);
    }

    public List<ClassDTO> getClasses(DayOfWeek dow, boolean upper, int groupId){
        return jdbcTemplate.query("""
                with
                     asd as (
                         select
                             t.*
                         from timeslot t
                
                                  where :dow = t.day_of_week and :upper_now = t.upper),
                    by_group as (
                        select asd.*,
                        c.*
                        from asd
                                join class c on asd.timeslot_id = c.timeslot_id
                                join class_group cg on cg.class_id = c.class_id and cg.group_id = :group_id
                    )
                select time, r.room_number, r.building, s.name as subject, s.type,s.additional_data, t.name, t.surname, t.patronymic
                from by_group
                         left join room r on by_group.room_id = r.room_id
                         left join subject s on by_group.subject_id = s.subject_id
                         left join teacher t on by_group.teacher_id = t.teacher_id;
                """, new MapSqlParameterSource("upper_now", upper).addValue("group_id", groupId).addValue("dow", dow.getValue()),
                (rs, rowNum) -> new ClassDTO(rs.getObject("time", LocalTime.class),
                        rs.getString("room_number"),
                        rs.getString("building"),
                        rs.getString("subject"),
                        rs.getString("type"),
                        rs.getString("additional_data"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("patronymic")));
    }
}
