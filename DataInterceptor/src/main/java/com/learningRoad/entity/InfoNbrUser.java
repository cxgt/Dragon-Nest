package com.learningRoad.entity;


import lombok.Data;

import java.util.Date;

/**
 * @author chenxin
 * @date 2023/06/30 10:44
 */
@Data
public class InfoNbrUser {
	/**
	 * id
	 */
	private Long id;

	/**
	 * acc_nbr
	 */
	private String accNbr;

	/**
	 * user_id
	 */
	private String userId;

	/**
	 * 0 失效n      1 有效n
	 */
	private Integer state;

	/**
	 * end_time
	 */
	private Date endTime;

	/**
	 * province_no
	 */
	private String provinceNo;

	/**
	 * destroy_time
	 */
	private Date destroyTime;

	public InfoNbrUser() {}

	public InfoNbrUser(Long id, String accNbr, String userId, Integer state, Date endTime, String provinceNo, Date destroyTime) {
		this.id = id;
		this.accNbr = accNbr;
		this.userId = userId;
		this.state = state;
		this.endTime = endTime;
		this.provinceNo = provinceNo;
		this.destroyTime = destroyTime;
	}
}
