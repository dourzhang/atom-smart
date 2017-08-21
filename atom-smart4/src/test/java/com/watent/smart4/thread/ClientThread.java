package com.watent.smart4.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientThread extends Thread {

    private Logger logger = LoggerFactory.getLogger(ClientThread.class);

    private Sequence sequence;

    public ClientThread(Sequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            logger.info(Thread.currentThread().getName() + "=>" + sequence.getNumber());
        }
    }
}
