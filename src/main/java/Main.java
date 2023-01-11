import Engine.Window;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;

public class Main {

    // The window handle
    private Window window = new Window(800,800,"Hello World");

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
    }

    private void loop() {
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (window.isOpen()) {
            window.update();
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GL.createCapabilities();


            //draw



            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }

    }

    public static void main(String[] args) {
        new Main().run();
    }

}

