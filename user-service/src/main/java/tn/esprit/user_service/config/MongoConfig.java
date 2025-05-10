package tn.esprit.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(
                new LocalDateTimeReadConverter(),
                new LocalDateTimeWriteConverter()
        ));
    }

    public static class LocalDateTimeReadConverter implements Converter<Date, LocalDateTime> {
        @Override
        public LocalDateTime convert(Date date) {
            return date.toInstant()
                    .atZone(ZoneOffset.UTC)
                    .toLocalDateTime();
        }
    }

    public static class LocalDateTimeWriteConverter implements Converter<LocalDateTime, Date> {
        @Override
        public Date convert(LocalDateTime localDateTime) {
            return Date.from(localDateTime
                    .atZone(ZoneOffset.UTC)
                    .toInstant());
        }
    }
}