package com.eden.domain;

import java.io.Serializable;

import com.eden.adapter.ExpertAdapter;

public class ListExpertAdpeter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4960006889076989046L;
	private ExpertAdapter adapter;

	public ListExpertAdpeter(ExpertAdapter adapter) {
		super();
		this.adapter = adapter;
	}

	public ExpertAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(ExpertAdapter adapter) {
		this.adapter = adapter;
	}

}
