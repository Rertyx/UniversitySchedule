package ru.dimov.universityschedule.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnvironmentService {
    private final NamedParameterJdbcTemplate JdbcTemplate;

    public void saveIntoEnvironment(String key, String value){
        JdbcTemplate.update("""
                INSERT into environment (key, value)
                VALUES(:key_text, :value_text)
                ON CONFLICT (key) DO UPDATE SET value = excluded.value;
                """, new MapSqlParameterSource("key_text", key).addValue("value_text", value));
    }

    public String getTypeOfWeek(){
        return JdbcTemplate.query("""
                select value
                from environment
                where key = 'typeOfWeek';
                """, (rs, rowNum) -> rs.getString("value")).get(0);
    }
}