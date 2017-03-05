package com.example.sarah.imageselect2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    private static final int SELECT_PHOTO = 100;
    private static final int SAVE_PHOTO = 200;
    private static final int CAMERA_PIC_REQUEST = 300;

    ImageView image;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.display_image);
        saveButton = (Button) findViewById(R.id.button);
    }

    public void pickAImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    public void takePicture(View view){

        try{
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void saveAImage(View view){

        Toast.makeText(getApplicationContext(), "sssaa image!", Toast.LENGTH_LONG).show();
        try{
            Intent saveIntent = new Intent(Intent.ACTION_SEND);
            Intent chooser = null;
            Uri selectedImage = saveIntent.getData();
            Uri imageUri = Uri.parse("android.resources://com.example.sarah.imageselect2/drawable" + R.drawable.camera);

            saveIntent.setType("image/*");
            saveIntent.putExtra(Intent.EXTRA_STREAM,imageUri );
            chooser = Intent.createChooser(saveIntent, "send image");
            startActivity(chooser);

        }catch(Exception e){
            e.printStackTrace();
        }

   }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECT_PHOTO:
                Toast.makeText(getApplicationContext(), "Pick image!", Toast.LENGTH_LONG).show();
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                    image.setImageURI(selectedImage);// To display selected image in image view
                    break;
                }


            case CAMERA_PIC_REQUEST:
                Toast.makeText(getApplicationContext(), "take image!", Toast.LENGTH_LONG).show();
                try {
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    thumbnail = Bitmap.createScaledBitmap(thumbnail, 700, 500, true);
                    ImageView imageView = (ImageView) findViewById(R.id.display_image);
                    image.setImageBitmap(thumbnail);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
                break;

            case SAVE_PHOTO:
                Toast.makeText(getApplicationContext(), "Save image!", Toast.LENGTH_LONG).show();
                return;

        }
    }

}