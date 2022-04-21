package com.example.demo.domain.es;

/* @ProjectName:    demo
 * @Package:        com.example.demo.domain.es
 * @ClassName:      Article
 * @author:     jiangtao
 * @description:
 * @date:    2022/4/21 14:27
 * @version:    1.0
 */


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

//注意indexName要小写
@Document(indexName = "blog")
@Data
public class Article {
    @Id
    private String id;
    private String title;
    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Author> authors;

    public Article(String title) {
        this.title = title;
    }
}
