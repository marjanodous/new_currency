package com.example.currencyconvertnew;
/*support telgram id =@javaprogrammer_eh
 * 04/02/1398
 * creted by elmira hossein zadeh*/

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    String url = "https://www.megaweb.ir/api/money";
    Button btn_return, btn_calculate;
    EditText et_value;
    Spinner spin;
    ArrayList<StoreList>  arraySpin;
    public int  spinnerId;
    String  namespinner;
    public static int priceUsd;
    public static int priceEuro;
    public static String titleUsd, jdateUsd, gdateUsd;
    public static String titleEuro, jdateEuro, gdateEuro;
    public static int ResultRialToUSD;
    public static int ResultUsdToRial;
    public static int ResultRialToEuro;
    public static int ResultUsdToEuro;
    public static int ResultEuroToRial;
    public static int ResultEuroToUsd;
    public int valuePrice;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();
        btn_calculate = findViewById(R.id.btn_convert);
        btn_return = findViewById(R.id.btn_return);
        et_value = findViewById(R.id.edt_price);
        spin = findViewById(R.id.spin_first);


        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_value.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "لطفا مقداری برای تبدیل وارد نمایید", Toast.LENGTH_SHORT).show();
                } else if (spinnerId == 0) {
                    Toast.makeText(MainActivity.this, "لطفا ابتدا واحد های تبدیل مورد نظر را انتخاب نمایید", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, " انتخاب درست", Toast.LENGTH_SHORT).show();

                    if (namespinner.equals("انتخاب کنید")) {
                        et_value.setText("");
                    } else if (namespinner.equals("ریال به دلار")) {
                        valuePrice = Integer.parseInt(et_value.getText().toString());
                        calcRialToUsd(valuePrice);
                        btn_return.setText(String.valueOf(ResultRialToUSD));
                    }
                    else if (namespinner.equals("ریال به یورو")) {
                        valuePrice = Integer.parseInt(et_value.getText().toString());
                        calcRialToEuro(valuePrice);
                        btn_return.setText(String.valueOf(ResultRialToEuro));
                    }else if (namespinner.equals("دلار به ریال")) {
                        valuePrice = Integer.parseInt(et_value.getText().toString());
                        calcUsdToRial(valuePrice);
                        btn_return.setText(String.valueOf(ResultUsdToRial));
                    } else if (namespinner.equals("دلار به یورو")) {
                        valuePrice = Integer.parseInt(et_value.getText().toString());
                        calcUsdToEuro(valuePrice);
                        btn_return.setText(String.valueOf(ResultUsdToEuro));
                    } else if (namespinner.equals("یورو به ریال")) {
                        valuePrice = Integer.parseInt(et_value.getText().toString());
                        calcEuroToRial(valuePrice);
                        btn_return.setText(String.valueOf(ResultEuroToRial));
                    } else if (namespinner.equals("یورو به دلار")) {
                        valuePrice = Integer.parseInt(et_value.getText().toString());
                        calcEuroToUsd(valuePrice);
                        btn_return.setText(String.valueOf(ResultEuroToUsd));
                    }
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_value.getWindowToken(),0);
            }
        });

        arraySpin = new ArrayList<>();
        arraySpin.add(new StoreList("0", "انتخاب کنید"));
        arraySpin.add(new StoreList("1", "ریال به دلار"));
        arraySpin.add(new StoreList("2", "ریال به یورو"));
        arraySpin.add(new StoreList("3", "دلار به ریال"));
        arraySpin.add(new StoreList("4", "دلار به یورو"));
        arraySpin.add(new StoreList("5", "یورو به ریال"));
        arraySpin.add(new StoreList("6", "یورو به دلار"));

        ArrayAdapter<StoreList> arrayAdapter = new ArrayAdapter<StoreList>(this, R.layout.sppiner,arraySpin);
        spin.setAdapter(arrayAdapter);
        spin.setDropDownVerticalOffset(100);

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spin);

            // Set popupWindow height to 500px
            popupWindow.setHeight(350);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView2, View selectedItemView2, int position2, long id2) {
                StoreList list2 = (StoreList) parentView2.getSelectedItem();
                spinnerId = Integer.parseInt(list2.getId());
                namespinner = list2.getName();
                if (namespinner.equals("انتخاب کنید")) {
                    Toast.makeText(MainActivity.this, "لطفا ابتدا واحد های تبدیل مورد نظر را انتخاب نمایید", Toast.LENGTH_SHORT).show();
                    et_value.setText("");
                    btn_return.setText("");
                } else if (namespinner.equals("ریال به دلار")) {
                    et_value.setText("ریال");
                    btn_return.setText("دلار");
                } else if (namespinner.equals("ریال به یورو")) {
                    et_value.setText("ریال");
                    btn_return.setText("یورو");
                }else if (namespinner.equals("دلار به ریال")) {
                    et_value.setText("دلار");
                    btn_return.setText("ریال");
                } else if (namespinner.equals("دلار به یورو")) {
                    et_value.setText("دلار");
                    btn_return.setText("یورو");
                } else if (namespinner.equals("یورو به ریال")) {
                    et_value.setText("یورو");
                    btn_return.setText("ریال");
                } else if (namespinner.equals("یورو به دلار")) {
                    et_value.setText("یورو");
                    btn_return.setText("دلار");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        et_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_value.setText("");


            }
        });
    }

    public void calcRialToUsd(int rial) {
        int dular = priceUsd;
        int Rial = rial;
        ResultRialToUSD = Rial / dular;
    }

    public void calcRialToEuro(int rial) {
        int euro = priceEuro;
        int Rial = rial;
        ResultRialToEuro = Rial / euro;
    }

    public void calcUsdToRial(int dolar) {
        int pric = priceUsd;
        int usd = dolar;
        ResultUsdToRial = usd * pric;
    }

    public void calcUsdToEuro(int dolar) {
        int pric = priceUsd;
        int usd = dolar;
        int valueUsdToRial = usd * pric;
        int pic2 = priceEuro;
        ResultUsdToEuro=valueUsdToRial/pic2;
    }

    public void calcEuroToRial(int euro) {
        int pric = priceEuro;
        int Euro = euro;
        ResultEuroToRial = pric * Euro;
    }

    public void calcEuroToUsd(int euro) {
        Integer pric = priceEuro;
        int Euro = euro;
        int valueEuroToRial = pric * Euro;
        int pic2 = priceUsd;
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
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(MainActivity.this, "لطفا اینترنت خود رابررسی نمایید", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(MainActivity.this, "خطایی در سمت سرور اتفاق افتاد", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(MainActivity.this, "خطای شبکه", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(MainActivity.this, "خطا در دریافت اطلاعات", Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleTone.getmInstance(getApplicationContext()).addToRequestQue(stringRequest);
    }

    public void parsData(JSONObject jsonObject) {
        try {
            JSONObject buy_usdObject = jsonObject.getJSONObject("buy_usd");
            JSONObject buy_eurObject = jsonObject.getJSONObject("buy_eur");
            String[] priceAraaayUsd = buy_usdObject.getString("price").split(",");
            String priceStringUsd = priceAraaayUsd[0] + priceAraaayUsd[1];
            priceUsd = Integer.parseInt(priceStringUsd);
            titleUsd = buy_usdObject.getString("title");
            jdateUsd = buy_usdObject.getString("jdate");
            gdateUsd = buy_usdObject.getString("gdate");
            String[] priceArarayEuro = buy_eurObject.getString("price").split(",");
            String priceStringEro = priceArarayEuro[0] + priceArarayEuro[1];
            priceEuro = Integer.parseInt(priceStringEro);
            titleEuro = buy_eurObject.getString("title");
            jdateEuro = buy_eurObject.getString("jdate");
            gdateEuro = buy_eurObject.getString("gdate");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
