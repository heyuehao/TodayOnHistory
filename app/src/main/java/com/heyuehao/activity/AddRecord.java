package com.heyuehao.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import cn.leancloud.AVUser;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.heyuehao.common.LeanCloud.QueryRecord;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.heyuehao.R;
import com.heyuehao.common.Utils.Thing;

import java.util.Calendar;

/**
 * 添加记录
 */
public class AddRecord extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private MaterialButton submitBtn;
    private EditText things;
    private TextView dateView;
    private FragmentManager fragmentManager;
    private DatePickerDialog datePickerDialog ;
    private int year, month, day;
    private String dateStr;
    private AlertDialog builder;
    Thing thing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        dateView = findViewById(R.id.dateView);
        // 设置时间
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);
        dateStr = year + "年" + (month + 1) + "月" + day + "日";
        dateView.setText(dateStr);

        // 点击弹出日期选择
        // Datepicker(dateView);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Datepicker(dateView);
            }
        });

        // 点击返回
        findViewById(R.id.backImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 自动弹起软键盘
        things = findViewById(R.id.things);
        things.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getObj();
            }
        });

    }

    private int choice = -1;
    public void getObj () {
        thing = new Thing();
        if(things.getText().toString().equals("")) {
            builder = new AlertDialog.Builder(this)
                    .setMessage("内容不能为空")
                    .setPositiveButton("确定", null)
                    .create();
            builder.show();
        } else {
            final String[] items = {"是", "否"};
            builder = new AlertDialog.Builder(this)
                    .setTitle("是否在同月同日推送")
                    .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            choice = which;
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            thing.setPush(choice <= 0 ? true : false);
                            // 判断当天是否已有记录
                            QueryRecord query = new QueryRecord(AddRecord.this);
                            query.EqualTo(thing, AddRecord.this);
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create();
            builder.show();
        }

        thing.setDate(dateStr);
        thing.setContent(things.getText().toString());
        thing.setUser(AVUser.getCurrentUser());
    }

    // 设置DatePicker弹出
    public void Datepicker(View v) {
        datePickerDialog = DatePickerDialog.newInstance(this, year, month, day);
        datePickerDialog.setThemeDark(false);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setAccentColor("#6200EE");
        datePickerDialog.vibrate(false);
        datePickerDialog.setOkText("确定");
        datePickerDialog.setCancelText("取消");
        fragmentManager = getSupportFragmentManager();
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                // Toast.makeText(MainActivity.this, "Datepicker Canceled", Toast.LENGTH_SHORT).show();
            }
        });
        datePickerDialog.show(fragmentManager, "DatePickerDialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dateStr = year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日";
        dateView.setText(dateStr);
    }
}