package com.example.swipestack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import link.fls.swipestack.SwipeStack;

public class MainActivity extends AppCompatActivity {

    private SwipeStack cardStack;
    private CardsAdapter cardsAdapter;
    private ArrayList<CardItem> cardItems;
    private View btnCancel;
    private View btnLove;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardStack = (SwipeStack) findViewById(R.id.container);
        btnCancel = findViewById(R.id.cancel);
        btnLove = findViewById(R.id.love);

        setCardStackAdapter();
        currentPosition = 0;

        //Handling swipe event of Cards stack
        cardStack.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {
                currentPosition = position + 1;
                Toast.makeText(MainActivity.this, "You liked " + cardItems.get(currentPosition-1).getName(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onViewSwipedToRight(int position) {
                currentPosition = position + 1;
            }

            @Override
            public void onStackEmpty() {
                btnCancel.setVisibility(View.GONE);
                btnLove.setVisibility(View.GONE);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardStack.swipeTopViewToRight();
            }
        });

        btnLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "You liked " + cardItems.get(currentPosition).getName(),
//                        Toast.LENGTH_SHORT).show();
                cardStack.swipeTopViewToLeft();
            }
        });
    }

    private void setCardStackAdapter() {
        cardItems = new ArrayList<>();

        cardItems.add(new CardItem(R.drawable.image, "Huyen My", "Hanoi"));
        cardItems.add(new CardItem(R.drawable.image1, "Do Ha", "Nghe An"));
        cardItems.add(new CardItem(R.drawable.image2, "Dong Nhi", "Hue"));
        cardItems.add(new CardItem(R.drawable.image5, "Le Quyen", "Sai Gon"));
        cardItems.add(new CardItem(R.drawable.image6, "Phuong Linh", "Thanh Hoa"));
        cardItems.add(new CardItem(R.drawable.image7, "Phuong Vy", "Hanoi"));
        cardItems.add(new CardItem(R.drawable.image10, "Ha Ho", "Da Nang"));

        cardsAdapter = new CardsAdapter(this, cardItems);
        cardStack.setAdapter(cardsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reset) {
            cardStack.resetStack();
            btnCancel.setVisibility(View.VISIBLE);
            btnLove.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }
}