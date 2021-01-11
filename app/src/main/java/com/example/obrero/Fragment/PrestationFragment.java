package com.example.obrero.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.obrero.ApiService;
import com.example.obrero.Model.Categories;
import com.example.obrero.R;
import com.example.obrero.RetrofitInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class PrestationFragment extends Fragment {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";
    private String test5;

    private String test3;
    private String test4;
    private String test1;
    private String test2;

    EditText tarifP;
    EditText nomP;
    EditText descP;
    EditText photoP;
    Button supp1;
    Button supp2;
    Spinner dropdown;
    Button vald;
    Button comm;
    private static final int PICK_IMAGE = 1;
    Bitmap mBitmap;
    Uri imageUri;
    ApiService apiService;
    String UploadedImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_prestation, container, false);
        return v;

    }

    private void initRetrofitClient() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        apiService = new Retrofit.Builder().baseUrl(BASE_URL).client(client).build().create(ApiService.class);
    }

    private void multipartImageUpload() {
        try {
            File filesDir = getActivity().getFilesDir();
            File file = new File(filesDir, "image" + ".png");

            OutputStream os;
            try {
                os = new FileOutputStream(file);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();


            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();


            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");

            Call<ResponseBody> req = apiService.postImage(body, name);
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {


                    if (response.code() == 200) {
                        Toast.makeText(getActivity(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                        try {
                            UploadedImage =response.body().string();
                            System.out.println("Uploaded Image name :"+ UploadedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    Toast.makeText(getActivity(), response.code() + "test", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    Toast.makeText(getActivity(), "Request failed", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            final InputStream imageStream;
            try {
                imageStream = getContext().getContentResolver().openInputStream(imageUri);
                mBitmap = BitmapFactory.decodeStream(imageStream);
                EditText imgName = getActivity().findViewById(R.id.photoP);
                imgName.setText(imageUri.getPath());
                imgName.setEnabled(false);
                multipartImageUpload();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }



        }

        else {
            Toast.makeText(getActivity(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        supp1= getActivity().findViewById(R.id.pres);
        supp2= getActivity().findViewById(R.id.supp);
        tarifP= getActivity().findViewById(R.id.tarifP);
        nomP= getActivity().findViewById(R.id.nomP);
        descP= getActivity().findViewById(R.id.descP);
        photoP= getActivity().findViewById(R.id.photoP);
        ImageView iv = getActivity().findViewById(R.id.pickimage);
        vald= getActivity().findViewById(R.id.vald);

        Bundle bundle1 = this.getArguments();
        int i = bundle1.getInt("key");

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        List<String> list = new ArrayList<String>();
        Call<List<Categories>> call = retrofitInterface.getCategories();

        call.enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {


                if (response.code() == 200) {
                    List<Categories> categories = response.body();

                    for (Categories post : categories) {


                        test5 = post.getNom();
                        list.add(test5);

                        dropdown = getActivity().findViewById(R.id.spinner1);

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, list);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dropdown.setAdapter(adapter);

                    }


                    if (response.code() == 404) {
                        Toast.makeText(getActivity(),
                                "erreur", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {
                Toast.makeText(getActivity(),
                        "erreur", Toast.LENGTH_LONG).show();
            }
        });



        initRetrofitClient();

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
              /*  Toast toast = Toast.makeText(AddCampaign.this, "Working", Toast.LENGTH_SHORT);
                toast.show();*/
            }
        });



        vald.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();

                test1 = nomP.getText().toString();
                map.put("Nom", test1);

                test2= descP.getText().toString();
                map.put("description", test2);

                test3 = tarifP.getText().toString();
                map.put("tarif", test3);

                test4= photoP.getText().toString();
                map.put("img", UploadedImage);

                map.put("nomC", dropdown.getSelectedItem().toString());
                //get the spinner from the xml.

                if (test1.equals("")){Toast.makeText(getActivity(),
                        "Verfifier votre Nom", Toast.LENGTH_LONG).show();}

                else if (test2.equals("")){Toast.makeText(getActivity(),
                        "Verfifier votre description", Toast.LENGTH_LONG).show();}

                else if (test3.equals("")){Toast.makeText(getActivity(),
                        "Verfifier votre tarif", Toast.LENGTH_LONG).show();}



                else {

                    Call<Void> call = retrofitInterface.addPrestation(map,i);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.code() == 200) {
                                Toast.makeText(getActivity(),
                                        "Prestation ajouter", Toast.LENGTH_LONG).show();
                            } else if (response.code() == 404) {
                                Toast.makeText(getActivity(),
                                        "err", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });}


            }
        });




    }



}
