package quiz5;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserInteractionManager {
    private CommunicationManager chatClient;
    private List<String> conversationHistory;

    public UserInteractionManager(CommunicationManager chatClient) {
        this.chatClient = chatClient;
        this.conversationHistory = new ArrayList<>();
    }

    public void logMessage(String sender, String message) {
        conversationHistory.add(sender + ": " + message);
    }

    public JSONObject convertHistoryToJson() {
        JSONArray jsonArray = new JSONArray(conversationHistory);
        return new JSONObject().put("conversationHistory", jsonArray);
    }

    public void transmitUserMessage(String message) {
        logMessage("User", message);
        JSONObject payload = convertHistoryToJson();
        JSONObject serverResponse = chatClient.postMessage(payload);
        if (serverResponse != null) {
            String botReply = serverResponse.getString("message");
            logMessage("Chatbot", botReply);
            System.out.println("Chatbot: " + botReply);
        } else {
            System.out.println("Chatbot: Error communicating with the server.");
        }
    }
}