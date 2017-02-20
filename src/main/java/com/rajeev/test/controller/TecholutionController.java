package com.rajeev.test.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajeev.test.comparator.SatisfactionComperator;
import com.rajeev.test.comparator.TimeTakenComperator;
import com.rajeev.test.util.SatisfactionTime;

@RestController
@RequestMapping("/techolution")
public class TecholutionController {
	private Logger log = Logger.getLogger(TecholutionController.class);
	
	private int haveTime;
	private int noOfItem;
	private int satisfactionsc = 0;

	/**
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	/**
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findSatisfaction")
	public Object findSatisfaction(HttpServletRequest request, Model model)
			throws Exception {
		

		List<SatisfactionTime> satsfactionTimeList = readInputFile();
		Collections.sort(satsfactionTimeList, new TimeTakenComperator());
		int satisfactionScore = getMaxSatisfaction(satsfactionTimeList);
		satisfactionsc = satisfactionScore;

		Collections.sort(satsfactionTimeList, new SatisfactionComperator());
		satisfactionScore = getMaxSatisfaction(satsfactionTimeList);

		if (satisfactionsc < satisfactionScore) {
			satisfactionsc = satisfactionScore;
		}
		return satisfactionsc;
	}

	/**
	 * @param satisfactionTimeList
	 * @return
	 */
	private int getMaxSatisfaction(List<SatisfactionTime> satisfactionTimeList) {

		int satisfactionScore = 0;
		for (int i = 0; i < satisfactionTimeList.size(); i++) {

			List<SatisfactionTime> listSliceNo = satisfactionTimeList.subList(
					i, satisfactionTimeList.size());
			int satisfaction = getSatisfaction(listSliceNo);

			if (satisfactionScore < satisfaction) {
				satisfactionScore = satisfaction;
			}

		}
		return satisfactionScore;
	}

	/**
	 * @param satisfactionTimeList
	 * @param haveTime
	 * @param noOfiItem
	 * @return current satisfaction scores
	 */
	private int getSatisfaction(List<SatisfactionTime> satisfactionTimeList) {

		int score = 0;
		int time = 0;
		int noOfItemTry = 0;
		for (SatisfactionTime st : satisfactionTimeList) {
			score = score + st.getSatisfactionAmount();
			time = time + st.getTimeTaken();
			noOfItemTry++;
			if (time < haveTime && noOfItemTry <= noOfItem)
				continue;
			else {

				break;
			}
		}

		return score;

	}

	/**
	 * @return
	 */
	private List<SatisfactionTime> readInputFile() {
		BufferedReader br = null;
		List<SatisfactionTime> satsfactionTimeList = new ArrayList<SatisfactionTime>();

		try {
			br = new BufferedReader(new FileReader("data.txt"));

			String line = br.readLine();
			int i = 0;
			int j = 0;
			while (line != null) {

				String[] strLine = line.split(" ");

				if (j == 0) {
					haveTime = Integer.parseInt(strLine[0]);
					noOfItem = Integer.parseInt(strLine[1]);
				} else {
					SatisfactionTime st = new SatisfactionTime();
					st.setSatisfactionAmount(strLine[0] != null ? Integer
							.parseInt(strLine[0]) : null);
					st.setTimeTaken(strLine[1] != null ? Integer
							.parseInt(strLine[1]) : null);
					st.setIndex(i++);
					satsfactionTimeList.add(st);
				}// System.out.println(st.toString());
				line = br.readLine();
				j++;
			}

		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return satsfactionTimeList;
	}

}
