package com.rolling.code.accionesargentina.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rolling.code.accionesargentina.AccionActivity;
import com.rolling.code.accionesargentina.R;
import com.rolling.code.accionesargentina.listener.ActionesBonosRemoveButtonsOnTouchListener;
import com.rolling.code.accionesargentina.listener.OnTouchListenerRow;

public class AccionBonoViewGenerator {

	public void generateView(List<Float> colWidths, 
							 List<String> colNames, 
							 List<List<String>> results, 
							 Activity activity,
							 Date date,
							 Boolean esBono,
							 Boolean esSeguimiento) {
		TableLayout tl_header = (TableLayout) activity.findViewById(R.id.table_header);
		TableLayout tl_body = (TableLayout) activity.findViewById(R.id.accionesTableLayout);
		tl_body.removeAllViews();
		tl_header.removeAllViews();
		
		generateTableContent(tl_header, tl_body, colWidths, colNames, results, activity, esBono, esSeguimiento);
		
//		TableRow tr = new TableRow(activity);
//		TextView empty = new TextView(activity);
//		TextView col = new TextView(activity);
//		tr.addView(empty);
//		for (String colName : colNames) {
//			col = new TextView(activity);
//			col.setText(colName);
//			empty = new TextView(activity);
//			tr.addView(col);
//			tr.addView(empty);
//		}
//		col = new TextView(activity);
//		empty = new TextView(activity);
//		tr.addView(col);
//		tr.addView(empty);
//		
//		tl_header.addView(tr);
//		
//		tr.setGravity(Gravity.CENTER_VERTICAL);
//		
//        for ( int cellnum = 0 ; cellnum < tr.getChildCount() ; cellnum++ ) {
//            View cell = tr.getChildAt(cellnum);
//            TableRow.LayoutParams params = (TableRow.LayoutParams)cell.getLayoutParams();
//            params.width = 0;
//            params.weight = colWidths.get(cellnum);
//        }
//        TableLayout.LayoutParams param = (TableLayout.LayoutParams)tr.getLayoutParams();
//        param.height = 300;
//
//		tr = new TableRow(activity);
//		tr.setBackgroundColor(Color.GRAY);
//		tl_header.addView(tr);
//		
//		empty = new TextView(activity);
//		tr.addView(empty);
//        View cell = tr.getChildAt(0);
//        TableRow.LayoutParams params = (TableRow.LayoutParams)cell.getLayoutParams();
//        params.span = 12;
//        params.height = 2;
//		
//		param = (TableLayout.LayoutParams)tr.getLayoutParams();
//		param.height = 2;
//
//		int count = 0;
//		for (List<String> list : results) {
//			tr = new TableRow(activity);
//			
//			empty = new TextView(activity);
//			tr.addView(empty);
//			for (int i = 0 ; i < colNames.size() ; i++) {
//				col = new TextView(activity);
//				col.setText(list.get(i));
//				col.setTextColor(Color.BLACK);
//				col.setTypeface(null, Typeface.BOLD);
//				empty = new TextView(activity);
//				tr.addView(col);
//				tr.addView(empty);
//			}
//
//			String value = "nochanges";
//			if(list.get(colNames.size()-1).startsWith("-")) {
//				value = "downchanges";
//			} else if(!isZero(list.get(colNames.size()-1))) {
//				value = "upchanges";
//			}
//			
//			int id = 0;
//			id = activity.getResources().getIdentifier(value, "drawable", activity.getPackageName());
//			ImageView imageView = new ImageView(activity);
//			if(id != 0) {
//				imageView.setBackgroundResource(id);
//			}
//			tr.setGravity(Gravity.CENTER_VERTICAL);
//			
//			empty = new TextView(activity);
//			tr.addView(imageView);
//			tr.addView(empty);
//			tl_body.addView(tr);
//
//			if(!esBono) {
//				tr.setOnTouchListener(new OnTouchListenerRow(AccionActivity.class, activity, list.get(0), list.get(1), list.get(2), list.get(3), null));
//			} else {
//				tr.setOnTouchListener(new OnTouchListenerRow(AccionActivity.class, activity, list.get(0), list.get(2), list.get(3), list.get(4), list.get(1)));
//			}
//
//			if(count % 2 == 0) {
//				tr.setBackgroundColor(Color.WHITE);
//			} else {
//				tr.setBackgroundColor(Color.GRAY);
//			}
//			
//	        for ( int cellnum = 0 ; cellnum < tr.getChildCount() ; cellnum++ ) {
//	            cell = tr.getChildAt(cellnum);
//	            params = (TableRow.LayoutParams)cell.getLayoutParams();
//	            params.width = 0;
//	            params.weight = colWidths.get(cellnum);
//	        }
//
//			tr = new TableRow(activity);
//			tr.setBackgroundColor(Color.GRAY);
//			tl_body.addView(tr);
//			
//			empty = new TextView(activity);
//			tr.addView(empty);
//            cell = tr.getChildAt(0);
//            params = (TableRow.LayoutParams)cell.getLayoutParams();
//            params.span = 12;
//            params.height = 1;
//			
//			param = (TableLayout.LayoutParams)tr.getLayoutParams();
//			param.height = 1;
//            
//			count++;
//		}
		
		SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
		sdf.applyPattern("dd/MM/yyyy hh:mm a");
		String lastDate = sdf.format(date);
		TextView dateLastUpdate = (TextView) activity.findViewById(R.id.date_update_index);
		dateLastUpdate.setText("Ultima actualizacion: " + lastDate);
		
	}
	
