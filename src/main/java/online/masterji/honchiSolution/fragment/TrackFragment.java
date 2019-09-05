package online.masterji.honchiSolution.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.domain.Listdata;


public class TrackFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "TrackFragment";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LinearLayout layoutEmptyt;

    public TrackFragment() {
        // Required empty public constructor
    }


    public static TrackFragment newInstance(String param1, String param2) {
        TrackFragment fragment = new TrackFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    List<Listdata> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


//    LinearLayout que1, quee119, que119, quee118, que117, que118, que116, quee117, quee116, quee115, que115, quee113, quee114, que114, que113, que13, quee13, queee11, quee112, queee1, quee11, que10, que112, que111, quee10, que7, que9, que99, que8, que88, que77, que33, que22, que44, Ans1, que55, que66, que2, que3, que4, que6, que5;

    LinearLayout que1, que2, que3, que4, que5, que6, que7, que8, que9, que10, que11, que12, que13, que14, que15, que16, que17, que18, que19, que20;
    LinearLayout Ans1, Ans2, Ans3, Ans4, Ans5, Ans6, Ans7, Ans8, Ans9, Ans10, Ans11, Ans12, Ans13, Ans14, Ans15, Ans16, Ans17, Ans18, Ans19, Ans20;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track, container, false);
        view.setBackgroundColor(Color.WHITE);

        que1 = view.findViewById(R.id.que1);
        que2 = view.findViewById(R.id.que2);
        que3 = view.findViewById(R.id.que3);
        que4 = view.findViewById(R.id.que4);
        que5 = view.findViewById(R.id.que5);
        que6 = view.findViewById(R.id.que6);
        que7 = view.findViewById(R.id.que7);
        que8 = view.findViewById(R.id.que8);
        que9 = view.findViewById(R.id.que9);
        que10 = view.findViewById(R.id.que10);
        que11 = view.findViewById(R.id.que11);
        que12 = view.findViewById(R.id.que12);
        que13 = view.findViewById(R.id.que13);
        que14 = view.findViewById(R.id.que14);
        que15 = view.findViewById(R.id.que15);
        que16 = view.findViewById(R.id.que16);
        que17 = view.findViewById(R.id.que17);
        que18 = view.findViewById(R.id.que18);
        que19 = view.findViewById(R.id.que19);
        que20 = view.findViewById(R.id.que20);
//        que119 = view.findViewById(R.id.que119);


        Ans1 = view.findViewById(R.id.Ans1);
        Ans2 = view.findViewById(R.id.Ans2);
        Ans3 = view.findViewById(R.id.Ans3);
        Ans4 = view.findViewById(R.id.Ans4);


        Ans5 = view.findViewById(R.id.Ans5);
        Ans6 = view.findViewById(R.id.Ans6);
        Ans7 = view.findViewById(R.id.Ans7);
        Ans8 = view.findViewById(R.id.Ans8);
        Ans9 = view.findViewById(R.id.Ans9);

        Ans10 = view.findViewById(R.id.Ans10);
        Ans11 = view.findViewById(R.id.Ans11);
        Ans12 = view.findViewById(R.id.Ans12);
        Ans13 = view.findViewById(R.id.Ans13);
        Ans14 = view.findViewById(R.id.Ans14);


        Ans15 = view.findViewById(R.id.Ans15);
        Ans16 = view.findViewById(R.id.Ans16);
        Ans17 = view.findViewById(R.id.Ans17);

        Ans18 = view.findViewById(R.id.Ans18);
        Ans19 = view.findViewById(R.id.Ans19);
        Ans20 = view.findViewById(R.id.Ans20);
