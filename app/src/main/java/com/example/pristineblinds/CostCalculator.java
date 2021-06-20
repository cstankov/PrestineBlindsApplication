package com.example.pristineblinds;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.ArrayList;

public class CostCalculator {
    private ArrayList<InputHandler> inputHandlerList;
    private BlindInformationGatherer blindInformationGatherer;
    private ArrayList<InputHandler> faux_blinds = new ArrayList<>();
    private ArrayList<InputHandler> zebra_blinds = new ArrayList<>();
    private ArrayList<InputHandler> roller_blinds = new ArrayList<>();

    private Integer zebraSquareFootageTotal = 0;
    private Integer rollerSquareFootageTotal = 0;
    private Double totalFauxCost = 0.0;
    private Double totalZebraCost = 0.0;
    private Double totalRollerCost = 0.0;
    private Double totalMotorCost = 0.0;
    private Double totalCost = 0.0;
    private Integer numberOfMotors = 0;

    private Double fauxCostMultiplier = 1.0;
    private Double zebraCostMultiplier = 1.0;
    private Double rollerCostMultiplier = 1.0;
    private Double motorCost = 1.0;

    private boolean allFauxSelected = false;
    private boolean allRollerSelected = false;
    private boolean allZebraSelected = false;

    private final int[][] roller_square_footage = {
            {0,     12,	    24,	    36,	    48,	    60,	    72,	    84,	    96,	    108,    120,    132,    144},
            {12,	9,	    9,	    9,	    9,	    9,	    9,	    9,	    9,	    9,	    10,	    11,	    12},
            {24,	9,	    9,	    9,	    9,	    18,	    12,	    14,	    16,	    18,     20,	    22,	    24},
            {36,	9,	    9,	    9,	    12,	    15,	    18,	    21,	    24,	    27,     30,	    33,	    36},
            {48,	9,	    9,	    12,	    16,	    20,	    24,	    28,	    32,	    36,     40,	    44,	    48},
            {60,	9,	    10,	    15,	    20,	    25,	    30,	    35,	    40,	    45,     50,	    55,	    60},
            {72,	9,	    12,	    18,	    24,	    30,	    36,	    42,	    48,	    54,     60,	    66,	    72},
            {84,	9,	    14,	    21,	    28,	    35,	    42,	    49,	    56,	    63,     70,	    77,	    84},
            {96,	9,	    16,	    24,	    32,	    40,	    48,	    56,	    64,	    72,     80,	    88,	    96},
            {108,	9,	    18,	    27,	    36,	    45,	    54,	    63,	    72,	    81,     90,	    99,	    108},
            {120,	10,	    20,	    30,	    40,	    50,	    60,	    70,	    80,	    90,     100,    110,	120},
            {132,	11,	    22,	    33,	    44,	    55,	    66,	    77,	    88,	    99,     110,    121,	132},
            {144,	12,	    24,	    36,	    48,	    60,	    72,	    84,	    96,	    108,    120,    132,	144}
    };

    private final int[][] faux_cost ={
            {0,     24,     30,	    36,	    42,	    48,	    54,	    60,	    66,	    72,	    78,	    84,	    90,	    96},
            {18,	38,	    45,	    52,	    58,	    66,	    72,	    80,	    88,	    95,	    103,	111,	119,	126},
            {24,	42,	    51,	    58,	    65,	    74,	    81,	    90,	    99,	    108,	117,	125,	134,	143},
            {30,	46,	    56,	    64,	    72,	    82,	    90,	    100,	110,	120,	130,	140,	150,	160},
            {36,	50,	    61,	    70,	    79,	    90,	    99,	    110,	121,	132,	143,	154,	165,	176},
            {42,	54,	    66,	    76,	    86,	    98,	    109,	121,	133,	145,	157,	169,	181,	193},
            {48,	58,	    71,	    82,	    93,	    107,	118,	131,	144,	157,	170,	183,	196,	210},
            {54,	62,	    76,	    88,	    101,	115,	127,	141,	155,	169,	184,	198,	212,	226},
            {60,	66,	    82,	    95,	    108,	123,	136,	151,	166,	182,	197,	212,	228,	243},
            {66,	70,	    87,	    101,	115,	131,	145,	161,	178,	194,	210,	227,	243,	259},
            {72,	74,	    92,	    107,	122,	139,	154,	172,	189,	206,	224,	241,	259,	276},
            {78,	78,	    97,	    113,	129,	147,	163,	182,	200,	219,	237,	256,	274,    -1},
            {84,	83,	    102,	119,	136,	155,	172,	192,	211,	231,	251,	270,    -1,     -1},
            {90,	87,	    107,	125,	143,	164,	181,	202,	223,	243,	264,    -1,     -1,     -1},
            {96,	91,	    112,	131,	150,	172,	191,	212,	234,	256,	277,    -1,     -1,     -1},
            {102,	95,	    118,	137,	157,	180,	200,	222,	245,	268,    -1,     -1,     -1,     -1},
            {108,	99,	    123,	143,	164,	188,	209,	233,	256,    -1,     -1,     -1,     -1,     -1},
            {114,	103,	128,	150,	171,	196,	218,	243,    -1,     -1,     -1,     -1,     -1,     -1},
            {120,	107,	133,	156,	178,	204,	227,	253,    -1,     -1,     -1,     -1,     -1,     -1}
    };

