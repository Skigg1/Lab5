package com.example.lab5;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;

public class FeedbackFragment extends Fragment  {
    private TextInputEditText editTextName, editTextEmail;
    private Button buttonSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_feedback_fragment, container, false);

        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        // Явно разрешаем все языки ввода
        editTextName.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
        editTextEmail.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        buttonSubmit.setOnClickListener(v -> submitFeedback());

        return view;
    }

    private void submitFeedback() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty()) {
            showAlert("Ошибка", "Заполните все поля!");
            return;
        }

        showAlert("Заявка отправлена",
                "ФИО: " + name + "\nEmail: " + email + "\n\nВаша заявка принята в обработку!");

        editTextName.setText("");
        editTextEmail.setText("");
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}