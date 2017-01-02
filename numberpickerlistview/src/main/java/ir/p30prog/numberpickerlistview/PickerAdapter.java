package ir.p30prog.numberpickerlistview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.attr.textSize;
import static android.R.attr.theme;
import static android.R.color.black;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


class PickerAdapter extends ArrayAdapter<Object> {

  private static final String EMPTY_STRING = "";
  private ArrayList<Object> items;
  private Context mContext;
  private TextView txtMeasure;
  private int middleItemTop;
  private int centerColor;
  private int notCenterColor;
  private Typeface typeface;

  PickerAdapter(Context context) {
    super(context, R.layout.listview_item);
    this.mContext = context;
  }

  void setItems(ArrayList<Object> items) {
    this.items = null;
    this.items = items;
    super.addAll(addEmptyRows(items));
    notifyDataSetChanged();
  }

  public void setTypeface(Typeface typeface) {
    this.typeface = typeface;
  }

  public ArrayList<Object> getItems() {
    return items;
  }

  void setCenterColor(int color) {
    centerColor = color;
    notifyDataSetChanged();
  }

  void setNotCenterColor(int color) {
    notCenterColor = color;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public View getView(int position, View convertView, @NonNull ViewGroup parent) {
    ViewHolder holder;

    Object item = getItem(position);
    if (convertView == null) {
      LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(
        Context.LAYOUT_INFLATER_SERVICE);
      convertView = layoutInflater.inflate(R.layout.listview_item, parent, false);
      holder = new ViewHolder(convertView, typeface);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    if (txtMeasure == null) {
      txtMeasure = holder.cloneTextView();
    }
    holder.fill(item, middleItemTop, centerColor, notCenterColor, txtMeasure);
    return convertView;
  }

  void setMiddleItemTop(int top) {
    middleItemTop = top;
  }

  private ArrayList<Object> addEmptyRows(ArrayList<Object> rawItems) {
    ArrayList<String> emptyRows = new ArrayList<String>();
    emptyRows.add(EMPTY_STRING);
    emptyRows.add(EMPTY_STRING);
    ArrayList<Object> items = new ArrayList<Object>();
    items.addAll(emptyRows);
    items.addAll(rawItems);
    items.addAll(emptyRows);
    return items;
  }

  private static class ViewHolder {
    TextView txtTitle;

    ViewHolder(View view, Typeface typeface) {
      txtTitle = (TextView) view.findViewById(R.id.txt_item);
      if (typeface != null)
        txtTitle.setTypeface(typeface);
    }

    TextView cloneTextView() {
      View v = LayoutInflater.from(txtTitle.getContext()).inflate(R.layout.listview_item, null);
      return (TextView) v.findViewById(R.id.txt_item);
    }

    int calcOffset(String text, int itemHeight, TextView measureTextView) {
      int textHeight = itemHeight;
      int offset = (itemHeight - textHeight) / 2;
      if (measureTextView != null) {
        measureTextView.setText(text);
        measureTextView.measure(0, 0);
        textHeight = measureTextView.getMeasuredHeight();
        offset = (itemHeight - textHeight) / 2;
      }
      return offset;
    }

    int calcY() {
      int[] location = new int[2];
      txtTitle.getLocationInWindow(location);
      int itemHeight = txtTitle.getHeight();
      return location[1];
    }

    void fill(final Object item, int middleItemTop, int centerColor, int notCenterColor, TextView txtMeasure) {
      txtTitle.setText(item.toString());

      int itemHeight = txtTitle.getHeight();
      int y = calcY();
      int offset = calcOffset(item.toString(), itemHeight, txtMeasure);

      if (y + itemHeight <= middleItemTop || y >= middleItemTop + itemHeight) {
        txtTitle.getPaint().setShader(null);
        txtTitle.setTextColor(notCenterColor);
      } else {
        txtTitle.setTextColor(centerColor);
        Shader textShader;
        if (y < middleItemTop) {
          float f = (float) (middleItemTop - y - offset) / (float) itemHeight;
          textShader = new LinearGradient(0, 0, 0, itemHeight,
            new int[]{notCenterColor, notCenterColor, centerColor, centerColor},
            new float[]{0, f, f, 1}, Shader.TileMode.CLAMP);
          txtTitle.getPaint().setShader(textShader);
        } else if (y < middleItemTop + itemHeight) {
          float f = (float) (middleItemTop + itemHeight - y - offset) / (float) itemHeight;
          textShader = new LinearGradient(0, 0, 0, itemHeight,
            new int[]{centerColor, centerColor, notCenterColor, notCenterColor},
            new float[]{0, f, f, 1}, Shader.TileMode.CLAMP);
          txtTitle.getPaint().setShader(textShader);
        }
      }
      if (itemHeight != 0) {
        txtTitle.setRotationX(((float) (-y + middleItemTop) / (float) itemHeight) * 20);
      }

    }
  }
}
