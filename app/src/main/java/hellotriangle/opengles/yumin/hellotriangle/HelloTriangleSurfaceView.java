package hellotriangle.opengles.yumin.hellotriangle;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

public class HelloTriangleSurfaceView extends GLSurfaceView {
    private final int CONTEXT_CLIENT_VERSION = 3;
    private HelloTriangleRenderer mTriangleRenderer;

    class myEGLConfigChooser implements GLSurfaceView.EGLConfigChooser {
        @Override
        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
            int[] attrib_list = {
                    EGL10.EGL_RED_SIZE, 8,
                    EGL10.EGL_GREEN_SIZE, 8,
                    EGL10.EGL_BLUE_SIZE, 8,
                    EGL10.EGL_DEPTH_SIZE, 1,
                    EGL10.EGL_SAMPLES, 16,
                    EGL10.EGL_NONE
            };

            EGLConfig[] configs = new EGLConfig[64];
            int[] num_config = new int[1];
            if (!egl.eglChooseConfig(display, attrib_list, configs, 64, num_config)) {
                throw new RuntimeException("eglChooseConfig failed");
            }
            int configs_size = num_config[0];
            EGLConfig config = configs_size > 0 ? configs[0] : null;
            if (config == null)
                throw new RuntimeException("No config chosen");

            return config;
        }
    }

    public HelloTriangleSurfaceView(Context context) {
        super(context);

        this.setEGLContextClientVersion(CONTEXT_CLIENT_VERSION);
        this.setEGLConfigChooser(new myEGLConfigChooser());
        this.mTriangleRenderer = new HelloTriangleRenderer(context);
        this.setRenderer(mTriangleRenderer);
    }
}
