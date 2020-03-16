package com.example.juhenews;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListFragment extends Fragment {

    private View view;
    private ListView newsList;
    private List<NewsBean.ResultBean.DataBean> newsData;
    private String title;
    // 适配器
    class NewsAdapter extends BaseAdapter{
        private List<NewsBean.ResultBean.DataBean> data;    // 数据
        private View view;          // 列表项视图
        public NewsAdapter(List<NewsBean.ResultBean.DataBean> data) {
            this.data = data;
            this.view = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, null);
        }

        @Override
        public int getCount() {
            return this.data.size();
        }

        @Override
        public Object getItem(int position) {
            return this.data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView newsTitle, newsSrc, newsTime;
            ImageView pic1, pic2, pic3;

            newsTitle = this.view.findViewById(R.id.newsTitle);
            newsSrc = this.view.findViewById(R.id.newsSrc);
            newsTime = this.view.findViewById(R.id.newsTime);
            pic1 = this.view.findViewById(R.id.newsImage1);
            pic2 = this.view.findViewById(R.id.newsImage2);
            pic3 = this.view.findViewById(R.id.newsImage3);
            newsTitle.setText(this.data.get(position).getTitle());
            newsSrc.setText(this.data.get(position).getAuthor_name());
            newsTime.setText(this.data.get(position).getDate());
            if (this.data.get(position).getThumbnail_pic_s()!=null){
                // 如果pic1的url存在，则加载图片到相应的位置
                Picasso.get().load(this.data.get(position).getThumbnail_pic_s()).into(pic1);
            }
            if (this.data.get(position).getThumbnail_pic_s02()!=null){
                // 如果pic2的url存在，则加载图片到相应的位置
                Picasso.get().load(this.data.get(position).getThumbnail_pic_s02()).into(pic2);
            }
            if (this.data.get(position).getThumbnail_pic_s03()!=null){
                // 如果pic3的url存在，则加载图片到相应的位置
                Picasso.get().load(this.data.get(position).getThumbnail_pic_s03()).into(pic3);
            }
            return this.view;
        }
    }
    private NewsAdapter newsAdapter;


    public NewsListFragment(String title) {
        // Required empty public constructor
//        this.newsData = new ArrayList<>();
        this.title = title;
        this.getNews();
        this.newsList = this.view.findViewById(R.id.newsList);
    }

    private void getNews() {
        String url = "http://v.juhe.cn/toutiao/index?type=" + this.title + "&key=27835d81acd3f1eef28ef3622ae9f071";
        RequestParams requestParams = new RequestParams(url);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                // 先从result中解析出数据
                NewsBean newsBean = new Gson().fromJson(result, NewsBean.class);
                NewsBean.ResultBean resultBean = newsBean.getResult();
                NewsListFragment.this.newsData = resultBean.getData();


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_news_list, container, false);
        return this.view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 将数据传入适配器
        this.newsAdapter = new NewsAdapter(this.newsData);
        // 给列表设置适配器
        this.newsList.setAdapter(this.newsAdapter);
    }
}
