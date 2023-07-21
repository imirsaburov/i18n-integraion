package com.imirsaburov.i18nintegration.translation;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TranslationCriteriaRepository {


    private final MongoTemplate mongoTemplate;


    public Page<TranslationEntity> findAllWithFilters(TranslationPage page,
                                                      TranslationSearchCriteria searchCriteria) {


        Query query = getQuery(searchCriteria);

        Pageable pageable = getPageable(page);

        long count = getCount(query);

        query.with(pageable);

        List<TranslationEntity> list = mongoTemplate.find(query, TranslationEntity.class);

        return new PageImpl<>(list, pageable, count);
    }

    private Query getQuery(TranslationSearchCriteria searchCriteria) {

        Query query = new Query();

        if (Strings.isNotBlank(searchCriteria.getKey())) {
            query.addCriteria(Criteria.where("key").regex(searchCriteria.getKey()));
        }

        if (Strings.isNotBlank(searchCriteria.getContent())) {
            query.addCriteria(Criteria.where("translations.content").regex(searchCriteria.getContent()));
        }

        return query;
    }


    public static Pageable getPageable(TranslationPage page) {
        Sort sort = Sort.by(page.getSortDirection(), page.getSortBy());
        return PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);
    }

    private long getCount(Query query) {
        return mongoTemplate.count(query, TranslationEntity.class);
    }
}
