package com.example.test_weather;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookDataService {


//    r.POST("/book", api.CreateBook)
//            r.GET("/books", api.GetBooks)
//            r.GET("/book/:id", api.GetBook)
//            r.PUT("/book/:id", api.UpdateBook)
//            r.DELETE("/book/:id", api.DeleteBook)

    public static final String URL_POST = "https://api.projectidek.dev/book";
    public static final String URL_BOOKS = "https://api.projectidek.dev/books";
    public static final String URL_ID = "https://api.projectidek.dev/book/";
    private final Context context;


    public BookDataService(Context context) {
        this.context = context;

    }

    public interface VolleyResponsListener {
        void onError(String message);

        void onRespone(String bookTitle);
    }

    public void getBookTitle(String bookID, VolleyResponsListener volleyResponsListener) {
        String url = URL_ID + bookID;

// Formulate the request and handle the response.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject book_data = null;
                try {
                    book_data = response.getJSONObject("data");
                    String title = book_data.getString("title");
                    Toast.makeText(context, "title: " + title, Toast.LENGTH_SHORT).show();
                    volleyResponsListener.onRespone(title);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                volleyResponsListener.onError("Somthing wrong");
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }
//    public List<BookModel> getBooks()
//


    public interface BooksRespons {
        void onError(String message);

        void onRespone(List<BookModel> bookModel);
    }
    public void getBooks(BooksRespons booksRespons) {
        List<BookModel> BookList = new ArrayList<>();
        String url = URL_BOOKS;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Toast.makeText(context, "Test_22_good", Toast.LENGTH_SHORT).show();
                try {
                    JSONArray data = response.getJSONArray("data");

                    for(int i = 0; i < data.length(); i++) {


                        BookModel one_book = new BookModel();
                        JSONObject Book_i = (JSONObject) data.get(i);
                        one_book.setId(Book_i.getInt("id"));
                        one_book.setTitle(Book_i.getString("title"));
                        one_book.setAuthor(Book_i.getString("author"));
                        one_book.setYear(Book_i.getInt("year"));
                        BookList.add(one_book);


                    }
                    booksRespons.onRespone(BookList);



                } catch (JSONException e) {
                    Toast.makeText(context, "Test_22_Error", Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getBookByTitle(String bookID){
        List<BookModel> BookList = new ArrayList<>();
        String url = URL_BOOKS;

        //get json obj
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //get propperty "data"

        //get each item in the array and assign it to a new BookModel obj

        //get json obj inside the array
        MySingleton.getInstance(context).addToRequestQueue(request);

    }


}
