package com.example.ncovi_2020.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.ncovi_2020.R;

import hotchemi.android.rate.AppRate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Tacgia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Tacgia extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Tacgia() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Tacgia.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Tacgia newInstance(String param1, String param2) {
        Fragment_Tacgia fragment = new Fragment_Tacgia();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    ImageButton imgFaceBook, imgMail, imgPlayStore, imgYoutube;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__tacgia, container, false);
        imgFaceBook = view.findViewById(R.id.facebook_drawer);
        imgMail = view.findViewById(R.id.gmail_drawer);
        imgYoutube = view.findViewById(R.id.youtube_drawer);
        imgPlayStore = view.findViewById(R.id.rate_me_drawer);

        imgFaceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFacebookPage();
            }
        });

        imgMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmail();
            }
        });

        imgPlayStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayStore();
            }
        });

        imgYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYoutube();
            }
        });

        RateMe();


        return view;
    }

    public void openFacebookPage() {
        String YourPageURL = "https://www.facebook.com/banggia.160199";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));
        startActivity(browserIntent);
    }

    public void openEmail(){
        String[] recipientList = new String[]{"banggia1601@gmail.com"}; // mảng chứa danh sách các email nhận thư
        String subject = "Xin chào!"; // tiêu đề thư gửi
        String message = "Cảm ơn bạn đã tạo ra ứng dụng này"; // nội dung thư gửi

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipientList);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));

    }

    public void openPlayStore(){
        try{
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + "com.android.chrome")));
        }catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/apps/details?id="+getParentFragment())));
        }
    }

    public void openYoutube() {
        String YourPageURL = "https://www.youtube.com/";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));
        startActivity(browserIntent);
    }

    private void RateMe(){
        AppRate.with(getActivity()) // text hiện thị sưa trong file string.xml
                .setInstallDays(0) // số ngày kể từ khi cài đặt app sẽ bắt đầu hỏi vote
                .setLaunchTimes(3)
                .setRemindInterval(2)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(getActivity());
        //AppRate.with(this).showRateDialog(this); // hiện lại mỗi khi ấn no thanks và tắt app mở lại
    }
}