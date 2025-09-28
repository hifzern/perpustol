package com.example.test_weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_getBookByID, btn_getBooks, btn_getwaetherByCityName, btn_Tambah, btn_Edit, btn_Hapus;
    EditText et_dataInput, et_title, et_author, et_year, et_cover_url, et_genre, et_description;
    TextView et_ID;

    private void showBookPopup(List<BookModel> bookList) {
        // Inflate layout test.xml
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View popupView = inflater.inflate(R.layout.test, null);
        ListView listView = popupView.findViewById(R.id.list_books);
        // Adapter untuk list buku
        ArrayAdapter<BookModel> adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                bookList
        );
        listView.setAdapter(adapter);
        // Buat dialog
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setView(popupView)
                .setCancelable(true)
                .create();
        // Ketika item dipilih
        listView.setOnItemClickListener((parent, view, position, id) -> {
            BookModel selectedBook = bookList.get(position);
            et_ID.setText(String.valueOf(selectedBook.getId()));
            et_title.setText(selectedBook.getTitle());
            et_author.setText(selectedBook.getAuthor());
            et_year.setText(String.valueOf(selectedBook.getYear()));
            et_cover_url.setText(String.valueOf(selectedBook.getCover_url()));
            et_genre.setText(String.valueOf(selectedBook.getGenre()));
            et_description.setText(String.valueOf(selectedBook.getDescription()));
            dialog.dismiss();
        });

        dialog.show();
    }
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
        et_ID= findViewById(R.id.et_ID);
        et_author = findViewById(R.id.et_author);
        et_description = findViewById(R.id.et_description);
        et_genre = findViewById(R.id.et_genre);
        et_cover_url = findViewById(R.id.et_cover_url);

        BookDataService bookDataService = new BookDataService(MainActivity.this);
        // Assuming 'lv_books' is your ListView variable
        //click Listener
        btn_getBookByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = et_dataInput.getText().toString().trim();
                bookDataService.getBookByID(et_dataInput.getText().toString(), new BookDataService.VolleyResponsListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Somthing's worng", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRespone(List<BookModel> bookModel) {
                        if (bookModel != null && !bookModel.isEmpty()) {
                            showBookPopup(bookModel);
                        }
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
                        if (bookModel != null && !bookModel.isEmpty()) {
                            showBookPopup(bookModel);
                        }
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
                        if (bookModel != null && !bookModel.isEmpty()) {
                            showBookPopup(bookModel);
                        }
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
                String genre = et_genre.getText().toString();
                String description = et_description.getText().toString();
                String cover_url = et_cover_url.getText().toString();
                int year = Integer.parseInt(et_year.getText().toString());
                bookDataService.addBook(title, author, year, genre, description, cover_url, new BookDataService.AddBookListener() {
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
                String genre = et_genre.getText().toString();
                String description = et_description.getText().toString();
                String cover_url = et_cover_url.getText().toString();
                int year = Integer.parseInt(et_year.getText().toString()); // Remember to handle NumberFormatException

                bookDataService.updateBook(bookId, title, author, year, genre, description, cover_url, new BookDataService.UpdateBookListener() {
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