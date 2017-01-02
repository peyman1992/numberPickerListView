package ir.p30prog.numberpickerlistview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import static android.R.attr.left;
import static android.R.attr.theme;

@SuppressWarnings("unused")
class PickerListView extends ListView {
  private int scrollTop, firstItem, lastNotifyPosition = -1, dividerWidth = -1;
  private PickerAdapter pickerAdapter;
  private OnItemChangeListener onItemChangeListener;
  private Paint dividerPaint, backgroundPaint;


  public PickerListView(Context context) {
    super(context);
    init();
  }

  public PickerListView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public PickerListView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }


  private void init() {
    dividerPaint = new Paint();
    dividerPaint.setAntiAlias(true);
    dividerPaint.setColor(Color.BLACK);
    dividerPaint.setStrokeWidth(Helpers.dpToPx(1));
    backgroundPaint = new Paint();
    dividerPaint.setAntiAlias(true);
    dividerPaint.setColor(Color.TRANSPARENT);


    setOnScrollListener(new OnScrollListener() {
      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == 0) {
          if (scrollTop < -Helpers.dpToPx(40) / 2) {
            setSelectionAndNotifyToListener(firstItem + 1);
          } else {
            setSelectionAndNotifyToListener(firstItem);
          }
        }
      }

      @Override
      public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        View v = getChildAt(0);
        scrollTop = (v == null) ? 0 : v.getTop();
        firstItem = firstVisibleItem;
        if (pickerAdapter != null)
          pickerAdapter.notifyDataSetChanged();
      }
    });
  }

  public Object getValue() {
    smoothScrollBy(0, 0);
    int position = getCenterItemPosition() - 2;
    setSelection(position);
    post(new Runnable() {
      @Override
      public void run() {
        pickerAdapter.notifyDataSetChanged();
      }
    });
    return getAdapter().getItems().get(position);
  }

  public void setSelectedItem(int position) {
    setSelectionAndNotifyToListener(position);
  }

  private void setSelectionAndNotifyToListener(final int position) {
    setSelection(position);
    post(new Runnable() {
      @Override
      public void run() {
        pickerAdapter.notifyDataSetChanged();
      }
    });
    if (onItemChangeListener != null) {
      int pos = getCenterItemPosition() - 2;
      if (pos != lastNotifyPosition) {
        onItemChangeListener.onItemChange(getAdapter().getItems().get(pos), pos);
        lastNotifyPosition = pos;
      }
    }
  }

  private int getCenterItemPosition() {
    return pointToPosition(getWidth() / 2, getHeight() / 2);
  }

  public void setOnItemChangeListener(OnItemChangeListener onItemChangeListener) {
    this.onItemChangeListener = onItemChangeListener;
  }

  public void setAdapter(PickerAdapter adapter) {
    super.setAdapter(adapter);
    pickerAdapter = adapter;
  }

  public PickerAdapter getAdapter() {
    return (PickerAdapter) super.getAdapter();
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    ViewGroup.LayoutParams params = getLayoutParams();
    params.height = Helpers.dpToPx(200);
    setLayoutParams(params);

    int middleTop = (Helpers.dpToPx(40) - 1) * 2;
    if (pickerAdapter != null)
      pickerAdapter.setMiddleItemTop(middleTop);
  }

  public void setCenterItemBackgroundColor(int dividerColor) {
    backgroundPaint.setColor(dividerColor);
    postInvalidate();
  }

  public void setDividerColor(int dividerColor) {
    dividerPaint.setColor(dividerColor);
    postInvalidate();
  }

  public void setDividerStroke(int dividerStroke) {
    dividerPaint.setStrokeWidth(dividerStroke);
    postInvalidate();
  }

  public void setDividerWidth(int dividerWidth) {
    this.dividerWidth = dividerWidth;
    postInvalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    if (getAdapter().getItems() != null && getAdapter().getItems().size() > 0) {
      int top = (Helpers.dpToPx(40) - 1) * 2;
      int bottom = (Helpers.dpToPx(40) - 1) * 3;
      int width = getWidth();
      if (dividerWidth != -1) {
        width = dividerWidth;
      }
      if (backgroundPaint.getColor() == Color.TRANSPARENT) {
        int left = getWidth() / 2 - width / 2;
        int right = width / 2 + getWidth() / 2;
        canvas.drawLine(left, top, getWidth() / 2, top, dividerPaint);
        canvas.drawLine(getWidth() / 2, top, right, top, dividerPaint);
        canvas.drawLine(left, bottom, getWidth() / 2, bottom, dividerPaint);
        canvas.drawLine(getWidth() / 2, bottom, right, bottom, dividerPaint);
      } else {
        canvas.drawRect(0, top, getWidth(), bottom, backgroundPaint);
      }
    }
  }
}
