package nl.avans.android.todos.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.Film;

import static nl.avans.android.todos.presentation.MainActivity.FILM_DATA;

public class FilmDetailActivity extends AppCompatActivity {

    private TextView textTitle;
    private TextView textContents;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        textTitle = (TextView) findViewById(R.id.textDetailFilmTitle);
        textContents = (TextView) findViewById(R.id.textDetailFilmContents);

        Bundle extras = getIntent().getExtras();

        Film film = (Film) extras.getSerializable(FILM_DATA);
        Log.i(TAG, film.toString());

        textTitle.setText(film.getTitle());
        textContents.setText(film.getContents());
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
