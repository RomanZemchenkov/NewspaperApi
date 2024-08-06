package com.roman.dao.filter.category;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "title")
public class CategoryFilter {

    private Long id;
    private String title;

    private CategoryFilter(){}

    public static class Builder{
        private final CategoryFilter filter;

        public Builder(){
            this.filter = new CategoryFilter();
        }

        public Builder id(Long id){
            filter.setId(id);
            return this;
        }

        public Builder title(String title){
            filter.setTitle(title);
            return this;
        }

        public CategoryFilter build(){
            return filter;
        }
    }
}
