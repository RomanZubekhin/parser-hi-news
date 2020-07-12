import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    private long chat_id;
    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        chat_id = update.getMessage().getChatId();
        sendMessage.setText(input(update.getMessage().getText()));
        try{
            execute(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return PropertiesData.prop.getProperty("username");
    }

    public String getBotToken() {
        return PropertiesData.prop.getProperty("token");
    }
    public String getInfoArticle(){
        String art = new Parser().getArticle();
        String url = new Parser().getUrl();
        return art + "\n" + url;
    }

    public String input(String msg){
        if (msg.contains("Hi") || msg.contains("hi") || msg.contains("Hello")
                || msg.contains("hello") || msg.contains("Привет")
                || msg.contains("привет")) {
            return "Привет Роман!";
        }
        if (msg.contains("Article")){
            return getInfoArticle();
        }
        return "нет такой команды";
    }
}
