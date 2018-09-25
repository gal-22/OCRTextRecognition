package com.zaid.green.ocrtextrecognition;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.soundcloud.android.crop.Crop;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AutoDetectActivity extends AppCompatActivity {

    // UI Views
    private final int REQUEST_CAMERA_PERMISSION = 200;
    private Button quickCaptureBtn, advancedCameraBtn, convertBtn;
    private ImageView imageView;

    // Objects
    private Uri imageViewUri;
    private Context context = this;

    // Variables
    private String imageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_detect);
        initViews();
    }

    public void initViews() {
        quickCaptureBtn = findViewById(R.id.quickCaptureBtn);
        advancedCameraBtn = findViewById(R.id.advancedCaptureBtn);
        imageView = findViewById(R.id.imageView);
        convertBtn = findViewById(R.id.convertImageBtn);

        advancedCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        quickCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intentMainActivity = new Intent(context, MainActivity.class);
                 startActivity(intentMainActivity);
            }
        });

        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageViewUri != null) {
                    Intent intentTextEditor = new Intent(context, TextEditorActivity.class);
                    imageString = convertImageViewToText(imageView);
                    if(imageString != null && !imageString.equals("")) {
                        intentTextEditor.putExtra("text", imageString);
                        startActivity(intentTextEditor);
                    }
                    else {
                        Toast.makeText(context, "No Text has been found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    public void openGallery() {
        // start picker to get image for cropping and then use the image in cropping activity
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    public String convertImageViewToText(ImageView imageView) {
        String selectedText;
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if(!textRecognizer.isOperational()) {
            Toast.makeText(this, "There was a problem with the text recognizer", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "" + bitmap.getWidth(), Toast.LENGTH_SHORT).show();
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<TextBlock> items = textRecognizer.detect(frame);
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < items.size(); i++) {
            TextBlock item = items.valueAt(i);
            stringBuilder.append(item.getValue());
            stringBuilder.append("\n");
        }
        selectedText = stringBuilder.toString();
        return selectedText;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageViewUri = result.getUri();
                imageView.setImageURI(imageViewUri);
                convertBtn.setVisibility(View.VISIBLE);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                error.printStackTrace();
            }
        }
    }

    private void handleCrop(int requestCode, Intent data) {
        if(requestCode == RESULT_OK) {
            imageView.setImageURI(Crop.getOutput(data));
        }
    }


}
