package com.example.pristineblinds;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Blind {
    private BlindInformationGatherer blindInformationGatherer;
//    private MeasurementConverter measurementConverter;

    private InputParser inputParser;
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
    private Double squareFootage;
    private Double cost;

    public Blind(BlindInformationGatherer blindInformationGatherer){
        this.blindInformationGatherer = blindInformationGatherer;
        this.inputParser = new InputParser(blindInformationGatherer);
    }

    public void parseInputData(){
        CheckBox checkBox = blindInformationGatherer.findViewById(calculateId);
        if(checkBox.isChecked()) {
            count = inputParser.parseCount(countId);
            width = inputParser.parseWidth(widthId);
            widthRemainder = inputParser.parseWidthRemainder(widthRemainderId);
            height = inputParser.parseHeight(heightId);
            heightRemainder = inputParser.parseHeightRemainder(heightRemainderId);
            zebra = inputParser.parseIsZebra(zebraId);
            roller = inputParser.parseIsRoller(rollerId);
            faux = inputParser.parseIsFaux(fauxId);
            motor = inputParser.parseMotor(motorId);
            notes = inputParser.parseNotes(notesId);
            side = inputParser.parseLeftOrRight(leftId, rightId);
            roundedWidthRemainder = inputParser.parseRoundedWidthRemainder(widthRemainderId, widthRemainder);
            roundedHeightRemainder = inputParser.parseRoundedHeightRemainder(heightRemainderId, heightRemainder);
            roundedWidthRemainderString = inputParser.parseWidthRemainderString(roundedWidthRemainder);
            roundedHeightRemainderString = inputParser.parseHeightRemainderString(roundedHeightRemainder);
        }
    }

    public String toCSVOutput(){
        StringBuilder csvOutput = new StringBuilder();
        csvOutput.append(width.toString()).append('-').append(roundedWidthRemainderString);
        csvOutput.append(height.toString()).append('-').append(roundedHeightRemainderString);
        csvTypeOutput(csvOutput);
        csvOutput.append(notes).append(',');
        csvOutput.append(side).append(',');
        csvOutput.append(squareFootage.toString()).append(',');
        csvOutput.append(cost.toString()).append(",\n");
        return csvOutput.toString();
    }

    private void csvTypeOutput(StringBuilder csvOutput){
        if(faux){
            csvOutput.append("X,,");
        }else if(zebra){
            csvOutput.append(",X,");
        }else if(roller){
            csvOutput.append(",,X");
        }
        if(motor){
            csvOutput.append("X,");
        }
    }

    public Double getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(Double squareFootage) {
        this.squareFootage = squareFootage;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
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
