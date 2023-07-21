package com.imirsaburov.i18nintegration.translation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TranslationRepository extends MongoRepository<TranslationEntity, String> {

    boolean existsByKey(String key);

    Optional<TranslationEntity> findByKey(String key);
}