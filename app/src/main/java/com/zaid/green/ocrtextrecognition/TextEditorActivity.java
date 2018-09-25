package com.zaid.green.ocrtextrecognition;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.irshulx.Editor;
import com.github.irshulx.EditorListener;

public class TextEditorActivity extends AppCompatActivity {

    // UI views
    Editor editor;
    Button exportBtn;

    // Variables
    private String receivedString; // the text recived from the last activity
    private String changedText; // the text that has been edited by the editor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_editor);
        Intent intent = getIntent();
        receivedString = (String) intent.getSerializableExtra("text");
        changedText = receivedString;
        initEditor();
    }

    public void initEditor() {
        exportBtn = findViewById(R.id.exportBtn);
        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportData();
            }
        });
        editor = findViewById(R.id.editor);
        editor.render("<p>" + receivedString + "</p>");
        editor.setEditorListener(new EditorListener() {
            @Override
            public void onTextChanged(EditText editText, Editable text) {
                changedText = text.toString();
            }
            @Override
            public void onUpload(Bitmap image, String uuid) {

                //do your upload image operations here, once done, call onImageUploadComplete and pass the url and uuid as reference.
                editor.onImageUploadComplete("http://www.videogamesblogger.com/wp-content/uploads/2015/08/metal-gear-solid-5-the-phantom-pain-cheats-640x325.jpg",uuid);
                // editor.onImageUploadFailed(uuid);
            }
        });
    }

    public void exportData() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, changedText);
        intent.setType("text/plain");
        startActivity(intent);
        if (intent.resolveActivity(getPackageManager()) != null) {
            if(changedText != null && !changedText.equals("")) {
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "No text has been entered", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Couldn't find any applications to export", Toast.LENGTH_SHORT).show();
        }
    }
}