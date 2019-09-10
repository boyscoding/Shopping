package com.dao;

import com.pojo.UserRedPacket;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRedPacketDao {

	/**
	 * 插入抢红包信息.
	 * @param userRedPacket ——抢红包信息
	 * @return 影响记录数.
	 */
	public int grapRedPacket(UserRedPacket userRedPacket);
}