    public CostCalculator(ArrayList<InputHandler> inputHandlerList, BlindInformationGatherer blindInformationGatherer){
        this.inputHandlerList = inputHandlerList;
        this.blindInformationGatherer = blindInformationGatherer;
    }

    public String displayCostCalculations() {
        faux_blinds.clear();
        zebra_blinds.clear();
        roller_blinds.clear();
        rollerSquareFootageTotal = 0;
        zebraSquareFootageTotal = 0;
        totalCost = 0.0;
        getFauxCostMultiplier();
        getZebraCostMultiplier();
        getRollerCostMultiplier();
        getMotorCost();
        findRowsToCalculate();

        StringBuilder cost = new StringBuilder();
        buildFauxCalculations(cost);
        buildZebraCalculations(cost);
        buildRollerCalculations(cost);
        buildMotorCosts(cost);
        totalCost = totalFauxCost + totalRollerCost + totalZebraCost + totalMotorCost;
        String finalCost = "Total Cost: $" + totalCost.toString() + "\n";
        cost.append(finalCost);

        return cost.toString();
    }

    private void getFauxCostMultiplier(){
        EditText costMultiplier = blindInformationGatherer.findViewById(R.id.fauxMultiplier);
        String costMultiplierString = costMultiplier.getText().toString().trim();
        try{
            fauxCostMultiplier = Double.parseDouble(costMultiplierString);
        }catch (NumberFormatException ignored){
        }
    }

    private void getZebraCostMultiplier(){
        EditText costMultiplier = blindInformationGatherer.findViewById(R.id.zebraMultiplier);
        String costMultiplierString = costMultiplier.getText().toString().trim();
        try{
            zebraCostMultiplier = Double.parseDouble(costMultiplierString);
        }catch (NumberFormatException ignored){
        }
    }

    private void getRollerCostMultiplier(){
        EditText costMultiplier = blindInformationGatherer.findViewById(R.id.rollerMultiplier);
        String costMultiplierString = costMultiplier.getText().toString().trim();
        try{
            rollerCostMultiplier = Double.parseDouble(costMultiplierString);
        }catch (NumberFormatException ignored){
        }
    }

    private void getMotorCost(){
        EditText costMultiplier = blindInformationGatherer.findViewById(R.id.motorCost);
        String costMultiplierString = costMultiplier.getText().toString().trim();
        try{
            motorCost = Double.parseDouble(costMultiplierString);
        }catch (NumberFormatException ignored){
        }
    }

    private void findRowsToCalculate(){
        RadioButton allFaux = blindInformationGatherer.findViewById(R.id.allFaux);
        RadioButton allZebra = blindInformationGatherer.findViewById(R.id.allZebra);
        RadioButton allRoller = blindInformationGatherer.findViewById(R.id.allRoller);

        for (InputHandler inputHandler : inputHandlerList) {
            CheckBox checkBox = blindInformationGatherer.findViewById(inputHandler.getCalculateId());
            if (checkBox.isChecked()) {
                if (allFaux.isChecked()) {
                    faux_blinds.add(inputHandler);
                    allFauxSelected = true;
                } else if (allZebra.isChecked()) {
                    zebra_blinds.add(inputHandler);
                    allZebraSelected = true;
                } else if (allRoller.isChecked()) {
                    roller_blinds.add(inputHandler);
                    allRollerSelected = true;
                }else{
                    if (inputHandler.isRoller()) {
                        roller_blinds.add(inputHandler);
                    } else if (inputHandler.isZebra()) {
                        zebra_blinds.add(inputHandler);
                    } else if (inputHandler.isFaux()) {
                        faux_blinds.add(inputHandler);
                    }
                }
                if (inputHandler.isMotor()) {
                    numberOfMotors++;
                }
            }
        }
    }

    private void buildFauxCalculations(StringBuilder cost){
        if(!faux_blinds.isEmpty()) {
            cost.append("2\" Faux Blinds:\n");
            for (InputHandler inputHandler : faux_blinds) {
                buildDimensions(inputHandler, cost);
            }
            cost.append("Total Faux Cost: $").append(totalFauxCost.toString()).append("\n\n");
        }
    }

