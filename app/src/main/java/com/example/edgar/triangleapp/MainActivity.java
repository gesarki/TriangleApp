package com.example.edgar.triangleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE1 = "com.example.edgar.triangleapp.MESSAGE1";
    public static final String EXTRA_MESSAGE2 = "com.example.edgar.triangleapp.MESSAGE2";

    public String getIsEmptyStr (EditText txtview) {
        return Boolean.toString(txtview.getText().toString().trim().isEmpty());
    }
    public boolean getIsEmpty (EditText txtview) {
        return txtview.getText().toString().trim().isEmpty();
    }
    public void showToast(String tst) {
        Toast.makeText(MainActivity.this, tst, Toast.LENGTH_SHORT).show();
    }
    public boolean validLengths (double hypot, double adj)  {
        return (hypot > adj);
    }
    public int checkWhichEmpty(EditText hypot, EditText sideA, EditText sideB){
        boolean[] a = new boolean[3];
        a[0] = getIsEmpty(hypot);
        a[1] = getIsEmpty(sideA);
        a[2] = getIsEmpty(sideB);
        int totalEmptyFields = 0; // stores how many empty fields there are
        int loctnEmptyField = 0;
        boolean found = false;

        for (int i = 0; i < 3; i ++ ){
            if (a[i] == true){
                totalEmptyFields ++;
                if (!found) {
                    loctnEmptyField = i;
                    found = true;
                }
            }
        }
        int result = 0;
        if (totalEmptyFields > 2) {
            result = 6; //means all fields are empty
        }
        else if (totalEmptyFields == 0){
            result =  5;//means all fields are full
        }
        else if (totalEmptyFields == 2){
            result =  4;//means 2 fiels are empty
        }
        else if (totalEmptyFields == 1) {
            result =  loctnEmptyField; //returns the index of the empty field starting from 0
        }
        return result;

    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button)this.findViewById(R.id.click_btn);
        b.setOnClickListener(new Button.OnClickListener(){public void onClick(View V) {
            EditText hypot = (EditText) findViewById(R.id.hypot);
            EditText sideA = (EditText) findViewById(R.id.Side1);
            EditText sideB = (EditText) findViewById(R.id.Side2);

//            System.out.println("hypot divided by 2 is " + hypot.getText().toString());
//            System.out.println("sideA is " + sideA.getText());
//            System.out.println("sideB is " + sideB.getText());
//            System.out.println("bool " + sideB.getText().toString().trim().isEmpty());

            String sideAempty = getIsEmptyStr(sideA);
            String sideBempty = getIsEmptyStr(sideB);
            String hypotEmpty = getIsEmptyStr(hypot);
            String empties  = hypotEmpty + sideAempty + sideBempty;

            int whichIsEmpty = checkWhichEmpty(hypot,sideA, sideB);

            double lenA;
            double lenB;
            double lenC;

            switch (whichIsEmpty){
                case 6:
                    showToast("You Must Fill Out At Least 2 Fields");
                    break;
                case 4:
                    showToast("You Did Not Fill Out Enough Fields");
                    break;
                case 2:
                    lenC = Double.parseDouble(hypot.getText().toString());
                    lenA = Double.parseDouble(sideA.getText().toString());
                    if (validLengths(lenC, lenA)) {
                        sideB.setText(Double.toString(Math.sqrt((lenC * lenC) - (lenA * lenA))));
                    }
                    else {
                        showToast("The Hypoteneuse Must Be the Longest Side, IDIOT");
                        hypot.setText("");
                    }
                    break;
                case 1:
                    lenC = Double.parseDouble(hypot.getText().toString());
                    lenB = Double.parseDouble(sideB.getText().toString());
                    if (validLengths (lenC, lenB)) {
                        sideA.setText(Double.toString(Math.sqrt((lenC*lenC)-(lenB*lenB))));
                    }
                    else{
                        showToast("The Hypoteneuse Must Be the Longest Side, IDIOT");
                    }
                    break;
                case 0:
                    lenA = Double.parseDouble(sideA.getText().toString());
                    lenB = Double.parseDouble(sideB.getText().toString());
                    hypot.setText(Double.toString(Math.sqrt((lenA*lenA)+(lenB*lenB))));
                    break;
                case 5:
                    showToast("You Left No Fields Empty\n" +
                            "But I'll Make Sure Your Hypoteneuse Is Correct");
                    lenA = Double.parseDouble(sideA.getText().toString());
                    lenB = Double.parseDouble(sideB.getText().toString());
                    hypot.setText(Double.toString(Math.sqrt((lenA*lenA)+(lenB*lenB))));
                    break;
                default:
                    showToast("You Did Something I Didn't Think Of");
            }
        }
        });
    }

    public void showTriangle (View view) // When they click on Show Triangle (we assigned the button
            // to this function in the xml)
    {
        try {
            // Make an intent to pass in the side lengths to ShowTriangleActivity
            Intent intent = new Intent(this, ShowTriangleActivity.class);
            // Get the EditText objects with of the sidelengths
            EditText sideA1 = (EditText) findViewById(R.id.Side1);
            EditText sideB1 = (EditText) findViewById(R.id.Side2);

            // Makes sure fields are non empty
            if (getIsEmpty(sideA1) || getIsEmpty(sideB1) ) {
                throw new Exception();
            }

            // Get the side lengths from the editTexts
            String sideA = sideA1.getText().toString();
            String sideB = sideB1.getText().toString();

            // Include the side lengths in the intent as key-value pairs, EXTRA_MESSAGE[#] being
            // the key, sidelenghts as strings being the values
            intent.putExtra(EXTRA_MESSAGE1, sideA);
            intent.putExtra(EXTRA_MESSAGE2, sideB);

            //showToast(sideA);
            // Start ShowTriangleActivity with the intent
            startActivity(intent);

        }catch (Exception e){ // Catch the empty exception and inform the user of their stupidity
            showToast("There's nothing to FUCKING SHOW, IDIOT");
        }
    }
}
