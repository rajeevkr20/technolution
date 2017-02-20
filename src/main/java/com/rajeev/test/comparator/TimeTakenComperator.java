package com.rajeev.test.comparator;

import java.util.Comparator;

import com.rajeev.test.util.SatisfactionTime;

/**
 * @author Rajeev
 *
 */
public class TimeTakenComperator implements Comparator<SatisfactionTime> {

	public int compare(SatisfactionTime o1, SatisfactionTime o2) {
		
		return o1.getTimeTaken() - o2.getTimeTaken();
	}

}
