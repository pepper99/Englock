package th.ac.bodin.ppnt.englock;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import th.ac.bodin.ppnt.englock.extra_assets.CustomFreeShopAdapter;
import th.ac.bodin.ppnt.englock.extra_assets.FreeShopDialog;
import th.ac.bodin.ppnt.englock.extra_assets.FreeShopProduct;

public class FreeShopMenu extends Activity {

    private ViewStub stubGrid;
    private GridView gridView;
    private CustomFreeShopAdapter gridViewAdapter;
    private List<FreeShopProduct> productList;
    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.freeshopmenu);

        this.activity = this;

        stubGrid = (ViewStub)findViewById(R.id.freeshop_stub_grid);
        stubGrid.inflate();

        gridView = (GridView)findViewById(R.id.freeshop_gridview);

        getProductList();

        gridView.setOnItemClickListener(onItemClick);
        //Display gridview
        stubGrid.setVisibility(View.VISIBLE);

        gridViewAdapter = new CustomFreeShopAdapter(this, R.layout.freeshop_griditem, productList);
        gridView.setAdapter(gridViewAdapter);
    }

    private List<FreeShopProduct> getProductList() {
        //pseudo code to get product, replace your code to get real product here

        productList = new ArrayList<>();
        productList.add(new FreeShopProduct(R.drawable.db_school_small, getResources().getString(R.string.shopItem0)));
        productList.add(new FreeShopProduct(R.drawable.db_clothes_small, getResources().getString(R.string.shopItem1)));
        productList.add(new FreeShopProduct(R.drawable.db_emotions_small, getResources().getString(R.string.shopItem2)));
        productList.add(new FreeShopProduct(R.drawable.db_etiquette_small, getResources().getString(R.string.shopItem3)));
        productList.add(new FreeShopProduct(R.drawable.db_landforms_small, getResources().getString(R.string.shopItem4)));
        productList.add(new FreeShopProduct(R.drawable.db_fruits_small, getResources().getString(R.string.shopItem5)));
        productList.add(new FreeShopProduct(R.drawable.db_kingdom_small, getResources().getString(R.string.shopItem6)));
        productList.add(new FreeShopProduct(R.drawable.db_outerspace_small, getResources().getString(R.string.shopItem7)));
        productList.add(new FreeShopProduct(R.drawable.db_festival_small, getResources().getString(R.string.shopItem8)));
        productList.add(new FreeShopProduct(R.drawable.db_music_small, getResources().getString(R.string.shopItem9)));

        return productList;
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Do any thing when user click to item

            String AlertTxt = "\"" + productList.get(position).getTitle() + "\"\n" + getResources().getString(R.string.freeques);

            FreeShopDialog alert = new FreeShopDialog.Builder()
                    .setMessage(AlertTxt)
                    .setImage(productList.get(position).getImageId())
                    .setYesButton(R.string.yes)
                    .setNoButton(R.string.no)
                    .setLayout(R.layout.shop_box)
                    .setPosition(position)
                    .build();
            alert.setActivity(activity);
            alert.show(getFragmentManager(), "FREESHIT");
        }
    };

    @Override
    public void onBackPressed() {
        return; //Do nothing!
    }
}
