package com.cm.rabbitmq.controller;

import com.cm.rabbitmq.provider.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈萌
 * @date 2022/4/2 17:27
 */
@RestController
@RequestMapping(value = "/provider")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @RequestMapping(value = "/sendPriorityMsg/{message}", method = RequestMethod.GET)
    public void sendPriorityMsg(@PathVariable(value = "message") String message) {
        for (int i = 1; i <= 10; i++) {
            providerService.sendPriorityMsg(message + i, i);
        }
    }

}
