package com.example.section.messagequeue;
import com.example.section.dto.messageIn.MentoringAddAfterOutDto;
import com.example.section.dto.messageIn.MentoringEditRequestOutDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;


@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    /**
     * 멘토링 생성
     */
    @Bean
    public ConsumerFactory<String, MentoringAddAfterOutDto> mentoringConsumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092, localhost:39092, localhost:49092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-mentoring-query-service");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(MentoringAddAfterOutDto.class, false));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MentoringAddAfterOutDto> mentoringAddAfterDtoListener() {
        ConcurrentKafkaListenerContainerFactory<String, MentoringAddAfterOutDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(mentoringConsumerFactory());
        return factory;
    }
    /**
     * 멘토링 수정
     */
    @Bean
    public ConsumerFactory<String, MentoringEditRequestOutDto> mentoringEditConsumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092, localhost:39092, localhost:49092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-mentoring-query-service");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(MentoringEditRequestOutDto.class, false));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MentoringEditRequestOutDto> mentoringEditRequestDtoListener() {
        ConcurrentKafkaListenerContainerFactory<String, MentoringEditRequestOutDto>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(mentoringEditConsumerFactory());
        return factory;
    }



}