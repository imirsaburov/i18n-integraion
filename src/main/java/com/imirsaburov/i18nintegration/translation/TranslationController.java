package com.imirsaburov.i18nintegration.translation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/translation")
@RequiredArgsConstructor
public class TranslationController {

    private final TranslationService translationService;


    @GetMapping("/pageable")
    @ResponseStatus(HttpStatus.OK)
    public Page<TranslationDTO> listFull(TranslationPage translationPage,
                                         TranslationSearchCriteria translationSearchCriteria) {
        return translationService.getList(translationPage, translationSearchCriteria);
    }


    @GetMapping("/{locale}/list")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> list(@PathVariable String locale) {
        return translationService.getLanguages(locale);
    }


    @PostMapping("/create/url-encoded")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> create(
            @RequestParam Map<String, String> languages) {
        return translationService.create(languages);
    }

    @PostMapping("/create/body")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> createWithBody(
            @RequestBody Map<String, String> languages) {
        return translationService.create(languages);
    }


    @PutMapping("/translate")
    @ResponseStatus(HttpStatus.OK)
    public TranslationDTO translate(@RequestBody TranslationRequest translateDTO) {
        return translationService.translate(translateDTO);
    }


    @PutMapping("/translate/list")
    @ResponseStatus(HttpStatus.OK)
    public List<TranslationDTO> translates(@RequestBody List<TranslationRequest> translates) {
        return translationService.translate(translates);
    }

}
