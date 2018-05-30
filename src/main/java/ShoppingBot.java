
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
        if (message.matches("(?i)/add .*")) {

            addToList(message.substring(5));
            return message.substring(5) + " was added to your list! \nYour list: " + list.toString().replace("[","").replace("]","");

        } else if (message.matches("/show")){

            if (list.size() < 1){

                return "Your list is empty!";

            } else {

                return "Your list: " + list.toString().replace("[","").replace("]","");
            }

        } else if (message.matches("/delete .*")){

            if (list.contains(message.substring(8))) {

                deleteFromList(message.substring(8));

                if (list.size() < 1){

                    return message.substring(8) + " was removed from your list! \nYour list is empty!";

                } else {

                    return message.substring(8) + " was removed from your list! \nYour list: " + list.toString().replace("[","").replace("]","");
                }

            } else {

                return message.substring(8) + " is not an existing item on your current list! \nCheck your list again to see the items it contains!";
            }


        } else if (message.matches("/clear")){

            deleteAll();
            return "You've successfully deleted your current list! \nFeel free to create a new one!";

        }

        return "";
    }

    public List<String> addToList(String message){

        list.add(message);
        System.out.println("List: " + list);

        return list;

    }

    public List<String> deleteFromList(String message){


        list.remove(message);
        System.out.println("List: " + list);

        return list;
    }

    public List<String> deleteAll() {

        list.clear();
        System.out.println("List: " + list);

        return list;
    }

    public String getBotUsername() {
        return "shopping_linda_bot";
    }

    public String getBotToken() {
        return "597003461:AAGZduQ4_Oizz37kc28i8BHP-7usRmn2zCM";
    }
}

