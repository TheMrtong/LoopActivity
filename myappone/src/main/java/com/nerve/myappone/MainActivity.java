package com.nerve.myappone;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager women;
    private TextView tv_title;
    private LinearLayout point_ll;
    private int[] pics;
    private String[] title;
    private ArrayList<ImageView> imgList;
    private int lastPosition;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDate();
        initAdapter();
        new Thread() {
            @Override
            public void run() {
                isRunning = true;
                while (isRunning) {

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            women.setCurrentItem(women.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    private void initAdapter() {
        point_ll.getChildAt(0).setEnabled(true);
        tv_title.setText(title[0]);
        lastPosition = 0;
        women.setAdapter(new MyAdapter());
        int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imgList.size());
        women.setCurrentItem(5000000);
    }

    private void initDate() {

        imgList = new ArrayList<>();
        ImageView imageView;
        View pointView;
        for (int i = 0; i < pics.length; i++) {
            imageView = new ImageView(this);
            imageView.setBackgroundResource(pics[i]);
            imgList.add(imageView);
            pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.point_bg);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            if (i != 0) {
                params.leftMargin = 10;
            }
            pointView.setEnabled(false);
            point_ll.addView(pointView, params);
        }
    }

    private void initView() {
        women = (ViewPager) findViewById(R.id.women);
        tv_title = (TextView) findViewById(R.id.tv_title);
        point_ll = (LinearLayout) findViewById(R.id.point_ll);
        pics = new int[]{R.mipmap.womenone, R.mipmap.womentwo, R.mipmap.womenthree, R.mipmap.womenfour, R.mipmap.womenfive};
        title = new String[]{"美女一", "美女二", "美女三", "美女四", "美女五"};
        women.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int newPoint = position % imgList.size();
        tv_title.setText(title[newPoint]);
        point_ll.getChildAt(lastPosition).setEnabled(false);
        point_ll.getChildAt(newPoint).setEnabled(true);
        lastPosition = newPoint;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int newPoints = position % imgList.size();
            ImageView img = imgList.get(newPoints);
            container.addView(img);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
