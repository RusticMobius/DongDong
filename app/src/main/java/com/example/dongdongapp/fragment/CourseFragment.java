package com.example.dongdongapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dongdongapp.CoursePage;
import com.example.dongdongapp.MainActivity;
import com.example.dongdongapp.R;
import com.example.dongdongapp.VideoRecordPage;

// 课程详情页面

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment {



    // TODO: Rename and change types of parameters

    private TextView courseNameView;
    private TextView tipsView;
    private String allTips;
    private String courseName;
    private String courseType;
    private int userId;
    private int courseId;
    private ImageButton retButton;
    private Button recordButton;
    private String TAG = "courseFragment";
    private Bundle recordActivityBundle;

    public CourseFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment CourseFragment.
//     */
    // TODO: Rename and change types and number of parameters
    public static CourseFragment newInstance() {
        CourseFragment fragment = new CourseFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            courseType = getArguments().getString("courseType");
            courseName = getArguments().getString("courseName");
            allTips = getArguments().getString("courseTips");
            courseId = getArguments().getInt("courseId");
          userId = getArguments().getInt("userId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        courseNameView = view.findViewById(R.id.courseNameText);
        tipsView = view.findViewById(R.id.tipsText);
        recordButton = view.findViewById(R.id.recordButton);
        retButton = view.findViewById(R.id.retButton);
        courseNameView.setText(courseName);
        tipsView.setText(allTips);
        tipsView.setMovementMethod(ScrollingMovementMethod.getInstance());
        initButtonListener();
        initRecordIntentBundle();
        return view;
    }

    private void initButtonListener(){
      recordButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(getActivity(), VideoRecordPage.class);
          intent.putExtras(recordActivityBundle);
          startActivityForResult(intent,1);
        }
      });

      retButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          // TODO
          getActivity().finish();
//          Intent intent = new Intent(getActivity(), MainActivity.class);
//          startActivity(intent);
        }
      });
    }

  private void initRecordIntentBundle(){
    recordActivityBundle = new Bundle();
    recordActivityBundle.putInt("courseId", courseId);
    recordActivityBundle.putInt("userId", userId);
    recordActivityBundle.putString("courseType",courseType);
    recordActivityBundle.putString("courseName",courseName);
    recordActivityBundle.putString("courseTips",allTips);
  }
}
