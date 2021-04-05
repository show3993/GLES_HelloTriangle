#version 300 es
layout(location = 0) uniform mat4 uMVPMatrix;
layout(location = 1) uniform mat4 uRotateMatrix;
layout(location = 0) in vec4 vPosition;
layout(location = 1) in vec4 vColor;

out vec4 fColor;

void main()
{
    fColor = vColor;
    gl_Position = uRotateMatrix * uMVPMatrix * vPosition;
}