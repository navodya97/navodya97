package com.example.healthmonitor.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("result_code")
    private int resultCode;

    @SerializedName("full_name")
    private String FullName;

    @SerializedName("body_temp")
    private String Temp;

    @SerializedName("heart_rate")
    private String Heart_rate;

    @SerializedName("ppg")
    private String PPG;

    @SerializedName("battery_level")
    private String Battery_level;



    @SerializedName("max_temp")
    private String Max_temp;

    @SerializedName("max_time_temp")
    private String Max_time_temp;

    @SerializedName("min_temp")
    private String Min_temp;

    @SerializedName("min_time_temp")
    private String Min_time_temp;



    @SerializedName("max_heart")
    private String Max_heart;

    @SerializedName("max_time_heart")
    private String Max_time_heart;

    @SerializedName("min_heart")
    private String Min_heart;

    @SerializedName("min_time_heart")
    private String Min_time_heart;



    @SerializedName("max_ppg")
    private String Max_ppg;

    @SerializedName("max_time_ppg")
    private String Max_time_ppg;

    @SerializedName("min_ppg")
    private String Min_ppg;

    @SerializedName("min_time_ppg")
    private String Min_time_ppg;



    @SerializedName("data_temp")
    ArrayList<data_graphtemp> Data_temp;

    public class data_graphtemp{

        @SerializedName("body_temp1")
        private String body_temp1;

        @SerializedName("time_s")
        private String time_s;

        public String getGraph_Temp() { return body_temp1; }

        public String getTime_s() {
            return time_s;
        }
    }




    @SerializedName("data_heart_rate")
    ArrayList<data_graph_hear_trate> Data_heart_rate;

    public class data_graph_hear_trate{

        @SerializedName("heart_rate")
        private String heart_rate;

        @SerializedName("time_s")
        private String time_s;

        public String getHeart_rate() {
            return heart_rate;
        }

        public String getTime_s() {
            return time_s;
        }
    }



    @SerializedName("data_ppg")
    ArrayList<data_graph_ppg> Data_ppg;

    public class data_graph_ppg{

        @SerializedName("ppg")
        private String ppg;

        @SerializedName("time_s")
        private String time_s;

        public String getPpg() {
            return ppg;
        }

        public String getTime_s() {
            return time_s;
        }
    }

    @SerializedName("data_ecg")
    ArrayList<data_graph_ecg> Data_ecg;

    public class data_graph_ecg {

        @SerializedName("ecg")
        private String ppg;

        @SerializedName("time_s")
        private String time_s;

        public String getecg() {
            return ppg;
        }

        public String getTime_s() {
            return time_s;
        }
    }


    public ArrayList<data_graph_ecg> getData_ecg() { return Data_ecg; }

    public ArrayList<data_graph_hear_trate> getData_heart_rate() {
        return Data_heart_rate;
    }

    public ArrayList<data_graph_ppg> getData_ppg() {
        return Data_ppg;
    }

    public ArrayList<data_graphtemp> getTemp_data() {
        return Data_temp;
    }



    public String getTemp() {
        return Temp;
    }

    public String getHeart_rate() {
        return Heart_rate;
    }

    public String getPPG() {
        return PPG;
    }

    public String getBattery_level() { return Battery_level; }

    public String getFullName() {
        return FullName;
    }

    public String getStatus() {
        return status;
    }

    public int getResultCode() {
        return resultCode;
    }



    public String getMax_temp() {
        return Max_temp;
    }

    public String getMax_time_temp() {
        return Max_time_temp;
    }

    public String getMin_temp() {
        return Min_temp;
    }

    public String getMin_time_temp() {
        return Min_time_temp;
    }



    public String getMax_heart() {
        return Max_heart;
    }

    public String getMax_time_heart() {
        return Max_time_heart;
    }

    public String getMin_heart() {
        return Min_heart;
    }

    public String getMin_time_heart() {
        return Min_time_heart;
    }



    public String getMax_ppg() {
        return Max_ppg;
    }

    public String getMax_time_ppg() {
        return Max_time_ppg;
    }

    public String getMin_ppg() {
        return Min_ppg;
    }

    public String getMin_time_ppg() {
        return Min_time_ppg;
    }

}
