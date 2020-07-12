import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Parser {
    private Document document;
    public Parser(){
        connect();
    }
    private void connect(){
        try {
            document = Jsoup.connect("https://hi-news.ru/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getArticle() {
        Elements tag = document.getElementsByTag("h2");
        String title = null;
        for (Element el: tag) {
            Element aElement = el.child(0);
            title = aElement.text();
        }
        return title;
    }
    public String getUrl() {
        Elements tag = document.getElementsByTag("h2");
        String url = null;
        for (Element el: tag) {
            Element aElement = el.child(0);
            url = aElement.attr("href");
        }
        return url;
    }
}
