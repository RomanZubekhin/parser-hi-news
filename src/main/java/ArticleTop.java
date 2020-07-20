import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ArticleTop {
    private Document document = null;

    public String[] getArticleHabr(String period) throws IOException {
        if (period.equals("Лучшие за сутки")) {
            document = Jsoup.connect("https://habr.com/ru/top").get();
        }
        if (period.equals("Лучшие за неделю")) {
            document = Jsoup.connect("https://habr.com/ru/top/weekly/").get();
        }

        Elements name = document.getElementsByClass("post__title");

        ArrayList<String> article = new ArrayList<String>();
        for (int i = 0; i < name.size(); i++) {
            if (i < 10) {
                article.add(name.get(i).select("a").attr("href"));
            }
        }

        String[] strArticle = new String[article.size()];
        for (int i = 0; i < article.size(); i++) {
            strArticle[i] = article.get(i);
        }

        return strArticle;
    }
    public String[] getArticleHi(String period) throws IOException {
        if (period.equals("Последние 10 статей")) {
            document = Jsoup.connect("https://hi-news.ru/").get();
        }
        Elements name = document.getElementsByClass("more-link");

        ArrayList<String> article = new ArrayList<String>();
        for (int i = 0; i < name.size(); i++) {
            if (i < 10) {
                article.add(name.get(i).select("a").attr("href"));
            }
        }

        String[] strArticle = new String[article.size()];
        for (int i = 0; i < article.size(); i++) {
            strArticle[i] = article.get(i);
        }

        return strArticle;
    }
}