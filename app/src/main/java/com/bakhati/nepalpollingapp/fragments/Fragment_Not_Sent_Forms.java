package com.bakhati.nepalpollingapp.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bakhati.nepalpollingapp.MainActivity;
import com.bakhati.nepalpollingapp.R;
import com.bakhati.nepalpollingapp.adapter.GridSpacingItemDecorator;
import com.bakhati.nepalpollingapp.adapter.Not_Sent_Forms_Adapter;
import com.bakhati.nepalpollingapp.adapter.RecyclerItemClickListener;
import com.bakhati.nepalpollingapp.database.DatabaseNepalPoolingAppNotSent;
import com.bakhati.nepalpollingapp.dialog.Default_DIalog;
import com.bakhati.nepalpollingapp.model.SavedFormParameters;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Not_Sent_Forms extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    public static List<SavedFormParameters> resultCur = new ArrayList<>();
    Not_Sent_Forms_Adapter ca;
    Context context = getActivity() ;


    public Fragment_Not_Sent_Forms() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment__sent__form_list, container, false);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.NewsList);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecorator(1, 5, true));
        createList();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                alert_editlist(position);

            }

            @Override
            public void onItemLongClick(View view, int position) {


            }
        }));

        return rootview;
    }

    //-------------------------------Method Dialog Box List for << REPORT DETAIL, SEND and DELETE >>-----------------------------------//
    protected void alert_editlist(final int position) {

        // TODO Auto-generated method stub
        final CharSequence[] items = {"Open", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Action");

        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                if (items[item] == "Open") {
                    String id = resultCur.get(position).formId;
                    String jSon = resultCur.get(position).jSON;
                    String DBid = resultCur.get(position).dbId;
                    String sent_Status = resultCur.get(position).status;
                    loadForm(id, jSon, DBid, sent_Status);

                } else if (items[item] == "Delete") {
                    DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
                    int width = metrics.widthPixels;
                    int height = metrics.heightPixels;

                    final Dialog showDialog = new Dialog(getActivity());
                    showDialog.setContentView(R.layout.delete_dialog);
                    TextView tvDisplay = (TextView) showDialog.findViewById(R.id.textViewDefaultDialog);
                    Button btnOk = (Button) showDialog.findViewById(R.id.button_delete);
                    Button cancle = (Button) showDialog.findViewById(R.id.button_cancle);
                    showDialog.setTitle("Are You Sure ??");
                    tvDisplay.setText("Are you sure you want to delete the data ??");
                    showDialog.setCancelable(true);
                    showDialog.show();
                    showDialog.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);

                    btnOk.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub


                            DatabaseNepalPoolingAppNotSent dataBaseNepalPoolingAppNotSent = new DatabaseNepalPoolingAppNotSent(getActivity());
                            dataBaseNepalPoolingAppNotSent.open();
                            dataBaseNepalPoolingAppNotSent.dropRowNotSentForms(resultCur.get(position).dbId);
//                Toast.makeText(getActivity() ,resultCur.get(position).date+ " Long Clicked "+id , Toast.LENGTH_SHORT ).show();
                            dataBaseNepalPoolingAppNotSent.close();
                            showDialog.dismiss();
                            createList();
                        }
                    });
                    cancle.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            showDialog.dismiss();
                        }
                    });

                }
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showDeleteDialog(final int position) {
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        final Dialog showDialog = new Dialog(getActivity());
        showDialog.setContentView(R.layout.delete_dialog);
        TextView tvDisplay = (TextView) showDialog.findViewById(R.id.textViewDefaultDialog);
        Button btnOk = (Button) showDialog.findViewById(R.id.button_delete);
        Button cancle = (Button) showDialog.findViewById(R.id.button_cancle);
        showDialog.setCancelable(true);
        showDialog.show();
        showDialog.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);

        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                DatabaseNepalPoolingAppNotSent databaseNepalPoolingAppNotSent = new DatabaseNepalPoolingAppNotSent(getActivity());
                databaseNepalPoolingAppNotSent.open();
                int id = (int) databaseNepalPoolingAppNotSent.updateTable_DeleteFlag(resultCur.get(position).dbId);
//                Toast.makeText(getActivity() ,resultCur.get(position).date+ " Long Clicked "+id , Toast.LENGTH_SHORT ).show();
                databaseNepalPoolingAppNotSent.close();
                showDialog.dismiss();
                createList();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog.dismiss();
            }
        });

    }

    public void loadForm(String formId, String jsonData, String DBid, String sent_Status){
//        if(formId!= null) {
            switch (formId) {
                case "1":
                    Intent intent1 = new Intent(getActivity(), MainActivity.class);
                    intent1.putExtra("JSON1", jsonData);
                    intent1.putExtra("DBid", DBid);
                    intent1.putExtra("sent_Status", sent_Status);
                    startActivity(intent1);
                    break;

            }
//        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void createList() {
        resultCur.clear();
//        Single_String_Title newData1 = new Single_String_Title();
//        newData1.title = "CF Detail";
//        resultCur.add(newData1);
        DatabaseNepalPoolingAppNotSent databaseNepalPoolingAppNotSent = new DatabaseNepalPoolingAppNotSent(getActivity());
        databaseNepalPoolingAppNotSent.open();
        boolean isTableEmpty = databaseNepalPoolingAppNotSent.is_TABLE_MAIN_Empty();
        if(isTableEmpty){
            Default_DIalog.showDefaultDialog(getActivity() , R.string.app_name , "No data Saved ");
        }else{
            int count = databaseNepalPoolingAppNotSent.getNotSentFormsRowCount() +1;
            Log.e("FragmentNotSent", "createList: Total Row Count " + count );
            for(int i=count ; i>=1 ; i--) {
//                String[] data = dataBaseNepalPublicHealthNotSent.return_Data_TABLE_MAIN(i);
                String[] data = databaseNepalPoolingAppNotSent.return_Data_ID(i);
                SavedFormParameters savedData = new SavedFormParameters();
                Log.e("DATA" , "08 "+data[5] +" one: "+ data[1]+" two: "+data[2]);
//                savedData.dbId = data[0];
                savedData.formId = data[0];
                savedData.formName = data[1];
                savedData.date = data[2];
                savedData.jSON = data[3];
                savedData.status = data[4];
                savedData.deletedStatus = data[5];
                savedData.dbId = data[6];

                if(savedData.dbId!= null) {

                    resultCur.add(savedData);
                }

            }
        }
        fillTable();
    }

    public void fillTable() {
        Log.e("FILLTABLE", "INSIDE FILL TABLE");
        ca = new Not_Sent_Forms_Adapter(resultCur);
        recyclerView.setAdapter(ca);
        Log.e("FILLTABLE", "AFTER FILL TABLE");
//        CheckValues.setValue();
    }

}

