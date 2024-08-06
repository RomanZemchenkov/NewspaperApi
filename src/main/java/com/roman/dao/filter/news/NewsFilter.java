package com.roman.dao.filter.news;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsFilter {

    private String title;
    private String text;

    private NewsFilter(){}

    public static class Builder{
        private final NewsFilter filter;

        public Builder(){
            this.filter = new NewsFilter();
        }

        public Builder title(String title){
            filter.setTitle(title);
            return this;
        }

        public Builder text(String text){
            filter.setText(text);
            return this;
        }

        public NewsFilter build(){
            return filter;
        }
    }
}
