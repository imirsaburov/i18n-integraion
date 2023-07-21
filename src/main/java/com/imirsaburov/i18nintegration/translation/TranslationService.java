package com.imirsaburov.i18nintegration.translation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class TranslationService {
    private final TranslationRepository translationRepository;
    private final TranslationCriteriaRepository translationCriteriaRepository;

    @Value("${spring.application.primary-lang}")
    private String primaryLang;

    public Map<String, String> create(Map<String, String> languages) {

        for (Map.Entry<String, String> language : languages.entrySet()) {
            String key = language.getKey();

            if (Strings.isBlank(key) || translationRepository.existsByKey(key))
                continue;

            saveFirstTime(key);
        }
        return languages;
    }

    public TranslationEntity saveFirstTime(String key) {
        TranslationEntity translationEntity = new TranslationEntity();
        translationEntity.setKey(key);
        translationEntity.setCreatedTime(LocalDateTime.now());
        translationEntity.setModifiedTime(LocalDateTime.now());

        Translation primaryTranslation = new Translation(primaryLang, key);
        translationEntity.setTranslations(Collections.singletonList(primaryTranslation));

        return translationRepository.save(translationEntity);
    }


    public String getContent(String key, String locale) {

        TranslationEntity translationEntity = translationRepository
                .findByKey(key)
                .orElseGet(() -> saveFirstTime(key));

        return getContent(translationEntity, locale);
    }

    public HashMap<String, String> getLanguages(String locale) {
        HashMap<String, String> maps = new HashMap<>();
        List<TranslationEntity> translationEntities = translationRepository.findAll();
        for (TranslationEntity translationEntity : translationEntities) {
            maps.put(translationEntity.getKey(), getContent(translationEntity, locale));
        }
        return maps;
    }

    public String getContent(TranslationEntity translationEntity, String locale) {
        Translation translation = getTranslation(translationEntity, locale);

        if (translation == null)
            return getTranslation(translationEntity, primaryLang).getContent();

        return translation.getContent();
    }

    public Translation getTranslation(TranslationEntity translationEntity, String locale) {

        for (Translation translation : translationEntity.getTranslations()) {
            if (translation.getLocale().equals(locale))
                return translation;
        }
        return null;
    }

    public Page<TranslationDTO> getList(TranslationPage translationPage,
                                        TranslationSearchCriteria translationSearchCriteria) {

        Page<TranslationEntity> allWithFilters = translationCriteriaRepository.findAllWithFilters(translationPage, translationSearchCriteria);

        return allWithFilters.map(this::toDTO);
    }

    public TranslationDTO toDTO(TranslationEntity entity, TranslationDTO dto) {
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public TranslationDTO toDTO(TranslationEntity entity) {
        return toDTO(entity, new TranslationDTO());
    }


    public TranslationDTO translate(TranslationRequest translationRequest) {

        try {
            if (Strings.isBlank(translationRequest.getContent()))
                return null;

            TranslationEntity translationEntity = translationRepository
                    .findByKey(translationRequest.getKey())
                    .orElseGet(() -> saveFirstTime(translationRequest.getKey()));

            translationEntity.setModifiedTime(LocalDateTime.now());
            Translation translation = getTranslation(translationEntity, translationRequest.getLocale());

            if (translation == null) {
                translation = new Translation();
                translationEntity.getTranslations().add(translation);
            }

            translation.setLocale(translationRequest.getLocale());
            translation.setContent(translationRequest.getContent());
            translationRepository.save(translationEntity);
            return toDTO(translationEntity);
        } catch (Throwable e) {

        }
        return null;
    }


    public List<TranslationDTO> translate(List<TranslationRequest> translates) {
        return translates.stream().map(this::translate).toList();
    }
}
