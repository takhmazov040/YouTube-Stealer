import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class YouTubeStealer {
    private YouTubeStealer() {
    }
    
    public static String stealVideoInfo(String videoID) {
        OutputStream os = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            String YT_PLAYER_URL = "https://www.youtube.com/youtubei/v1/player";
            String YT_PLAYER_KEY = "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8";
            URL url = new URL(YT_PLAYER_URL + "?key=" + YT_PLAYER_KEY);

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/json;");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();

            os = http.getOutputStream();
            os.write(
                ("{" +
                "\"context\": {" +
                "\"client\": {" +
                "\"hl\": \"en\"," +
                "\"clientName\": \"WEB\"," +
                "\"clientVersion\": \"2.20210721.00.00\"," +
                "\"clientFormFactor\": \"UNKNOWN_FORM_FACTOR\"," +
                "\"clientScreen\": \"WATCH\"," +
                "\"mainAppWebInfo\": {" +
                "\"graftUrl\": \"/watch?v=" + videoID + "\"" +
                "}" +
                "}," +
                "\"user\": {" +
                "\"lockedSafetyMode\": false" +
                "}," +
                "\"request\": {" +
                "\"useSsl\": true," +
                "\"internalExperimentFlags\": []," +
                "\"consistencyTokenJars\": []" +
                "}" +
                "}," +
                "\"videoId\": \"" + videoID + "\"," +
                "\"playbackContext\": {" +
                "\"contentPlaybackContext\": {" +
                "\"vis\": 0," +
                "\"splay\": false," +
                "\"autoCaptionsDefaultOn\": false," +
                "\"autonavState\": \"STATE_NONE\"," +
                "\"html5Preference\": \"HTML5_PREF_WANTS\"," +
                "\"lactMilliseconds\": \"-1\"" +
                "}" +
                "}," +
                "\"racyCheckOk\": false," +
                "\"contentCheckOk\": false" +
                "}").getBytes(StandardCharsets.UTF_8)
            );

            if (http.getResponseCode() != HttpURLConnection.HTTP_OK)
                return null;

            isr = new InputStreamReader(http.getInputStream());
            br = new BufferedReader(isr);
            StringBuilder response = new StringBuilder();
            String read;
            while ((read = br.readLine()) != null) {
                response.append(read);
            }

            return response.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
