
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
            return message.substring(5) + " wurde zur Liste hinzugefügt! \nListe: " + list;

        } else if (message.matches("show list")){

            if (list.size() < 1){

                return "Deine Liste ist leer!";

            } else {

                return "Liste: " + list;
            }

        } else if (message.matches("delete: .*")){

            deleteFromList(message.substring(8));

            if (list.size() < 1){

                return message.substring(8) + " wurde von der Liste entfernt! \nDeine Liste ist leer!";

            } else {

                return message.substring(8) + " wurde von der Liste entfernt! \nListe: " + list;
            }

        } else if (message.matches("delete list")){

            deleteAll();
            return "Liste wurde vollständig gelöscht!";

        }

        return "";
    }

    public List<String> addToList(String message){

        list.add(message);
        System.out.println("Liste: " + list);

        return list;

    }

    public List<String> deleteFromList(String message){


        list.remove(message);
        System.out.println("Liste: " + list);

        return list;
    }

    public List<String> deleteAll() {

        list.clear();
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

