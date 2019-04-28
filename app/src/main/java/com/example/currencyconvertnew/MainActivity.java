package com.example.currencyconvertnew;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    String url = "https://www.megaweb.ir/api/money";
    Button btn_return, btn_calculate;
    EditText et_value;
    Spinner spin_first, spin_secound;
    ArrayList<StoreList> arraySpin_first, arraySpin_secound;
    public int spinner1Id, spinner2Id;
    String namespinner1, namespinner2;
    public static float priceUsd;
    public static float priceEuro;
    public static String titleUsd, jdateUsd, gdateUsd;
    public static String titleEuro, jdateEuro, gdateEuro;
    public static float ResultRialToUSD;
    public static float ResultUsdToRial;
    public static float ResultRialToEuro;
    public static float ResultUsdToEuro;
    public static float ResultEuroToRial;
    public static float ResultEuroToUsd;
    public int valuePrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();

        btn_calculate = findViewById(R.id.btn_convert);
        btn_return = findViewById(R.id.btn_return);
        et_value = findViewById(R.id.edt_price);
        spin_first = findViewById(R.id.spin_secound);
        spin_secound = findViewById(R.id.spin_first);


        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_value.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "لطفا مقداری برای تبدیل وارد نمایید", Toast.LENGTH_SHORT).show();
                } else if (spinner1Id == 0 || spinner2Id == 0) {
                    Toast.makeText(MainActivity.this, "لطفا ابتدا واحد های تبدیل مورد نظر را انتخاب نمایید", Toast.LENGTH_SHORT).show();
                } else if (spinner1Id == spinner2Id) {
                    Toast.makeText(MainActivity.this, "دو واحد پولی نمیتوانند یکسان باشند", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, " انتخاب درست", Toast.LENGTH_SHORT).show();

                    if (namespinner1.equals("ریال") && namespinner2.equals("دلار")) {
                        valuePrice = Integer.parseInt(et_value.getText().toString());
                        calcRialToUsd(valuePrice);
                        btn_return.setText(String.valueOf(ResultRialToUSD));
                        Log.i("spinner1RialToDolarOKKK", namespinner1);
                        Log.i("spinner2RialToDolarOKKK", namespinner2);
                    } else if (namespinner1.equals("ریال") && namespinner2.equals("یورو")) {
                        valuePrice = Integer.parseInt(et_value.getText().toString());
                        calcRialToEuro(valuePrice);
                        btn_return.setText(String.valueOf(ResultRialToEuro));
                        Log.i("spinner1RialToEurookkkk", namespinner1);
                        Log.i("spinner2RialToEurookkkk", namespinner2);
                    } else if (namespinner1.equals("دلار") && namespinner2.equals("ریال")) {
                        valuePrice = Integer.parseInt(et_value.getText().toString());
                        calcUsdToRial(valuePrice);
                        btn_return.setText(String.valueOf(ResultUsdToRial));
                        Log.i("spinner1DolarToRialOKKK", namespinner1);
                        Log.i("spinner2DolarToRialOKKK", namespinner2);
                    } else if (namespinner1.equals("دلار") && namespinner2.equals("یورو")) {
                        valuePrice = Integer.parseInt(et_value.getText().toString());
                        calcUsdToEuro(valuePrice);
                        btn_return.setText(String.valueOf(ResultUsdToEuro));
                        Log.i("spinner1DolarToEuroOKKK", namespinner1);
                        Log.i("spinner2DolarToEuroOKKK", namespinner2);
                    } else if (namespinner1.equals("یورو") && namespinner2.equals("ریال")) {
                        valuePrice = Integer.parseInt(et_value.getText().toString());
                        calcEuroToRial(valuePrice);
                        btn_return.setText(String.valueOf(ResultEuroToRial));
                        Log.i("spinner1UroToRialOKKKKK", namespinner1);
                        Log.i("spinner2UroToRialOKKKKK", namespinner2);
                    } else if (namespinner1.equals("یورو") && namespinner2.equals("دلار")) {
                        valuePrice = Integer.parseInt(et_value.getText().toString());
                        calcEuroToUsd(valuePrice);
                        btn_return.setText(String.valueOf(ResultEuroToUsd));
                        Log.i("spinner1EuroToDolarOKKK", namespinner1);
                        Log.i("spinner2EuroToDolarOKKK", namespinner2);
                    }
                }
            }
        });

        arraySpin_first = new ArrayList<>();
        arraySpin_first.add(new StoreList("0", "انتخاب کنید"));
        arraySpin_first.add(new StoreList("1", "ریال"));
        arraySpin_first.add(new StoreList("2", "دلار"));
        arraySpin_first.add(new StoreList("3", "یورو"));
        ArrayAdapter<StoreList> arrayAdapterSpin_first = new ArrayAdapter<StoreList>(MainActivity.this, android.R.layout.simple_spinner_item, arraySpin_first);
        arrayAdapterSpin_first.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_first.setAdapter(arrayAdapterSpin_first);


        arraySpin_secound = new ArrayList<>();
        arraySpin_secound.add(new StoreList("0", "انتخاب کنید"));
        arraySpin_secound.add(new StoreList("1", "ریال"));
        arraySpin_secound.add(new StoreList("2", "دلار"));
        arraySpin_secound.add(new StoreList("3", "یورو"));
        ArrayAdapter<StoreList> arrayAdapterSpin_secound = new ArrayAdapter<StoreList>(MainActivity.this, android.R.layout.simple_spinner_item, arraySpin_secound);
        arrayAdapterSpin_secound.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_secound.setAdapter(arrayAdapterSpin_secound);


        spin_first.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView1, View selectedItemView1, int position1, long id1) {
                StoreList list1 = (StoreList) parentView1.getSelectedItem();
                spinner1Id = Integer.parseInt(list1.getId());
                namespinner1 = list1.getName();
                Log.i("spinnerFirrrrrrrrrst", String.valueOf(spinner1Id));
                Log.i("nameSpinner1111111111", namespinner1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        spin_secound.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView2, View selectedItemView2, int position2, long id2) {
                StoreList list2 = (StoreList) parentView2.getSelectedItem();
                spinner2Id = Integer.parseInt(list2.getId());
                namespinner2 = list2.getName();
                Log.i("spinnersecooooooond", String.valueOf(spinner2Id));
                Log.i("nameSpinner22222222222", namespinner2);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }
    public void calcRialToUsd(int rial) {
        float dular = priceUsd;
        int Rial = rial;
        ResultRialToUSD = Rial / dular;
    }

    public void calcRialToEuro(int rial) {
        float euro = priceEuro;
        int Rial = rial;
        ResultRialToEuro = Rial / euro;
    }

    public void calcUsdToRial(int dolar) {
        float pric = priceUsd;
        int usd = dolar;
        ResultUsdToRial = usd * pric;
    }

    public void calcUsdToEuro(int dolar) {
        float pric = priceUsd;
        int usd = dolar;
        float valueUsdToRial = usd * pric;
        float pic2 = priceEuro;
        ResultUsdToEuro=valueUsdToRial/pic2;
    }

    public void calcEuroToRial(int euro) {
        float pric = priceEuro;
        int Euro = euro;
        ResultEuroToRial = pric * Euro;
    }

    public void calcEuroToUsd(int euro) {
        float pric = priceEuro;
        int Euro = euro;
        float valueEuroToRial = pric * Euro;
        float pic2 = priceUsd;
        ResultEuroToUsd = valueEuroToRial / pic2;
    }

    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            parsData(jsonObject);
                        } catch (Exception e) {
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        VolleySingleTone.getmInstance(getApplicationContext()).addToRequestQue(stringRequest);
    }

    public void parsData(JSONObject jsonObject) {

        try {
            JSONObject buy_usdObject = jsonObject.getJSONObject("buy_usd");
            JSONObject buy_eurObject = jsonObject.getJSONObject("buy_eur");

            ///JsonUsd
            String[] priceAraaayUsd = buy_usdObject.getString("price").split(",");
            String priceStringUsd = priceAraaayUsd[0] + priceAraaayUsd[1];
            priceUsd = Float.parseFloat(priceStringUsd);
            titleUsd = buy_usdObject.getString("title");
            // String price = buy_usdObject.getString("price");
            jdateUsd = buy_usdObject.getString("jdate");
            gdateUsd = buy_usdObject.getString("gdate");

            //JsonEuro
            String[] priceArarayEuro = buy_eurObject.getString("price").split(",");
            String priceStringEro = priceArarayEuro[0] + priceArarayEuro[1];
            priceEuro = Float.parseFloat(priceStringEro);
            titleEuro = buy_eurObject.getString("title");
            //  String price = buy_usdObject.getString("price");
            jdateEuro = buy_eurObject.getString("jdate");
            gdateEuro = buy_eurObject.getString("gdate");


//            Log.i("nameEurooooooooooooooo", titleEuro);
//            Log.i("nameEurooooooooooooooo", priceStringEro);
//            Log.i("nameEurooooooooooooooo", jdateEuro);
//            Log.i("nameEurooooooooooooooo", gdateEuro);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
