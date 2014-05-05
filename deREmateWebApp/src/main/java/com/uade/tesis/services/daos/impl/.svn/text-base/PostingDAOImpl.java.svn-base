package com.uade.tesis.services.daos.impl;

import java.util.List;

import com.uade.tesis.domains.Posting;
import com.uade.tesis.services.daos.PostingDAO;
import com.uade.tesis.services.daos.dummys.PostingsDummy;

public class PostingDAOImpl implements PostingDAO {

	private List<Posting> postings;
	
	public PostingDAOImpl() {
		PostingsDummy dummy = new PostingsDummy();
		postings = dummy.getPostings();
	}
	
	public Long getNextPostingId() {
		return new Long(postings.size()+1);
	}
	
	public Posting getPostingById(Long id) {
		for(Posting element : postings) {
			if(element.getId().equals(id)) {
				return element;
			}
		}
		return null;
	}
	
	public void savePosting(Posting posting) {
		postings.add(posting);
	}
	
	public List<Posting> getAllPostings() {
		return postings;
	}
}
