package com.martroutine.android.androidgreendao.app;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.martroutine.android.greendaoexample.DaoMaster;
import com.martroutine.android.greendaoexample.DaoSession;
import com.martroutine.android.greendaoexample.Note;
import com.martroutine.android.greendaoexample.NoteDao;

import java.util.Date;
import java.util.List;


public class MainActivity extends Activity {

    private final static String TAG = "ANDROIDGREENDAO";
    private SQLiteDatabase db;
    private DaoMaster.DevOpenHelper helper;
    private DaoSession daoSession;
    private DaoMaster daoMaster;
    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        noteDao = daoSession.getNoteDao();
        Note note = new Note(null, "mart","testing green dao", new Date());
        noteDao.insert(note);
        Log.d(TAG, "Inserted new note, ID: " + note.getId());

        List<Note> notes = noteDao.loadAll();
        Log.d(TAG,"Note size: " + notes.size());
        for (Note data : notes){
            Log.d(TAG,"Comment from database: " + data.getComment());
        }

        List<Note> specificList = noteDao.queryBuilder().where(NoteDao.Properties.Text.eq("vtanathip")).list();
        Log.d(TAG,"specific list size: " + specificList.size());
        for (Note data : specificList){
            Log.d(TAG,"specific text from database: " + data.getText());
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
