package amazon_crawler;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Helper {

    /*
     * TODO: Check page has alread been crawled
     * */
    public static boolean checkPagePresent(ArrayList<Integer> arr, int targetValue) {

        for (int i : arr) {
            if (i == targetValue) {
                return true;
            }
        }

        return false;
    }

    /*
     * TODO: Read from file
     * */
    public static ArrayList<Integer> readArrayFromFile(String filename) throws FileNotFoundException {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextInt()) {
            arr.add(scanner.nextInt());
        }

        return arr;
    }

    /*
    * TODO: Convert Integer to int
    * */
    public static int[] convertIntegers(ArrayList<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = iterator.next();
        }
        return ret;
    }

    /*
    * TODO: Write ArrayList to file
    * */
    public static void writeArrayToFile (String filename, ArrayList<Integer>  pages) throws IOException {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        int[] arrPages = convertIntegers(pages);
        for (int i : arrPages) {
            outputWriter.write(Integer.toString(i));
            outputWriter.newLine();
        }
        outputWriter.close();
    }

    /*
    * TODO: Parse review
    * */
    public static Review parseReview(Element reviewBlock) throws ParseException {
        String review_id = "";
        String title = "";
        int rating = 0;
        String reviewContent = "";
        String url = "";
        int classLabel = 0;

        // review id
        review_id = reviewBlock.id();

        // title
        Element reviewTitle = reviewBlock.select("a.review-title").first();
        title = reviewTitle.text();

        // rating
        Element star = reviewBlock.select("i.a-icon-star").first();
        String starinfo = star.text();
        rating = Integer.parseInt(starinfo.substring(0, 1));

        //class label
        if (rating > 3.0)
            classLabel = 1;
        else
            classLabel = 0;

        // review date
        Elements date = reviewBlock.select("span.review-date");
        String datetext = date.first().text();
        datetext = datetext.substring(3); // remove "On "
        Date reviewDate = null;
        try {
            reviewDate = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(datetext);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // review content
        Element contentDoc = reviewBlock.select("span.review-text").first();
        reviewContent = contentDoc.text();


        return new Review(review_id, title, rating, url, reviewDate, reviewContent, classLabel);

    }
}
