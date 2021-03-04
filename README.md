# SWE264 Dating App Development
## Introduction

Nowadays, dating app has been increasingly popular among young people because of their habits of relying on social networks. The tendency to communicate with each other through social networks even makes it uncomfortable for the young to get along with others face to face. Therefore, it is necessary to develop a dating app that helps young people meet new people and find suitable companions in future lives. 

## How we meet the requirement

### 8 software components

- User login
- User sign out
- Find people around you 
- Chat with people 
- Update profile
- Check profile around you 
- Modify portrait
- Database encryption 

### 3 component types provided by Android

- Activity
- recycleView
- Fragment
- Navigation

### Integrate with an external system

- AMap SDK
- AWS
- NetEase YunXin 

### Amazon AWS Relational Database Service(RDS)

Store User info in RDS and Connect the Database Using JDBC

## Technology

We plan to develop an Android app by implementing the AWS Elastic Server and using MySQL as back-end server to store the information of users and other important data. In addition, Instant Message will be implemented based on the NetEaseYunXin so that the stability of this function can be guaranteed. Besides, we realize the function of locating users by applying the Bing Map SDK and use Python to match users with similar interests. We will use Android Studio and JetBrain Idea to develop and test our app to realize all the functions we design. 

Here is the summary:

IDE: Android studio

Server: AWS Elastic Server 

Database: MySQL

Backend: Java

Frontend: Android

IM: NetEaseYunXin SDK

Location-based function: Bing Map SDK

Match function: Python 

# Some hints!

- Apache HTTP Server cannot be used in Andriod Studio




# TO-DO list
- Merge user_db and ConnectDatabase (done)
- Test same-name methods (done)
- Delete same-name sub-folder (done)
- Optimize sql speed（profile page， login page）(done)
- Optimize avatar (done)
- Optimize the page of profile (done)
- Optimize the speed of server (done)
- map's thread handler (SDK's problem)
- Logout button

# Reference

Sun Yu (https://github.com/duke326)

Lai Wang (https://github.com/LaiWang2020)

Xinyi Hu (https://github.com/samaritanhu)

Chupeng Zhang (https://github.com/chupengrocky/)