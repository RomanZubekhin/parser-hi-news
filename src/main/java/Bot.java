import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
        return PropertiesData.prop.getProperty("username");
    }
    public String getBotToken() {
        return PropertiesData.prop.getProperty("token");
    }

    public String getMessage(String msg){
        ArrayList<KeyboardRow> keyboardRowsList = new ArrayList<KeyboardRow>();
        KeyboardRow row1 = new KeyboardRow();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        if (msg.equalsIgnoreCase("hi")
                || msg.equalsIgnoreCase("hello")
                || msg.equalsIgnoreCase("привет")
                || msg.equalsIgnoreCase("/start")) {
            keyboardRowsList.clear();
            row1.add("Популярное");
            row1.add("Новости hi-news");
            keyboardRowsList.add(row1);
            keyboardMarkup.setKeyboard(keyboardRowsList);
            return "Привет Юзер!" + "\n" + "Выбери что тебя интересует.";
        }
        if (msg.equals("Новости hi-news")){
            keyboardRowsList.clear();
            row1.add("Статьи за сегодня");
            row1.add("Статьи за неделю");
            keyboardRowsList.add(row1);
            keyboardMarkup.setKeyboard(keyboardRowsList);
            return "Выберите пункт меню";
        }
        return "нет такой команды";
    }
}
