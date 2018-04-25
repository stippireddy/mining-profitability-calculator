package stippireddy.ufl.edu.miningcalculator;

import java.io.Serializable;

public class CurrencyData implements Serializable{

    String currencyID;
    String currencyName;
    double blockReward;
    double difficulty;
    double exchangeRate;
    String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(String currencyID) {
        this.currencyID = currencyID;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public double getBlockReward() {
        return blockReward;
    }

    public void setBlockReward(double blockReward) {
        this.blockReward = blockReward;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {
        return "CurrencyData{" +
                "currencyID='" + currencyID + '\'' +
                ", currencyName='" + currencyName + '\'' +
                ", blockReward=" + blockReward +
                ", difficulty=" + difficulty +
                ", exchangeRate=" + exchangeRate +
                ", tag='" + tag + '\'' +
                '}';
    }
}
