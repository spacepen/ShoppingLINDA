import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.regex.Pattern;

public class ShoppingBot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {

        if (update.hasMessage() &&
                update.getMessage().hasText()) {

            String response = getResponse(update.getMessage().getText());

            if (!response.isEmpty()) {
                SendMessage message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText(update.getMessage().getText().substring(6));

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public String getResponse(String message) {
        if (message.matches("(?i)echo: .*")) {
            return message.substring(6);
        }
        return "";
    }

    public String getBotUsername() {
        return "shopping_linda_bot";
    }

    public String getBotToken() {
        return "597003461:AAGZduQ4_Oizz37kc28i8BHP-7usRmn2zCM";
    }
}

