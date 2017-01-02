package ir.p30prog.numberpickerlistview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;


public class PickerFragment extends Fragment {
  private PickerListView pickerListView;
  private PickerAdapter pickerAdapter;
  private int centerColor, notCenterColor, backgroundColor, centerItemBackgroundColor, dividerColor, dividerStroke, dividerWidth;
  private CharSequence[] items;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_layout, container, false);
    pickerListView = (PickerListView) view.findViewById(R.id.picker_listview);

    pickerAdapter = new PickerAdapter(this.getContext());
    if (pickerListView != null) {
      pickerListView.setAdapter(pickerAdapter);
    }
    init();
    return view;
  }

  public void init() {
    if (items != null) {
      ArrayList<Object> array = new ArrayList<>();
      Collections.addAll(array, items);
      pickerAdapter.setItems(array);
    }
    setCenterColor(centerColor);
    setNotCenterColor(notCenterColor);
    setDividerColor(dividerColor);
    setDividerStroke(dividerStroke);
    setDividerWidth(dividerWidth);
    setBackgroundColor(backgroundColor);
    setCenterItemBackgroundColor(centerItemBackgroundColor);
  }


  @Override
  public void onInflate(Context activity, AttributeSet attrs, Bundle savedInstanceState) {
    super.onInflate(activity, attrs, savedInstanceState);

    TypedArray attr = activity.obtainStyledAttributes(attrs, R.styleable.picker_fragment);

    centerColor = attr.getColor(R.styleable.picker_fragment_center_color, Color.BLACK);
    notCenterColor = attr.getColor(R.styleable.picker_fragment_not_center_color, Color.argb(255 / 2, 0, 0, 0));
    dividerColor = attr.getColor(R.styleable.picker_fragment_divider_color, Color.BLACK);
    dividerStroke = attr.getDimensionPixelSize(R.styleable.picker_fragment_divider_stroke, Helpers.dpToPx(1));
    dividerWidth = attr.getDimensionPixelSize(R.styleable.picker_fragment_divider_width, -1);
    backgroundColor = attr.getColor(R.styleable.picker_fragment_background_color, Color.TRANSPARENT);
    centerItemBackgroundColor = attr.getColor(R.styleable.picker_fragment_center_item_background_color, Color.TRANSPARENT);
    items = attr.getTextArray(R.styleable.picker_fragment_items);

    attr.recycle();
  }


  public Object getValue() {
    return pickerListView.getValue();
  }

  public void setSelection(int pos) {
    pickerListView.setSelection(pos);
  }

  public void setTypeFace(Typeface typeFace) {
    pickerAdapter.setTypeface(typeFace);
  }

  public void setItems(ArrayList<Object> items) {
    pickerAdapter.setItems(items);
  }

  public void setDividerColor(int dividerColor) {
    this.dividerColor = dividerColor;
    pickerListView.setDividerColor(dividerColor);
  }

  public void setDividerStroke(int dividerStroke) {
    this.dividerStroke = dividerStroke;
    pickerListView.setDividerStroke(dividerStroke);
  }

  public void setDividerWidth(int dividerWidth) {
    this.dividerWidth = dividerWidth;
    pickerListView.setDividerWidth(dividerWidth);
  }

  public void setCenterColor(int centerColor) {
    this.centerColor = centerColor;
    pickerAdapter.setCenterColor(centerColor);
  }

  public void setNotCenterColor(int notCenterColor) {
    this.notCenterColor = notCenterColor;
    pickerAdapter.setNotCenterColor(notCenterColor);
  }

  public void setOnItemChangeListener(OnItemChangeListener onItemChangeListener) {
    pickerListView.setOnItemChangeListener(onItemChangeListener);
  }

  public void setBackgroundColor(int color) {
    pickerListView.setBackgroundColor(color);
  }

  public void setCenterItemBackgroundColor(int centerItemBackgroundColor) {
    this.centerItemBackgroundColor = centerItemBackgroundColor;
    pickerListView.setCenterItemBackgroundColor(centerItemBackgroundColor);
  }
}
