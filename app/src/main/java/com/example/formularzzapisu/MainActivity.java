package com.example.formularzzapisu;

import android.widget.ArrayAdapter;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText etName, etEmail;
    Button btnBirthDate, btnSubmit;
    Spinner spCourse;
    RadioGroup rgMode;
    SeekBar sbLevel;
    TextView tvLevelValue, tvSummary;
    MaterialSwitch swNewsletter;
    CheckBox cbTerms;

    String birthDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        btnBirthDate = findViewById(R.id.btnBirthDate);
        btnSubmit = findViewById(R.id.btnSubmit);
        spCourse = findViewById(R.id.spCourse);
        rgMode = findViewById(R.id.rgMode);
        sbLevel = findViewById(R.id.sbLevel);
        tvLevelValue = findViewById(R.id.tvLevelValue);
        tvSummary = findViewById(R.id.tvSummary);
        swNewsletter = findViewById(R.id.swNewsletter);
        cbTerms = findViewById(R.id.cbTerms);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.courses,
                        android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spCourse.setAdapter(adapter);


        btnBirthDate.setOnClickListener(v -> showDatePicker());


        btnSubmit.setOnClickListener(v -> {
            if (validateForm()) {
                showSummary();
            }
        });

        btnSubmit.setEnabled(false);

        cbTerms.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    btnSubmit.setEnabled(isChecked);
                });



        sbLevel.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(
                            SeekBar seekBar, int progress, boolean fromUser) {
                        tvLevelValue.setText(String.valueOf(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, day) -> {
                    birthDate = String.format(
                            Locale.US, "%04d-%02d-%02d",
                            year, month + 1, day);

                    btnBirthDate.setText(birthDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.show();
    }

    private void showSummary() {

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        String mode = getString(R.string.mode_none);

        if (rgMode.getCheckedRadioButtonId() == R.id.rbOnsite) {
            mode = getString(R.string.mode_onsite);
        }

        if (rgMode.getCheckedRadioButtonId() == R.id.rbRemote) {
            mode = getString(R.string.mode_remote);
        }

        String newsletter = getString(R.string.no_short);

        if (swNewsletter.isChecked()) {
            newsletter = getString(R.string.yes_short);
        }

        String course = getString(R.string.course_none);

        if (spCourse.getSelectedItem() != null) {
            course = spCourse.getSelectedItem().toString();
        }

        String summary = getString(
                R.string.summary_format,
                name,
                email,
                birthDate,
                course,
                mode,
                sbLevel.getProgress(),
                newsletter);

        tvSummary.setText(summary);
    }

    private boolean validateForm() {

        String name = etName.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError(getString(R.string.error_name));
            etName.requestFocus();
            return false;
        }

        String email = etEmail.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.error_email));
            etEmail.requestFocus();
            return false;
        }

        if (birthDate.isEmpty()) {
            Toast.makeText(this, R.string.error_date,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (spCourse.getSelectedItemPosition() == 0) {
            Toast.makeText(this, R.string.error_course,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (rgMode.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, R.string.error_mode,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!cbTerms.isChecked()) {
            Toast.makeText(this, R.string.error_terms,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
