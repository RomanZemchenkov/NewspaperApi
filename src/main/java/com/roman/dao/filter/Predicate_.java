package com.roman.dao.filter;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Predicate_ {

    private final List<Predicate> predicateList = new ArrayList<>();

    private Predicate_(){}

    public static Predicate_ of(){
        return new Predicate_();
    }

    public <T> Predicate_ and(T object, Function<T,Predicate> function){
        if(object != null){
            Predicate predicate = function.apply(object);
            predicateList.add(predicate);
        }
        return this;
    }

    public Predicate[] finish(){
        return predicateList.toArray(new Predicate[0]);
    }
}
