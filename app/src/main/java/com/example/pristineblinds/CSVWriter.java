package com.example.pristineblinds;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.namespace.QName;

public class CSVWriter {
    private ArrayList<Blind> blindList;
    private BlindInformationGatherer blindInformationGatherer;
    private CostCalculator costCalculator;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    public CSVWriter(ArrayList<Blind> blindList, BlindInformationGatherer blindInformationGatherer, CostCalculator costCalculator){
        this.blindList = blindList;
        this.blindInformationGatherer = blindInformationGatherer;
        this.costCalculator = costCalculator;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void writeToCSV(){
        File baseDir = android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        EditText name = blindInformationGatherer.findViewById(R.id.name);
        File file;
        if(!name.getText().toString().equals("")){
            file = new File(baseDir, name.getText().toString() + ".csv");
        }else{
            file = new File(baseDir, "VBCpricing.csv");
        }
        try {
            int permission = ActivityCompat.checkSelfPermission(blindInformationGatherer, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                        blindInformationGatherer,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
            }
            FileWriter fileWriter = new FileWriter(file);
            StringBuilder csvOutput = buildCSVOutput();
            fileWriter.append(csvOutput);
            fileWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private StringBuilder buildCSVOutput(){
        StringBuilder csvOutput = new StringBuilder();
        getHeader(csvOutput);
        int index = 1;
        for(Blind blind : blindList){
            CheckBox checkBox = blindInformationGatherer.findViewById(blind.getCalculateId());
            if (checkBox.isChecked()) {
                csvOutput.append(Integer.toString(index))
                    .append(",");
                csvOutput.append(blind.toCSVOutput());
                index++;
            }
        }
        getFooter(csvOutput);
        return csvOutput;
    }

    private void getHeader(StringBuilder csvOutput){
        EditText name = blindInformationGatherer.findViewById(R.id.name);
        csvOutput.append("CUSTOMER'S NAME").append(',').append(name.getText().toString()).append(',');
        EditText phone = blindInformationGatherer.findViewById(R.id.phone);
        csvOutput.append("PHONE").append(',').append(phone.getText().toString()).append(',');
        csvOutput.append("DEPOSIT").append(',').append(costCalculator.getDeposit().toString()).append(",\n");

        EditText address = blindInformationGatherer.findViewById(R.id.address);
        csvOutput.append("ADDRESS").append(',').append(address.getText().toString()).append(',');
        EditText email = blindInformationGatherer.findViewById(R.id.email);
        csvOutput.append("EMAIL").append(',').append(email.getText().toString()).append(',');
        EditText install_date = blindInformationGatherer.findViewById(R.id.installationDate);
        csvOutput.append("INSTALLATION DATE").append(',').append(install_date.getText().toString()).append(",\n");

        csvOutput.append("#,WIDTH,HEIGHT,2\" Faux, Zebra, Roller, Motor, Notes, Control L/R, Square Footage, Cost,\n");
    }

    private void getFooter(StringBuilder csvOutput){
        csvOutput.append("ZEBRA SQ FT TOTAL,")
                .append(costCalculator.getZebraSquareFootageTotal().toString())
                .append(",,ZEBRA MULTIPLIER,")
                .append(costCalculator.getZebraCostMultiplier().toString())
                .append(",,ZEBRA TOTAL COST,")
                .append(costCalculator.getTotalZebraCost().toString())
                .append(",\n");

        csvOutput.append("ROLLER SQ FT TOTAL,")
                .append(costCalculator.getRollerSquareFootageTotal().toString())
                .append(",,ROLLER MULTIPLIER,")
                .append(costCalculator.getRollerCostMultiplier().toString())
                .append(",,ROLLER TOTAL COST,")
                .append(costCalculator.getTotalRollerCost().toString())
                .append(",\n");

        csvOutput.append(",,,,FAUX MULTIPLIER,")
                .append(costCalculator.getFauxCostMultiplier().toString())
                .append(",,FAUX TOTAL COST,")
                .append(costCalculator.getTotalFauxCost().toString())
                .append(",\n");

        csvOutput.append("NUMBER OF MOTORS,")
                .append(costCalculator.getNumberOfMotors().toString())
                .append(",,TOTAL MOTOR COST,")
                .append(costCalculator.getTotalMotorCost().toString())
                .append(",\n");

        int totalSqFootage = costCalculator.getRollerSquareFootageTotal() + costCalculator.getZebraSquareFootageTotal();
        csvOutput.append("SQUARE FOOT TOTAL,")
                .append(Integer.toString(totalSqFootage))
                .append(",\n");
        csvOutput.append("TOTAL COST,")
                .append(costCalculator.getTotalCost().toString())
                .append(",\n");
        csvOutput.append("DEPOSIT,")
                .append(costCalculator.getDeposit().toString())
                .append(",\n");
        double balance = costCalculator.getTotalCost() - costCalculator.getDeposit();
        csvOutput.append("BALANCE,")
                .append(Double.toString(balance))
                .append(",\n");
    }
}
