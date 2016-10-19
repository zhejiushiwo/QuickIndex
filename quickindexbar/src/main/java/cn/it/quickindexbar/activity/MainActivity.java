package cn.it.quickindexbar.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import cn.it.quickindexbar.R;
import cn.it.quickindexbar.adapter.MyAdapter;
import cn.it.quickindexbar.bean.GoodMan;
import cn.it.quickindexbar.utils.Cheeses;
import cn.it.quickindexbar.view.QuickIndex;

/**  */
public class MainActivity extends Activity {

    private QuickIndex quick_index;
    private TextView tv_text;

    private Handler handler = new Handler();
    private ListView listView;
    private ArrayList<GoodMan> goodMenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quick_index = (QuickIndex) findViewById(R.id.quick_index);
        tv_text = (TextView) findViewById(R.id.tv_text);
        listView = (ListView) findViewById(R.id.list_view);
        //给Adapter准备数据
        goodMenList = new ArrayList<>();
        for (String name : Cheeses.NAMES) {
            goodMenList.add(new GoodMan(name));
        }
        //进行排序
        Collections.sort(goodMenList);
        listView.setAdapter(new MyAdapter(goodMenList));
        //对右边字母控件设置滑动监听器
        quick_index.setOnSelectTextListener(onSelectTextListener);
    }

    QuickIndex.OnSelectTextListener onSelectTextListener = new QuickIndex.OnSelectTextListener() {
        @Override
        public void selectTextListener(String text) {
            tv_text.setVisibility(View.VISIBLE);
            tv_text.setText(text);
            handler.postDelayed(runnable,2000);
            //遍历获取的数据集合
            for (int i = 0; i < goodMenList.size(); i++) {
                GoodMan goodMan = goodMenList.get(i);
                //获取当前条目展示的英文字母
                String firstText = goodMan.getPinyin().charAt(0)+"";
                //判断当前展示的英文字母是否和手指触摸的字母一样，
                if (TextUtils.equals(text,firstText)){
                    // 如果一样定位到该字母,并结束该循环
                    listView.setSelection(i);
                    break;
                }
            }
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tv_text.setVisibility(View.INVISIBLE);
        }
    };

}
