package ir.p30prog.numberpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import ir.p30prog.numberpickerlistview.OnItemChangeListener;
import ir.p30prog.numberpickerlistview.PickerFragment;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ArrayList<Object> arrayList = new ArrayList<>();
    for (int i = 0; i < 10000; i++) {
      String a = i + " سانتی متر";
      arrayList.add(i);
    }

    PickerFragment pickerFragment = (PickerFragment) getSupportFragmentManager().findFragmentById(R.id.picker_fragment);
    pickerFragment.setItems(arrayList);
    pickerFragment.setOnItemChangeListener(new OnItemChangeListener() {
      @Override
      public void onItemChange(Object object, int position) {
        Log.i("LOG", "string: " + object.toString());
        Log.i("LOG", "position: " + position);
      }
    });

  }
}
