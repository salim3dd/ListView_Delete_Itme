package com.salim3dd.listview_delete_itme;

/**
 * Created by Salim3DD on 10/13/2016.
 */

public class ListItme_All {
    private int id;
    private String title;

    public ListItme_All(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
