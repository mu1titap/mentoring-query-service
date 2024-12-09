package com.example.section.messagequeue;
import com.example.section.messagequeue.messageIn.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;


@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value("${kafka.cluster.uri}")
    private String kafkaClusterUri;
    /**
     * 멘토링 생성
     */
    @Bean
    public ConsumerFactory<String, MentoringAddAfterOutDto> mentoringConsumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaClusterUri);
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
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaClusterUri);
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

    /**
     * 멘토링 세션 참가 등록
     */
    @Bean
    public ConsumerFactory<String, AfterSessionUserOutDto> sessionUserConsumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaClusterUri);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-mentoring-query-service");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(AfterSessionUserOutDto.class, false));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AfterSessionUserOutDto> afterSessionUserOutDtoListener() {
        ConcurrentKafkaListenerContainerFactory<String, AfterSessionUserOutDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(sessionUserConsumerFactory());
        return factory;
    }

    /**
     * 멘토링 세션 참가 취소
     */
    @Bean
    public ConsumerFactory<String, CancelSessionUserMessage> cancelSessionUserConsumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaClusterUri);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-mentoring-query-service");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(CancelSessionUserMessage.class, false));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CancelSessionUserMessage> cancelSessionUserOutDtoListener() {
        ConcurrentKafkaListenerContainerFactory<String, CancelSessionUserMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cancelSessionUserConsumerFactory());
        return factory;
    }

    /**
     * 멘토링 세션 '재' 참가 등록
     */
    @Bean
    public ConsumerFactory<String, ReRegisterSessionUserMessage> reRegisterSessionUserConsumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaClusterUri);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-mentoring-query-service");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(ReRegisterSessionUserMessage.class, false));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReRegisterSessionUserMessage> reRegisterSessionUserOutDtoListener() {
        ConcurrentKafkaListenerContainerFactory<String, ReRegisterSessionUserMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(reRegisterSessionUserConsumerFactory());
        return factory;
    }

    /**
     * 세션 추가
     */
    @Bean
    public ConsumerFactory<String, SessionCreatedAfterOutDto> addSessionConsumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaClusterUri);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-mentoring-query-service");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(SessionCreatedAfterOutDto.class, false));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SessionCreatedAfterOutDto> addSessionUserOutDtoListener() {
        ConcurrentKafkaListenerContainerFactory<String, SessionCreatedAfterOutDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(addSessionConsumerFactory());
        return factory;
    }

    /**
     * 세션 확정 여부 업데이트
     */
    @Bean
    public ConsumerFactory<String, SessionConfirmedMessage> sessionConfirmedConsumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaClusterUri);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-mentoring-query-service");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(SessionConfirmedMessage.class, false));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SessionConfirmedMessage> sessionConfirmedListener() {
        ConcurrentKafkaListenerContainerFactory<String, SessionConfirmedMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(sessionConfirmedConsumerFactory());
        return factory;
    }

    /**
     * 멘토링 별 리뷰 집계
     */
    @Bean
    public ConsumerFactory<String, ReviewStarDto> reviewAggregationConsumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaClusterUri);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-mentoring-query-service");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(ReviewStarDto.class, false));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReviewStarDto> reviewAggregationListener() {
        ConcurrentKafkaListenerContainerFactory<String, ReviewStarDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(reviewAggregationConsumerFactory());
        return factory;
    }

}