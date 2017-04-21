package com.example.edgar.triangleapp;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ShowTriangleActivity extends AppCompatActivity {

    private GLSurfaceView mGLView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        //// Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        //Parse the floats that are in the strings we packaged with the intent
        float sideA = Float.parseFloat(intent.getStringExtra(MainActivity.EXTRA_MESSAGE1));
        float sideB = Float.parseFloat(intent.getStringExtra(MainActivity.EXTRA_MESSAGE2));

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new MyGLSurfaceView(this, sideA, sideB);
        setContentView(mGLView);

        //Toast.makeText(ShowTriangleActivity.this, "sideA is " + sideA, Toast.LENGTH_SHORT).show();
    }

    public class MyGLSurfaceView extends GLSurfaceView
    {
        private final MyGLRenderer mRenderer;

        public MyGLSurfaceView(Context context, float sideA, float sideB){
            super(context);

            // Create an OpenGL ES 2.0 context
            setEGLContextClientVersion(2);

            mRenderer = new MyGLRenderer();

            // Set the Renderer for drawing on the GLSurfaceView
            setRenderer(mRenderer);

            // Use the setCoords method we defined in MyGLRenderer to pass it the side lengths
            mRenderer.setCoords(sideA, sideB);


            // Render the view only when there is a change in the drawing data
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }
    }
}


