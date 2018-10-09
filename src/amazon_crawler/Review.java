package amazon_crawler;

import java.util.Date;

public class Review {
    private String review_id;
    private String title;
    private int rating;
    private String url;
    private Date date;
    private String reviewContent;
    private int classLabel;

    public Review() {
        this.review_id = "";
        this.title = "";
        this.rating = 0;
        this.url = "";
        this.date = null;
        this.reviewContent = "";
    }

    public Review(String review_id, String title, int rating, String url, Date date, String reviewContent, int classLabel) {
        this.review_id = review_id;
        this.title = title;
        this.rating = rating;
        this.url = url;
        this.date = date;
        this.reviewContent = reviewContent;
        this.classLabel = classLabel;
    }

    public String toString() {
        return classLabel + ", " + reviewContent;
    }

    public void printReviews() {
        System.out.println("Review id : " + this.review_id);
        System.out.println("Stars : " + this.rating);
        System.out.println("URL : " + this.url);
        System.out.println("Date : " + this.date);
        System.out.println("Review : " + this.reviewContent);
        System.out.println("ClassLabel : " + this.classLabel);
        System.out.println("==================================");
        System.out.println();
    }
}
