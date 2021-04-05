package hellotriangle.opengles.yumin.hellotriangle;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

public class HelloTriangle extends Activity {
    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        if (detectOpenGLES30()) {
            mGLSurfaceView = new HelloTriangleSurfaceView(this);
        }
        else
        {
            Log.e("HelloTriangle", "OpenGL ES 3.0 not supported on device. Exit.");
            finish();
        }

        setContentView(mGLSurfaceView);
    }

    private boolean detectOpenGLES30() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x30000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }
}
