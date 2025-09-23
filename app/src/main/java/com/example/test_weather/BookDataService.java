package com.example.test_weather;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

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

        void onRespone(List<BookModel> bookModel);
    }

    public void getBookByID(String bookID, VolleyResponsListener volleyResponsListener) {
        List<BookModel> BookList = new ArrayList<>();
        String url_id = URL_ID + bookID;

// Formulate the request and handle the response.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url_id, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject book_data = null;
                try {
                    book_data = response.getJSONObject("data");

                        BookModel one_book = new BookModel();
                        one_book.setId(book_data.getInt("id"));
                        one_book.setTitle(book_data.getString("title"));
                        one_book.setAuthor(book_data.getString("author"));
                        one_book.setYear(book_data.getInt("year"));
                        BookList.add(one_book);

                    volleyResponsListener.onRespone(BookList);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
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

    public void getBookByTitle(String title, final BooksRespons booksRespons) {
        // 1. Call getBooks to get all available books.
        getBooks(new BooksRespons() {
            @Override
            public void onError(String message) {
                booksRespons.onError(message);
            }

            @Override
            public void onRespone(List<BookModel> bookList) {
                String foundBookId = null;

                // 2. Iterate through the list to find the book with the matching title.
                for (BookModel book : bookList) {
                    if (book.getTitle().equals(title)) {
                        foundBookId = String.valueOf(book.getId());
                        break;
                    }
                }

                // If a book with the title is found, proceed to get its details by ID.
                if (foundBookId != null) {
                    // 3. Call getBookByID to fetch the specific book.
                    getBookByID(foundBookId, new VolleyResponsListener() {
                        @Override
                        public void onError(String message) {
                            booksRespons.onError(message);
                        }

                        @Override
                        public void onRespone(List<BookModel> singleBookList) {
                            // Pass the single book back to the original caller.
                            booksRespons.onRespone(singleBookList);
                        }
                    });
                } else {
                    // If no book with the title is found, return an empty list or an error.
                    booksRespons.onRespone(new ArrayList<>());
                }
            }
        });
    }

    public interface DeletionListener {
        void onComplete();
        void onError(String message);
    }

    public void deleteBookById(String bookId, final DeletionListener listener) {
        // 1. Construct the specific URL for the book ID.
        String url = URL_ID + bookId;

        // 2. Create a StringRequest for the DELETE method.
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 3. On success, show a Toast and notify the listener.
                        Toast.makeText(context, "Book deleted successfully!", Toast.LENGTH_SHORT).show();
                        if (listener != null) {
                            listener.onComplete();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 4. On error, show a Toast and notify the listener.
                        Toast.makeText(context, "Error: Failed to delete book.", Toast.LENGTH_SHORT).show();
                        if (listener != null) {
                            listener.onError(error.toString());
                        }
                    }
                });

        // 5. Add the request to the queue to execute it.
        MySingleton.getInstance(context).addToRequestQueue(deleteRequest);
    }

    public interface AddBookListener {
        void onComplete();
        void onError(String message);
    }

    public void addBook(String title, String author, int year, final AddBookListener listener) {
        // Create a JSON object with the book data to be sent in the request body.
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("title", title);
            requestBody.put("author", author);
            requestBody.put("year", year);
        } catch (JSONException e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onError("Error creating request body.");
            }
            return;
        }

        // Create a JsonObjectRequest for the POST method.
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL_POST, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // On a successful response, notify the listener.
                        Toast.makeText(context, "Book added successfully!", Toast.LENGTH_SHORT).show();
                        if (listener != null) {
                            listener.onComplete();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // On an error response, notify the listener.
                        Toast.makeText(context, "Error: Failed to add book.", Toast.LENGTH_SHORT).show();
                        if (listener != null) {
                            listener.onError(error.toString());
                        }
                    }
                });

        // Add the request to the Volley request queue to execute it.
        MySingleton.getInstance(context).addToRequestQueue(postRequest);
    }

    public interface UpdateBookListener {
        void onComplete();
        void onError(String message);
    }

    public void updateBook(String bookId, String title, String author, int year, final UpdateBookListener listener) {
        // 1. Construct the specific URL for the book ID.
        String url = URL_ID + bookId;

        // 2. Create a JSON object with the updated book data.
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("title", title);
            requestBody.put("author", author);
            requestBody.put("year", year);
        } catch (JSONException e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onError("Error creating request body.");
            }
            return;
        }

        // 3. Create a JsonObjectRequest for the PUT method.
        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // 4. On success, show a Toast and notify the listener.
                        Toast.makeText(context, "Book updated successfully!", Toast.LENGTH_SHORT).show();
                        if (listener != null) {
                            listener.onComplete();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 5. On error, show a Toast and notify the listener.
                        Toast.makeText(context, "Error: Failed to update book.", Toast.LENGTH_SHORT).show();
                        if (listener != null) {
                            listener.onError(error.toString());
                        }
                    }
                });

        // 6. Add the request to the queue to execute it.
        MySingleton.getInstance(context).addToRequestQueue(putRequest);
    }





}
