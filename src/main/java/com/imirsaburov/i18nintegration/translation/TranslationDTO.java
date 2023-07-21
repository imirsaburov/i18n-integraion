package com.imirsaburov.i18nintegration.translation;

import lombok.Data;

import java.util.List;

@Data
public class TranslationDTO {
    private String id;
    private String key;
    private List<Translation> translations;
}
