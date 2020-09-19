package savinykh.zlatoslava.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import savinykh.zlatoslava.Adapter.CategoryAdapter;
import savinykh.zlatoslava.Constants.AppConstants;
import savinykh.zlatoslava.Model.CategoryModel;
import savinykh.zlatoslava.R;
import savinykh.zlatoslava.Utility.ActivityUtilities;
import savinykh.zlatoslava.Utility.AppUtilities;

public class MainActivity extends BaseActivity {

    private Activity activity;
    private Context context;

    private Toolbar mToolbar;

    private AccountHeader mHeader = null;
    private Drawer mDrawer = null;

    private ArrayList<CategoryModel> mCategoryList;
    private CategoryAdapter mAdapter = null;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        activity = MainActivity.this;
        context = getApplicationContext();

        mRecyclerView = findViewById(R.id.rvTests);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

        mCategoryList = new ArrayList<>();
        mAdapter = new CategoryAdapter(context, activity, mCategoryList);
        mRecyclerView.setAdapter(mAdapter);

        initLoader();
        loadData();

        final IProfile profile = new ProfileDrawerItem().withIcon(R.drawable.ic_launcher_foreground); //TODO: change

        mHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.ic_launcher_background)
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        ActivityUtilities.getInstance().invokeCustomUrlActivity(activity, CustomUrlActivity.class,
                                getResources().getString(R.string.site), getResources().getString(R.string.site_url), false);
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .addProfiles(profile)
                .build();

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withHasStableIds(true)
                .withAccountHeader(mHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.about_app_title).withIcon(R.drawable.ic_info_black_24dp).withIdentifier(10).withSelectable(false),

                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.settings_title).withIcon(R.drawable.ic_settings_black_24dp).withIdentifier(20).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.rate_title).withIcon(R.drawable.ic_grade_black_24dp).withIdentifier(21).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.share_title).withIcon(R.drawable.ic_share_black_24dp).withIdentifier(22).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.privacy_title).withIcon(R.drawable.ic_license_black_24dp).withIdentifier(23).withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 10) {
                                ActivityUtilities.getInstance().invokeNewActivity(activity, AboutDevActivity.class, false);

                            } else if (drawerItem.getIdentifier() == 20) {
                                // TODO: invoke SettingActivity
                            } else if (drawerItem.getIdentifier() == 21) {
                                AppUtilities.rateThisApp(activity);
                            } else if (drawerItem.getIdentifier() == 22) {
                                AppUtilities.shareApp(activity);
                            } else if (drawerItem.getIdentifier() == 23) {
                                ActivityUtilities.getInstance().invokeCustomUrlActivity(activity, CustomUrlActivity.class,
                                        getResources().getString(R.string.privacy_title), getResources().getString(R.string.privacy_url), false);
                            }
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(false)
                .withShowDrawerUntilDraggedOpened(true)
                .build();
    }

    private void loadData() {
        showLoader();
        loadJson();
    }

    private void loadJson() {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try{
            br = new BufferedReader(new InputStreamReader(getAssets().open(AppConstants.CONTENT_FILE)));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        parseJson(sb.toString());
        Log.d("mtag", "loadJson: " + sb.toString());
    }

    private void parseJson(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.JSON_KEY_ITEMS);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                String categoryId = object.getString(AppConstants.JSON_KEY_CATEGORY_ID);
                String categoryName = object.getString(AppConstants.JSON_KEY_CATEGORY_NAME);

                mCategoryList.add(new CategoryModel(categoryId, categoryName));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        hideLoader();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer != null && mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else {
            AppUtilities.tapPromptToExit(this);
        }
    }

}
