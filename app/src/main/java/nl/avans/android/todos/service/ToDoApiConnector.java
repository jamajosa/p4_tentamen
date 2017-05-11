package nl.avans.android.todos.service;

import android.os.AsyncTask;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import nl.avans.android.todos.domain.ToDo;

public class ToDoApiConnector extends AsyncTask<String, Void, String> {

    private ToDoListener listener = null;
    private String urlString = "";
    private final String TAG = this.getClass().getSimpleName();

    // We retourneren de hele lijst met ToDos.
    private ArrayList<ToDo> toDoArrayList = new ArrayList<>();

    /**
     *
     * @param listener
     */
    public ToDoApiConnector(ToDoListener listener){
        this.listener = listener;
    }

    /**
     *
     * @param params
     * @return
     */
    @Override
    protected String doInBackground(String... params){

        if(params[0] != null) {
            urlString = params[0];
        }
        Log.i(TAG, "doInBackground " + urlString);

        InputStream inputStream = null;
        int responsCode = -1;
        String response = "";

        try{
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();

            if (!(urlConnection instanceof HttpURLConnection)) {
                // Url
                return null;
            }

            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            responsCode = httpConnection.getResponseCode();

            if (responsCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConnection.getInputStream();
                response = getStringFromInputStream(inputStream);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground MalformedURLEx " + e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e("TAG", "doInBackground IOException " + e.getLocalizedMessage());
            return null;
        }
        return response;
    }

    /**
     *
     * @param response
     */
    protected void onPostExecute(String response){

        JSONArray jsonArray;

        Log.i(TAG, "onPostExecute");

        // Check of we een response hebben
        if(response == "") {
            Log.e(TAG, "onPostExecute - response input was leeg!");
            return;
        }

        try{
            jsonArray = new JSONArray(response);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonProduct = jsonArray.getJSONObject(i);

                // Convert stringdate to Date
                String timestamp = jsonProduct.getString("AangemaaktOp");
                DateTime todoDateTime = ISODateTimeFormat.dateTimeParser().parseDateTime(timestamp);

                ToDo toDo = new ToDo(
                        jsonProduct.getString("Titel"),
                        jsonProduct.getString("Beschrijving"),
                        jsonProduct.getString("Status"),
                        todoDateTime
                );
                Log.i(TAG, "ToDo: " + toDo);

                toDoArrayList.add(toDo);
            }
            // Stuur de resultaten terug
            listener.onToDosAvailable(toDoArrayList);

        } catch( JSONException ex) {
            Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
    }

    private static String getStringFromInputStream(InputStream inputStream){
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public interface ToDoListener {
        void onToDosAvailable(ArrayList<ToDo> toDos);
    }
}
