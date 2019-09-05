package online.masterji.honchiSolution.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import online.masterji.honchiSolution.R;

public class FaqAnswerActivity extends AppCompatActivity {
    LinearLayout que1,que6, que2, que3, que4, que5;
    Intent intent;
    String data, que;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_answer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        que5 = findViewById(R.id.que5);
        que4 = findViewById(R.id.que4);
        que3 = findViewById(R.id.que3);
        que2 = findViewById(R.id.que2);
        que1 = findViewById(R.id.que1);
        que6 = findViewById(R.id.que6);
        intent = getIntent();
        que = intent.getStringExtra("que");
        data = intent.getStringExtra("data");

        if (que.equals("1")) {
            getSupportActionBar().setTitle(data);

            que1.setVisibility(View.VISIBLE);

            que2.setVisibility(View.GONE);
            que3.setVisibility(View.GONE);
            que4.setVisibility(View.GONE);
            que5.setVisibility(View.GONE);

        } else if (que.equals("2")) {
            que2.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle(data);

            que1.setVisibility(View.GONE);
            que3.setVisibility(View.GONE);
            que4.setVisibility(View.GONE);
            que5.setVisibility(View.GONE);

        } else if (que.equals("3")) {
            que3.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle(data);

            que2.setVisibility(View.GONE);
            que1.setVisibility(View.GONE);
            que4.setVisibility(View.GONE);
            que5.setVisibility(View.GONE);
        } else if (que.equals("4")) {
            que4.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle(data);

            que2.setVisibility(View.GONE);
            que3.setVisibility(View.GONE);
            que1.setVisibility(View.GONE);
            que5.setVisibility(View.GONE);
        } else if (que.equals("6")) {
            que6.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle(data);

            que2.setVisibility(View.GONE);
            que3.setVisibility(View.GONE);
            que1.setVisibility(View.GONE);
            que5.setVisibility(View.GONE);
            que4.setVisibility(View.GONE);
        } else {
            que5.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle(data);

            que2.setVisibility(View.GONE);
            que3.setVisibility(View.GONE);
            que4.setVisibility(View.GONE);
            que1.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
