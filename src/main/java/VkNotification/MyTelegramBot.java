package VkNotification;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MyTelegramBot extends TelegramLongPollingBot {
    private String chatId; // the chat ID where the message will be sent
    private String message; // the message to send when triggered

    public MyTelegramBot(String chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Do nothing, since the bot will only send messages when triggered
    }

    public void sendMessage() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "BOT USERNAME";
    }

    @Override
    public String getBotToken() {
        return "YOUR TOKEN";
    }

}