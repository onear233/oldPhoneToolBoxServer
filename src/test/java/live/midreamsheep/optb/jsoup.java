package live.midreamsheep.optb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URLEncoder;

public class jsoup {
    public static String translate(String text) throws IOException {
        //将text进行编码转义为url支持的格式
        text = URLEncoder.encode(text, "UTF-8");
        Document document = Jsoup.connect("https://www.youdao.com/result?word=#{text}&lang=en".replace("#{text}", text))
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36")
                .timeout(5000)
                .ignoreContentType(true)
                .get();
        String s = document.attr("ul.basic>li.word-exp>span.trans");
        System.out.println(s);
        return "";
    }

    public static void main(String[] args) throws Exception {
        String s = new Translator().translate("en", "zh-CN", "hello!this is java|||dsadwa&&da");
        System.out.println(s);

    }
}
