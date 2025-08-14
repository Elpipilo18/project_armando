package com.example.mobileApp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileApp.HumidityRecords;
import com.example.mobileApp.R;
import com.example.mobileApp.TempRecords;
import com.example.mobileApp.model.WeatherRecord;

import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;

public class WeatherRecordAdapter extends RecyclerView.Adapter<WeatherRecordAdapter.WeatherRecordHolder> {

    private List<WeatherRecord> weatherRecords;
    private Context context;

    public WeatherRecordAdapter(List<WeatherRecord> weatherRecords, Context context) {
        this.weatherRecords = weatherRecords;
        this.context = context;
    }

    @NonNull
    @Override
    public WeatherRecordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.record_ui, parent, false);
        return new WeatherRecordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherRecordHolder holder, int position) {
        WeatherRecord weatherRecord = weatherRecords.get(position);
        holder._value.setText(weatherRecord.getValue());
        holder._date.setText(weatherRecord.getDateLabel());
        holder._time.setText(weatherRecord.getTime());
        holder.delete_record.setOnClickListener(v -> {
            if (context instanceof TempRecords) ((TempRecords) context).deleteRecord(weatherRecord.getId());
            else if (context instanceof HumidityRecords) ((HumidityRecords) context).deleteRecord(weatherRecord.getId());
        });
    }

    @Override
    public int getItemCount() {
        return weatherRecords.size();
    }

    public class WeatherRecordHolder extends RecyclerView.ViewHolder {

        private TextView _value, _date, _time;
        private Button delete_record;

        public WeatherRecordHolder(@NonNull View itemView) {
            super(itemView);
            _value = itemView.findViewById(R.id._value);
            _date = itemView.findViewById(R.id._date);
            _time = itemView.findViewById(R.id._time);
            delete_record = itemView.findViewById(R.id.btn_delete);
        }
    }
}
