package hellotriangle.opengles.yumin.hellotriangle;

import android.content.Context;
import android.opengl.GLES30;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class myGLUtil {
    public static int loadShader(int type, String src) {
        int shader = GLES30.glCreateShader(type);
        if (shader == 0) {
            Log.e("ERROR", "Shader Create Error!");
            return 0;
        }

        GLES30.glShaderSource(shader, src);
        GLES30.glCompileShader(shader);

        int[] compiled = new int[1];
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0);

        if (compiled[0] == 0) {
            Log.e("ERROR", "Shader Compile Error!");
            Log.e("ERROR", GLES30.glGetShaderInfoLog(shader));
            GLES30.glDeleteShader(shader);
            return 0;
        }

        return shader;
    }

    public static int loadProgram(int vShader, int fShader) {
        int program = GLES30.glCreateProgram();
        if (program == 0) {
            Log.e("ERROR", "Program Create Error!");
            return 0;
        }

        GLES30.glAttachShader(program, vShader);
        GLES30.glAttachShader(program, fShader);
        GLES30.glLinkProgram(program);

        int linked[] = new int[1];
        GLES30.glGetProgramiv(program, GLES30.GL_LINK_STATUS, linked, 0);

        if (linked[0] == 0) {
            Log.e("ERROR", "Program Link Error!");
            Log.e("ERROR", GLES30.glGetProgramInfoLog(program));
            GLES30.glDeleteProgram(program);
            return 0;
        }

        return program;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = br.readLine()) != null)
                sb.append(line).append('\n');
        } catch (IOException e) {
            Log.e("ERROR", "Shader file read error");
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.e("ERROR", "Shjader file close error");
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String readShaderFromAsset(Context context, String fileName) {
        InputStream is = null;

        try {
            is = context.getAssets().open(fileName);
        } catch (IOException e) {
            Log.e("ERROR", "Shader file open error.");
            e.printStackTrace();
            return null;
        }

        return convertStreamToString(is);
    }
}
