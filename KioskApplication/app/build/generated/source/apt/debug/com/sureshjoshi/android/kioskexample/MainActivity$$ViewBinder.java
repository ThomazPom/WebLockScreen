// Generated code from Butter Knife. Do not modify!
package com.sureshjoshi.android.kioskexample;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.sureshjoshi.android.kioskexample.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230721, "field 'mWebView'");
    target.mWebView = finder.castView(view, 2131230721, "field 'mWebView'");
  }

  @Override public void unbind(T target) {
    target.mWebView = null;
  }
}
