package com.example.krishiapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment3 extends Fragment {

    static TextView cropinfo,mininfo,maxinfo,upddate;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment3 newInstance(String param1, String param2) {
        fragment3 fragment = new fragment3();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_fragment3, container, false);

        cropinfo = v.findViewById(R.id.cropresult);
        mininfo = v.findViewById(R.id.minresult);
        maxinfo = v.findViewById(R.id.maxresult);
        upddate = v.findViewById(R.id.updatedate);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET,
                "https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b&format=json&offset=0"
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                    try {
                        cropinfo.append("\n\n\nCROP \n\n");
                        mininfo.append("\n\n\nMIN \n\n");
                        maxinfo.append("\n\n\nMAX \n\n");

                        cropinfo.append("\n \n\n");
                        mininfo.append("\n/100Kg \n\n");
                        maxinfo.append("\n/100Kg \n\n");
                        String date = response.getString("updated_date");
                        String substr = date.substring(0,10);
                        upddate.append(substr);

                        for(int i=0;i<=10;i++){
                        String comm = response.getJSONArray("records").getJSONObject(i).getString("commodity");
                        int  min = response.getJSONArray("records").getJSONObject(i).getInt("min_price");
                        int max = response.getJSONArray("records").getJSONObject(i).getInt("max_price");

                        String strmin = String.valueOf(min);
                        String strmax = String.valueOf(max);

                        cropinfo.append(comm +"\n\n");
                        mininfo.append(strmin +"\n\n");
                        maxinfo.append(strmax +"\n\n");
                    }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("myapp","something went wrong");

            }
        });

              requestQueue.add(jsonObjectRequest);

        return v;
    }
}