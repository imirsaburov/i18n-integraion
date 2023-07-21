package com.imirsaburov.i18nintegration.translation;

import lombok.Data;

@Data
public class TranslationRequest {
    String locale;
    String key;
    String content;
}
