package com.sanoob.trilateral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.sanoob.trilateral.adapter.Adapter;
import com.sanoob.trilateral.operation.TrianglesModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class History extends AppCompatActivity {

    RecyclerView recyclerView;
    List<TrianglesModel> triangles;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException ignored){

        }
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        triangles = (ArrayList<TrianglesModel>) intent.getSerializableExtra("TRIANGLES");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Adapter adapter = new Adapter(History.this, triangles);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {

        Intent resultIntent = new Intent();
        resultIntent.putExtra("TRIANGLES", (Serializable) triangles);
        setResult(Activity.RESULT_OK, resultIntent);
        super.onBackPressed();
        finish();
    }
}