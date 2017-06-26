package com.example.ncku.e_medical;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity{
    private List<CheckBox> checkboxes = new ArrayList<>();
    private RadioGroup rgroup;
    private TextView tv_response;
    private TextView tv_case;
    private TextView tv_device;
    private TextView tv_similartity;
    private Button btnRun;
    private ListView lv_hospital_list;

    String strData;
    JSONObject json_obj;

    private String serverURL = "http://192.168.72.79:1880/postPatient";

    private HospitalAdapter itemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkboxes.add((CheckBox) findViewById(R.id.cb_a));
        checkboxes.add((CheckBox) findViewById(R.id.cb_b));
        checkboxes.add((CheckBox) findViewById(R.id.cb_c));
        checkboxes.add((CheckBox) findViewById(R.id.cb_d));
        checkboxes.add((CheckBox) findViewById(R.id.cb_e));
        checkboxes.add((CheckBox) findViewById(R.id.cb_f));

        rgroup = (RadioGroup) findViewById(R.id.rgPrefer);
        //tv_response = (TextView) findViewById(R.id.tv_response);
        tv_case = (TextView) findViewById(R.id.tv_case);
        tv_device = (TextView) findViewById(R.id.tv_device);
        tv_similartity = (TextView) findViewById(R.id.tv_similartity);

        btnRun = (Button) findViewById(R.id.btnRun);
        lv_hospital_list = (ListView) findViewById(R.id.lv_hospital_list);

    }


    public void RunResult(View view) {
        String cb_list = "";
        for (CheckBox c : checkboxes) {
            if (c.isChecked()) cb_list += c.getText();
        }

        RadioButton rb = (RadioButton) findViewById(rgroup.getCheckedRadioButtonId());

        if (cb_list == "" || rb == null) {
            Toast.makeText(this, "please choose", Toast.LENGTH_LONG).show();
        } else {
            GPSTracker gps;
            double latitude = 22.997108 , longitude = 120.221494;

            //使用模擬器不需要以下
            /*gps = new GPSTracker(this);
            if(gps.canGetLocation()){
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
            }*/
            //使用模擬器不需要以上

            strData = "{ symptom: \"" + cb_list + "\", prefer: \"" + rb.getText() + "\"}";

            json_obj = new JSONObject();
            try {
                json_obj.put("symptom", cb_list);
                json_obj.put("prefer", rb.getText());
                json_obj.put("lat_lng", latitude+","+longitude);
            } catch (JSONException e) {
                e.printStackTrace();
            }




            new LoadList().execute();
        }
    }

    class LoadList extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection=null;
            try {
                URL url=new URL(serverURL);
                connection =(HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/form-data");
                connection.setConnectTimeout(30 * 1000);
                connection.setReadTimeout(5000);
                connection.setDoOutput(true);
                connection.connect();

                //Send data
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(json_obj.toString());
                wr.flush();
                wr.close();

                //Read response
                InputStream in=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                StringBuilder response=new StringBuilder();
                String line;
                while((line=reader.readLine())!=null){
                    response.append(line);
                }

                return response.toString();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                if(connection!=null){
                    connection.disconnect();
                }
            }
            return null;
        }

        protected void onPostExecute(String s) {

            JSONObject jsonObject;
            JSONArray jsonArray;
            String[][] data;
            try {
                jsonObject = new JSONObject(s);
                tv_case.setText(jsonObject.getString("disease_name"));
                tv_device.setText(jsonObject.getString("device_name"));
                tv_similartity.setText(jsonObject.getString("similartity")+" %");
                //tv_response.setText(jsonObject.getString("data"));

                jsonArray = jsonObject.getJSONArray("data");
                data = new String[jsonArray.length()][7];

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    data[i][0] = json.getString("iddevice");
                    data[i][1] = json.getString("hospital_name");
                    data[i][2] = json.getString("quantity");
                    data[i][3] = json.getString("cost");
                    data[i][4] = json.getString("level");
                    data[i][5] = json.getString("distance")+" 公尺";
                    //data[i][5] = String.valueOf(jsonArray.length());
                    data[i][6] = json.getString("address");
                }

                setListView(jsonArray.length(), data);
                setListViewHeightBasedOnChildren(lv_hospital_list);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void setListView(int length, final String[][] data){
        List<HospitalAdapter.Item> items = new ArrayList<HospitalAdapter.Item>();
        for(int i = 0; i < length; i++){
            items.add(new HospitalAdapter.Item(data[i][0], data[i][1], data[i][2], data[i][3], data[i][4], data[i][5], data[i][6]));
        }


        itemAdapter = new HospitalAdapter(this, R.layout.hospital_list_item, items);
        itemAdapter.notifyDataSetChanged();
        lv_hospital_list.setAdapter(itemAdapter);
        lv_hospital_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    //建立函數將dp轉換為像素
    public int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    //建立函數動態設定ListView的高度
    public void setListViewHeightBasedOnChildren(ListView listView) {
        //取得ListView的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        //固定每一行的高度
        int itemHeight = 114;
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            totalHeight += Dp2Px(this,itemHeight)+listView.getDividerHeight();
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if(totalHeight > (metrics.heightPixels*0.5))
            totalHeight = (int) (metrics.heightPixels*0.5);

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;

        listView.setLayoutParams(params);
    }




}


