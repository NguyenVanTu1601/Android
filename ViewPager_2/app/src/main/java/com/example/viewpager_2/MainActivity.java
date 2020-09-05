package com.example.viewpager_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private OnboardingAdapter onboardingAdapter;
    private List<OnboardingItem> onboardingItems;
    private LinearLayout layoutOnboardingIndicator;
    private MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutOnboardingIndicator = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);
        final ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);

        setupOnboardingItem();

        onboardingViewPager.setAdapter(onboardingAdapter);

        setupOnboardingIndicator();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()){
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                }else{
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                }
            }
        });
    }

    public void setupOnboardingItem(){
        onboardingItems = new ArrayList<>();

        OnboardingItem onboardingItemGirl1 = new OnboardingItem(R.drawable.image6,"Girl one","Girl xinh Hà Thành");
        OnboardingItem onboardingItemGirl2 = new OnboardingItem(R.drawable.image7,"Girl two","Girl xinh group");
        OnboardingItem onboardingItemGirl3 = new OnboardingItem(R.drawable.image10,"Girl three","Girl xinh quê Bác");

        onboardingItems.add(onboardingItemGirl1);
        onboardingItems.add(onboardingItemGirl2);
        onboardingItems.add(onboardingItemGirl3);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);


    }

    // Mấy ô nhỏ nhỏ ở dưới góc trái thể hiện đang kéo qua trang thứ mấy
    private void setupOnboardingIndicator(){
        ImageView[] indicator = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8,0,8,0);
        for (int i = 0; i < indicator.length; i++){
            indicator[i] = new ImageView(getApplicationContext());
            indicator[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inative));

            indicator[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicator.addView(indicator[i]);

        }
    }

    // Dung để biết đang mở trang nào thì trang đó sẽ có background active, các trang khác là inative
    private void setCurrentOnboardingIndicator(int index){
        int childCount = layoutOnboardingIndicator.getChildCount();
        for (int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView) layoutOnboardingIndicator.getChildAt(i);
            if(i == index){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicatior_active));
            }else{
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inative));
            }
        }
        if(index == onboardingAdapter.getItemCount() - 1){
            buttonOnboardingAction.setText("Start");
        }else{
            buttonOnboardingAction.setText("Next");
        }
    }
}