package com.exam.services;

import lombok.Getter;

public class AbstractActionFactory {
    @Getter
    private final static ActionFactory instance = new ActionFactory();
    //public ActionFactory getInstance
}