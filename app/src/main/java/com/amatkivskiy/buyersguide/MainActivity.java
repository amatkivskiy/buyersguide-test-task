package com.amatkivskiy.buyersguide;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.amatkivskiy.buyersguide.ui.fragments.AllCarsListFragment;
import com.amatkivskiy.buyersguide.ui.fragments.FavouritesCarListFragment;
import com.amatkivskiy.buyersguide.util.ToolbarSearchView;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity {

  public interface OnSearchActionsListener extends ToolbarSearchView.OnSearchListener {
    void onCancelSearch();
  }

  private Drawer drawer;
  private OnSearchActionsListener searchActionsListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.inflateMenu(R.menu.menu_main);

    drawer = new DrawerBuilder()
        .withActivity(this)
        .withToolbar(toolbar)
        .addDrawerItems(
            new PrimaryDrawerItem().withName(R.string.text_drawer_all)
                .withIcon(GoogleMaterial.Icon.gmd_list).withIdentifier(1).withCheckable(false),
            new PrimaryDrawerItem().withName(R.string.text_drawer_favourites)
                .withIcon(GoogleMaterial.Icon.gmd_favorite).withIdentifier(2).withCheckable(false)
        )
        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
          @Override
          public boolean onItemClick(AdapterView<?> parent, View view, int position, long id,
                                     IDrawerItem drawerItem) {
            if (drawerItem != null) {
              if (position == drawer.getCurrentSelection()) {
                return false;
              }

              if (drawerItem.getIdentifier() == 1) {
                showCarListFragment();
              } else if (drawerItem.getIdentifier() == 2) {
                showFavouritesListFragment();
              }

              drawer.setSelectionByIdentifier(drawerItem.getIdentifier(), false);
            }

            return false;
          }
        })
        .withSavedInstance(savedInstanceState)
        .withShowDrawerOnFirstLaunch(true)
        .withSelectedItem(0)
        .build();

    if (savedInstanceState == null) {
      drawer.setSelectionByIdentifier(1, false);
      showCarListFragment();
    }
  }

  private void showFavouritesListFragment() {
    FavouritesCarListFragment fragment = new FavouritesCarListFragment();
    searchActionsListener = fragment;

    getFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
    invalidateOptionsMenu();
  }

  private void showCarListFragment() {
    AllCarsListFragment fragment = new AllCarsListFragment();
    searchActionsListener = null;

    getFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
    invalidateOptionsMenu();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    initSearchView(menu);
    return true;
  }

  private void initSearchView(Menu menu) {
    MenuItem item = menu.findItem(R.id.search_cars);
    if (searchActionsListener == null) {
      item.setVisible(false);
      return;
    }

    MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
      @Override
      public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
      }

      @Override
      public boolean onMenuItemActionCollapse(MenuItem item) {
        searchActionsListener.onCancelSearch();
        return true;
      }
    });
    ToolbarSearchView toolbarSearchView = (ToolbarSearchView) MenuItemCompat.getActionView(item);
    toolbarSearchView.setSearchPlaceholder(getString(R.string.text_serach_hint));
    toolbarSearchView.setOnSearchListener(searchActionsListener);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    outState = drawer.saveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onBackPressed() {
    if (drawer != null && drawer.isDrawerOpen()) {
      drawer.closeDrawer();
    } else {
      super.onBackPressed();
    }
  }
}