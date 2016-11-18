package cool.proto.retry.fragment;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by moises on 17/11/16.
 */

public abstract class LoadingContainer {

    public static void loadingStart(AppCompatActivity activity){
        FrameLayout frameLayout = (FrameLayout) activity.findViewById(R.id.fragment_container);
        frameLayout.setVisibility(View.VISIBLE);

    }

    public static void loadingEnd(AppCompatActivity activity) {
        FrameLayout frameLayout = (FrameLayout) activity.findViewById(R.id.fragment_container);
        frameLayout.setVisibility(View.GONE);
    }
}