    private void buildZebraCalculations(StringBuilder cost){
        if(!zebra_blinds.isEmpty()) {
            cost.append("Zebra Blinds:\n");
            for (InputHandler inputHandler : zebra_blinds) {
                buildDimensions(inputHandler, cost);
            }
            cost.append("Total Square Footage: ").append(zebraSquareFootageTotal.toString()).append("\n");

            totalZebraCost = zebraCostMultiplier * zebraSquareFootageTotal;
            totalZebraCost = Math.round(totalZebraCost * 100.0) / 100.0;
            cost.append("Total Zebra cost: $").append(totalZebraCost.toString()).append("\n\n");
        }
    }

    private void buildRollerCalculations(StringBuilder cost){
        if(!roller_blinds.isEmpty()) {
            cost.append("Roller Blinds:\n");
            for (InputHandler inputHandler : roller_blinds) {
                buildDimensions(inputHandler, cost);
            }
            cost.append("Total Square Footage: ").append(rollerSquareFootageTotal.toString()).append("\n");

            totalRollerCost = rollerCostMultiplier * rollerSquareFootageTotal;
            totalRollerCost = Math.round(totalRollerCost * 100.0) / 100.0;
            cost.append("Total roller cost: $").append(totalRollerCost.toString()).append("\n\n");
        }
    }

    private void buildMotorCosts(StringBuilder cost){
        if(numberOfMotors != 0) {
            cost.append("Motors:\n");
            totalMotorCost = numberOfMotors * motorCost;
            totalMotorCost = Math.round(totalMotorCost * 100.0) / 100.0;
            cost.append(numberOfMotors.toString()).append(" motors ~ $").append(totalMotorCost.toString()).append("\n\n");
        }
    }

    private void buildDimensions(InputHandler inputHandler, StringBuilder cost){
        Integer count = inputHandler.getCount();
        Integer width = inputHandler.getWidth();
        Integer height = inputHandler.getHeight();
        Double widthRemainder = inputHandler.getRoundedWidthRemainder();
        Double heightRemainder = inputHandler.getRoundedHeightRemainder();
        cost.append(" ").append(count.toString()).append(". ").append(width.toString());
        if(widthRemainder != 0){
            cost.append("-").append(inputHandler.getRoundedWidthRemainderString());
        }
        cost.append("\" x ").append(height.toString());
        if(heightRemainder != 0){
            cost.append("-").append(inputHandler.getRoundedHeightRemainderString());
        }
        cost.append("\"  ~  $").append(calculateCost(inputHandler)).append("\n");
    }

    private String calculateCost(InputHandler inputHandler){
        Integer width = inputHandler.getWidth();
        Double widthRemainder = inputHandler.getRoundedWidthRemainder();
        Integer height = inputHandler.getHeight();
        Double heightRemainder = inputHandler.getRoundedHeightRemainder();
        Integer actualWidth = (int) Math.ceil(width + widthRemainder);
        Integer actualHeight = (int) Math.ceil(height + heightRemainder);
        Double individualCost = 0.0;

        if(inputHandler.isFaux() || allFauxSelected){
            individualCost = (double) calculateFaux(actualWidth, actualHeight);
            individualCost = fauxCostMultiplier * individualCost;
        }else if(inputHandler.isRoller() || allRollerSelected){
            individualCost = (double) calculateZebraAndRoller(actualWidth, actualHeight, false);
            individualCost = rollerCostMultiplier * individualCost;
        }else if(inputHandler.isZebra() || allZebraSelected){
            individualCost = (double) calculateZebraAndRoller(actualWidth, actualHeight, true);
            individualCost = zebraCostMultiplier * individualCost;
        }

        individualCost = Math.round(individualCost * 100.0)/100.0;
        return individualCost.toString();
    }

    private Integer calculateFaux(Integer actualWidth, Integer actualHeight){
        int col;
        int row;
        for(col = 1; col < faux_cost[0].length; col++){
            if(faux_cost[0][col-1] < actualWidth && actualWidth <= faux_cost[0][col]){
                break;
            }
        }
        for(row = 1; row < faux_cost.length; row++){
            if(faux_cost[row-1][0] < actualHeight && actualHeight <= faux_cost[row][0]){
                break;
            }
        }
        totalFauxCost += faux_cost[row][col];
        return faux_cost[row][col];
    }

    private Integer calculateZebraAndRoller(Integer actualWidth, Integer actualHeight, boolean isZebra){
        int col;
        int row;
        for(col = 1; col < roller_square_footage[0].length; col++){
            if(roller_square_footage[0][col-1] < actualWidth && actualWidth <= roller_square_footage[0][col]){
                break;
            }
        }
        for(row = 1; row < roller_square_footage.length; row++){
            if(roller_square_footage[row-1][0] < actualHeight && actualHeight <= roller_square_footage[row][0]){
                break;
            }
        }

        if(isZebra){
            zebraSquareFootageTotal += roller_square_footage[row][col];
        }else{
            rollerSquareFootageTotal += roller_square_footage[row][col];

        }
        return roller_square_footage[row][col];
    }
}
