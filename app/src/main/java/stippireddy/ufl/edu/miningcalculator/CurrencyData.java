/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package stippireddy.ufl.edu.miningcalculator;

import java.io.Serializable;

/**
 * Simple POJO class that contains the data about a specific coin.
 * This implements the serializable interface.
 * This allows this class objects to be serialized between components..
 *
 * All the methods and names in this class are self-explanatory.
 */
public class CurrencyData implements Serializable {

    String currencyID;
    String currencyName;
    double blockReward;
    double difficulty;
    double exchangeRate;
    String tag;
    double blockTime;

    @Override
    public String toString() {
        return "CurrencyData{" +
                "currencyID='" + currencyID + '\'' +
                ", currencyName='" + currencyName + '\'' +
                ", blockReward=" + blockReward +
                ", difficulty=" + difficulty +
                ", exchangeRate=" + exchangeRate +
                ", tag='" + tag + '\'' +
                ", blockTime=" + blockTime +
                '}';
    }

    public double getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(double blockTime) {
        this.blockTime = blockTime;
    }

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

}
