package com.eup.rxandroidsearchdemo.data;

import java.util.ArrayList;

import io.reactivex.Observable;

public class DataSourcev {
    public static ArrayList<String> getStudentList() {
        ArrayList<String> data = new ArrayList<String>();
        data.add("Khiem");
        data.add("Hung");
        data.add("Quang");
        data.add("Van");
        data.add("Son");
        data.add("Anh");
        data.add("Dung");
        data.add("Hien");
        data.add("Nga");
        data.add("Long");
        data.add("Huyen");
        data.add("Hien");
        return data;
    }

    public static Observable<ArrayList<String>> getSearchData(String key) {
        ArrayList<String> data = getStudentList();
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).toUpperCase().contains(key.toUpperCase())) {
                result.add(data.get(i));
            }
        }
        Observable<ArrayList<String>> observable = Observable.just(result);
        return observable;
    }
}
