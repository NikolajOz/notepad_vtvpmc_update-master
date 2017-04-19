package com.example.om.notepad_vtvpmc;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    private EditText mEtTitle;
    private EditText mEtContent;
    private String mNoteFileName;
    private Note mLoadedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mEtTitle = (EditText) findViewById(R.id.note_et_title);
        mEtContent = (EditText) findViewById(R.id.note_et_content);

        mNoteFileName = getIntent().getStringExtra("NOTE_FILE");
        if(mNoteFileName != null && !mNoteFileName.isEmpty()){
            mLoadedNote = Utilities.getNoteByName(this, mNoteFileName);

            if(mLoadedNote != null){
                mEtTitle.setText(mLoadedNote.getmTitle());
                mEtContent.setText(mLoadedNote.getmContent());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater() .inflate(R.menu.menu_note_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.action_main_new_save:
                saveNote();

            case R.id.action_main_new_delete:
                deleteNote();

                break;

        }

        return true;
    }




    private void saveNote() {
        Note note;

        if(mEtTitle.getText().toString().trim().isEmpty() || mEtContent.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Iveskite pavadinima ir irasa", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mLoadedNote == null) {
            note = new Note(System.currentTimeMillis(), mEtTitle.getText().toString(),
                    mEtContent.getText().toString());
        }else {
            note = new Note(mLoadedNote.getmDateTime(), mEtTitle.getText().toString(),
                    mEtContent.getText().toString());
        }
        if (Utilities.saveNote(this, note)){
            Toast.makeText(this, "Jusu irasas issaugoas!", Toast.LENGTH_SHORT) .show();

        }else {
            Toast.makeText(this, "Negaliu issaugoti iraso, nes nera vietos diske"
            ,Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void deleteNote(){
        if(mLoadedNote == null){
            finish();
        }else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Trinti")
                    .setMessage("Jus ketinate istrinti " + mEtTitle.getText().toString() + ", tikrai?")
                    .setPositiveButton("taip", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Utilities.deleteNote(getApplicationContext()
                                            , mLoadedNote.getmDateTime() + Utilities.FILE_EXTENSION);

                                    Toast.makeText( getApplicationContext()
                                    , mEtTitle.getText().toString() + " Istrinta", Toast.LENGTH_SHORT).show();
                                finish();
                                }
                            })
                    .setNegativeButton("ne", null)
                    .setCancelable(false);
            dialog.show();




                    }
        }
    }


