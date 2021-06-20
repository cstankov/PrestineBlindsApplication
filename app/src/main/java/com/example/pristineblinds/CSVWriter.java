package com.example.pristineblinds;

import java.util.ArrayList;

public class CSVWriter {
    private ArrayList<InputHandler> inputHandlerList;
    private BlindInformationGatherer blindInformationGatherer;

    public CSVWriter(ArrayList<InputHandler> inputHandlerList, BlindInformationGatherer blindInformationGatherer){
        this.inputHandlerList = inputHandlerList;
        this.blindInformationGatherer = blindInformationGatherer;
    }

    public void writeToCSV(){

    }
}
