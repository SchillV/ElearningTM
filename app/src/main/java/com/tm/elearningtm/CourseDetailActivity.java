package com.tm.elearningtm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.tm.elearningtm.data.AppData;
import com.tm.elearningtm.classes.Curs;

public class CourseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentcourse);

        int courseId = getIntent().getIntExtra("COURSE_ID", -1);
        Curs curs = AppData.getCatalog().getCursById(courseId);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView title = findViewById(R.id.text_course_title);
        title.setText(curs != null ? curs.getTitlu() : "Curs inexistent");
    }
}