	private boolean isZero(String number) {
		for (int i = 0 ; i < number.length() ; i++) {
			if(Character.isDigit(number.charAt(i)) &&
					Character.getNumericValue(number.charAt(i)) != 0) {
				return false;
			}
		}
		return true;
	}
	
	public void generateTableContent(TableLayout tl_header,
									 TableLayout tl_body,
									 List<Float> colWidths, 
									 List<String> colNames, 
									 List<List<String>> results, 
									 Activity activity,
									 Boolean esBono,
									 Boolean esSeguimiento) {
		TableRow tr = new TableRow(activity);
		TextView empty = new TextView(activity);
		TextView col = new TextView(activity);
		tr.addView(empty);
		for (String colName : colNames) {
			col = new TextView(activity);
			col.setText(colName);
			empty = new TextView(activity);
			tr.addView(col);
			tr.addView(empty);
		}
//		col = new TextView(activity);
//		empty = new TextView(activity);
//		tr.addView(col);
//		tr.addView(empty);
		
		tl_header.addView(tr);
		
		tr.setGravity(Gravity.CENTER_VERTICAL);
		
		int delay = 0;
		if(esSeguimiento) {
			delay = 1;
		}
		
        for ( int cellnum = 0 ; cellnum < tr.getChildCount() ; cellnum++ ) {
            View cell = tr.getChildAt(cellnum);
            TableRow.LayoutParams params = (TableRow.LayoutParams)cell.getLayoutParams();
            params.width = 0;
            params.weight = colWidths.get(cellnum);
        }
        TableLayout.LayoutParams param = (TableLayout.LayoutParams)tr.getLayoutParams();
        param.height = 300;

		if(esSeguimiento) {
			col = new TextView(activity);
			empty = new TextView(activity);
			tr.addView(col);
			tr.addView(empty);
		}
		
		tr = new TableRow(activity);
		tr.setBackgroundColor(Color.GRAY);
		tl_header.addView(tr);
		
		empty = new TextView(activity);
		tr.addView(empty);
        View cell = tr.getChildAt(0);
        TableRow.LayoutParams params = (TableRow.LayoutParams)cell.getLayoutParams();
        params.span = 12;
        params.height = 2;
		
		param = (TableLayout.LayoutParams)tr.getLayoutParams();
		param.height = 2;

		int count = 0;
		for (List<String> list : results) {
			tr = new TableRow(activity);
			tr.setGravity(Gravity.CENTER_VERTICAL);
			
			empty = new TextView(activity);
			tr.addView(empty);
			for (int i = 0 ; i < colNames.size() ; i++) {
				col = new TextView(activity);
				col.setText(list.get(i + delay).trim());
				col.setTextColor(Color.BLACK);
				col.setTextSize(15f);
				col.setTypeface(null, Typeface.BOLD);
				empty = new TextView(activity);
				tr.addView(col);
				tr.addView(empty);
			}

			TextView var = (TextView) tr.getChildAt(tr.getChildCount() - 2);
			var.setGravity(Gravity.CENTER_VERTICAL);
			if(list.get(colNames.size()-1 + delay).startsWith("-")) {
				var.setTextColor(Color.rgb(255, 0, 0));
			} else if(!isZero(list.get(colNames.size()-1 + delay))) {
				var.setText("+" + var.getText());
				var.setTextColor(Color.rgb(0, 255, 0));
			} else {
				var.setText(" " + var.getText());
				var.setTextColor(Color.rgb(0, 0, 0));
			}
			

//			String value = "nochanges";
//			if(list.get(colNames.size()-1 + delay).startsWith("-")) {
//				value = "downchanges";
//			} else if(!isZero(list.get(colNames.size()-1 + delay))) {
//				value = "upchanges";
//			}
//			
//			int id = 0;
//			id = activity.getResources().getIdentifier(value, "drawable", activity.getPackageName());
//			ImageView imageView = new ImageView(activity);
//			if(id != 0) {
//				imageView.setBackgroundResource(id);
//			}
//			tr.setGravity(Gravity.CENTER_VERTICAL);
//			
//			empty = new TextView(activity);
//			tr.addView(imageView);
//			tr.addView(empty);
			
			tl_body.addView(tr);

			if(!esBono) {
				tr.setOnTouchListener(new OnTouchListenerRow(AccionActivity.class, activity, list.get(0 + delay), list.get(1 + delay), list.get(2 + delay), list.get(3 + delay), null));
			} else {
				tr.setOnTouchListener(new OnTouchListenerRow(AccionActivity.class, activity, list.get(0 + delay), list.get(2 + delay), list.get(3 + delay), list.get(4 + delay), list.get(1 + delay)));
			}

			if(count % 2 == 0) {
				tr.setBackgroundColor(Color.WHITE);
			} else {
				tr.setBackgroundColor(Color.GRAY);
			}
			
	        for ( int cellnum = 0 ; cellnum < tr.getChildCount() ; cellnum++ ) {
	            cell = tr.getChildAt(cellnum);
	            params = (TableRow.LayoutParams)cell.getLayoutParams();
	            params.width = 0;
	            params.weight = colWidths.get(cellnum);
	        }

	        if(esSeguimiento) {
				Button remove = new Button(activity);
				remove.setText("-");
				remove.setBackgroundResource(R.drawable.shapered);
				remove.setTextColor(Color.WHITE);
				remove.setTypeface(null, Typeface.BOLD);
				remove.setOnClickListener(new ActionesBonosRemoveButtonsOnTouchListener(activity, Long.valueOf(list.get(0)), list.get(2), esBono));
				remove.setPadding(7, 4, 7, 4);
				tr.addView(remove);
				empty = new TextView(activity);
				empty.setText(" ");
				tr.addView(empty);
	        }
	        
			tr = new TableRow(activity);
			tr.setBackgroundColor(Color.GRAY);
			tl_body.addView(tr);
			
			empty = new TextView(activity);
			tr.addView(empty);
            cell = tr.getChildAt(0);
            params = (TableRow.LayoutParams)cell.getLayoutParams();
            params.span = 12;
            params.height = 1;
			
			param = (TableLayout.LayoutParams)tr.getLayoutParams();
			param.height = 1;
            
			count++;
		}
	}
}
