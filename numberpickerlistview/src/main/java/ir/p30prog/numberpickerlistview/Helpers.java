package ir.p30prog.numberpickerlistview;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import static java.security.AccessController.getContext;

class Helpers {

  static int dpToPx(int dp) {
    return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
  }
}
