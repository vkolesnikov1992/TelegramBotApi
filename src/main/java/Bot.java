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

    public void sendMsg2(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            setButtons2(sendMessage);
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
                    sendMsg2(message, "Добро пожаловать! Выберите подходящий вариант:");
                    break;

                case "Текущая погода":
                    sendMsg(message, "Введите или выберите город:");
                    break;

                case "Москва":
                    try {
                        sendMsg(message, WeatherFiveDay.getWeather(message.getText(),model));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;


                case "Погода на 5 дней":
                    sendMsg(message, "Ввведите или выбирите город:");
                    break;

                default:
                    try {
                        sendMsg2(message, Weather.getWeather(message.getText(), model));
                    } catch (Exception ex) {
                        sendMsg2(message, "Город не найден");
                    }

            }
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardTwoRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("Благовещенск"));
        keyboardFirstRow.add(new KeyboardButton("Февральск"));
        keyboardTwoRow.add(new KeyboardButton("Свободный"));
        keyboardTwoRow.add(new KeyboardButton("Мазаново"));

        keyboardRows.add(keyboardFirstRow);
        keyboardRows.add(keyboardTwoRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    public void setButtons2(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardTwoRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("Текущая погода"));
        keyboardTwoRow.add(new KeyboardButton("Погода на 5 дней"));

        keyboardRows.add(keyboardFirstRow);
        keyboardRows.add(keyboardTwoRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    public String getBotUsername() {
        return "WeatherBot";
    }

    public String getBotToken() {
        return "**************";
    }
}