//        Ans4 = view.findViewById(R.id.Ans4);


        que1.setOnClickListener(this);
        que2.setOnClickListener(this);
        que3.setOnClickListener(this);
        que4.setOnClickListener(this);


        que5.setOnClickListener(this);
        que6.setOnClickListener(this);
        que7.setOnClickListener(this);
        que8.setOnClickListener(this);

        que9.setOnClickListener(this);
        que10.setOnClickListener(this);
        que11.setOnClickListener(this);
        que12.setOnClickListener(this);

        que13.setOnClickListener(this);
        que14.setOnClickListener(this);
        que15.setOnClickListener(this);
        que16.setOnClickListener(this);

        que17.setOnClickListener(this);
        que18.setOnClickListener(this);
        que19.setOnClickListener(this);
        que20.setOnClickListener(this);


        return view;
    }

    Intent intent;

    int i = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.que1:
                if (i == 0) {

                    Ans1.setVisibility(View.VISIBLE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);



                    i = 1;

                } else {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 0;
                }


                break;
            case R.id.que2:
                if (i == 0) {

                    Ans2.setVisibility(View.VISIBLE);

                    Ans1.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 1;

                } else {


                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 0;
                }


                break;


            case R.id.que3:
                if (i == 0) {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.VISIBLE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 1;

                } else {


                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 0;
                }


                break;
            case R.id.que4:
                if (i == 0) {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.VISIBLE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 1;

                } else {


                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 0;
                }


                break;
            case R.id.que5:
                if (i == 0) {


                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.VISIBLE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 1;

                } else {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);

                    i = 0;
                }


                break;
            case R.id.que6:

                if (i == 0) {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.VISIBLE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);



                    i = 1;

                } else {

                    Ans6.setVisibility(View.GONE);
                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 0;
                }


                break;
            case R.id.que7:
                if (i == 0) {
                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.VISIBLE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);



                    i = 1;

                } else {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);



                    i = 0;
                }


                break;
            case R.id.que8:
                if (i == 0) {
                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.VISIBLE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);



                    i = 1;

                } else {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 0;
                }
                break;
            case R.id.que9:
                if (i == 0) {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.VISIBLE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 1;

                } else {
                    Log.e(TAG, "onClick: " + i);


                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 0;
                }
                break;
            case R.id.que10:
                Log.e(TAG, "onClick: 1");
                if (i == 0) {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.VISIBLE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);

                    i = 1;

                } else {
                    Log.e(TAG, "onClick: " + i);


                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);

                    i = 0;
                }
                break;
            case R.id.que11:
                Log.e(TAG, "onClick: 1");
                if (i == 0) {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.VISIBLE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);

                    i = 1;

                } else {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 0;
                }
                break;

            case R.id.que12:
                Log.e(TAG, "onClick: 1");
                if (i == 0) {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.VISIBLE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 1;

                } else {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 0;
                }
                break;

            case R.id.que13:
                int i2 = 0;
                if (i == 0) {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.VISIBLE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 1;

                } else {
                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 0;
                }

                break;
            case R.id.que14:
                int i3 = 0;
                if (i == 0) {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.VISIBLE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 1;

                } else {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 0;
                }

                break;
            case R.id.que15:
                int i4 = 0;
                if (i == 0) {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.VISIBLE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);



                    i = 1;

                } else {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);



                    i = 0;
                }

                break;

            case R.id.que16:
                int i6 = 0;
                if (i == 0) {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.VISIBLE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);



                    i = 1;

                } else {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 0;
                }
            case R.id.que17:
                // int i7 = 0;
                if (i == 0) {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.VISIBLE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);

                    i = 1;

                } else {

                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);

                    i = 0;
                }
                break;
            case R.id.que18:
                int i8 = 0;
                if (i == 0) {
                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.VISIBLE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);


                    i = 1;
                } else {
                    Log.e(TAG, "onClick: " + i);
                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);



                    i = 0;
                }
                /* */
                break;
            case R.id.que19:
                int i9 = 0;
                if (i == 0) {
                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.VISIBLE);
                    Ans20.setVisibility(View.GONE);

                    i = 1;
                } else {
                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);

                    i = 0;
                }
                /* */
                break;


            case R.id.que20:
                int i10 = 0;
                if (i == 0) {
                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.VISIBLE);

                    i = 1;
                } else {
                    Ans1.setVisibility(View.GONE);

                    Ans2.setVisibility(View.GONE);
                    Ans3.setVisibility(View.GONE);
                    Ans4.setVisibility(View.GONE);

                    Ans5.setVisibility(View.GONE);
                    Ans6.setVisibility(View.GONE);
                    Ans7.setVisibility(View.GONE);
                    Ans8.setVisibility(View.GONE);

                    Ans9.setVisibility(View.GONE);
                    Ans10.setVisibility(View.GONE);
                    Ans11.setVisibility(View.GONE);
                    Ans12.setVisibility(View.GONE);

                    Ans13.setVisibility(View.GONE);
                    Ans14.setVisibility(View.GONE);
                    Ans15.setVisibility(View.GONE);
                    Ans16.setVisibility(View.GONE);

                    Ans17.setVisibility(View.GONE);
                    Ans18.setVisibility(View.GONE);
                    Ans19.setVisibility(View.GONE);
                    Ans20.setVisibility(View.GONE);

                    i = 0;
                }
                /* */
                break;
            default:
                Ans1.setVisibility(View.GONE);
                Ans2.setVisibility(View.GONE);
                Ans3.setVisibility(View.GONE);
                Ans4.setVisibility(View.GONE);

                Ans5.setVisibility(View.GONE);
                Ans6.setVisibility(View.GONE);
                Ans7.setVisibility(View.GONE);
                Ans8.setVisibility(View.GONE);
                Ans9.setVisibility(View.GONE);
                Ans10.setVisibility(View.GONE);
                Ans11.setVisibility(View.GONE);
                Ans12.setVisibility(View.GONE);
                Ans13.setVisibility(View.GONE);
                Ans14.setVisibility(View.GONE);
                Ans15.setVisibility(View.GONE);
                Ans16.setVisibility(View.GONE);
                Ans17.setVisibility(View.GONE);

                Ans18.setVisibility(View.GONE);
                Ans19.setVisibility(View.GONE);
                Ans20.setVisibility(View.GONE);
//               Ans15.setVisibility(View.GONE);
                break;
        }
    }

}


