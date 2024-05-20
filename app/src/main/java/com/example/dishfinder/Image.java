package com.example.dishfinder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

public class Image extends AppCompatActivity {
    public Button uploadButton;
    public TextView imageDish;
    public ImageView image, returnView3;
    private final int GALLERY_REQ_CODE = 0;
    GenerativeModelFutures model = GenerativeModelFutures.from(new GenerativeModel("gemini-pro-vision", "AIzaSyBE227sWGV6DVxTKaIVYraxt3vjZvhZq6U"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        uploadButton = findViewById(R.id.uploadButton);
        imageDish = findViewById(R.id.imageDish);
        image = findViewById(R.id.imageView);
        returnView3 = findViewById(R.id.returnView3);
        uploadButton.setOnClickListener(v -> {
            Intent gallery = new Intent(Intent.ACTION_PICK);
            gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, GALLERY_REQ_CODE);
        });
        returnView3.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQ_CODE && data != null && data.getData() != null) {
            image.setImageURI(data.getData());
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Content content = new Content.Builder().addText("Provide the name of the dish in the following image.").addImage(bitmap).build();
                Executor executor = Runnable::run;
                Futures.addCallback(model.generateContent(content), new FutureCallback<GenerateContentResponse>() {
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                        imageDish.setText(result.getText());
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }, executor);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
