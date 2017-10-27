package com.example.brandonderbidge.myapplication.main;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.brandonderbidge.myapplication.R;
import com.example.brandonderbidge.myapplication.model.Model;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by justinbrunner on 10/15/17.
 */

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private MainController mainController;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Users");

    private static int RESULT_LOAD_IMAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Starting onCreate of Login Activity");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Iconify.with(new FontAwesomeModule());

        this.mainController = new MainController(this);

        setSellFragment(savedInstanceState);

        if(Model.instance().getCurrentUser().getPhoneNumber().equals("")){

            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
            View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
            alertDialogBuilderUserInput.setView(mView);

            final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
            alertDialogBuilderUserInput
                    .setCancelable(false)
                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {
                            // ToDo get user input here
                            Model.instance().getCurrentUser().setPhoneNumber(userInputDialogEditText.getText().toString());
                            myRef.child(Model.instance().getCurrentUser().getID()).setValue(Model.instance().getCurrentUser());
                        }
                    });

            AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
            alertDialogAndroid.show();
        }
    }

    public void createToast(String message, int toastLength) {
        Toast.makeText(getBaseContext(), message, toastLength).show();
    }


    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of HomeFragment");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of HomeFragment");
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag(getString(R.string.TAG_new_contract)) != null) {
            setTitle(R.string.sell_contracts);

            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(false);
            }
        }

        Fragment newContractFragment = getSupportFragmentManager().findFragmentByTag(getString(R.string.TAG_datedialog));

        if (newContractFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(newContractFragment).commit();
        }

        super.onBackPressed();
    }

    public void setSellFragment(Bundle savedInstanceState) {
        MainFragment mainFrag = new MainFragment();
        mainFrag.setArguments(savedInstanceState);

        mainFrag.setMainController(mainController);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.activity_main_fragment_container, mainFrag, getString(R.string.TAG_main_frag))
                .commit();
    }


    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    RESULT_LOAD_IMAGE
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v(TAG, "Return with Image");
        super.onActivityResult(requestCode, resultCode, data);

        verifyStoragePermissions(this);

        System.out.println(requestCode + ", " + RESULT_LOAD_IMAGE);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Log.v(TAG, "Image Result successful");
            System.out.println("here");

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
            }
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Model.instance().setFilepath(filePath);
                Log.e("Made it", "YUP");
                //Setting image to ImageView

            } catch (Exception e) {
                e.printStackTrace();


                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            // String picturePath contains the path of selected Image
            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));


            }

        }
    }

    public void openImages() {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
}