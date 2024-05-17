package com.cc.airline.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserPrompter {

	protected String prompt = "?";
	protected BufferedReader lineReader;

	public UserPrompter() {
		lineReader = new BufferedReader(new InputStreamReader(System.in));
	}

	public UserPrompter(String prompt) {
		this();
		setPrompt(prompt);
	}

	public String getAnswer() {
        String answer = null;
        try {
            while (answer == null || answer.trim().isEmpty() || !answer.matches("[a-zA-Z]+")) {
                System.out.print(prompt + " ");
                answer = lineReader.readLine();
                if (!answer.matches("[a-zA-Z]+")) {
                    System.out.println("Please enter only alphabetic characters.");
                }
            }
            return answer.trim();
        } catch (IOException ioe) {
            // if console I/O fails there is no recovery
            return null;
        }
    }

	public String getAnswerId() {
        String answer = null;
        try {
            while (answer == null || answer.trim().isEmpty() || !answer.matches("\\d+")) {
                System.out.print(prompt + " ");
                answer = lineReader.readLine();
                if (!answer.matches("\\d+")) {
                    System.out.println("Please enter only numeric characters.");
                }
            }
            return answer.trim();
        } catch (IOException ioe) {
            // if console I/O fails there is no recovery
            return null;
        }
    }

	public boolean getYesNoAnswer() {
		for (int i = 0; i < 3; i++) {
			String answer = getAnswer();
			if (answer == null)
				return false;
			char ans = answer.toUpperCase().charAt(0);
			if (ans == 'Y')
				return true;
			setPrompt(getPrompt() + ". Please answer Y or N: ");
		}
		return false;
	}

	public static void main(String[] args) {
		UserPrompter up = new UserPrompter();
		up.setPrompt("What is your name?");
		System.out.println("hello " + up.getAnswer());
		UserPrompter confirmer = new UserPrompter("Are you ready?");
		if (confirmer.getYesNoAnswer()) {
			System.out.println("OK, let's go!");
		} else {
			System.out.println("Sorry to hear that.");
		}
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

}
