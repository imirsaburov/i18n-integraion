package com.imirsaburov.i18nintegration.translation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document("translation")
@Getter
@Setter
@ToString
public class TranslationEntity {
    @Id
    private String id;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    @Indexed(unique = true)
    private String key;
    private List<Translation> translations = new ArrayList<>();
}