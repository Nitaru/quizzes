import org.json.JSONArray;
import org.json.JSONObject;
import quiz5.CommunicationManager;

public class DummyCommunicationManager extends CommunicationManager {
    public DummyCommunicationManager() {
        super(null);  // No base URL needed for the dummy manager
    }

    @Override
    public JSONObject sendData(JSONObject data) {
        JSONArray chatHistory = data.getJSONArray("chatHistory");
        String lastUserMessage = chatHistory.getString(chatHistory.length() - 1);

        String botResponse;
        switch (lastUserMessage) {
            case "User: Hello":
                botResponse = "Good day";
                break;
            case "User: Is it sunny outside?":
                botResponse = "Yes, it's quite sunny outside";
                break;
            case "User: I should go for a walk!":
                botResponse = "Totally, can I go with you?";
                break;
            case "User: help":
                botResponse = "You should use other services!";
                break;
            default:
                botResponse = "I don't understand.";
        }

        return new JSONObject().put("message", botResponse);
    }
}