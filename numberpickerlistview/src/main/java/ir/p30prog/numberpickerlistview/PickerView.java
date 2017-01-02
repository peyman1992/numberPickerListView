package ir.p30prog.numberpickerlistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;

public class PickerView extends LinearLayout {
  private PickerListView pickerListView;
  private PickerAdapter pickerAdapter;
  private int centerColor;
  private int notCenterColor;
  private int centerItemBackgroundColor;
  private int dividerColor;
  private int dividerStroke;
  private int dividerWidth;

  public PickerView(Context context) {
    super(context);
    init();
  }

  public PickerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
    initAttr(attrs);
  }

  public PickerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
    initAttr(attrs);
  }

  private void init() {
    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
    View view = layoutInflater.inflate(R.layout.fragment_layout, this, true);

    pickerListView = (PickerListView) view.findViewById(R.id.picker_listview);

    pickerAdapter = new PickerAdapter(this.getContext());
    if (pickerListView != null) {
      pickerListView.setAdapter(pickerAdapter);
    }


  }

  private void initAttr(AttributeSet attrs) {
    TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.picker_fragment);

    centerColor = attr.getColor(R.styleable.picker_fragment_center_color, Color.BLACK);
    notCenterColor = attr.getColor(R.styleable.picker_fragment_not_center_color, Color.argb(255 / 2, 0, 0, 0));
    dividerColor = attr.getColor(R.styleable.picker_fragment_divider_color, Color.BLACK);
    dividerStroke = attr.getDimensionPixelSize(R.styleable.picker_fragment_divider_stroke, Helpers.dpToPx(1));
    dividerWidth = attr.getDimensionPixelSize(R.styleable.picker_fragment_divider_width, -1);
    int backgroundColor = attr.getColor(R.styleable.picker_fragment_background_color, Color.TRANSPARENT);
    centerItemBackgroundColor = attr.getColor(R.styleable.picker_fragment_center_item_background_color, Color.TRANSPARENT);
    CharSequence[] items = attr.getTextArray(R.styleable.picker_fragment_items);
    attr.recycle();

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
