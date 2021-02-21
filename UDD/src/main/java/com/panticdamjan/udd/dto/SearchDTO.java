package com.panticdamjan.udd.dto;

import com.panticdamjan.udd.enumeration.SearchType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchDTO {

    private String title;
    private String operatorTitle;
    private String writer;
    private String operatorWriter;
    private String genres;
    private String operatorGenres;
    private String keyWords;
    private String operatorKeyWords;
    private String text;
    private String operatorText;
    private SearchType searchType;
}
