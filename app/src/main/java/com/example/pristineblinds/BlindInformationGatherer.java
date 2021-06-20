package com.example.pristineblinds;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class BlindInformationGatherer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Integer count = 1;
    private TableLayout table;
    private DrawerLayout menuDrawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle menuActionBarDrawerToggle;
    private ArrayList<InputHandler> inputHandlerList;
    private CostCalculator costCalculator;
    private CSVWriter csvWriter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_converter);
        this.table = findViewById(R.id.table_layout);
        inputHandlerList = new ArrayList<>();
        costCalculator = new CostCalculator(inputHandlerList, this);
        setDrawer();
        addRowButton();
        addFiveRows();
        hideUpperMenu();
    }

    private void hideUpperMenu(){
        ImageButton expandClientInformationButton = findViewById(R.id.expandClientInformation);
        expandClientInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = findViewById(R.id.clientMenu);
                linearLayout.setVisibility((linearLayout.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
            }
        });
    }

    private void setDrawer(){
        toolbar = findViewById(R.id.menu_toolbar);
        menuDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.converter_menu);
        setSupportActionBar(toolbar);
        menuActionBarDrawerToggle = new ActionBarDrawerToggle(this, this.menuDrawerLayout, this.toolbar, R.string.open, R.string.close);
        menuDrawerLayout.addDrawerListener(this.menuActionBarDrawerToggle);
        menuActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        menuActionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void addRowButton(){
        ImageButton addRow = findViewById(R.id.addRowButton);
        addRow.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                createRow();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addFiveRows(){
        int number_of_starting_rows = 5;
        for(int i = 0; i < number_of_starting_rows; i++){
            createRow();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void createRow(){
        TableRow tableRow = new TableRow(this);
        tableRow.setBackgroundResource(R.drawable.tablelines);
        InputHandler inputHandler = new InputHandler(BlindInformationGatherer.this);
        tableRow.addView(addRowToCalculation(inputHandler));
        tableRow.addView(addNumber(inputHandler));
        tableRow.addView(addWidth(inputHandler));
        tableRow.addView(addWidthRemainder(inputHandler));
        tableRow.addView(addHeight(inputHandler));
        tableRow.addView(addHeightRemainder(inputHandler));
        tableRow.addView(addType(inputHandler));
        tableRow.addView(addMotor(inputHandler));
        tableRow.addView(addNotes(inputHandler));
        tableRow.addView(addControl(inputHandler));
        inputHandlerList.add(inputHandler);
        table.addView(tableRow);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private CheckBox addRowToCalculation(InputHandler inputHandler){
        CheckBox calculate = new CheckBox(this);
        int calculateId = View.generateViewId();
        inputHandler.setCalculateId(calculateId);
        calculate.setId(calculateId);
        return calculate;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private TextView addNumber(InputHandler inputHandler){
        TextView count = new TextView(this);
        int numberId = View.generateViewId();
        inputHandler.setCountId(numberId);
        count.setTextSize(20);
        count.setGravity(Gravity.CENTER);
        String count_string = this.count.toString();
        count.setText(count_string);
        count.setId(numberId);
        this.count++;
        return count;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private EditText addWidth(InputHandler inputHandler){
        EditText width = new EditText(this);
        width.setHint("In");
        int widthId = View.generateViewId();
        inputHandler.setWidthId(widthId);
        width.setId(widthId);
        return width;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private EditText addHeight(InputHandler inputHandler){
        EditText height = new EditText(this);
        height.setHint("In");
        int heightId = View.generateViewId();
        inputHandler.setHeightId(heightId);
        height.setId(heightId);
        return height;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private EditText addWidthRemainder(InputHandler inputHandler){
        EditText widthRemainder = new EditText(this);
        widthRemainder.setHint("Ex. 1/4");
        int widthRemainderId = View.generateViewId();
        inputHandler.setWidthRemainderId(widthRemainderId);
        widthRemainder.setId(widthRemainderId);
        return widthRemainder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private EditText addHeightRemainder(InputHandler inputHandler){
        EditText heightRemainder = new EditText(this);
        heightRemainder.setHint("Ex. 1/4");
        int heightRemainderId = View.generateViewId();
        inputHandler.setHeightRemainderId(heightRemainderId);
        heightRemainder.setId(heightRemainderId);
        return heightRemainder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private RadioGroup addType(InputHandler inputHandler){
        RadioGroup type = new RadioGroup(this);
        type.setPadding(60,0,10, 0);
        type.setOrientation(RadioGroup.HORIZONTAL);

        RadioButton faux = new RadioButton(this);
        RadioButton zebra = new RadioButton(this);
        RadioButton roller = new RadioButton(this);
        int fauxId = View.generateViewId();
        int zebraId = View.generateViewId();
        int rollerId = View.generateViewId();
        inputHandler.setFauxId(fauxId);
        inputHandler.setZebraId(zebraId);
        inputHandler.setRollerId(rollerId);
        faux.setId(fauxId);
        zebra.setId(zebraId);
        roller.setId(rollerId);
        faux.setPadding(0,0,30, 0);
        zebra.setPadding(0,0,30, 0);
        faux.setText(R.string.faux);
        zebra.setText(R.string.zebra);
        roller.setText(R.string.roller);
        type.addView(faux);
        type.addView(zebra);
        type.addView(roller);
        return type;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private RadioButton addMotor(InputHandler inputHandler){
        RadioButton motor = new RadioButton(this);
        int motorId = View.generateViewId();
        inputHandler.setMotorId(motorId);
        motor.setId(motorId);
        motor.setText(R.string.motor);
        return motor;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private EditText addNotes(InputHandler inputHandler){
        EditText notes = new EditText(this);
        notes.setHint("Information");
        int notesId = View.generateViewId();
        inputHandler.setNotesId(notesId);
        notes.setId(notesId);
        return notes;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private RadioGroup addControl(InputHandler inputHandler){
        RadioGroup control = new RadioGroup(this);
        control.setPadding(30,0,30, 0);
        control.setOrientation(RadioGroup.HORIZONTAL);
        RadioButton left = new RadioButton(this);
        RadioButton right = new RadioButton(this);
        int leftId = View.generateViewId();
        int rightId = View.generateViewId();
        inputHandler.setLeftId(leftId);
        inputHandler.setRightId(rightId);
        left.setId(leftId);
        right.setId(rightId);
        left.setPadding(0,0,30, 0);
        left.setText(R.string.left);
        right.setText(R.string.right);
        control.addView(left);
        control.addView(right);
        right.toggle();
        return control;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.clear){
            TableLayout tableLayout = findViewById(R.id.table_layout);
            tableLayout.removeViews(1, Math.max(0, tableLayout.getChildCount()-1));
            count = 1;
            inputHandlerList.clear();
            TextView squareFootage = findViewById(R.id.costCalculations);
            squareFootage.setText("");
            addFiveRows();
        }else if(id == R.id.Download){

        }else if(id == R.id.calculate){
            for(InputHandler inputHandler : inputHandlerList){
                inputHandler.parseInputData();
            }
            String cost = costCalculator.displayCostCalculations();
            TextView squareFootage = findViewById(R.id.costCalculations);
            squareFootage.setText(cost);
        }

        return true;
    }
}