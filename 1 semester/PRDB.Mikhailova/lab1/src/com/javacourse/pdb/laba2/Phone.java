package com.javacourse.pdb.laba2;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by bkalika on 28.09.2020.
 */

public abstract class Phone {

    public String phoneNumber;
    public String operator;
    public int status;
    public double balance;
    private String incomingCall;
    private String lastMessage;
    private ArrayList messages = new ArrayList();
    LinkedList<String> outcomingMessage = new LinkedList<String>();

    public void call (Phone phoneNumber)
    {
        System.out.println("Successful call from: " + this.getPhoneNumber() + " to " + phoneNumber.getPhoneNumber());
    }

    public void replenishAccount (double money)
    {
        this.balance = this.balance + money;
    }

    public String sendMessage (Phone target, String text)
    {
        System.out.println(this.getPhoneNumber()+" send message to " + target.getPhoneNumber()+ " with text: " + text);
        target.setLastMessage(text);
        return "successful";
    }

    public String sendMessage (Phone target, Phone target2, String text)
    {
        System.out.println(this.getPhoneNumber()+" send message to " + target.getPhoneNumber()+ "with text: " +text);
        target.setLastMessage(text);
        target2.setLastMessage(text);
        return "successful";
    }

    public Phone(String phoneNumber,
                 String operator,
                 int status,
                 double balance,
                 String incomingCall,
                 String lastMessage,
                 ArrayList messages
    ) {
        this.phoneNumber = phoneNumber;
        this.operator = operator;
        this.status = status;
        this.balance = balance;
        this.incomingCall = incomingCall;
        this.lastMessage = lastMessage;
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", operator='" + operator + '\'' +
                ", status=" + status +
                ", balance=" + balance +
                ", incomingCall='" + incomingCall + '\'' +
                ", lastMessage='" + lastMessage + '\'' +
                ", messages=" + messages +
                ", outcomingMessage=" + outcomingMessage +
                '}';
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

//    public String getOperator() {
//        return operator;
//    }
//
//    public String getIncomingCall() {
//        return incomingCall;
//    }
//
//    public void setIncomingCall(String incomingCall) {
//        this.incomingCall = incomingCall;
//    }

    public Phone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Phone() {
        this.phoneNumber = null;
        this.operator = null;
    }

    public Phone(String phoneNumber, String operator) {
        this.phoneNumber = phoneNumber;
        this.operator = operator;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
        setMessages(lastMessage);
    }

    public ArrayList getMessages() {
        for (int i = 0 ;i<this.messages.size();i++)
        {
            System.out.println(messages.get(i));
        }
        return messages;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setMessages(String lastMessage) {
        this.messages.add(lastMessage);
    }
}
