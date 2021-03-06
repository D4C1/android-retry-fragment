package cool.proto.retry.fragment.fragments;

import android.app.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cool.proto.retry.fragment.R;

/**
 * Created by moises on 26/09/16.
 */

public class LoadingFragment extends Fragment {

    public static final String MESSAGE_KEY = "message";
    public static final String IMAGE_KEY = "image";
    public static final String IMAGE_BITMAP_KEY = "imageBitmap";
    public static final String DELAY_TIME_KEY = "delayTime";
    public static final int DELAY_TIME = 100;

    private OnLoadingListener callBack;
    private Handler handler;

    private View view;
    private Bundle args;
    private String loadingMessage;
    private int iconId;
    private int customDelayTime;
    private Bitmap iconBitmap;
    private boolean done;


    public interface OnLoadingListener {
        void onLoadingFinish();
    }

    public void setCallBack(OnLoadingListener callBack) {
        this.callBack = callBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        view = inflater.inflate(R.layout.fragment_loading, container, false);
        args = getArguments();
        handler = new Handler();
        done = false;

        loadingMessage = getString(R.string.cool_proto_surf_retry_fragment_loading);

        if (args != null) {
            customize();
        }

        return view;
    }

    public void loading() {
        if (customDelayTime != 0) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    done = true;
                    callBack.onLoadingFinish();
                }
            }, customDelayTime);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    done = true;
                    callBack.onLoadingFinish();
                }
            }, DELAY_TIME);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!done){
            loading();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private void customize() {
        if (args.getParcelable(IMAGE_BITMAP_KEY) != null) {
            iconBitmap = args.getParcelable(IMAGE_BITMAP_KEY);
            ImageView imageView = (ImageView) view.findViewById(R.id.loading_image);
            imageView.setImageBitmap(iconBitmap);
        } else if (args.getInt(IMAGE_KEY, 0) != 0) {
            iconId = args.getInt(IMAGE_KEY);
            ImageView imageView = (ImageView) view.findViewById(R.id.loading_image);
            imageView.setImageResource(iconId);
        }
        if (!args.getString(MESSAGE_KEY, loadingMessage).equals(loadingMessage)) {
            loadingMessage = args.getString(MESSAGE_KEY);
            TextView messageText = (TextView) view.findViewById(R.id.loading_text);
            messageText.setText(loadingMessage);
        }
        if (args.getInt(DELAY_TIME_KEY, 0) != 0) {
            customDelayTime = args.getInt(DELAY_TIME_KEY);
        }
    }

    public LoadingFragment show(AppCompatActivity activity) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, this)
                .commitAllowingStateLoss();
        return this;
    }
}
