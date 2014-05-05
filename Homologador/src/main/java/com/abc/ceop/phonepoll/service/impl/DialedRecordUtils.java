package com.abc.ceop.phonepoll.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.abc.ceop.model.dto.DialedOption;
import com.abc.ceop.model.dto.DialedRecord;

public class DialedRecordUtils {
	
	
	private DialedRecordUtils() {}
	
	public static List<DialedOption> createHeader(List<DialedRecord> dialedRecords) {
		Set<DialedOption> headerFields = new LinkedHashSet<DialedOption>();
		for (DialedRecord dialedRecord : dialedRecords) {
			headerFields.addAll(dialedRecord.getDialedValues().keySet());
		}
		return sortHeader(headerFields); 
	}
	
	public static List<DialedOption> sortHeader(Set<DialedOption> headerFields) {
		DialedOption[] dialedOptions = headerFields.toArray(new DialedOption[headerFields.size()]);
		List<DialedOption> dialedOptionSortedHeader = new ArrayList<DialedOption>();
		if (dialedOptions.length > 0) {
			dialedOptionSortedHeader.add(dialedOptions[0]);
			for (int i = 1; i < dialedOptions.length; i++) {
				if (!dialedOptionSortedHeader.contains(dialedOptions[i])) {
					if (Pattern.matches(".+\\d+", (dialedOptions[i]).getColumn())) {
						List<DialedOption> aux = new ArrayList<DialedOption>();
						String[] pn = new String[2];
						pn[0] = (dialedOptions[i]).getColumn().replaceAll("\\d+", "");
						pn[1] = (dialedOptions[i]).getColumn().replace(pn[0], "");
						aux.add(dialedOptions[i]);
						for (int j = i + 1; j < dialedOptions.length; j++) {
							String letter = (dialedOptions[j]).getColumn().replaceAll("\\d+", "");
							if (letter.equals(pn[0])) {
								aux.add(dialedOptions[j]);
							}
						}
						Collections.sort(aux, new Comparator<DialedOption>() {
							@Override
							public int compare(DialedOption o1, DialedOption o2) {
								String o1n = o1.getColumn().replaceAll("\\D+", "");
								String o2n = o2.getColumn().replaceAll("\\D+", "");
								return new Integer(o1n).compareTo(new Integer(o2n));
							}
						});
						dialedOptionSortedHeader.addAll(aux);
					} else {
						dialedOptionSortedHeader.add(dialedOptions[i]);
					}
				}
			}
		}
		return dialedOptionSortedHeader;
	}
	
}
