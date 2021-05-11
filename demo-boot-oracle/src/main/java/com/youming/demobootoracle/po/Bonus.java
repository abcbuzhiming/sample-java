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
public class Bonus implements Serializable {

    private static final long serialVersionUID = 1L;

	private String ename;
	private String job;
	private Double sal;
	private Double comm;


	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Double getSal() {
		return sal;
	}

	public void setSal(Double sal) {
		this.sal = sal;
	}

	public Double getComm() {
		return comm;
	}

	public void setComm(Double comm) {
		this.comm = comm;
	}

	@Override
	public String toString() {
		return "Bonus{" +
			", ename=" + ename +
			", job=" + job +
			", sal=" + sal +
			", comm=" + comm +
			"}";
	}
}
