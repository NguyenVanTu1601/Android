package com.example.android_bai3_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android_bai3_layout.databinding.ActivityB01KTBinding;
import com.example.android_bai3_layout.model.Catalog;
import com.example.android_bai3_layout.model.Product;

import java.util.ArrayList;
import java.util.List;

public class B01_KT_Activity extends AppCompatActivity {

    private Spinner spinner;
    private ArrayList<Catalog> catalogs;

    private EditText edtProId, edtProName;
    private Button buttonAdd;
    private ListView listViewProduct;
    private String[] products;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b01__k_t_);

        init();

        products = addProductToView(catalogs.get(0).getProducts());
        adapter = new ArrayAdapter<String>(B01_KT_Activity.this,
                android.R.layout.simple_list_item_1,products);
        listViewProduct.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String catalogId = spinner.getItemAtPosition(position).toString().substring(0,1).trim();
                for (int i = 0; i < catalogs.size(); i++){
                    if (catalogs.get(i).getId().equals(catalogId)){
                        products = addProductToView(catalogs.get(i).getProducts());
                        adapter = new ArrayAdapter<String>(B01_KT_Activity.this,
                                android.R.layout.simple_list_item_1,products);
                        listViewProduct.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String catalogId = spinner.getSelectedItem().toString().substring(0,1).trim();
                String idPro = edtProId.getText().toString();
                String namePro = edtProName.getText().toString();
                Product product = new Product(idPro, namePro);

                for (int i = 0; i < catalogs.size(); i++){
                    if (catalogs.get(i).getId().equals(catalogId)){
                        if (catalogs.get(i).getProducts() != null){
                            if (!checkExist(catalogs.get(i), product)){
                                catalogs.get(i).getProducts().add(product);
                            }
                        }else{
                            ArrayList<Product> products = new ArrayList<>();
                            products.add(product);
                            catalogs.get(i).setProducts(products);
                        }

                        products = addProductToView(catalogs.get(i).getProducts());
                        adapter = new ArrayAdapter<String>(B01_KT_Activity.this,
                                android.R.layout.simple_list_item_1,products);
                        listViewProduct.setAdapter(adapter);
                    }
                }

            }
        });
    }

    private void init() {
        catalogs = new ArrayList<>();
        catalogs.add(new Catalog("1","Android"));
        catalogs.add(new Catalog("2","IOS"));
        catalogs.add(new Catalog("3","Windows phones"));


        String [] cata = new String[3];
        cata[0] = catalogs.get(0).toString();
        cata[1] = catalogs.get(1).toString();
        cata[2] = catalogs.get(2).toString();

        spinner         = findViewById(R.id.spinnerCatalog);
        edtProId        = findViewById(R.id.edtProId);
        edtProName      = findViewById(R.id.edtProName);
        buttonAdd       = findViewById(R.id.buttonAddProduct);
        listViewProduct = findViewById(R.id.listProduct);

        ArrayAdapter adapterCatalog = new ArrayAdapter(this, android.R.layout.simple_spinner_item,cata);
        adapterCatalog.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCatalog);
    }

    public boolean checkExist(Catalog catalog, Product product){
        for(int i = 0; i < catalog.getProducts().size(); i++){
            if (catalog.getProducts().get(i).getId().equals(product.getId())){
                Toast.makeText(this, "Exist!", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    public String[] addProductToView(ArrayList<Product> products){
        if (products != null){

            String[] list = new String[products.size()];
            for (int i = 0; i < products.size(); i++){
                list[i] = products.get(i).toString();
            }
            return list;
        }
        return new String[]{"null"};
    }

}