package com.example.drawnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class DrawActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivColorPicker;
    private RadioGroup rgPenSize;
    private ImageView ivSave;
    private DrawingView drawingView;

    private int currentColor;
    private int currentPenSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        ivColorPicker = findViewById(R.id.iv_color_picker);
        rgPenSize = findViewById(R.id.rg_pen_size);
        ivSave = findViewById(R.id.iv_save);

        drawingView = findViewById(R.id.drawing_view);

        ivColorPicker.setOnClickListener(this);
        ivSave.setOnClickListener(this);

        currentColor = getResources().getColor(R.color.colorAccent);
        ivColorPicker.setColorFilter(currentColor);

        currentPenSize = 10;
        rgPenSize.check(R.id.rg_pen_size_normal);

        rgPenSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rg_pen_size_thin:
                        currentPenSize = 5;
                        break;
                    case R.id.rg_pen_size_normal:
                        currentPenSize = 10;
                        break;
                    case R.id.rg_pen_size_strong:
                        currentPenSize = 15;
                        break;
                }
                drawingView.setCurrentSize(currentPenSize);
                Toast.makeText(DrawActivity.this,
                        "pen size: "+currentPenSize,
                        Toast.LENGTH_SHORT).show();
            }
        });
        drawingView.setCurrentColor(currentColor);
        drawingView.setCurrentSize(currentPenSize);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_color_picker:
                showColorPickerDialog();
                break;
            case R.id.iv_save:

                break;
        }
    }

    private void showColorPickerDialog() {
        ColorPickerDialogBuilder
                .with(DrawActivity.this)
                .setTitle("Choose color")
                .initialColor(currentColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        Toast.makeText(DrawActivity.this, "onColorSelected", Toast.LENGTH_SHORT).show();

                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        currentColor = selectedColor;
                        drawingView.setCurrentColor(currentColor);
                        ivColorPicker.setColorFilter(currentColor);

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }
}
