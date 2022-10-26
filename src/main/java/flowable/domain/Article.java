package flowable.domain;

import lombok.Data;

@Data
public class Article {

    private String id;
    private String author;
    private String url;

    public Article() {
    }

    public Article(String author, String url) {
        this.author = author;
        this.url = url;
    }

    public Article(String id, String author, String url) {
        this.id = id;
        this.author = author;
        this.url = url;
    }

}