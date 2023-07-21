package com.imirsaburov.i18nintegration.translation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Translation {
    private String locale;
    private String content;
}
