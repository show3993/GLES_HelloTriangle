package hellotriangle.opengles.yumin.hellotriangle;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class HelloTriangleRenderer implements GLSurfaceView.Renderer {
    private Context mContext;
    private int mProgram;
    private int mWidth;
    private int mHeight;
    private int mAngle;
    private FloatBuffer mVertices;

    private final static String TAG = "HelloTriangleRenderer";
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mModeMatirx = new float[16];

    private final float[] mVerticesData =
            {
                    0.0f, (float)Math.tan(Math.toRadians(60)) / 2 * 0.3f, 0.0f,
                    -0.3f, -(float)Math.tan(Math.toRadians(60)) / 2 * 0.3f, 0.0f,
                    0.3f, -(float)Math.tan(Math.toRadians(60)) / 2 * 0.3f, 0.0f
            };

    public HelloTriangleRenderer(Context context) {
        mContext = context;
        mAngle = 0;
        mVertices = ByteBuffer.allocateDirect(mVerticesData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertices.put(mVerticesData).position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        int vShader, fShader;

        vShader = myGLUtil.loadShader(GLES30.GL_VERTEX_SHADER, myGLUtil.readShaderFromAsset(mContext, "TriangleShader.v"));
        fShader = myGLUtil.loadShader(GLES30.GL_FRAGMENT_SHADER, myGLUtil.readShaderFromAsset(mContext, "TriangleShader.f"));
        mProgram = myGLUtil.loadProgram(vShader, fShader);

        if (mProgram == 0) {
            Log.e("ERROR", "Program is invalid");
            return;
        }

        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mWidth = width;
        mHeight = height;

        float ratio = (float) width / height;
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 2, 7);
        // Matrix.orthoM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 2, 4);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glViewport(0, 0, mWidth, mHeight);

        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);

        float[] mRotateMatrix = new float[16];
        float[] mVPMatrix = new float[16];

        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3.0f, 0f, 0f, 0f, 0f,1.0f, 0f);
        Matrix.multiplyMM(mVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        GLES30.glUseProgram(mProgram);

        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, mVertices);
        GLES30.glEnableVertexAttribArray(0);

        Matrix.setIdentityM(mRotateMatrix, 0);

        Matrix.setIdentityM(mModeMatirx, 0);
        Matrix.translateM(mModeMatirx, 0, 0f, 0.4f, 0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mVPMatrix, 0, mModeMatirx, 0);
        GLES30.glUniformMatrix4fv(0, 1, false, mMVPMatrix, 0);

        Matrix.setRotateM(mRotateMatrix, 0, mAngle, 1.0f, 0f, 0f);
        GLES30.glUniformMatrix4fv(1, 1, false, mRotateMatrix, 0);

        GLES30.glVertexAttrib4f(1, 1.0f, 0.0f, 0.0f, 1.0f);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);
    }
}
