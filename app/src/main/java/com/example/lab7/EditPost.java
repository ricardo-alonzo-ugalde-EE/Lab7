package com.example.lab7;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

public class EditPost extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        Window window = EditPost.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(EditPost.this, R.color.colorAccent));
        
        Bundle extras = getIntent().getExtras();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final String description = (String) extras.getString("description");
        final EditText descriptionField = (EditText) findViewById(R.id.description);
        descriptionField.setText(description);
        final String postKey = (String) extras.getString("postKey");







        Button button = findViewById(R.id.update);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast toast =  Toast.makeText(getApplicationContext(),"Description updated",Toast.LENGTH_SHORT);
                toast.show();
                database.getReference("Posts/" +postKey).runTransaction(new Transaction.Handler()
                {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData)
                    {
                        PhotoPreview.Post p = mutableData.getValue(PhotoPreview.Post.class);
                        if(p==null)
                        {
                            return Transaction.success(mutableData);

                        }
                        p.description = descriptionField.getText().toString();
                        mutableData.setValue(p);
                        return Transaction.success(mutableData);

                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData)
                    {

                    }
                });
                Intent i = new Intent(EditPost.this, MainActivity.class);
            }
        });

    }
    public void Publish(View view)
    {
        finish();
    }









}