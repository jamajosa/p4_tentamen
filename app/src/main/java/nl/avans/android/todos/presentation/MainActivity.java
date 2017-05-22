package nl.avans.android.todos.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.ToDo;
import nl.avans.android.todos.domain.ToDoAdapter;
import nl.avans.android.todos.service.ToDoApiConnector;
import nl.avans.android.todos.service.ToDoRequest;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener
         , ToDoApiConnector.ToDoListener
{

    public final String TAG = this.getClass().getSimpleName();

    public final static String TODO_DATA = "TODOS";

    private ListView listViewToDos;
    private BaseAdapter productAdapter;
    private ArrayList<ToDo> toDos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listViewToDos = (ListView) findViewById(R.id.listViewToDos);
        listViewToDos.setOnItemClickListener(this);
        productAdapter = new ToDoAdapter(this, getLayoutInflater(), toDos);
        listViewToDos.setAdapter(productAdapter);

        // We kijken hier eerst of de gebruiker nog een geldig token heeft.
        // Het token is opgeslagen in SharedPreferences.
        // Mocht er geen token zijn, of het token is expired, dan moeten we
        // eerst opnieuw inloggen.
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.saved_token), "dummy default token");
        if(token == null || token.equals("dummy default token")){
            //
            // Blijkbaar was er geen token - eerst inloggen dus
            //
            Log.d(TAG, "Geen token gevonden - inloggen dus");
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(login);
        } else {
            //
            // We hebben een token. Je zou eerst nog kunnen valideren dat het token nog
            // geldig is; dat doen we nu niet.
            // Vul de lijst met ToDos
            //
            Log.d(TAG, "Token gevonden - ToDos ophalen!");
//            ToDoApiConnector apiConnector = new ToDoApiConnector(this);
//            apiConnector.execute(Config.URL_TODOS);
            getToDos();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(settings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "Position " + position + " is geselecteerd");

        ToDo toDo = toDos.get(position);
        Intent intent = new Intent(getApplicationContext(), ToDoDetailActivity.class);
        intent.putExtra(TODO_DATA, toDo);
        startActivity(intent);
    }

    @Override
    public void onToDosAvailable(ArrayList<ToDo> toDoArrayList) {

        Log.i(TAG, "We hebben " + toDoArrayList.size() + " items in de lijst");

        toDos.clear();
        for(int i = 0; i < toDoArrayList.size(); i++) {
            toDos.add(toDoArrayList.get(i));
        }
        productAdapter.notifyDataSetChanged();
    }


    private void getToDos(){

        ToDoRequest request = new ToDoRequest(getApplicationContext(), this);
        request.handleGetAllToDos();

    }

}
