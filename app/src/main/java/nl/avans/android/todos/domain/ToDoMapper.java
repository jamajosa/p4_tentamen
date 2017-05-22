package nl.avans.android.todos.domain;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 */
public class ToDoMapper {

    /**
     *
     */
    public static ArrayList<ToDo> mapToDoList(JSONObject response){

        ArrayList<ToDo> result = new ArrayList<>();

        try{
            JSONArray jsonArray = new JSONArray(response);

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
                Log.i("ToDoMapper", "ToDo: " + toDo);

                result.add(toDo);
            }
        } catch( JSONException ex) {
            Log.e("ToDoMapper", "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
        return result;
    }
}
