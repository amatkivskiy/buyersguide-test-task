package com.amatkivskiy.buyersguide.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.amatkivskiy.buyersguide.R;

public class ToolbarSearchView extends FrameLayout implements TextWatcher,
    TextView.OnEditorActionListener {

  private EditText wrappedEditText;
  private OnSearchListener searchListener;

  public ToolbarSearchView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public ToolbarSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public ToolbarSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context);
  }

  public ToolbarSearchView(Context context) {
    super(context);
    init(context);
  }

  /**
   * Set the search listener to be used on this search
   * @param searchListener the search listener to be used on this search
   * @see OnSearchListener
   */
  public void setOnSearchListener(OnSearchListener searchListener) {
    this.searchListener = searchListener;
  }

  /**
   * Sets the search text
   * @param searchText the text to set on the search
   * @see #setSearchText(int)
   */
  public void setSearchText(CharSequence searchText) {
    wrappedEditText.setText(searchText);
  }

  /**
   * Sets the search text using a resource
   * @param searchTextRes the resource to set the text
   * @see #setSearchText(CharSequence)
   */
  public void setSearchText(int searchTextRes) {
    wrappedEditText.setText(searchTextRes);
  }

  /**
   * @return the current text on the search
   */
  public CharSequence getSearchText() {
    return wrappedEditText.getText();
  }

  /**
   * Set the search placeholder (hint)
   * @param placeholder the placeholder
   * @see #setSearchPlaceholder(int)
   */
  public void setSearchPlaceholder(CharSequence placeholder) {
    wrappedEditText.setHint(placeholder);
  }

  /**
   * Set the search placeholder (hint)
   * @param placeholderRes the placeholder
   * @see #setSearchPlaceholder(CharSequence)
   */
  public void setSearchPlaceholder(int placeholderRes) {
    wrappedEditText.setHint(placeholderRes);
  }

  /**
   * Inflate the layout to this FrameLayout wrapper
   * @param context for inflate views
   */
  protected void init(Context context) {
    LayoutInflater.from(context).inflate(R.layout.search_view, this, true);
    wrappedEditText = (EditText) findViewById(R.id.wrapped_search);
    wrappedEditText.addTextChangedListener(this);
    wrappedEditText.setOnEditorActionListener(this);
  }

  @Override public void beforeTextChanged(CharSequence constraint, int start, int count, int after) { }

  @Override public void onTextChanged(CharSequence constraint, int start, int count, int after) {
    if (searchListener != null) {
      searchListener.onSearch(this, constraint);
      return;
    }
    Log.w(getClass().getName(), "SearchListener == null");
  }

  @Override public void afterTextChanged(Editable editable) { }

  @Override public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
    if (searchListener != null) {
      searchListener.onSearchHint(this, textView.getText());
      return true;
    }
    Log.w(getClass().getName(), "SearchListener == null");
    return false;
  }

  /**
   * This interface is an custom method to wrapp the
   * TextWatcher implementation and provide the search constraint
   *
   * @author Leonardo Rossetto
   */
  public interface OnSearchListener {

    /**
     * This method is called every time the EditText change it content
     * @param toolbarSearchView the searchview
     * @param constraint the current input data
     */
    void onSearch(ToolbarSearchView toolbarSearchView, CharSequence constraint);

    /**
     * This method is called when the user press the search button on the keyboard
     * @param toolbarSearchView the searchview
     * @param constraint the current input data
     */
    void onSearchHint(ToolbarSearchView toolbarSearchView, CharSequence constraint);

  }

}