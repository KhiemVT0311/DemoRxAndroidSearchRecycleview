package com.eup.rxandroidsearchdemo;

import android.widget.SearchView;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxSearchObservable {
    public static Observable<String> fromSearchView(androidx.appcompat.widget.SearchView searchView) {
        final PublishSubject<String> publisher = PublishSubject.create();
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                publisher.onComplete();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                publisher.onNext(s);
                return true;
            }
        });

        return publisher;
    }

}
