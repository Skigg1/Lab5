package com.example.lab5;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.widget.Toast;

public class NotificationsFragment extends Fragment  {

    // Добавьте эти объявления переменных
    private Button buttonNotify;
    private static final String CHANNEL_ID = "student_channel"; // Добавьте эту константу
    private static final int NOTIFICATION_PERMISSION_CODE = 1003;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Убедитесь, что используете правильное имя layout файла
        View view = inflater.inflate(R.layout.activity_notifications_fragment, container, false); // Измените на fragment_notifications

        buttonNotify = view.findViewById(R.id.buttonNotify);
        createNotificationChannel();

        buttonNotify.setOnClickListener(v -> sendStudentNotification());

        return view;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Student Channel";
            String description = "Channel for student notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendStudentNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(),
                    android.Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_CODE);
                return;
            }
        }

        try {
            String studentName = "Соловьев Александр";
            String groupNumber = "Группа: ИСиТ -333902у";

            NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Информация о студенте")
                    .setContentText(studentName + " | " + groupNumber)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.notify(3, builder.build());

            Toast.makeText(requireContext(), "Уведомление отправлено!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(requireContext(), "Ошибка отправки уведомления", Toast.LENGTH_SHORT).show();
        }
    }

    // Добавьте обработчик результата запроса разрешений
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendStudentNotification();
            } else {
                Toast.makeText(requireContext(), "Разрешение на уведомления не предоставлено", Toast.LENGTH_LONG).show();
            }
        }
    }
}