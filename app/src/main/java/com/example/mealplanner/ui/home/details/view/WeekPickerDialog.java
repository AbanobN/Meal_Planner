package com.example.mealplanner.ui.home.details.view;

import android.app.Dialog;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mealplanner.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WeekPickerDialog extends Dialog {

    private Calendar calendar;
    private TextView tvWeekRange;
    private LinearLayout llDaysContainer;
    private SimpleDateFormat dayFormatFull = new SimpleDateFormat("EEEE", Locale.getDefault()); // Full day names
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public interface OnDateSelectedListener {
        void onDateSelected(String selectedDay);
    }

    private OnDateSelectedListener listener;
    private Calendar startOfWeek;
    private Calendar endOfWeek;

    public WeekPickerDialog(@NonNull Context context, OnDateSelectedListener listener, Calendar startOfWeek, Calendar endOfWeek) {
        super(context);
        setContentView(R.layout.week_picker_dialog);

        this.listener = listener; // Store the listener
        this.startOfWeek = startOfWeek;
        this.endOfWeek = endOfWeek;

        calendar = Calendar.getInstance();
        tvWeekRange = findViewById(R.id.tvWeekRange);
        llDaysContainer = findViewById(R.id.llDaysContainer);

        updateWeek();
    }

    private void updateWeek() {
        calendar.setTime(startOfWeek.getTime());

        Calendar tempCalendar = (Calendar) calendar.clone();
        tempCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        StringBuilder weekRangeText = new StringBuilder();

        llDaysContainer.removeAllViews();

        for (int i = 0; i < 7; i++) {
            final String fullDayName = dayFormatFull.format(tempCalendar.getTime());

            TextView dayTextView = new TextView(getContext());
            dayTextView.setText(fullDayName);
            dayTextView.setTag(fullDayName);
            dayTextView.setPadding(16, 16, 16, 16);
            dayTextView.setTextSize(18);
            dayTextView.setOnClickListener(v -> {
                for (int j = 0; j < llDaysContainer.getChildCount(); j++) {
                    llDaysContainer.getChildAt(j).setSelected(false);
                }
                v.setSelected(true);

                String selectedDay = (String) v.getTag();
                if (listener != null) {
                    listener.onDateSelected(selectedDay);
                }
                dismiss();
            });

            llDaysContainer.addView(dayTextView);

            if (i == 0) {
                weekRangeText.append(dateFormat.format(tempCalendar.getTime()));
            } else if (i == 6) {
                weekRangeText.append(" ~ ").append(dateFormat.format(tempCalendar.getTime()));
            }

            tempCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        tvWeekRange.setText(weekRangeText.toString());
    }
}
