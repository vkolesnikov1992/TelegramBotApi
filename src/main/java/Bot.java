import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException ex) {
            ex.printStackTrace();
        }
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            sendMessage(sendMessage);

        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {

                case "/start":
                    sendMsg(message, "Добро пожаловать! Чтобы узнать погоду введите ваш город");
                    break;


                default:
                    try {
                        sendMsg(message, Weather.getWeather(message.getText(), model));
                    } catch (Exception ex) {
                        sendMsg(message, "Город не найден");
                    }

            }
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        ReplyKeyboardMarkup replyKeyboardMarkup1 = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup1);
        replyKeyboardMarkup1.setSelective(true);
        replyKeyboardMarkup1.setResizeKeyboard(true);
        replyKeyboardMarkup1.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        List<KeyboardRow> keyboardRows1 = new ArrayList<>();
        KeyboardRow keyboardTwoRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("Благовещенск"));
        keyboardFirstRow.add(new KeyboardButton("Февральск"));
        keyboardTwoRow.add(new KeyboardButton("Свободный"));
        keyboardTwoRow.add(new KeyboardButton("Новокиевский Увал"));

        keyboardRows.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);

        keyboardRows1.add(keyboardTwoRow);
        replyKeyboardMarkup1.setKeyboard(keyboardRows1);
    }

    public String getBotUsername() {
        return "WeatherBot";
    }

    public String getBotToken() {
        return "679967918:AAHbrwG9Ckr0ABcLrOX2jSfg-r_h_6OfCuQ";
    }
}
