package com.panticdamjan.udd.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(indexName = IndexUnit.INDEX_NAME, type = IndexUnit.TYPE_NAME, shards = 1, replicas = 0)
public class IndexUnit {

    public static final String INDEX_NAME = "digitallibrary";
    public static final String TYPE_NAME = "book";

    @Id
    @Field(type = FieldType.Keyword)
    private String filename;
    @Field(type = FieldType.Text, analyzer = "serbian")
    private String text;
    @Field(type = FieldType.Text, analyzer = "serbian")
    private String title;
    @Field(type = FieldType.Text, analyzer = "serbian")
    private String keywords;
    @Field(type = FieldType.Text, analyzer = "serbian")
    private String genres;
    @Field(type = FieldType.Text, analyzer = "serbian")
    private String writer;

}
