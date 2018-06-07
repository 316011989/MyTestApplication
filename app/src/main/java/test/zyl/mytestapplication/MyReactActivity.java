package test.zyl.mytestapplication;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;

import javax.annotation.Nullable;

public class MyReactActivity extends ReactActivity {

    @Nullable
    @Override
    protected String getMainComponentName() {
        return "mytestapplication";
    }
}
