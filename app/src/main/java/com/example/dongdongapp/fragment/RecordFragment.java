package com.example.dongdongapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dongdongapp.Adapter.RecordAdapter;
import com.example.dongdongapp.MainActivity;
import com.example.dongdongapp.R;
import com.example.dongdongapp.model.RecordModel;
import com.example.dongdongapp.service.VideoService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// 用户学习记录列表页

public class RecordFragment extends Fragment {

//     TODO: Rename parameter arguments, choose names that match
//     the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    private RecyclerView recordRecyclerView;
    private List<RecordModel> recordModelList;
    private RecordAdapter recordAdapter;
    private int courseId;
    private int userId;
    private String courseType;
    private String courseName;

    private TextView guideText;
    private ImageButton retButton;


    public RecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordFragment newInstance(String param1, String param2) {
        RecordFragment fragment = new RecordFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
              courseId = getArguments().getInt("courseId");
              userId = getArguments().getInt("userId");
              courseName = getArguments().getString("courseName");
              courseType = getArguments().getString("courseType");
        }
        // recordModelList = new ArrayList<RecordModel>();
        recordModelList = setRecordModelList();
        recordAdapter = new RecordAdapter(getContext(),getArguments());
        recordAdapter.setRecordModelList(recordModelList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        guideText = view.findViewById(R.id.recordGuideLine);
        recordRecyclerView = view.findViewById(R.id.recordRecyclerView);
        retButton = view.findViewById(R.id.retButton);

        recordRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recordRecyclerView.setAdapter(recordAdapter);

        guideText.setText("In " + courseName +"\n" + "You have done so much");
        initButtonListener();
        return view;
    }

    private void initButtonListener(){
      retButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          // TODO
          Intent intent = new Intent(getActivity(), MainActivity.class);
          startActivity(intent);
        }
      });
    }


    private List<RecordModel> setRecordModelList(){
      // TODO implement a method to get user record list in THIS COURSE


      List<RecordModel> recordModelList = new ArrayList<>();

//      VideoService videoService = new VideoService();
//      recordModelList = videoService.getRecordByType(userId, courseType);

      // fake implement
      Random random = new Random();
      int recordNum = 1 + random.nextInt(7);

      String [] ranks = {"A+", "A","B","C"};
      boolean [] status = {true, false};

      for(int i = 0; i < recordNum; i++){
        RecordModel record  = new RecordModel();
        int rankIndex = random.nextInt(4);
        int statusIndex = random.nextInt(2);
        record.setAnalyzeFinish(status[statusIndex]);
        record.setRecordRank(ranks[rankIndex]);
        record.setRecordDate("2022.10.1"+String.valueOf(i));
        record.setRecordId(i);
        record.setRecordAdvice("A piece of Advice for record" + i + " in " + courseName);

        recordModelList.add(record);
      }
      Collections.sort(recordModelList);
      return recordModelList;
    }
}
