import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {
    private long chat_id;
    String lastMessage = "";
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        chat_id = update.getMessage().getChatId();

        String text = update.getMessage().getText();
        sendMessage.setReplyMarkup(keyboardMarkup);

        try{
            sendMessage.setText(getMessage(text));
            execute(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    public String getBotUsername() {
        return System.getenv("username");
    }
    public String getBotToken() {
        return System.getenv("token");
    }

    public String getMessage(String msg){
        ArrayList<KeyboardRow> keyboardRowsList = new ArrayList<KeyboardRow>();
        KeyboardRow row1 = new KeyboardRow();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        if (msg.equalsIgnoreCase("hi")
                || msg.equalsIgnoreCase("hello")
                || msg.equalsIgnoreCase("привет")
                || msg.equalsIgnoreCase("/start")) {
            row1.add("Habr");
            row1.add("Новости hi-news");
            keyboardRowsList.add(row1);
            keyboardMarkup.setKeyboard(keyboardRowsList);
            return "Привет!" + "\n" + "Выбери что тебя интересует.";
        }
        if (msg.equals("Новости hi-news")){
            lastMessage = msg;
            keyboardRowsList.clear();
            row1.add("Последние 10 статей");
            row1.add("Меню");
            keyboardRowsList.add(row1);
            keyboardMarkup.setKeyboard(keyboardRowsList);
            return "Выберите пункт меню";
        }
        if(lastMessage.equals("Новости hi-news")){
            if(msg.equals("Последние 10 статей")){
                try {
                    return getArticleHi(new ArticleTop().getArticleHi(msg));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (msg.equals("Habr")){
            lastMessage = msg;
            keyboardRowsList.clear();
            row1.add("Лучшие за сутки");
            row1.add("Лучшие за неделю");
            row1.add("Меню");
            keyboardRowsList.add(row1);
            keyboardMarkup.setKeyboard(keyboardRowsList);
            return "Выберите пункт меню";
        }
        if(lastMessage.equals("Habr")){
            if(msg.equals("Лучшие за сутки") || msg.equals("Лучшие за неделю")){
                keyboardRowsList.clear();
                try {
                    return getArticleHabr(new ArticleTop().getArticleHabr(msg));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (msg.equalsIgnoreCase("меню")) {
            keyboardRowsList.clear();
            row1.add("Habr");
            row1.add("Новости hi-news");
            keyboardRowsList.add(row1);
            keyboardMarkup.setKeyboard(keyboardRowsList);
            return "Выбери что тебя интересует.";
        }
        return "нет такой команды";
    }

    private String getArticleHi(String[] articleHi) {
        SendMessage sendMessage = new SendMessage().setChatId(chat_id);
        for(int i = 0; i < articleHi.length; i++){
            try {
                if((i + 1) == articleHi.length){
                    return articleHi[i];
                }
                sendMessage.setText(articleHi[i]);
                execute(sendMessage);

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return "Что-то пошло не так!";
    }

    private String getArticleHabr(String[] articleHabr) {
        SendMessage sendMessage = new SendMessage().setChatId(chat_id);
        for(int i = 0; i < articleHabr.length; i++){
            try {
                if((i + 1) == articleHabr.length){
                    return articleHabr[i];
                }
                sendMessage.setText(articleHabr[i]);
                execute(sendMessage);

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return "Что-то пошло не так!";
    }
}
