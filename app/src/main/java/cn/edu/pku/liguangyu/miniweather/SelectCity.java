package cn.edu.pku.liguangyu.miniweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.pku.liguangyu.app.MyApplication;
import cn.edu.pku.liguangyu.bean.City;

import static cn.edu.pku.liguangyu.miniweather.R.id.title_back;

public class SelectCity extends Activity implements View.OnClickListener{
    private ImageView mBackBtn;
    private TextView mTestBtn;
    private ListView listView ;
    private TextView cityselected ;
    private List<City> listcity = MyApplication.getInstance().getCityList();
    private int listSize = listcity.size();
    private String[] city = new String[listSize];
    private ArrayList<String> mSearchResult = new ArrayList<>(); //搜索结果
    private Map<String,String> nameToCode = new HashMap<>();//城市名到编码
    private Map<String,String> nameToPinyin = new HashMap<>(); //城市名到拼音
    private EditText mSearch;
    String returnCode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);
// onclick 方法要在这里写绑定监听事件
        mBackBtn = (ImageView) findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);
        mTestBtn = (TextView) findViewById(R.id.title_name);
        mTestBtn.setOnClickListener(this);
// 实现 ListView 对数组的展示
// listView = new ListView(this);
// listView.setAdapter(newArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,getData()));
// setContentView(listView);
        cityselected = (TextView) findViewById(R.id.title_name);
        Log.i("City", listcity.get(1).getCity());
        for (int i = 0; i < listSize; i++) {
            city[i] = listcity.get(i).getCity();
            Log.d("City", city[i]);
        }
//建立映射
        String strName;
        String strNamePinyin;
        String strCode;
        for(City city1:listcity){
            strCode = city1.getNumber();
            strName = city1.getCity();
            strNamePinyin = city1.getFirstPY();
            nameToCode.put(strName,strCode);
            nameToPinyin.put(strName,strNamePinyin);
            mSearchResult.add(strName);
        }
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, mSearchResult);
        listView = findViewById(R.id.list_view);
        listView.setAdapter(arrayAdapter); //设置适配器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String returnCityName = mSearchResult.get(i);
                Toast.makeText(SelectCity.this, "你已选择：" + returnCityName, Toast.LENGTH_SHORT).show();
                returnCode = nameToCode.get(returnCityName);
                Log.d("returncode",returnCode);
                cityselected.setText("当前城市：" + returnCityName);
                Intent i2 = new Intent();
                i2.putExtra("cityCode",returnCode );
                setResult(RESULT_OK, i2);
                finish();
            }
        });
        mSearch = (EditText)findViewById(R.id.search_edit);
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mSearchResult.clear();
                if (s!=null){
                    for(City city1:listcity){
                        if(city1.getCity().toString().contains(s)){
                            nameToCode.put(city1.getCity(),city1.getNumber());
                            nameToPinyin.put(city1.getCity(),city1.getFirstPY());
                            mSearchResult.add(city1.getCity());
                        }
                    }
                }
                else{
                    for(City city1:listcity){
                        nameToCode.put(city1.getCity(),city1.getNumber());
                        nameToPinyin.put(city1.getCity(),city1.getFirstPY());
                        mSearchResult.add(city1.getCity());
                    }
                }
                arrayAdapter.getFilter().filter(s);


            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_back) {
            //Log.d("myWeather", "我点击了返回界面");
           // int position = listView.getCheckedItemPosition();
           // String select_cityCode = listcity.get(position).getNumber();
            Intent i = new Intent();
            i.putExtra("cityCode","101010100" );
            setResult(RESULT_OK, i);
            finish();
        }
        if (v.getId() == R.id.title_name) {
            Log.d("myWeather", "我是北京");
        }
    }
}

