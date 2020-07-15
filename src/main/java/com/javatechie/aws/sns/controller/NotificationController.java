package com.javatechie.aws.sns.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.SubscribeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
public class NotificationController {

    public static final String TOPIC_ARN = "";
    public static final String PHONE_NUMBER = "";
    @Autowired
    private AmazonSNSClient snsClient;


    @GetMapping("/addSubscribers")
    private String addSubscriberToTopic()  {
        SubscribeRequest subscribeRequest = new SubscribeRequest(TOPIC_ARN, "email", "javatechie4u@gmail.com");
        snsClient.subscribe(subscribeRequest);
        snsClient.shutdown();
        return "Subscription ARN request is pending. To confirm the subscription, check your email.";
    }

    /**
     * SEND NOTIFICATION OVER THE EMAIL USING AWS SNS
     **/
    @RequestMapping("/sendEmail")
    private String sendEmail() {
        final String msg = "Thank you email from javatechie !";
        PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, "Thanks for your subscription to java techie , please keep in touch and do not miss any update from javatechie !", msg);
        PublishResult publishResponse = snsClient.publish(publishRequest);
        snsClient.shutdown();
        return "Email sent to subscribers. Message-ID: " + publishResponse.getMessageId();
    }

    /**
     * SEND NOTIFICATION OVER THE SMS(PHONE_NUMBER) USING AWS SNS
     **/
    @GetMapping("/sendSMS")
    private String sendSMS() throws URISyntaxException {
        final PublishRequest publishRequest = new PublishRequest().withPhoneNumber(PHONE_NUMBER).withMessage("Javatechie greeting message to all viewers");
        PublishResult publishResult = snsClient.publish(publishRequest);
        //snsClient.shutdown();
        return "SMS sent to " + PHONE_NUMBER + ". Message-ID: " + publishResult.getMessageId();
    }
}
