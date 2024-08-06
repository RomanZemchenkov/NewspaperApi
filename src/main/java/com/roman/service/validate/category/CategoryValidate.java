package com.roman.service.validate.category;

import com.roman.service.validate.IdValidate;
import com.roman.service.validate.TitleValidate;
import com.roman.service.validate.exception.CategoryDoesntExistException;
import com.roman.service.validate.exception.NewsDoesntExistException;
import com.roman.service.validate.exception.TitleExistException;
import com.roman.service.validate.exception.TitlePatternException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryValidate {

    private final TitleValidate titleValidate;
    private final IdValidate idValidate;

    public void checkTitleExist(String title){
        if(!titleValidate.checkExistCategoryTitle(title)){
            throw new TitleExistException("Категория с названием '%s' уже существует.".formatted(title));
        }
    }

    public void checkTitlePattern(String title){
        if(!titleValidate.checkTitlePattern(title)){
            throw new TitlePatternException();
        }
    }

    public void checkId(Long id){
        if(idValidate.checkCategoryId(id)){
            throw new CategoryDoesntExistException(id);
        }
    }
}
