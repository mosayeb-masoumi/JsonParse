package com.example.tornado.jsonparse;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tornado.jsonparse.Adapter.CustomAdapter;
import com.example.tornado.jsonparse.Model.Person;
import com.example.tornado.jsonparse.Model.Phone;
import com.example.tornado.jsonparse.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   ArrayList<Person> persons = new ArrayList<>();
   CustomAdapter customAdapter;
   ListView lstContactsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstContactsInfo=findViewById(R.id.lstContactsInfo);

        //etelaat dasty jahat test (ghablesh khat code (new getJson(getApplicationContext()).execute();) ra comment mikonim
//        person.add(new Person("","hassan","mb.sdfsf@@gmal.com", "", new Phone("09645865", "","")));

        customAdapter= new CustomAdapter(this,persons);
        lstContactsInfo.setAdapter(customAdapter);




       new getJson(getApplicationContext()).execute();


    }


    private class getJson extends AsyncTask<Void,Void,String>{

        Context context;
        private ProgressDialog progressDialog;

        public getJson(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("please wait ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {

            String Address="http://wiadevelopers.ir/api/contacts.json";



            return Utils.getData(Address);
        }


        @Override
                         //natayeje info haye grefteshode az server dakhele moteghayere jsonStr rikhte mishavad
        protected void onPostExecute(String jsonStr) {

            progressDialog.dismiss();

            if(jsonStr !=null)
            {
               //agar info az server dashtim amaliat parse kardan ro anjam bde
                //try catch estefade mikonim va kodhaye pars kardan ro dakhle try minevisim

                try
                {
                   //neveshtan codhaye parse kardan
                    // tabgh file Json ke az server gereftim az JsonArray va JsonObject Astefade mikonim (movie time 22 min)

                    //jsonobject chun kol etelaat ba { shru shode
                    JSONObject jsonObj= new JSONObject(jsonStr);

                    //json array chun etelaat contact be [ shru shode
                    JSONArray jsonArray= jsonObj.getJSONArray("contacts");

                    //hal ma 13 ta nontact darim ke baraye dastresi be anha az halghe for estefade minkonim
                    for (int i = 0; i <jsonArray.length() ; i++)
                    {
                        //har contact (har kodam az 13 mored) baz ba yek accolade { shoru shode ke bayad az jsonobject estefade konim
                        //pas har abject array marbut be 13contact khodash ziremajmuye jsonarray ast
                        JSONObject c = jsonArray.getJSONObject(i);

                        //hal be mohtaviate har kodam az un 13 mored dastresi darim

                        String id= c.getString("id");
                        String name= c.getString("name");
                        String email = c.getString("email");
                        String address = c.getString("address");

                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");


                        //add kardan mavarede bala be arraylist
                        persons.add(new Person(id,name,email,address, new Phone(mobile,home,office)));

                    }

                    ((BaseAdapter)lstContactsInfo.getAdapter()).notifyDataSetChanged();


                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            else
            {
                Toast.makeText(context, "no data", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
