package amazon_crawler;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

    public static ArrayList<Review> reviewList = new ArrayList<Review>();
    public static String PAGESFILE = "/Users/manhhung/Documents/BKHN/webmining/SentimentAnalysisOnProductReview/src/amazon_crawler/PagesScraped.txt";
    ArrayList<Integer> pagesScraped;

    /*
     * TODO: Fetch all reviews
     * */

    public void fetchReviews(String itemID) {
        int pageNumber = (1 + (int) (Math.random() * 10));
        System.out.println("Random page number: " + pageNumber);
        String url = "http://www.amazon.com/product-reviews/" + itemID
                + "/?showViewpoints=0&sortBy=byRankDescending&pageNumber=" + pageNumber;
        System.out.println("URL: " + url);
        //Modify this file name to reflect the name of the product you are reviewing.
        File file = new File("YahooMailReviews.txt");

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            // Get the max number of review pages;
            org.jsoup.nodes.Document reviewpage1 = null;
            reviewpage1 = Jsoup.connect(url).timeout(10 * 1000).get();
            int maxpage = 1;
            Elements pagelinks = reviewpage1.select("a[href*=pageNumber=]");
            if (pagelinks.size() != 0) {
                ArrayList<Integer> pagenum = new ArrayList<Integer>();
                for (Element link : pagelinks) {
                    try {
                        pagenum.add(Integer.parseInt(link.text()));
                    } catch (NumberFormatException nfe) {
                    }
                }
                System.out.println("Page num: " + pagenum);

                maxpage = Collections.max(pagenum);
                System.out.println("Max page: " + maxpage);

                pagesScraped = Helper.readArrayFromFile(PAGESFILE);
                int p = 0;
                while (p <= maxpage) {
                    p = 1 + (int) (Math.random() * maxpage);
                    if (Helper.checkPagePresent(pagesScraped, p)) {
                        continue;
                    }

                    pagesScraped.add(p);
                    url = "http://www.amazon.com/product-reviews/"
                            + itemID
                            + "/?sortBy=helpful&pageNumber="
                            + p;
                    org.jsoup.nodes.Document reviewpage = null;
                    reviewpage = Jsoup.connect(url).timeout(10 * 1000).get();
                    if (reviewpage.select("div.a-section.review").isEmpty()){
                        System.out.println("Oppss! Empty reviews!");
                    }else {
                        Elements reviewsHTMLs = reviewpage.select("div.a-section.review");
                        for (Element reviewBlock: reviewsHTMLs){
                            Review aReview = Helper.parseReview(reviewBlock);
                            reviewList.add(aReview);
                            System.out.println(aReview);
                            fileWriter.write(aReview.toString() + "\n");
                        }
                    }

                    if(pagesScraped.size() > 0 && Helper.convertIntegers(pagesScraped) != null)
                        Helper.writeArrayToFile(PAGESFILE,pagesScraped);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
