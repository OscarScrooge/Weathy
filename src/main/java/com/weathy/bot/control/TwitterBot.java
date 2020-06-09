package com.weathy.bot.control;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterBot {

    private String tweet="It is empty at this moment";
    private static TwitterBot twitterBot=null;

    private TwitterBot(){
    }

    public static TwitterBot newBot(){

        if(twitterBot==null){
            twitterBot = new TwitterBot();
        }
        return twitterBot;
    }


    public void setTweetText(String tweet) {
        this.tweet = tweet;
    }

    public String getTweetText() {
        return tweet;
    }

    public void newTweet(String tweetText){
         tweet =tweetText;

        Twitter twitter = TwitterFactory.getSingleton();
        try {
            Status status = twitter.updateStatus(tweet);
            System.out.println("Succes: "+status.getText());
        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }



}
