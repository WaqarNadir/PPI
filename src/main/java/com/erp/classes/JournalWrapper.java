package com.erp.classes;

import java.util.List;

public class JournalWrapper {
	private Journal journal;
	private List<JournalDetails> JDList;

	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	public List<JournalDetails> getJDList() {
		return JDList;
	}

	public void setJDList(List<JournalDetails> jDList) {
		JDList = jDList;
	}

}
