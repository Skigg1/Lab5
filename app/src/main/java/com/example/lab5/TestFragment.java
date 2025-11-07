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
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.widget.Toast;
import android.widget.RadioGroup;
import android.widget.RadioButton;

public class TestFragment extends Fragment  {
    private Button buttonStartTest;
    private TextView textViewQuestion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // УБЕДИТЕСЬ ЧТО ИМЯ ФАЙЛА ПРАВИЛЬНОЕ!
        View view = inflater.inflate(R.layout.activity_test_fragment, container, false);

        textViewQuestion = view.findViewById(R.id.textViewQuestion);
        buttonStartTest = view.findViewById(R.id.buttonStartTest);

        buttonStartTest.setOnClickListener(v -> showTestDialog());

        return view;
    }

    private void showTestDialog() {
        final String[] options = {"Java", "Python", "C++", "JavaScript"};
        final int[] selectedAnswer = {-1};

        // Создаем кастомный layout для диалога
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View dialogView = inflater.inflate(R.layout.activity_dialog_test, null);

        TextView textViewQuestion = dialogView.findViewById(R.id.dialogQuestion);
        textViewQuestion.setText("Какой язык программирования используется для разработки Android приложений?");

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Тестирование")
                .setView(dialogView)
                .setPositiveButton("Отправить", (dialog, which) -> {
                    if (selectedAnswer[0] != -1) {
                        checkAnswer(selectedAnswer[0]);
                    } else {
                        Toast.makeText(requireContext(), "Выберите вариант ответа!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Отмена", (dialog, which) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();

        // Настраиваем RadioButtons после создания диалога
        RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroup);
        for (int i = 0; i < options.length; i++) {
            RadioButton radioButton = new RadioButton(requireContext());
            radioButton.setText(options[i]);
            radioButton.setTextSize(16);
            radioButton.setPadding(0, 16, 0, 16);

            final int position = i;
            radioButton.setOnClickListener(v -> {
                selectedAnswer[0] = position;
            });

            radioGroup.addView(radioButton);
        }

        dialog.show();
    }

    private void checkAnswer(int selectedPosition) {
        if (selectedPosition == 0) { // Java - правильный ответ
            Toast.makeText(requireContext(), "Правильно! Java используется для Android разработки", Toast.LENGTH_LONG).show();
        } else {
            String[] languages = {"Java", "Python", "C++", "JavaScript"};
            String selectedLanguage = languages[selectedPosition];
            Toast.makeText(requireContext(), "Неправильно! " + selectedLanguage + " не является основным для Android", Toast.LENGTH_LONG).show();
        }
    }
}