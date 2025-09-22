package com.example.test_weather;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_getCityID, btn_getWaetherByID,  btn_getwaetherByCityName;
    EditText et_dataInput;
    ListView lv_waetherReaports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Assign value to each control in the lay out
        btn_getCityID = findViewById(R.id.btn_getBookByID);
        btn_getWaetherByID = findViewById(R.id.btn_getBooks);
        btn_getwaetherByCityName = findViewById(R.id.btn_getwaetherByCityName);

        et_dataInput = findViewById(R.id.et_dataInput);
        lv_waetherReaports = findViewById(R.id.lv_waetherReaports);

        BookDataService bookDataService = new BookDataService(MainActivity.this);

        //click Listener
        btn_getCityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookDataService.getBookTitle(et_dataInput.getText().toString(), new BookDataService.VolleyResponsListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Somthing's worng", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRespone(String bookTitle) {
                        Toast.makeText(MainActivity.this, "title: " + bookTitle, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_getWaetherByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookDataService.getBooks(new BookDataService.BooksRespons() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Somthing's worng", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRespone(List<BookModel> bookModel) {
                        //put list

                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, bookModel);
                        lv_waetherReaports.setAdapter(arrayAdapter);
                    }
                });

            }
        });

        btn_getwaetherByCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "test_btn_03", Toast.LENGTH_SHORT).show();

            }
        });
    }
}