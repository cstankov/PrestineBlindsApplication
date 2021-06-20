package com.example.pristineblinds;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class InputHandler {
    private BlindInformationGatherer blindInformationGatherer;
    private MeasurementConverter measurementConverter;

    private Integer calculateId;
    private Integer countId;
    private Integer widthId;
    private Integer widthRemainderId;
    private Integer heightId;
    private Integer heightRemainderId;
    private Integer fauxId;
    private Integer zebraId;
    private Integer rollerId;
    private Integer motorId;
    private Integer notesId;
    private Integer leftId;
    private Integer rightId;

    private Integer count = -1;
    private Integer width = -1;
    private Double widthRemainder = 0.0;
    private Integer height = -1;
    private Double heightRemainder = 0.0;
    private boolean zebra = false;
    private boolean faux = false;
    private boolean roller = false;
    private boolean motor = false;
    private String notes = "";
    private String side = "";

    private Double roundedWidthRemainder = 0.0;
    private String roundedWidthRemainderString = "";
    private Double roundedHeightRemainder = 0.0;
    private String roundedHeightRemainderString = "";

    public InputHandler(BlindInformationGatherer blindInformationGatherer){
        this.blindInformationGatherer = blindInformationGatherer;
        measurementConverter = new MeasurementConverter();
    }

    public void parseInputData(){
        CheckBox checkBox = blindInformationGatherer.findViewById(calculateId);
        if(checkBox.isChecked()) {
            parseCount();
            parseWidth();
            parseWidthRemainder();
            parseHeight();
            parseHeightRemainder();
            parseType();
            parseMotor();
            parseNotes();
            parseLeftOrRight();
            roundRemainder();
        }
    }

    private void parseCount(){
        TextView count_entry = blindInformationGatherer.findViewById(countId);
        String count_string = count_entry.getText().toString();
        count = Integer.parseInt(count_string.trim());
    }

    private void parseWidth(){
        EditText width_entry = blindInformationGatherer.findViewById(widthId);
        String width_string = width_entry.getText().toString();

        if(width_string.matches("")){
            Toast.makeText(blindInformationGatherer.getApplicationContext(), "Error: Missing width for a value.", Toast.LENGTH_SHORT).show();
        }

        try{
            width = Integer.parseInt(width_string.trim());
        }catch (NumberFormatException e){
            Toast.makeText(blindInformationGatherer.getApplicationContext(), "Error: Width value incorrect.", Toast.LENGTH_SHORT).show();
        }
    }

    private void parseWidthRemainder(){
        EditText width_remainder_entry = blindInformationGatherer.findViewById(widthRemainderId);
        String width_remainder_string = width_remainder_entry.getText().toString();

        if(!width_remainder_string.matches("")){
            try{
                if(width_remainder_string.contains("/")){
                    String[] ratio = width_remainder_string.trim().split("/");
                    widthRemainder = Double.parseDouble(ratio[0]) / Double.parseDouble(ratio[1]);
                }
            }catch (NumberFormatException e){
                Toast.makeText(blindInformationGatherer.getApplicationContext(), "Error: Width remainder value incorrect.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseHeight(){
        EditText height_entry = blindInformationGatherer.findViewById(heightId);
        String height_string = height_entry.getText().toString();

        if(height_string.matches("")){
            Toast.makeText(blindInformationGatherer.getApplicationContext(), "Error: Missing height for a value.", Toast.LENGTH_SHORT).show();
        }

        try{
            height = Integer.parseInt(height_string.trim());
        }catch (NumberFormatException e){
            Toast.makeText(blindInformationGatherer.getApplicationContext(), "Error: Height value incorrect.", Toast.LENGTH_SHORT).show();
        }
    }

    private void parseHeightRemainder(){
        EditText height_remainder_entry = blindInformationGatherer.findViewById(heightRemainderId);
        String height_remainder_string = height_remainder_entry.getText().toString();

        if(!height_remainder_string.matches("")){
            try{
                if(height_remainder_string.contains("/")){
                    String[] ratio = height_remainder_string.trim().split("/");
                    heightRemainder = Double.parseDouble(ratio[0]) / Double.parseDouble(ratio[1]);
                }
            }catch (NumberFormatException e){
                Toast.makeText(blindInformationGatherer.getApplicationContext(), "Error: Height remainder value incorrect.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseType(){
        RadioButton zebra_entry = blindInformationGatherer.findViewById(zebraId);
        RadioButton roller_entry = blindInformationGatherer.findViewById(rollerId);
        RadioButton faux_entry = blindInformationGatherer.findViewById(fauxId);

        if(zebra_entry.isChecked()){
            zebra = true;
        }
        if(roller_entry.isChecked()){
            roller = true;
        }
        if(faux_entry.isChecked()){
            faux = true;
        }

        if (zebra && roller || zebra && faux || roller && faux){
            faux = false;
            roller = false;
            zebra = false;
            Toast.makeText(blindInformationGatherer.getApplicationContext(), "Error: Too many types were selected.", Toast.LENGTH_SHORT).show();
        }
    }

    private void parseMotor(){
        RadioButton motor_entry = blindInformationGatherer.findViewById(motorId);
        if(motor_entry.isChecked()){
            motor = true;
        }
    }

    private void parseLeftOrRight(){
        RadioButton left_entry = blindInformationGatherer.findViewById(leftId);
        RadioButton right_entry = blindInformationGatherer.findViewById(rightId);

        if(left_entry.isChecked()){
            side = "Left";
        }
        if(right_entry.isChecked()){
            side = "Right";
        }
        if(right_entry.isChecked() && left_entry.isChecked()){
            side = "Unknown";
            Toast.makeText(blindInformationGatherer.getApplicationContext(), "Error: Too many sides were selected.", Toast.LENGTH_SHORT).show();
        }
    }

    private void parseNotes(){
        EditText notes_entry = blindInformationGatherer.findViewById(notesId);
        notes = notes_entry.getText().toString();
    }

    private void roundRemainder(){
        EditText width_remainder_entry = blindInformationGatherer.findViewById(widthRemainderId);
        String width_remainder_string = width_remainder_entry.getText().toString().trim();
        EditText height_remainder_entry = blindInformationGatherer.findViewById(heightRemainderId);
        String height_remainder_string = height_remainder_entry.getText().toString().trim();

        roundedWidthRemainder = measurementConverter.roundWidthRemainder(width_remainder_string, widthRemainder);
        roundedWidthRemainderString = measurementConverter.decimalToFraction(roundedWidthRemainder);
        roundedHeightRemainder = measurementConverter.roundHeightRemainder(height_remainder_string, heightRemainder);
        roundedHeightRemainderString = measurementConverter.decimalToFraction(roundedHeightRemainder);
    }

    public Integer getCalculateId() {
        return calculateId;
    }

    public void setCalculateId(Integer calculateId) {
        this.calculateId = calculateId;
    }

    public Integer getCountId() {
        return countId;
    }

    public void setCountId(Integer countId) {
        this.countId = countId;
    }

    public Integer getWidthId() {
        return widthId;
    }

    public void setWidthId(Integer widthId) {
        this.widthId = widthId;
    }

    public Integer getWidthRemainderId() {
        return widthRemainderId;
    }

    public void setWidthRemainderId(Integer widthRemainderId) {
        this.widthRemainderId = widthRemainderId;
    }

    public Integer getHeightId() {
        return heightId;
    }

    public void setHeightId(Integer heightId) {
        this.heightId = heightId;
    }

    public Integer getHeightRemainderId() {
        return heightRemainderId;
    }

    public void setHeightRemainderId(Integer heightRemainderId) {
        this.heightRemainderId = heightRemainderId;
    }

    public Integer getFauxId() {
        return fauxId;
    }

    public void setFauxId(Integer fauxId) {
        this.fauxId = fauxId;
    }

    public Integer getZebraId() {
        return zebraId;
    }

    public void setZebraId(Integer zebraId) {
        this.zebraId = zebraId;
    }

    public Integer getRollerId() {
        return rollerId;
    }

    public void setRollerId(Integer rollerId) {
        this.rollerId = rollerId;
    }

    public Integer getMotorId() {
        return motorId;
    }

    public void setMotorId(Integer motorId) {
        this.motorId = motorId;
    }

    public Integer getNotesId() {
        return notesId;
    }

    public void setNotesId(Integer notesId) {
        this.notesId = notesId;
    }

    public Integer getLeftId() {
        return leftId;
    }

    public void setLeftId(Integer leftId) {
        this.leftId = leftId;
    }

    public Integer getRightId() {
        return rightId;
    }

    public void setRightId(Integer rightId) {
        this.rightId = rightId;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getWidth() {
        return width;
    }

    public Double getWidthRemainder() {
        return widthRemainder;
    }

    public Integer getHeight() {
        return height;
    }

    public Double getHeightRemainder() {
        return heightRemainder;
    }

    public boolean isZebra() {
        return zebra;
    }

    public boolean isFaux() {
        return faux;
    }

    public boolean isRoller() {
        return roller;
    }

    public boolean isMotor() {
        return motor;
    }

    public String getNotes() {
        return notes;
    }

    public String getSide() {
        return side;
    }

    public Double getRoundedWidthRemainder() {
        return roundedWidthRemainder;
    }

    public String getRoundedWidthRemainderString() {
        return roundedWidthRemainderString;
    }

    public Double getRoundedHeightRemainder() {
        return roundedHeightRemainder;
    }

    public String getRoundedHeightRemainderString() {
        return roundedHeightRemainderString;
    }

}
