package com.sample.app.rocketmq.listener;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class TestTransactionListener extends AbstractTransactionListener {

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                log.info(new String(msg.getBody()));
        return LocalTransactionState.COMMIT_MESSAGE;
    }

}
