package com.example.android.criminalintent.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.criminalintent.MapsActivity;
import com.example.android.criminalintent.R;
import com.example.android.criminalintent.model.Crime;
import com.example.android.criminalintent.model.singleton.CrimeLab;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class CrimeFragment extends Fragment {

    public static final String EXTRA_CRIME_ID =
            "com.example.android.criminalintent.crime_id";

    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;
    private static final String TAG = CrimeFragment.class.getSimpleName();

    //New Additions
    public static ArrayList<String> places = new ArrayList<>();
    public static ArrayList<LatLng> locations = new ArrayList<>();
    public static ArrayAdapter adapter;

    private Crime mCrime;
    private EditText mTitleField;
    private EditText mPhoneField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private ImageButton mPhotoButton;

    //Experiment
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
//    private ImageButton btnPhoto;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd hh:mm a", Locale.ENGLISH);
    private TextView locationField;

    public interface DateChangeListener extends Serializable {
        void onDateChanged(Date date);
    }

    public static CrimeFragment newInstance(UUID crimeId) {

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);

        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    private void updateDate() {
        mDateButton.setText(dateFormat.format(mCrime.getDate()));
    }

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        locationField = (TextView)v.findViewById(R.id.text);
        mTitleField.setText(mCrime.getTitle());
        locationField.setText(mCrime.getLocation());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPhoneField = (EditText)v.findViewById(R.id.crime_phone);
        mPhoneField.setText(mCrime.getPhoneNumber());
        mPhoneField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setPhoneNumber(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button)v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            Crime crime;
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate(), new DateChangeListener() {
                    @Override
                    public void onDateChanged(Date date) {
                        Log.d("DATA", "onDateChanged: "+date);

                        mDateButton.setText(dateFormat.format(date));
                        mCrime.setDate(date);
                    }
                });
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        imageView = (ImageView)v.findViewById(R.id.imageView1);
        if (mCrime.getImage() !=null){
            imageView.setImageBitmap(mCrime.getImage());
        }
        mPhotoButton = (ImageButton)v.findViewById(R.id.crime_imageButton);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        Button addPlace = v.findViewById(R.id.crime_add_map);
        addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivityForResult(intent,100);
            }
        });

        // New addition
//        ListView listView = (ListView)v.findViewById(R.id.listView);
//
//        places.add("Add a new place....");
//        locations.add(new LatLng(0, 0));
//
//        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, places);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getContext(), MapsActivity.class);
//                intent.putExtra("placeNumber", position);
//                startActivity(intent);
//            }
//        });

        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == CAMERA_REQUEST )
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mCrime.setImage(photo);
            imageView.setImageBitmap(photo);
        }
        if (requestCode == 100){
            Log.d(TAG, "onActivityResult: "+data.getExtras());
            String location = data.getExtras().getString("LOCATION");
            locationField.setText(location);
            mCrime.setLocation(location);
        }



        if (resultCode == REQUEST_DATE) {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).saveCrimes();
    }


}
