package com.edu.thss.smartdental.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.edu.thss.smartdental.model.general.*;

public class Tools {
	
	public static boolean hasSDCard(){
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
	
	public static SDAccount [] getAccountInfoFromLocal (Context ctx) {

		try {
			FileInputStream streamIn = ctx.openFileInput("accounts.tmp");

	        ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
	        SDAccount [] account1 = (SDAccount []) objectinputstream.readObject();
	        
	        objectinputstream.close();
	        streamIn.close();
	        return account1;
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public static SDAccount[] parseSimpleAccount(String accountInfo){
		SDAccount[] result;
		try{
			JSONObject account = new JSONObject(accountInfo);
			
			JSONArray bills = account.getJSONArray("bills");
			result = new SDAccount[bills.length()];
			
			for(int j = 0; j < bills.length(); j++){
				result[j] = new SDAccount();
				JSONObject patient = bills.getJSONObject(j);
				result[j].patientId = Integer.parseInt(patient.getString("id"));
				result[j].patientName = patient.getString("patient");
				result[j].time = patient.getString("time");
				result[j].hospital = patient.getString("hospital");
				JSONArray medicines = patient.getJSONArray("medicines");
				
				result[j].success = account.getInt("success");
				result[j].medicine = new SDAccount.drug[medicines.length()];
				result[j].firstTotal = 0;
				result[j].finalTotal = 0;
				for(int i = 0; i < medicines.length(); i++){
					JSONObject drugs = medicines.getJSONObject(i);
					result[j].medicine[i] = result[j].new drug();
					result[j].medicine[i].medicineId = Integer.parseInt(drugs.getString("mid"));
					result[j].medicine[i].medicineCount = Integer.parseInt(drugs.getString("mcount"));
					result[j].medicine[i].medicineName = drugs.getString("mname");
					result[j].medicine[i].medicinePrice = Double.parseDouble(drugs.getString("mprice"));
					result[j].medicine[i].medicineReimbusement = Double.parseDouble(drugs.getString("mreimbusement"));
					result[j].medicine[i].medicineRatio = Double.parseDouble(drugs.getString("mratio"));
					result[j].medicine[i].medicineUnit = drugs.getString("munit");
					result[j].firstTotal = result[j].firstTotal + result[j].medicine[i].medicineCount * result[j].medicine[i].medicinePrice;
					result[j].finalTotal = result[j].finalTotal + result[j].medicine[i].medicineCount * (result[j].medicine[i].medicinePrice - result[j].medicine[i].medicineReimbusement);
				}
				result[j].totalRatio = (result[j].firstTotal - result[j].finalTotal) / result[j].firstTotal;
			}
			return result;
		}
		catch (JSONException e){
			Log.i("travis", "Json parses error!");
			e.printStackTrace();
			return null;
		}
	}
}
