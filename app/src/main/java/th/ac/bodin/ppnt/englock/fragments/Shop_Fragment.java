package th.ac.bodin.ppnt.englock.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import th.ac.bodin.ppnt.englock.MainActivity;
import th.ac.bodin.ppnt.englock.R;
import th.ac.bodin.ppnt.englock.extra_assets.CustomAdapter;
import th.ac.bodin.ppnt.englock.extra_assets.Product;
import th.ac.bodin.ppnt.englock.extra_assets.ShopDialog;
import th.ac.bodin.ppnt.englock.extra_assets.ViewDialog;

/**
 * A simple {@link Fragment} subclass.
 */

public class Shop_Fragment extends Fragment {

    private ViewStub stubGrid;
    private GridView gridView;
    private CustomAdapter gridViewAdapter;
    private List<Product> productList;
    TextView mTextMessage;
    boolean[] productStatus;

    int n = 10;

    public static Shop_Fragment newInstance() {
        return new Shop_Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop_, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.O){
            TextView coin;
            coin = (TextView) getView().findViewById(R.id.ptnShop);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(coin, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        }

        SharedPreferences shared = getActivity().getSharedPreferences("Englock Points", Context.MODE_PRIVATE);
        boolean isPTsaved = shared.getBoolean("isPTsaved", false);

        if(isPTsaved) updatePoints(shared);
        else createPoints();

        stubGrid = (ViewStub) getView().findViewById(R.id.stub_grid);
        stubGrid.inflate();

        gridView = (GridView) getView().findViewById(R.id.mygridview);

        getProductList();
        this.productStatus = checkProduct();

        gridView.setOnItemClickListener(onItemClick);

        //Display gridview
        stubGrid.setVisibility(View.VISIBLE);

        gridViewAdapter = new CustomAdapter(this.getContext(), R.layout.grid_item, productList);
        gridView.setAdapter(gridViewAdapter);
    }

    private List<Product> getProductList() {
        //pseudo code to get product, replace your code to get real product here
        boolean[] checker = checkProduct();
        boolean[] selecter = checkSelect();
        
        productList = new ArrayList<>();
        productList.add(new Product(R.drawable.db_school_small, "School", "Default", 200, checker[0], selecter[0]));
        productList.add(new Product(R.drawable.db_clothes_small, "Clothes", "400", 400, checker[1], selecter[1]));
        productList.add(new Product(R.drawable.db_emotions_small, "Emotions", "800", 800, checker[2], selecter[2]));
        productList.add(new Product(R.drawable.db_etiquette_small, "Etiquette", "1500", 1500, checker[3], selecter[3]));
        productList.add(new Product(R.drawable.db_landforms_small, "Landforms", "2000", 2000, checker[4], selecter[4]));
        
        return productList;
    }

    private boolean[] checkProduct() {
        boolean[] checker = new boolean[n];

        SharedPreferences shoppee = getActivity().getSharedPreferences("Englock Shop Stats", Context.MODE_PRIVATE);

        for (int i = 0; i < n; i++){
            checker[i] = shoppee.getBoolean("product" + String.valueOf(i), false);
        }

        checker[0] = true;
        return checker;
    }

    private boolean[] checkSelect() {
        boolean[] checker = new boolean[n];

        SharedPreferences shoppee = getActivity().getSharedPreferences("Englock Shop Stats", Context.MODE_PRIVATE);

        int selected = shoppee.getInt("selected", -1);

        for (int i = 0; i < n; i++){
            if( i == selected ) checker[i] = true;
            else checker[i] = false;
        }
        return checker;
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Do any thing when user click to item

            if(productList.get(position).isBought()) {

                SharedPreferences shoppee = getActivity().getSharedPreferences("Englock Shop Stats", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shoppee.edit();
                editor.putInt("selected", position);
                editor.commit();

                String txt = productList.get(position).getTitle() + " is now selected.";

                Toast.makeText(getActivity(), txt, Toast.LENGTH_SHORT).show();
                apply();
            }
            else {
                long price = productList.get(position).getPrice();
                String AlertTxt = "Are you sure you want to buy\n\"" + productList.get(position).getTitle()
                        + "\" for " + String.valueOf(price) + " coins?";
                ShopDialog alert = new ShopDialog.Builder()
                        .setMessage(AlertTxt)
                        .setImage(productList.get(position).getImageId())
                        .setYesButton(R.string.yes)
                        .setNoButton(R.string.no)
                        .setLayout(R.layout.shop_box)
                        .setPosition(position)
                        .setPrice(price)
                        .build();
                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        reloadFt();
                    }
                });
                alert.show(getActivity().getFragmentManager(), "SHOPPEEE");
            }

            //Toast.makeText(getActivity(), productList.get(position).getTitle() + " - " + productList.get(position).getDescription(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onResume() {

        SharedPreferences points = getActivity().getSharedPreferences("Englock Points", Context.MODE_PRIVATE);
        updatePoints(points);

        apply();

        super.onResume();
    }

    private void apply() {
        getProductList();
        this.productStatus = checkProduct();

        gridView.setOnItemClickListener(onItemClick);

        //Display gridview
        stubGrid.setVisibility(View.VISIBLE);

        gridViewAdapter = new CustomAdapter(this.getContext(), R.layout.grid_item, productList);
        gridView.setAdapter(gridViewAdapter);
    }

    //points thingyszzzzzz
    private void createPoints(){
        SharedPreferences points = getActivity().getSharedPreferences("Englock Points", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = points.edit();

        editor.putLong("pointsCount", 0);
        editor.putBoolean("isPTsaved", true);
        editor.commit();

        updatePoints(points);
    }

    private void updatePoints(SharedPreferences points){

        long pts = points.getLong("pointsCount", -1);

        mTextMessage = (TextView)getView().findViewById(R.id.ptnShop);
        if(pts != -1) mTextMessage.setText(String.valueOf(pts));
        else mTextMessage.setText("ERROR");
    }

    public void reloadFt() {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(this);
        fragmentTransaction.attach(this);
        fragmentTransaction.commit();
    }
}
