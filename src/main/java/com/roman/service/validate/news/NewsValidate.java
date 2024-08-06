package com.roman.service.validate.news;

import com.roman.service.validate.IdValidate;
import com.roman.service.validate.TitleValidate;
import com.roman.service.validate.exception.NewsDoesntExistException;
import com.roman.service.validate.exception.TitleExistException;
import com.roman.service.validate.exception.TitlePatternException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsValidate {

    private final TitleValidate titleValidate;
    private final IdValidate idValidate;

    public void checkTitleExist(String title){
        if(!titleValidate.checkExistNewsTitle(title)){
            throw new TitleExistException("Новость с названием '%s' уже существует.".formatted(title));
        }
    }

    public void checkTitlePattern(String title){
        if(!titleValidate.checkTitlePattern(title)){
            throw new TitlePatternException();
        }
    }

    public void checkId(Long id){
        if(idValidate.checkNewsId(id)){
            throw new NewsDoesntExistException(id);
        }
    }
}
