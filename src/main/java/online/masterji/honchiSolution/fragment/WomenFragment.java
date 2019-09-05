package online.masterji.honchiSolution.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.BookActivity;
import online.masterji.honchiSolution.activity.MainActivity;

public class WomenFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText etPrice, etPricee;
    Button btnNextMm, btnNextM, btnBackM;
    ImageView image;
    Spinner SpinnerWork, SpinnerDesignn, SpinnerWorkk, SpinnerDesign, SpinnerCategory, SpinnerFabric;
    List<String> Work = new ArrayList<>();
    List<String> Fabric = new ArrayList<>();
    List<String> Category = new ArrayList<>();
    List<String> Design = new ArrayList<>();
    ArrayAdapter<String> spinnerAdapter;
    String itemWork, itemFabric;
    LinearLayout layoutAlter, layoutSich, Pric;
    String work, febric, category, design, price, price1;

    public WomenFragment() {
        // Required empty public constructor
    }

    public static WomenFragment newInstance(String param1, String param2) {
        WomenFragment fragment = new WomenFragment();
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
        View view = inflater.inflate(R.layout.fragment_women, container, false);
        Design = new ArrayList<>();
        Design.add("Select Design");
        Design.add("Upload Design");
        Design.add("No Design");

        Category = new ArrayList<>();
        Category.add("Shirts");
        Category.add("Skirts");
        Category.add("Pant And Trousers");
        Category.add("Blouses");
        Category.add("Gowns");
        Category.add("Jacket And Waistcoat");
        Category.add("Kurtis");
        Category.add("Lehengas");
        Category.add("Sharara Suit");
        Category.add("Suit And Salwar");

        Fabric = new ArrayList<>();
        Fabric.add("Fabric");
        Fabric.add("No Fabric");


        Work = new ArrayList<String>();
        Work.add("Stitching");
        Work.add("Alteration");
/*

        btnNextM = view.findViewById(R.id.btnNextM);
        btnNextMm = view.findViewById(R.id.btnNextMm);
        btnNextMm.setOnClickListener(this);
        btnNextM.setOnClickListener(this);
        btnBackM = view.findViewById(R.id.btnBackM);
        btnBackM.setOnClickListener(this);

        etPrice = view.findViewById(R.id.etPrice);
        etPricee = view.findViewById(R.id.etPricee);
        layoutAlter = view.findViewById(R.id.layoutAlter);
        layoutSich = view.findViewById(R.id.layoutSich);
        Pric = view.findViewById(R.id.Pric);

        SpinnerWorkk = (Spinner) view.findViewById(R.id.SpinnerWorkk);
        SpinnerWorkkData();
        SpinnerDesignn = (Spinner) view.findViewById(R.id.SpinnerDesignn);
        SpinnerDesignnData();

        SpinnerDesign = (Spinner) view.findViewById(R.id.SpinnerDesign);
        SpinnerDesignData();
        SpinnerCategory = (Spinner) view.findViewById(R.id.SpinnerCategory);
        SpinnerCategoryData();
        SpinnerWork = (Spinner) view.findViewById(R.id.SpinnerWork);
        SpinnerWorkData();
        SpinnerFabric = (Spinner) view.findViewById(R.id.SpinnerFabric);
        SpinnerFabricData();

        //add on item selected listerners to spinner

*/

        return view;
    }


    private void SpinnerDesignData() {


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item, Design);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        SpinnerDesign.setAdapter(dataAdapter);
        SpinnerDesign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                design = item;

                // Showing selected spinner item
                // Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void SpinnerDesignnData() {


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item, Design);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        SpinnerDesignn.setAdapter(dataAdapter);
        SpinnerDesignn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                SpinnerDesignn.setSelection(i);
                // Showing selected spinner item
                // Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void SpinnerCategoryData() {


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item, Category);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        SpinnerCategory.setAdapter(dataAdapter);
        SpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                category = item;
                // Showing selected spinner item
                //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void SpinnerFabricData() {

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item, Fabric);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        SpinnerFabric.setAdapter(dataAdapter);
        SpinnerFabric.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                febric = item;
                // Showing selected spinner item
                //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void SpinnerWorkData() {


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item, Work);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        SpinnerWork.setAdapter(dataAdapter);

        SpinnerWork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                work = item;
                if (item.equals("Alteration")) {
                    SpinnerDesignn.setSelection(1);

                    layoutAlter.setVisibility(View.VISIBLE);
                    layoutSich.setVisibility(View.INVISIBLE);
                    Pric.setVisibility(View.INVISIBLE);
                } else if (item.equals("Stitching")) {
                    SpinnerDesignn.setSelection(0);

                    layoutAlter.setVisibility(View.INVISIBLE);
                    layoutSich.setVisibility(View.VISIBLE);
                    Pric.setVisibility(View.VISIBLE);
                }
                // Showing selected spinner item
                //  Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (work != null) {
            int spinnerPosition = dataAdapter.getPosition(work);
            SpinnerWork.setSelection(spinnerPosition);
        }
    }

    private void SpinnerWorkkData() {


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item, Work);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        SpinnerWorkk.setAdapter(dataAdapter);
        SpinnerWorkk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                work = item;
                if (item.equals("Alteration")) {

                    layoutAlter.setVisibility(View.VISIBLE);
                    Pric.setVisibility(View.INVISIBLE);
                    layoutSich.setVisibility(View.INVISIBLE);
                } else if (item.equals("Stitching")) {
                    layoutAlter.setVisibility(View.INVISIBLE);
                    layoutSich.setVisibility(View.VISIBLE);
                    Pric.setVisibility(View.VISIBLE);
                }
                // Showing selected spinner item
                //  Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (work != null) {
            int spinnerPosition = dataAdapter.getPosition(work);
            SpinnerWorkk.setSelection(spinnerPosition);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
           /* case R.id.btnBackM:
                startActivity(new Intent(getContext(), MainActivity.class));

                break;*/
            case R.id.btnNextM:
                price = etPrice.getText().toString().trim();
                price1 = etPricee.getText().toString().trim();

                Intent intent = new Intent(getContext(), BookActivity.class);
                intent.putExtra("work", work);
                intent.putExtra("febric", febric);
                intent.putExtra("category", category);
                intent.putExtra("design", design);
                intent.putExtra("price", price);
                intent.putExtra("price1", price1);

                startActivity(intent);

                break;
           /* case R.id.btnNextMm:
                price = etPrice.getText().toString().trim();
                price1 = etPricee.getText().toString().trim();

                Intent intent1 = new Intent(getContext(), BookActivity.class);
                intent1.putExtra("work", work);
                intent1.putExtra("febric", febric);
                intent1.putExtra("category", category);
                intent1.putExtra("design", design);
                intent1.putExtra("price", price);
                intent1.putExtra("price1", price1);

                startActivity(intent1);

                break;*/
        }
    }
}
