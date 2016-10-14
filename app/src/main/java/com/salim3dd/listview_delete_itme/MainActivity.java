package com.salim3dd.listview_delete_itme;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ListItme_All> listItem = new ArrayList<>();
    private ArrayList<Integer> listItem_to_delete = new ArrayList<>();

    private Boolean show_checkBox = false;

    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.listView1);

        listItem.add(new ListItme_All(10, "Salim"));
        listItem.add(new ListItme_All(111, "Ali"));
        listItem.add(new ListItme_All(212, "Mohammed"));
        listItem.add(new ListItme_All(323, "Amur"));
        listItem.add(new ListItme_All(24, "Hamad"));
        listItem.add(new ListItme_All(51, "Saeed"));


        arrayAdapter = new ArrayAdapter(listItem);
        listView.setAdapter(arrayAdapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                show_checkBox = true;
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.action_menu, menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_id:
                        int total_delete = listItem_to_delete.size();

                        for (int i = 0; i < listItem_to_delete.size(); i++) {
                            int n = listItem_to_delete.get(i);
                            for (int j = 0; j < listItem.size(); j++) {
                                int m = listItem.get(j).getId();
                                if (n==m) {
                                    listItem.remove(j);
                                }
                            }

                        }
                        listItem_to_delete.clear();
                        show_checkBox = false;
                        arrayAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "تم إزالة " + total_delete + " عناصر", Toast.LENGTH_SHORT).show();
                        mode.finish();
                        return true;
                }

                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                listItem_to_delete.clear();
                show_checkBox = false;
                arrayAdapter.notifyDataSetChanged();

            }
        });


    }

    class ArrayAdapter extends BaseAdapter {
        ArrayList<ListItme_All> listA = new ArrayList<>();

        public ArrayAdapter(ArrayList<ListItme_All> listA) {
            this.listA = listA;
        }

        @Override
        public int getCount() {
            return listA.size();
        }

        @Override
        public Object getItem(int position) {
            return listA.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.row_item, null);
            final TextView name = (TextView) view.findViewById(R.id.textView_row);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            final LinearLayout ly_main = (LinearLayout) view.findViewById(R.id.ly_main);

            name.setText(listA.get(position).getTitle());

            if (show_checkBox) {
                checkBox.setVisibility(View.VISIBLE);
            } else {
                checkBox.setVisibility(View.INVISIBLE);
            }


            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        listItem_to_delete.add(listA.get(position).getId());
                        ly_main.setBackgroundColor(Color.RED);

                    } else {
                        if (listItem_to_delete.size() > 0) {

                            for (int i = 0; i < listItem_to_delete.size(); i++) {
                                int m = listItem_to_delete.get(i);
                                if (m == listA.get(position).getId()) {
                                    listItem_to_delete.remove(i);
                                    ly_main.setBackgroundColor(Color.GRAY);
                                }
                            }
                        }
                    }
                }
            });
            return view;
        }
    }
}
