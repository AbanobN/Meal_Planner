package com.example.mealplanner.ui.home.details.view;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mealplanner.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WeekPickerDialog extends Dialog {

    private Calendar calendar;
    private TextView tvWeekRange;
    private Button[] dayButtons;
    private SimpleDateFormat dayFormatAbbr = new SimpleDateFormat("EEE", Locale.getDefault()); // Use "EEE" for abbreviated day names
    private SimpleDateFormat dayFormatFull = new SimpleDateFormat("EEEE", Locale.getDefault()); // Use "EEEE" for full day names
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public interface OnDateSelectedListener {
        void onDateSelected(String selectedDay);
    }

    public WeekPickerDialog(@NonNull Context context, OnDateSelectedListener listener) {
        super(context);
        setContentView(R.layout.week_picker_dialog);

        calendar = Calendar.getInstance();
        tvWeekRange = findViewById(R.id.tvWeekRange);

        dayButtons = new Button[]{
                findViewById(R.id.btnDay1),
                findViewById(R.id.btnDay2),
                findViewById(R.id.btnDay3),
                findViewById(R.id.btnDay4),
                findViewById(R.id.btnDay5),
                findViewById(R.id.btnDay6),
                findViewById(R.id.btnDay7)
        };

        ImageButton btnPrevWeek = findViewById(R.id.btnPrevWeekD);
        ImageButton btnNextWeek = findViewById(R.id.btnNextWeekD);

        btnPrevWeek.setOnClickListener(v -> updateWeek(-1));
        btnNextWeek.setOnClickListener(v -> updateWeek(1));

        updateWeek(0);

        for (Button dayButton : dayButtons) {
            dayButton.setOnClickListener(v -> {
                for (Button btn : dayButtons) {
                    btn.setSelected(false);
                }
                v.setSelected(true);

                String selectedDay = (String) v.getTag(); // Get the full day name from the tag
                listener.onDateSelected(selectedDay);     // Return the full day name
                dismiss();
            });
        }
    }

    private void updateWeek(int weekOffset) {
        calendar.add(Calendar.WEEK_OF_YEAR, weekOffset);

        Calendar tempCalendar = (Calendar) calendar.clone();
        tempCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        StringBuilder weekRangeText = new StringBuilder();

        for (int i = 0; i < 7; i++) {
            final String fullDayName = dayFormatFull.format(tempCalendar.getTime()); // Full day name (e.g., "Monday")
            final String abbrDayName = dayFormatAbbr.format(tempCalendar.getTime()); // Abbreviated day name (e.g., "Mon")
            dayButtons[i].setTag(fullDayName);  // Store the full day name as the tag for each button
            dayButtons[i].setText(abbrDayName); // Set button text to abbreviated day name

            if (i == 0) {
                weekRangeText.append(dateFormat.format(tempCalendar.getTime()));
            } else if (i == 6) {
                weekRangeText.append(" - ").append(dateFormat.format(tempCalendar.getTime()));
            }

            tempCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Update the week range text view
        tvWeekRange.setText(weekRangeText.toString());
    }
}
