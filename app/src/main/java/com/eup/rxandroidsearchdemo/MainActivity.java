package com.eup.rxandroidsearchdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.eup.rxandroidsearchdemo.adapter.StudentAdapter;
import com.eup.rxandroidsearchdemo.data.DataSourcev;
import com.eup.rxandroidsearchdemo.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        ButterKnife.bind(this);
        StudentAdapter adapter = new StudentAdapter();
        adapter.addStudentNames(DataSourcev.getStudentList());
        binding.rvStudentList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvStudentList.setAdapter(adapter);
        initSearchFeature(adapter);
    }

    private void initSearchFeature(StudentAdapter adapter) {
        RxSearchObservable.fromSearchView(binding.svKey)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(text -> !text.isEmpty())
                .distinctUntilChanged()
                .switchMap(new Function<String, ObservableSource<ArrayList<String>>>() {
                    @Override
                    public ObservableSource<ArrayList<String>> apply(@NonNull String key) throws Exception {
                        return DataSourcev.getSearchData(key);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(names -> {
         //           adapter.removeAllNames();
                    adapter.addStudentNames(names);
                });
    }
}