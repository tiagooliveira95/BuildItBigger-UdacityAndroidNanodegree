// Generated code from Butter Knife. Do not modify!
package com.udacity.gradle.builditbigger;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(MainActivity target, View source) {
    this.target = target;

    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progressBar2, "field 'progressBar'", ProgressBar.class);
    target.localJokeButton = Utils.findRequiredViewAsType(source, R.id.localJokeButton, "field 'localJokeButton'", Button.class);
    target.liveJokeButton = Utils.findRequiredViewAsType(source, R.id.liveJokeButton, "field 'liveJokeButton'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progressBar = null;
    target.localJokeButton = null;
    target.liveJokeButton = null;
  }
}
