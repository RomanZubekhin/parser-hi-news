import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestParser {
    public static void main(String[] args) {
        List<Article> articleList = new ArrayList<Article>();
        Document document;
        try {
            document = Jsoup.connect("https://hi-news.ru/").get();
            Elements tagA = document.getElementsByTag("h2");

            for (Element el: tagA) {
                Element aElement = el.child(0);
                String url = aElement.attr("href");
                String title = aElement.text();

                articleList.add(new Article(url,title));
            }
            for (Article list : articleList) {
                System.out.println(list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class Article {
    private String url;
    private String name;

    public Article(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Article{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
