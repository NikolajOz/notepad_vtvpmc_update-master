package com.example.om.notepad_vtvpmc;

import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by OM on 08.04.17.
 */

public class NoteAdapter extends ArrayAdapter<Note>{
    public NoteAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Note> notes) {
        super(context, resource, notes);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_note, null);
        }

        Note note = getItem(position);
        if (note != null ){
            TextView title = (TextView) convertView.findViewById(R.id.list_note_title);
            TextView date = (TextView) convertView.findViewById(R.id.list_note_date);
            TextView content = (TextView) convertView.findViewById(R.id.list_note_content);

            title.setText(note.getmTitle());
            date.setText(note.getDateTimeFormatted(getContext()));


            if(note.getmContent().length() > 50){
                content.setText(note.getmContent().substring(0,50));
            }else{
                content.setText(note.getmContent());
            }
        }
        return convertView;
    }
}
