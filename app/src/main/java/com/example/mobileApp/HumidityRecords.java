package com.example.mobileApp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileApp.adapters.WeatherRecordAdapter;
import com.example.mobileApp.model.LectureType;
import com.example.mobileApp.model.WeatherRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HumidityRecords extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Context context;
    private RequestQueue requestQueue;
    private List<WeatherRecord> weatherRecords;
    private WeatherRecordAdapter weatherRecordAdapter;

    private int offset = 0;
    private final int pageSize = 10;
    private boolean isLoading = false;
    private boolean hasMore = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.humidity_records);

        initComponents();
        setupRecyclerView();
        loadInitialData();
    }

    private void initComponents() {
        recyclerView = findViewById(R.id.humidity_rv);
        context = this;
        requestQueue = Volley.newRequestQueue(context);
        weatherRecords = new ArrayList<>();
    }

    private void setupRecyclerView() {
        weatherRecordAdapter = new WeatherRecordAdapter(weatherRecords, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(weatherRecordAdapter);
        addScrollListener();
    }

    private void loadInitialData() {
        requestData(0, pageSize);
    }

    private void requestData(int offset, int pageSize) {
        if (isLoading || !hasMore) return;

        isLoading = true;
        showProgressBar();
        this.offset = offset;

        String url = String.format("%sweatherRecord?type=2&offset=%d&max=%d",
                Constants.API_URL, offset, pageSize);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                this::handleResponse,
                this::handleError);

        requestQueue.add(request);


    }

    private void handleResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("weatherRecords");
            processResponseData(jsonArray);
        } catch (JSONException e) {
            Log.e("TempRecords", "JSON Error", e);
            showToast("Error en formato de datos");
        } finally {
            isLoading = false;
            hideProgressBar();
        }
    }

    private void processResponseData(JSONArray data) throws JSONException {
        int initialSize = weatherRecords.size();

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonObject = data.getJSONObject(i);
            WeatherRecord record = new WeatherRecord();
            record.setId(jsonObject.getInt("id"));
            record.setType(LectureType.get(jsonObject.getInt("type")));
            record.setValue(jsonObject.getString("value"));
            record.setDateLabel(jsonObject.getString("dateLabel"));
            record.setTime(jsonObject.getString("time"));
            weatherRecords.add(record);
        }

        if (data.length() < pageSize) {
            hasMore = false;
        }

        updateAdapter(initialSize, data.length());
    }

    private void updateAdapter(int initialSize, int newItemsCount) {
        if (initialSize == 0) {
            weatherRecordAdapter.notifyDataSetChanged();
        } else {
            weatherRecordAdapter.notifyItemRangeInserted(initialSize, newItemsCount);
        }
    }

    private void handleError(VolleyError error) {
        Log.e("TempRecords", "API Error", error);
        showToast("Error de conexión");
        isLoading = false;
        hideProgressBar();
    }

    private void addScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy <= 0 || isLoading || !hasMore) return;

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                    requestData(offset + pageSize, pageSize);
                    Log.e("TempRecords", "Cargando más datos desde el offset: " + (offset + pageSize));
                }
            }
        });
    }

    public void deleteRecord(int id) {
        String url = Constants.API_URL + "weatherRecord/" + id;

        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                response -> {
                    showToast("Registro eliminado");
                    refreshData();
                },
                error -> showToast("Error al eliminar"));

        requestQueue.add(request);
    }

    private void refreshData() {
        weatherRecords.clear();
        offset = 0;
        hasMore = true;
        requestData(0, pageSize);
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private void showProgressBar() {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }
}