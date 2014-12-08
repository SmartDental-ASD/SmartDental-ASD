package com.edu.thss.smartdental.model.general;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class parseJson {
	public class drug {
		public int medicineId;
		public String medicineName;
		public double medicinePrice;
		public int medicineCount;
		public double medicineReimbusement;
		public double medicineRatio;
		public drug (){
			medicineId = -1;
			medicineName = "hello";
			medicinePrice = -1.0;
			medicineCount = -1;
			medicineReimbusement = -1.0;
			medicineRatio = -1.0;
		}
	}
	public class Account {
		public String patientName;
		public int patientId;
		public String time;
		public String hospital;
		public drug[] medicine;
		public int success;
		public double firstTotal;
		public double finalTotal;
		public double totalRatio;
		public Account (){
			patientName = "hello";
			patientId = -1;
			time = "hello";
			hospital = "hello";
			
			success = -1;
			firstTotal = -1.0;
			finalTotal = -1.0;
			totalRatio = -1.0;
		}
	}
	public parseJson() {}
	public Account[] parseSimpleAccount(String accountInfo){
		//String accountInfo = "{\"bills\":[{\"id\":\"2\",\"patient\":\"你大爷的！！\",\"time\":\"2014-12-04 10:50:23\",\"hospital\":\"校医院\",\"medicines\":[{\"mid\":\"1\",\"mcount\":\"15\",\"mname\":\"去你妹的！！！\",\"mprice\":\"10\",\"mreimbusement\":\"2\",\"mratio\":\"0.2\"}]}],\"success\":1}";
		Account[] result;
		try{
			JSONObject account = new JSONObject(accountInfo);
			
			JSONArray bills = account.getJSONArray("bills");
			result = new Account[bills.length()];
			
			for(int j = 0; j < bills.length(); j++){
				result[j] = new Account();
				JSONObject patient = bills.getJSONObject(j);
				result[j].patientId = Integer.parseInt(patient.getString("id"));
				result[j].patientName = patient.getString("patient");
				result[j].time = patient.getString("time");
				result[j].hospital = patient.getString("hospital");
				JSONArray medicines = patient.getJSONArray("medicines");
				
				result[j].success = account.getInt("success");
				result[j].medicine = new drug[medicines.length()];
				result[j].firstTotal = 0;
				result[j].finalTotal = 0;
				for(int i = 0; i < medicines.length(); i++){
					JSONObject drugs = medicines.getJSONObject(i);
					result[j].medicine[i] = new drug();
					result[j].medicine[i].medicineId = Integer.parseInt(drugs.getString("mid"));
					result[j].medicine[i].medicineCount = Integer.parseInt(drugs.getString("mcount"));
					result[j].medicine[i].medicineName = drugs.getString("mname");
					result[j].medicine[i].medicinePrice = Double.parseDouble(drugs.getString("mprice"));
					result[j].medicine[i].medicineReimbusement = Double.parseDouble(drugs.getString("mreimbusement"));
					result[j].medicine[i].medicineRatio = Double.parseDouble(drugs.getString("mratio"));
					result[j].firstTotal = result[j].firstTotal + result[j].medicine[i].medicineCount * result[j].medicine[i].medicinePrice;
					result[j].finalTotal = result[j].finalTotal + result[j].medicine[i].medicineCount * (result[j].medicine[i].medicinePrice - result[j].medicine[i].medicineReimbusement);
				}
				result[j].totalRatio = (result[j].firstTotal - result[j].finalTotal) / result[j].firstTotal;
			}
			return result;
		} 
		catch (JSONException e){
			System.out.println("Json parses error!");
			e.printStackTrace();
			return null;
		}
	}
}
