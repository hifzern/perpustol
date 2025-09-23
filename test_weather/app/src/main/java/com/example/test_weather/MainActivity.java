package com.example.test_weather;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    Button btn_getBookByID, btn_getBooks,  btn_getwaetherByCityName, btn_Tambah, btn_Edit, btn_Hapus;
    EditText et_dataInput, et_title, et_author, et_year, et_ID;
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
        btn_getBookByID = findViewById(R.id.btn_getBookByID);
        btn_getBooks = findViewById(R.id.btn_getBooks);
        btn_getwaetherByCityName = findViewById(R.id.btn_getwaetherByCityName);
        btn_Tambah = findViewById(R.id.btn_Tambah);
        btn_Edit = findViewById(R.id.btn_Edit);
        btn_Hapus = findViewById(R.id.btn_Hapus);

        et_dataInput = findViewById(R.id.et_Title_ID);
        et_title = findViewById(R.id.et_title);
        et_year = findViewById(R.id.et_year);
        et_ID = findViewById(R.id.et_ID);
        et_author = findViewById(R.id.et_author);

        lv_waetherReaports = findViewById(R.id.lv_waetherReaports);

        BookDataService bookDataService = new BookDataService(MainActivity.this);


        // Assuming 'lv_books' is your ListView variable
        lv_waetherReaports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // 1. Get the specific 'BookModel' object that was clicked from the adapter.
                BookModel selectedBook = (BookModel) adapterView.getItemAtPosition(position);

                // 2. Check if the object is valid to avoid errors.
                if (selectedBook != null) {

                    // 3. Get the data from the 'selectedBook' object and set it into your EditTexts.

                    // Set the ID (convert the integer to a String)
                    et_ID.setText(String.valueOf(selectedBook.getId()));

                    // Set the Title
                    et_title.setText(selectedBook.getTitle());

                    // Set the Year (convert the integer to a String)
                    et_year.setText(String.valueOf(selectedBook.getYear()));

                    et_author.setText(String.valueOf(selectedBook.getAuthor()));

                    // Set the main data input field. You can use the title or author here.
                    // Tip: You could also use the author: et_dataInput.setText(selectedBook.getAuthor());
                }
            }
        });

        //click Listener
        btn_getBookByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookDataService.getBookByID(et_dataInput.getText().toString(), new BookDataService.VolleyResponsListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Somthing's worng", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRespone(List<BookModel> bookModel) {
                        //put list
//                        Toast.makeText(MainActivity.this, "title: " + bookTitle, Toast.LENGTH_SHORT).show();
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, bookModel);
                        lv_waetherReaports.setAdapter(arrayAdapter);
                    }
                });
            }
        });

        btn_getBooks.setOnClickListener(new View.OnClickListener() {
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
                bookDataService.getBookByTitle(et_dataInput.getText().toString(), new BookDataService.BooksRespons() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Somthing's worng", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRespone(List<BookModel> bookModel) {
                        //put list
//                        Toast.makeText(MainActivity.this, "title: " + bookTitle, Toast.LENGTH_SHORT).show();
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, bookModel);
                        lv_waetherReaports.setAdapter(arrayAdapter);
                    }
                });
            }
        });

        btn_Hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookDataService.deleteBookById(et_ID.getText().toString(), new BookDataService.DeletionListener() {
                    @Override
                    public void onComplete() {

                        Toast.makeText(MainActivity.this, "Delete Succesfull", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Somthing's worng", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_Tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = et_title.getText().toString();
                String author = et_author.getText().toString();
                int year = Integer.parseInt(et_year.getText().toString());
                bookDataService.addBook(title, author, year, new BookDataService.AddBookListener() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(MainActivity.this, "Add Successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookId = et_ID.getText().toString();
                String title = et_title.getText().toString();
                String author = et_author.getText().toString();
                int year = Integer.parseInt(et_year.getText().toString()); // Remember to handle NumberFormatException

                bookDataService.updateBook(bookId, title, author, year, new BookDataService.UpdateBookListener() {
                    @Override
                    public void onComplete() {

                        Toast.makeText(MainActivity.this, "Edit Successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something went wrong: " + message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}