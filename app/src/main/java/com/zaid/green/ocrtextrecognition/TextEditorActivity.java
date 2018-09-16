package com.zaid.green.ocrtextrecognition;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.github.irshulx.Editor;
import com.github.irshulx.EditorListener;

public class TextEditorActivity extends AppCompatActivity {

    // UI views
    Editor editor;
    // Variables
    private String receivedString; // the text recived from the last activity
    private String changedText; // the text that has been edited by the editor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_editor);
        Intent intent = getIntent();
        receivedString = (String) intent.getSerializableExtra("text");
        initEditor();
    }

    public void initEditor() {
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
}