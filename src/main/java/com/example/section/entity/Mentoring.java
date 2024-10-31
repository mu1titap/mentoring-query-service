package com.example.section.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "mentoring")
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mentoring {
    @Id
    private String id;

    private String mentoringId;

    private String mentoringUuid;

    private String name;

    private String detail;

    private String mentorUuid;

    private String thumbnailUrl;

    private Boolean isReusable;

    private Boolean isDeleted;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
