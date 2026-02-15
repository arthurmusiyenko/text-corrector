package com.arthur.textcorrector.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class YandexError {
    private int code;
    private int pos;
    private int row;
    private int col;
    private int len;
    private String word;
    private List<String> s;
}