package com.youming.demobootoracle.po;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Author
 * @since 2018-06-05
 */
public class Salgrade implements Serializable {

    private static final long serialVersionUID = 1L;

	private Double grade;
	private Double losal;
	private Double hisal;


	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public Double getLosal() {
		return losal;
	}

	public void setLosal(Double losal) {
		this.losal = losal;
	}

	public Double getHisal() {
		return hisal;
	}

	public void setHisal(Double hisal) {
		this.hisal = hisal;
	}

	@Override
	public String toString() {
		return "Salgrade{" +
			", grade=" + grade +
			", losal=" + losal +
			", hisal=" + hisal +
			"}";
	}
}
