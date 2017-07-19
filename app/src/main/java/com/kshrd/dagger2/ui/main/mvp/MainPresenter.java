package com.kshrd.dagger2.ui.main.mvp;

import com.kshrd.dagger2.base.BasePresenter;
import com.kshrd.dagger2.entity.Article;
import com.kshrd.dagger2.listener.CallbackWithList;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by pirang on 7/19/17.
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements
        MainContract.Presenter
{

    private MainContract.Interactor interactor;

    @Inject
    public MainPresenter(MainInteractor interactor){
        this.interactor = interactor;
    }

    @Override
    public void findAllArticle() {
        getMvpView().showLoading();
        interactor.findAllArticle(new CallbackWithList<Article>() {
            @Override
            public void onSuccess(List<Article> list) {

                getMvpView().updateRecyclerView(list);
                onComplete();

            }

            @Override
            public void onFailed() {

            }

            @Override
            public void onError() {
                onComplete();
            }

            @Override
            public void onComplete() {
            getMvpView().hideLoading();
            }
        });
    }

}
