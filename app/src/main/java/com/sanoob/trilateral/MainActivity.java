package com.sanoob.trilateral;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sanoob.trilateral.operation.AreaCalculator;
import com.sanoob.trilateral.operation.TriangleSides;
import com.sanoob.trilateral.operation.TrianglesModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.sanoob.trilateral.util.TrilateralUtil.isStringEmpty;
import static com.sanoob.trilateral.util.TrilateralUtil.cleanDoubleAndConvertToString;

public class MainActivity extends AppCompatActivity {

    EditText areaOutput;
    EditText sideAText;
    EditText sideBText;
    EditText sideCText;
    EditText totalAreaText;
    EditText totalAreaCentText;
    FloatingActionButton executeFab;
    Button clearButton;
    Button historyButton;

    List<TrianglesModel> triangles;
    int REQUEST_CODE = 2;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException ignored) {

        }
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG);
                getWindow().setStatusBarColor(Color.BLACK);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                break;
        }

        setContentView(R.layout.activity_main);
        initialize();

    }

    private void initialize() {
        triangles = new ArrayList<>();
        areaOutput = findViewById(R.id.areaOutputEditText);
        sideAText = findViewById(R.id.sideAeditText);
        sideBText = findViewById(R.id.sideBeditText);
        sideCText = findViewById(R.id.sideCeditText);
        executeFab = findViewById(R.id.floatingActionButton);
        totalAreaText = findViewById(R.id.totalAreaEditText);
        totalAreaCentText = findViewById(R.id.totalCentsEditText);
        clearButton = findViewById(R.id.clearButton);
        historyButton = findViewById(R.id.historyButton);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData(true);
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!triangles.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, History.class);
                    intent.putExtra("TRIANGLES", (Serializable) triangles);
                    startActivityForResult(intent, REQUEST_CODE);
                }else {
                    Toast.makeText(MainActivity.this, "List is empty",Toast.LENGTH_SHORT).show();
                }
            }
        });

        executeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndCalculate();
            }
        });
        sideCText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //validateAndCalculate();
                    return true;
                }
                return false;
            }
        });
    }

    private void validateAndCalculate() {


        if (!(isStringEmpty(sideAText.getText().toString())) &&
                !(isStringEmpty(sideBText.getText().toString())) &&
                !(isStringEmpty(sideCText.getText().toString()))) {
            TriangleSides sides = new TriangleSides(Double.parseDouble(sideAText.getText().toString()),
                    Double.parseDouble(sideBText.getText().toString()),
                    Double.parseDouble(sideCText.getText().toString()));
            double area = AreaCalculator.calculate(sides);
            if(Double.isNaN(area)){
                Toast.makeText(MainActivity.this, "Not a valid triangle. Please verify the input values", Toast.LENGTH_LONG).show();
                areaOutput.getText().clear();
                return;
            }
            TrianglesModel triangle = new TrianglesModel();
            triangle.setArea(area);
            triangle.setSides(sides);
            triangles.add(triangle);
            areaOutput.setText(cleanDoubleAndConvertToString(area));
            double totalArea = AreaCalculator.totalArea(triangles);
            totalAreaText.setText(cleanDoubleAndConvertToString(totalArea));
            totalAreaCentText.setText(cleanDoubleAndConvertToString(AreaCalculator.totalAreaInCents(totalArea)));

        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "All three sides are required", Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (2): {
                if (resultCode == Activity.RESULT_OK) {
                    triangles = (ArrayList<TrianglesModel>) data.getSerializableExtra("TRIANGLES");
                    double totalArea = AreaCalculator.totalArea(triangles);
                    clearData(false);
                    totalAreaText.setText(cleanDoubleAndConvertToString(totalArea));
                    totalAreaCentText.setText(cleanDoubleAndConvertToString( AreaCalculator.totalAreaInCents(totalArea)));
                }
                break;
            }
        }
    }



    private void clearData(boolean clearData) {
        if(clearData)
            triangles.clear();
        sideAText.getText().clear();
        sideBText.getText().clear();
        sideCText.getText().clear();
        areaOutput.getText().clear();
        totalAreaCentText.getText().clear();
        totalAreaText.getText().clear();
    }


}