import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBot extends TelegramLongPollingBot {

    private List<String> list = new ArrayList<String>();

    public void onUpdateReceived(Update update) {

        if (update.hasMessage() &&
                update.getMessage().hasText()) {

            String response = getResponse(update.getMessage().getText());

            if (!response.isEmpty()) {

                SendMessage message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText(response);

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public String getResponse(String message) {
        if (message.matches("(?i)add: .*")) {

            addToList(message.substring(5));
            return message.substring(5) + " wurde zur Liste hinzugefügt!";

        } else if (message.matches("show list")){

            return "Liste: " + list;
        }

        return "";
    }

    public List<String> addToList(String message){

        list.add(message);
        System.out.println("Liste: " + list);

        return list;

    }

    public String getBotUsername() {
        return "shopping_linda_bot";
    }

    public String getBotToken() {
        return "597003461:AAGZduQ4_Oizz37kc28i8BHP-7usRmn2zCM";
    }
}

