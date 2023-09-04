package com.driver.services;


import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.model.WebSeries;
import com.driver.repository.UserRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSeriesRepository webSeriesRepository;


    public Integer addUser(User user){

        //Jut simply add the user to the Db and return the userId returned by the repository
        userRepository.save(user);
        return user.getId();
    }

    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){

        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
        //Hint: Take out all the Webseries from the WebRepository
        User user = userRepository.findById(userId).get();
        Integer age = user.getAge();
        Subscription subscription = user.getSubscription();

        List<WebSeries> webSeriesList = webSeriesRepository.findAll();

        Integer count = 0;

        for(WebSeries webSeries : webSeriesList){
            if(age == webSeries.getAgeLimit()){
                if(subscription.getSubscriptionType().equals(SubscriptionType.ELITE)){
                    count++;
                }else if(subscription.getSubscriptionType().equals(SubscriptionType.PRO)){
                    if(webSeries.getSubscriptionType().equals(SubscriptionType.PRO) || webSeries.getSubscriptionType().equals(SubscriptionType.BASIC)){
                        count++;
                    }
                }else{
                    if(webSeries.getSubscriptionType().equals(SubscriptionType.BASIC)){
                        count++;
                    }
                }
            }
        }
        return count;
    }


}
