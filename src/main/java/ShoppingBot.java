
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
                        .setText(response)
                        .enableMarkdown(true);

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }


    public String getResponse(String message) {


        if (message.matches("(?i)/add .*")){

            if (list.contains(message.substring(5))) {

                return "The item " + "_" + message.substring(5) + "_" + " is already on your list!";

            } else {

                addToList(message.substring(5));
                return "_" + message.substring(5) + "_" + " was added to your list! \n\n*Your List:*\n" + list.toString()
                        .replace("[","")
                        .replace("]","")
                        .replace(", ", "\n");
            }

        } else if (message.matches("/show")){

            if (list.size() < 1){

                return "There's nothing on your list!";

            } else {

                return "*Your List:*\n" + list.toString()
                        .replace("[","")
                        .replace("]","")
                        .replace(", ", "\n");
            }

        } else if (message.matches("/delete .*")){

            if (list.contains(message.substring(8))){

                deleteFromList(message.substring(8));

                if (list.size() < 1){

                    return "_" + message.substring(8) + "_" + " was removed from your list! \nYour list is now empty!";

                } else {

                    return "_" + message.substring(8) + "_" + " was removed from your list! \n\n*Your list:*\n" + list.toString()
                            .replace("[","")
                            .replace("]","")
                            .replace(", ", "\n");
                }

            } else {

                return "_" + message.substring(8) + "_" + " is not an existing item on your current list! " +
                        "\nCheck your list again to see the items it contains!";
            }


        } else if (message.matches("/clear")){

            if (list.size() < 1){

                return "Your list is already empty!";

            } else {

                deleteAll();
                return "You've successfully cleared all items from your list! \nFeel free to add new items any time!";
            }

        } else if (message.matches("/linda")){

            return "*Hello friend!*" +
                    "\n\nUse the following tags to interact with me: " +
                    "\n\n/add - Add items to the list. " +
                    "\n/delete - Remove a specific item from the list. " +
                    "\n/show - Show all current items on the list. " +
                    "\n/clear - Remove all items from the list. " +
                    "\n/linda - Come back for help.";
        }

        return "I don't understand you! Use /linda for help!";
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

