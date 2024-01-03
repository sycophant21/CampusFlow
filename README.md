# CampusFlow (A Request Creation/Management App)
### This is a project for raising a request to the college authorities.
Requests like organinsing events, booking halls for talks, reimbursement etc can be raised.
## Problem Statement
Everytime there was an event was to be organised in college or someone had to book lecture hall for talks, seminars etc, students of the council had to run from one corner of the college to the other for signatures of the authorities. And at times, couldn't find the authorities in their designated office. Also, if there was some minute mistake they had to get back to the head of the council to rewrite the application.
This whole manual process was a tedious task. And sometimes that paper applications would get lost in the records resulting in last minute hassles.

## Solution
This best way to ease out this whole process was to *digitise* this and create such an interface that could be accessed from anywhere without the hassle of running from one office to the other. The way I went ahead with was to create an android app with 3 different interfaces
1. For students
2. For authorities
3. For admin (Director)

### Implementation
#### For Students
* The Students can log in with their college email id.
* Choose from a variety of pre-established categories.
* Enter the start and end date.
* Enter some optional information, like "required 10 tables" etc.
* Request gets to the authenticator at level 1 of the category of the request.
* Student can check the status of the request, i.e when was it last updated, who is delaying the request, etc.

#### For Authorities
* Different authorities can authorize requests from different categories and at different levels of authorization.
  - For instance, the same person can be the only authority for category C1 and for another category C2 another authorizer's signature might be required before his'. So Each authorizer has some categories assigned to them with a level assigned to each category.
* Authorities have the choice of either accepting or rejecting a request and provide some additional(optional) feedback.
* The authority can not see the request once they accept or reject it.
* Whenever the state of the request is changed, i.e. it gets accepted or rejected by some authority, the status of the request gets updated.

#### For ADMIN
* Admin can authorise any request.
* They have the rights to see history of every request that was ever created.


### Under development
* Admin functionalities
* UI
* Sub-categories
* Currently only accepts date and not time for request.
