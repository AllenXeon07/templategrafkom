import Engine.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Main {

    // The window handle
    private Window window = new Window(800,800,"Hello World");
    int vaoid;
    int vboid;
    private ShaderProgram shaderProgram;

    float[] vertices = new float[]{
            0.0f,  0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };

    public void run() {
        init();
        loop();

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        window.init();
        GL.createCapabilities();
        List<ShaderProgram.ShaderModuleData> shaderModuleDataList = new ArrayList<>();
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER));
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER));
        shaderProgram = new ShaderProgram(shaderModuleDataList);

        try (MemoryStack stack = MemoryStack.stackPush()) {

            vaoid = glGenVertexArrays();
            glBindVertexArray(vaoid);

            vboid = glGenBuffers();
            FloatBuffer positionBuffer = stack.callocFloat(vertices.length);
            positionBuffer.put(0, vertices);
            glBindBuffer(GL_ARRAY_BUFFER, vboid);
            glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_STATIC_DRAW);
            //memFree(positionBuffer);

            glEnableVertexAttribArray(0);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        }
    }

    private void loop() {
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (window.isOpen()) {
            window.update();
            GL.createCapabilities();
            shaderProgram.bind();
            //draw
            // Bind to the VAO
            glBindVertexArray(vaoid);
            glEnableVertexAttribArray(0);
            // Draw the vertices
            glDrawArrays(GL_TRIANGLES, 0, vertices.length/3);

            // Restore state
            glDisableVertexAttribArray(0);
            glBindVertexArray(0);
            shaderProgram.unbind();

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }

    }

    public static void main(String[] args) {
        new Main().run();
    }

}

