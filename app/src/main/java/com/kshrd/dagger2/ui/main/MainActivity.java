package com.kshrd.dagger2.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kshrd.dagger2.R;
import com.kshrd.dagger2.adapter.ArticleAdapter;
import com.kshrd.dagger2.api.ArticleApi;
import com.kshrd.dagger2.app.MyApplication;
import com.kshrd.dagger2.app.di.qualifier.ApiKey;
import com.kshrd.dagger2.base.BaseActivity;
import com.kshrd.dagger2.data.PreferenceHelper;
import com.kshrd.dagger2.entity.Article;
import com.kshrd.dagger2.ui.main.mvp.MainContract;
import com.kshrd.dagger2.ui.main.mvp.MainPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContract.View {

    @Inject
    String apiUrl;

    @Inject
    @ApiKey
    String apiKey;

    @Inject
    PreferenceHelper appPreferenceHelper;

    @Inject
    ArticleApi articleApi;

    @Inject
    MainPresenter presenter;
    RecyclerView recyclerViewArticle;
    ArticleAdapter articleAdapter;
    private List<Article> articleList;
    private ProgressDialog progressDialog;
    AlertDialog.Builder showInternetConnectionDialog;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Home");
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        setUnbinder(ButterKnife.bind(this));
        showInternetConnectionDialog=new AlertDialog.Builder(this);
        ((MyApplication) getApplication()).getApplicationComponent().inject(this);
        setupListView();
        presenter.onAttach(this);
        if(isNetworkConnected()){
            presenter.findAllArticle();
        }else {
            showInternetConnectionDialog = new AlertDialog.Builder(this);
            showInternetConnectionDialog
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle("No Internet Connection!")
                    .setMessage("Please Enable Mobile Data or Wi-Fi")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog dialog = showInternetConnectionDialog.create();
            dialog.show();
            Log.e("ooooo","No internet connection");
        }

    }

    @Override
    public void showLoading() {
    progressDialog.show();
    }

    @Override
    public void hideLoading() {
    progressDialog.hide();
    }
    private void setupListView() {
        articleList = new ArrayList<>();
        recyclerViewArticle= (RecyclerView) findViewById(R.id.rvArticle);
        recyclerViewArticle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        articleAdapter=new ArticleAdapter(this,articleList);
        recyclerViewArticle.setAdapter(articleAdapter);
    }

    @Override
    public void updateRecyclerView(List<Article> articleList) {
        this.articleList.addAll(articleList);
        articleAdapter.notifyDataSetChanged();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
