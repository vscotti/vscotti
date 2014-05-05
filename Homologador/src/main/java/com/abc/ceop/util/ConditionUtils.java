package com.abc.ceop.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;

import com.abc.ceop.model.dto.DialedOption;
import com.abc.ceop.model.entities.Thresholds;

public class ConditionUtils {

	public static boolean evalcondition(List<Thresholds> conditions, Map<DialedOption, Integer> values) {
		boolean valid = true;
		if(conditions != null &&
				conditions.size() <= 0) {
			return false;
		}			
		for (Thresholds condition : conditions) {
			try {
				valid &= eval(condition.getConditionRule(), values);
			} catch (CompileException e) {
				valid = false;
			} catch (InvocationTargetException e) {
				valid = false;
			}
		}
		return valid;
	}
	
	private static boolean eval(String condition, Map<DialedOption, Integer> values) throws CompileException, InvocationTargetException {
		List<String> list = new ArrayList<String>();
		
		String conditionAux = condition.replaceAll("HASVALUE", ">= 0");
		
		String b = conditionAux.replaceAll(" ","");
		b = b.replaceAll("[(]","");
		b = b.replaceAll("[)]","");
		b = b.replaceAll("!","");
		b = b.replaceAll("[||]","&");
		b = b.replaceAll("&&","&");
		b = b.replaceAll(">=","-");
		b = b.replaceAll("<=","-");
		b = b.replaceAll("==","-");
		b = b.replaceAll("<","-");
		b = b.replaceAll(">","-");
		String[] a = b.split("&");
		for (String d : a) {
			String[] h = d.split("-");
			if(h != null &&
					h.length == 2) {
				boolean duplicated = false;
				for (String string : list) {
					if(string.equals(h[0])) {
						duplicated = true;
					}
				}
				if(!duplicated) {
					list.add(h[0]);
				}
			}
		}
		
		String[] pamarNames = new String[list.size()];
		@SuppressWarnings("rawtypes")
		Class[] paramTypes = new Class[list.size()];
		Object[] parameterValues = new Object[list.size()];
		
		for (int i = 0; i < pamarNames.length; i++) {
			pamarNames[i] = list.get(i).toUpperCase();
			paramTypes[i] = int.class;
			parameterValues[i] = -1;
			for (DialedOption dialedOption : values.keySet()) {
				if((dialedOption.getColumn() != null &&
						dialedOption.getColumn().toUpperCase().equals(pamarNames[i])) ||
						(dialedOption.getColumnSynonym() != null &&
						dialedOption.getColumnSynonym().toUpperCase().equals(pamarNames[i]))) {
					parameterValues[i] = values.get(dialedOption) == null ? -1 : values.get(dialedOption);
					break;
				}
			}
		}
		
		// Compile the expression once; relatively slow.
		ExpressionEvaluator ee = new ExpressionEvaluator(
			conditionAux,                      	 // expression
		    boolean.class,                       // expressionType
		    pamarNames,           				 // parameterNames
		    paramTypes				 			 // parameterTypes
		);
		 
		// Evaluate it with varying parameter values; very fast.
		Boolean ret = (Boolean) ee.evaluate(
				parameterValues			         // parameterValues
		);
		
		return ret;
	}
}
