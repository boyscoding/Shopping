package com.service;

/**
 * @program: RedPacket
 * @description:
 * @author: boyscoding
 * @create: 2019-08-31 17:40
 **/
public interface RedisRedPacketService {
	public void saveUserRedPacketByRedis(Long redPacketId, Double unitAmount);
}