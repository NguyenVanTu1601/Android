package com.example.serviceorientedsoftware.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.serviceorientedsoftware.R;
import com.example.serviceorientedsoftware.adapter.PetAdapter;
import com.example.serviceorientedsoftware.adapter.TypePetAdapter;
import com.example.serviceorientedsoftware.constants.Constants;
import com.example.serviceorientedsoftware.databinding.ActivityPetsBinding;
import com.example.serviceorientedsoftware.googlemap.ShopLocationActivity;
import com.example.serviceorientedsoftware.model.Pet;
import com.example.serviceorientedsoftware.model.TypePet;
import com.example.serviceorientedsoftware.retrofit.APIUtils;
import com.example.serviceorientedsoftware.retrofit.DataClient;
import com.example.serviceorientedsoftware.service.AlarmNotification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityPetsBinding binding;
    private FirebaseAuth mAuth;
    private TextView txtNameDrawer;
    private TextView txtEmailDrawer;
    private ImageView imageAvataDrawer;
    private ArrayList<Pet> pets;
    private PetAdapter adapter;
    private Call<List<Pet>> getPet;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    boolean active;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPetsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        createNotificationChannel();

        setTypePet();

        setProfile();

        setPets("ALL");

        binding.imgAvataUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PetsActivity.this, ProfileUserActivity.class));
            }
        });

        binding.imgAvataUser.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(PetsActivity.this, LoginActivity.class));
                fileList();
                return true;
            }
        });

        binding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    binding.drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        binding.searchPets.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Pet> petsSearch = new ArrayList<>();
                if (s.length() == 0){
                    petsSearch.addAll(pets);
                }
                else{
                    for (int i = 0; i < pets.size(); i++){
                        if (pets.get(i).getName().contains(s)){
                            petsSearch.add(pets.get(i));
                        }
                    }
                }

                adapter = new PetAdapter(petsSearch,PetsActivity.this);
                binding.recyclerListPets.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                binding.recyclerListPets.setAdapter(adapter);
                return true;
            }

        });


    }

    // khởi tạo
    private void init() {
        mAuth = FirebaseAuth.getInstance();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, null,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);
        binding.recyclerListPets.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        View header = binding.navView.getHeaderView(0);
        imageAvataDrawer = header.findViewById(R.id.img_avata_profile_drawer);
        txtEmailDrawer = header.findViewById(R.id.email_nav_drawer);
        txtNameDrawer = header.findViewById(R.id.name_nav_drawer);

        sharedPreferences = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        // mode private : chỉ lưu trữ và sử dụng trong phạm vi ứng dụng
        editor = sharedPreferences.edit();
        active = sharedPreferences.getBoolean("active", false);
        //Toast.makeText(this, active + "", Toast.LENGTH_SHORT).show();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        if (active == true){
            nav_Menu.findItem(R.id.drawer_notification).setIcon(R.drawable.ic_notification);
        }else{
            nav_Menu.findItem(R.id.drawer_notification).setIcon(R.drawable.ic_notifications_off);
        }

    }

    // Lấy ảnh từ db firebase
    private void setProfile() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String address = document.getString(Constants.KEY_ADDESS);
                        if (!TextUtils.isEmpty(address)) {
                            binding.userLocation.setText(address);
                        }
                        String email = document.getString(Constants.KEY_EMAIL);
                        String name = document.getString(Constants.KEY_NAME);
                        String image = document.getString(Constants.KEY_IMAGE);

                        if (image != null && !TextUtils.isEmpty(image)) {
                            //Toast.makeText(PetsActivity.this, image, Toast.LENGTH_SHORT).show();
                            Glide.with(PetsActivity.this)
                                    .load(image)
                                    .centerCrop()
                                    .error(R.drawable.profile_image)
                                    .placeholder(R.drawable.profile_image)
                                    .into(binding.imgAvataUser);

                            Glide.with(PetsActivity.this)
                                    .load(image)
                                    .centerCrop()
                                    .error(R.drawable.profile_image)
                                    .placeholder(R.drawable.profile_image)
                                    .into(imageAvataDrawer);
                        }

                        txtNameDrawer.setText(name);
                        txtEmailDrawer.setText(email);

                    } else {

                    }
                } else {

                }
            }
        });
    }

    // Thêm loại pet để lựa chọn
    public void setTypePet() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // set Horizontal Orientation
        binding.recyclerChosenPets.setLayoutManager(linearLayoutManager);

        List<TypePet> types = new ArrayList<>();
        types.add(new TypePet(0, R.drawable.all_type, "All", "ALL"));
        types.add(new TypePet(0, R.drawable.dog_type, "Dogs", "DOG"));
        types.add(new TypePet(0, R.drawable.cat_type, "Cats", "CAT"));
        types.add(new TypePet(0, R.drawable.bird_type, "Birds", "BIRD"));
        types.add(new TypePet(0, R.drawable.fish_type, "Fishs", "FISH"));

        TypePetAdapter adapter = new TypePetAdapter(types, this);
        binding.recyclerChosenPets.setAdapter(adapter);
        binding.recyclerChosenPets.setHasFixedSize(true);
    }

    // Lấy danh sách pet theo loại
    public void setPets(String type) {
        //DataClient dataClient = APIUtils.getData(Constants.base_url); - android co ngoc
        DataClient dataClient = APIUtils.getData(Constants.sale_url);
        if (type.equals("ALL")) {
            getPet = dataClient.getListPet();
        } else {
            //DataClient dataClient = APIUtils.getData(Constants.pet_type_url);
            //Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
            getPet = dataClient.getListPetByType(type);
        }


        getPet.enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                pets = (ArrayList<Pet>) response.body();
                if (pets != null && pets.size() != 0) {
                    Collections.shuffle(pets);
                    adapter = new PetAdapter(pets, PetsActivity.this);
                    binding.recyclerListPets.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    binding.recyclerListPets.setAdapter(adapter);
                    //Toast.makeText(PetsActivity.this, type, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PetsActivity.this, "Không có dữ liệu hiển thị", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                //Toast.makeText(PetsActivity.this, "Không thể gọi...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Navigation drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.location_drawer:
                startActivity(new Intent(PetsActivity.this, ShopLocationActivity.class));
                break;
            case R.id.profile_drawer:
                startActivity(new Intent(PetsActivity.this, ProfileUserActivity.class));
                break;
            case R.id.drawer_share:
                Toast.makeText(this, "Go to Share!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drawer_notification:
                /* Thêm set permiss setAlam và reciver ... trong manifest*/
                editor = sharedPreferences.edit();
                active = sharedPreferences.getBoolean("active", false);
                Calendar calendar = Calendar.getInstance();
                AlarmManager alarmManager = (AlarmManager) getSystemService( ALARM_SERVICE);
                Intent intent = new Intent(PetsActivity.this, AlarmNotification.class);
                PendingIntent pendingIntent =PendingIntent.getBroadcast(PetsActivity.this,0,intent,0);
                if(active != true){
                    Toast.makeText(this, "Start notification!", Toast.LENGTH_SHORT).show();
                    item.setIcon(R.drawable.ic_notification);
                    editor.putBoolean("active", true);

                    calendar.set(Calendar.HOUR_OF_DAY,8);
                    calendar.set(Calendar.MINUTE,0);
                    calendar.set(Calendar.SECOND,0);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                }else{
                    Toast.makeText(this, "Stop notification!", Toast.LENGTH_SHORT).show();
                    item.setIcon(R.drawable.ic_notifications_off);
                    alarmManager.cancel(pendingIntent);
                    editor.putBoolean("active", false);
                }
                editor.commit();
                break;
            case R.id.logout_drawer:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(PetsActivity.this, LoginActivity.class));
                finish();
                break;
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            binding.searchPets.clearFocus();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(getPet != null && getPet.isExecuted()){
            getPet.cancel();
        }

    }

    // Tạo notification channel
    private void createNotificationChannel() {
        // Tạo notificationChannel đối với API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Name_Channel";
            String description = "Name_Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notification", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if(notificationManager!=null){
                notificationManager.createNotificationChannel(channel);
            }

        }
    }
}