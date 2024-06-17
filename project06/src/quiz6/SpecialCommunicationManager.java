package quiz6;

import org.json.JSONArray;
import org.json.JSONObject;
import quiz5.CommunicationManager;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpecialCommunicationManager extends CommunicationManager {
    private String generalServiceUrl;
    private String exclusiveServiceUrl;

    public SpecialCommunicationManager(String generalServiceUrl, String exclusiveServiceUrl) {
        super(null);  // Not using the base URL from the parent class
        this.generalServiceUrl = generalServiceUrl;
        this.exclusiveServiceUrl = exclusiveServiceUrl;
    }

    @Override
    public JSONObject postMessage(JSONObject message) {
        String destinationUrl = generalServiceUrl + "/chat";
        if (useExclusiveService(message)) {
            destinationUrl = exclusiveServiceUrl + "/chat";
        }

        try {
            URL url = new URL(destinationUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(message.toString().getBytes());
            outputStream.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));

            String line;
            StringBuilder responseContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }

            connection.disconnect();
            return new JSONObject(responseContent.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean useExclusiveService(JSONObject message) {
        JSONArray conversationHistory = message.getJSONArray("conversationHistory");
        for (int i = 0; i < conversationHistory.length(); i++) {
            if (conversationHistory.getString(i).toLowerCase().contains("help")) {
                return true;
            }
        }
        return false;
    }
}
