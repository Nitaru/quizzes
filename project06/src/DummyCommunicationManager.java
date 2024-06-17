import org.json.JSONArray;
import org.json.JSONObject;
import quiz5.CommunicationManager;

public class SimpleBotCommunicationManager extends CommunicationManager {
    public SimpleBotCommunicationManager() {
        super(null);  // No base URL needed for the simple bot manager
    }

    @Override
    public JSONObject postMessage(JSONObject message) {
        JSONArray conversationHistory = message.getJSONArray("conversationHistory");
        String lastUserMessage = conversationHistory.getString(conversationHistory.length() - 1);

        String botResponse;
        switch (lastUserMessage) {
            case "User: Hello":
                botResponse = "Good day";
                break;
            case "User: How's the weather?":
                botResponse = "It's sunny and warm!";
                break;
            case "User: Let's go for a walk!":
                botResponse = "Great idea! I'm ready when you are.";
                break;
            case "User: help":
                botResponse = "Sure, what do you need help with?";
                break;
            default:
                botResponse = "I'm not sure what you mean. Can you please clarify?";
        }

        return new JSONObject().put("message", botResponse);
    }
}
