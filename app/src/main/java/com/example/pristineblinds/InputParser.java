package com.example.pristineblinds;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class InputParser {
    private BlindInformationGatherer blindInformationGatherer;
    private MeasurementConverter measurementConverter;

    public InputParser(BlindInformationGatherer blindInformationGatherer){
        this.blindInformationGatherer = blindInformationGatherer;
        this.measurementConverter = new MeasurementConverter();
    }

    public Integer parseCount(Integer countId){
        TextView count_entry = blindInformationGatherer.findViewById(countId);
        String count_string = count_entry.getText().toString();
        return Integer.parseInt(count_string.trim());
    }

    public Integer parseWidth(Integer widthId){
        EditText width_entry = blindInformationGatherer.findViewById(widthId);
        String width_string = width_entry.getText().toString();

        if(width_string.matches("")){
            Toast.makeText(blindInformationGatherer.getApplicationContext(), "Error: Missing width for a value.", Toast.LENGTH_SHORT).show();
        }

        try{
            return Integer.parseInt(width_string.trim());
        }catch (NumberFormatException e){
            Toast.makeText(blindInformationGatherer.getApplicationContext(), "Error: Width value incorrect.", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    public Double parseWidthRemainder(Integer widthRemainderId){
        EditText width_remainder_entry = blindInformationGatherer.findViewById(widthRemainderId);
        String width_remainder_string = width_remainder_entry.getText().toString();

        if(!width_remainder_string.matches("")){
            try{
                if(width_remainder_string.contains("/")){
                    String[] ratio = width_remainder_string.trim().split("/");
                    return Double.parseDouble(ratio[0]) / Double.parseDouble(ratio[1]);

                }
            }catch (NumberFormatException e){
                Toast.makeText(blindInformationGatherer.getApplicationContext(), "Error: Width remainder value incorrect.", Toast.LENGTH_SHORT).show();
            }
        }
        return 0.0;
    }

    public Integer parseHeight(Integer heightId){
        EditText height_entry = blindInformationGatherer.findViewById(heightId);
        String height_string = height_entry.getText().toString();

        if(height_string.matches("")){
            Toast.makeText(blindInformationGatherer.getApplicationContext(), "Error: Missing height for a value.", Toast.LENGTH_SHORT).show();
        }

        try{
            return Integer.parseInt(height_string.trim());
        }catch (NumberFormatException e){
            Toast.makeText(blindInformationGatherer.getApplicationContext(), "Error: Height value incorrect.", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    public Double parseHeightRemainder(Integer heightRemainderId){
        EditText height_remainder_entry = blindInformationGatherer.findViewById(heightRemainderId);
        String height_remainder_string = height_remainder_entry.getText().toString();

        if(!height_remainder_string.matches("")){
            try{
                if(height_remainder_string.contains("/")){
                    String[] ratio = height_remainder_string.trim().split("/");
                    return Double.parseDouble(ratio[0]) / Double.parseDouble(ratio[1]);
                }
            }catch (NumberFormatException e){
                Toast.makeText(blindInformationGatherer.getApplicationContext(), "Error: Height remainder value incorrect.", Toast.LENGTH_SHORT).show();
            }
        }
        return 0.0;
    }

    public boolean parseIsZebra(Integer zebraId){
        RadioButton zebra_entry = blindInformationGatherer.findViewById(zebraId);
        return zebra_entry.isChecked();
    }

    public boolean parseIsRoller(Integer rollerId){
        RadioButton roller_entry = blindInformationGatherer.findViewById(rollerId);
        return roller_entry.isChecked();
    }
    public boolean parseIsFaux(Integer fauxId){
        RadioButton faux_entry = blindInformationGatherer.findViewById(fauxId);
        return faux_entry.isChecked();
    }

    public boolean parseMotor(Integer motorId){
        RadioButton motor_entry = blindInformationGatherer.findViewById(motorId);
        return motor_entry.isChecked();
    }

    public String parseLeftOrRight(Integer leftId, Integer rightId){
        RadioButton left_entry = blindInformationGatherer.findViewById(leftId);
        RadioButton right_entry = blindInformationGatherer.findViewById(rightId);

        if(left_entry.isChecked()){
            return "Left";
        }
        if(right_entry.isChecked()){
            return "Right";
        }
        else{
            Toast.makeText(blindInformationGatherer.getApplicationContext(), "Error: Too many sides were selected.", Toast.LENGTH_SHORT).show();
            return "Unknown";
        }
    }

    public String parseNotes(Integer notesId){
        EditText notes_entry = blindInformationGatherer.findViewById(notesId);
        return notes_entry.getText().toString();
    }

    public Double parseRoundedWidthRemainder(Integer widthRemainderId, Double widthRemainder){
        EditText width_remainder_entry = blindInformationGatherer.findViewById(widthRemainderId);
        String width_remainder_string = width_remainder_entry.getText().toString().trim();
        return measurementConverter.roundWidthRemainder(width_remainder_string, widthRemainder);
    }

    public String parseWidthRemainderString(Double roundedWidthRemainder){
        return measurementConverter.decimalToFraction(roundedWidthRemainder);
    }

    public Double parseRoundedHeightRemainder(Integer heightRemainderId, Double heightRemainder){
        EditText height_remainder_entry = blindInformationGatherer.findViewById(heightRemainderId);
        String height_remainder_string = height_remainder_entry.getText().toString().trim();
        return measurementConverter.roundHeightRemainder(height_remainder_string, heightRemainder);
    }

    public String parseHeightRemainderString(Double roundedHeightRemainder){
        return measurementConverter.decimalToFraction(roundedHeightRemainder);
    }
}
