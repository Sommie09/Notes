package com.example.notes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteKeeper extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NoteRecyclerAdapter mNoteRecyclerAdapter;
    private RecyclerView mRecyclerItems;
    private LinearLayoutManager mNotesLayoutManager;
    private CourseRecyclerAdapter mCourseRecyclerAdapter;
    private GridLayoutManager mCourseLayoutManager;
    private NoteKeeperOpenHelper mNoteKeeperOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_keeper);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNoteKeeperOpenHelper = new NoteKeeperOpenHelper(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoteKeeper.this, NoteActivity.class));
            }
        });

        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_notification, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_data_sync, false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_closed);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initializeDisplayContent();
    }

    @Override
    protected void onDestroy() {
        mNoteKeeperOpenHelper.close();
        super.onDestroy();
    }

    //Method to populate NoteList
    private void initializeDisplayContent() {
        mRecyclerItems = findViewById(R.id.list_items);

        mNotesLayoutManager = new LinearLayoutManager(this);
        mCourseLayoutManager = new GridLayoutManager(this,getResources().getInteger(R.integer.course_grid_span));

        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        mNoteRecyclerAdapter = new NoteRecyclerAdapter(this, notes);

        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        mCourseRecyclerAdapter = new CourseRecyclerAdapter(this, courses);


        displayNotes();
        displayCourses();


    }

    private void displayCourses() {
        mRecyclerItems.setLayoutManager(mCourseLayoutManager);
        mRecyclerItems.setAdapter(mCourseRecyclerAdapter);

        selectNavigationMenuItem(R.id.nav_courses);

    }

    private void selectNavigationMenuItem(int id){
        NavigationView navigationView =  findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        menu.findItem(id).setChecked(true);
    }


    private void displayNotes() {
        mRecyclerItems.setLayoutManager(mNotesLayoutManager);
        mRecyclerItems.setAdapter(mNoteRecyclerAdapter);

        SQLiteDatabase db = mNoteKeeperOpenHelper.getReadableDatabase();

        selectNavigationMenuItem(R.id.nav_notes);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mNoteRecyclerAdapter.notifyDataSetChanged();
        updateNavHeader();
    }

    private void updateNavHeader() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        TextView textUserName = headerView.findViewById(R.id.text_user_name);
        TextView textEmailAddress = headerView.findViewById(R.id.text_email_address);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = sharedPreferences.getString("user_display_name", "");
        String email = sharedPreferences.getString("user_email_address", "");

        textUserName.setText(username);
        textEmailAddress.setText(email);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_keeper, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_notes){
                displayNotes();
        }else if(id == R.id.nav_courses){
            displayCourses();
        }else if(id == R.id.nav_share){

        }else if(id == R.id.nav_send){

        }

        DrawerLayout mDrawerLayout =  findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings){
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}