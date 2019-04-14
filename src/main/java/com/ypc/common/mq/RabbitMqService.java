package com.ypc.common.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName RabbitMqService
 * @Description TODO
 * @Author JWD
 * @Date 2018/9/13
 * @Version 1.0
 **/
//@Component
public class RabbitMqService {
    @Autowired
    private AmqpTemplate rabbitTemplate;



    public void sendMessage(String message) {
        //发送消息
        this.rabbitTemplate.convertAndSend("hello", message);
    }

    @RabbitListener(queues = "hello")
    public void receive(String message){
        System.out.println(message);
    }
}
