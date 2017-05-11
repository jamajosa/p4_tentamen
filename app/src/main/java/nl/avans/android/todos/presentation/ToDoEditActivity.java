package nl.avans.android.todos.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.ToDo;

import static nl.avans.android.todos.presentation.MainActivity.TODO_DATA;

public class ToDoEditActivity extends AppCompatActivity {

    private TextView textTitle;
    private TextView textContents;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_edit);

        textTitle = (TextView) findViewById(R.id.textEditToDoTitle);
        textContents = (TextView) findViewById(R.id.textEditToDoContents);

        Bundle extras = getIntent().getExtras();

        ToDo toDo = (ToDo) extras.getSerializable(TODO_DATA);
        Log.i(TAG, toDo.toString());

        textTitle.setText(toDo.getTitle());
        textContents.setText(toDo.getContents());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish(); // or go to another activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
