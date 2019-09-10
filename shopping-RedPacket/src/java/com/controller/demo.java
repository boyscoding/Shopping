package com.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @program: RedPacket3
 * @description:
 * @author: boyscoding
 * @create: 2019-09-01 07:10
 **/
public class demo {
	public String PrintMinNumber(int [] numbers) {
		if(numbers==null)
			return null;
		String s="";
		ArrayList<Integer> list= new ArrayList<Integer>();
		for(int i=0;i<numbers.length;i++){
			list.add(numbers[i]);

		}
		Collections.sort(list, new Comparator<Integer>(){

			public int compare(Integer str1,Integer str2){
				String s1=str1+""+str2;
				String s2=str2+""+str1;
				return s1.compareTo(s2);
			}
		});

		for(int j:list){
			s+=j;
		}
		return s;
	}

	public static void main(String[] args) {
		int[] num = {1,2,3};
	}
}
