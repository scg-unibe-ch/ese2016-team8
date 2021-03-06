# Scenarios

## 1: Using bookmarked list to get to offer, rent it, contact provider.

### PreState
At least one object, which the user intends to rent, is added to the **bookmarked list**.

Thy user starts from the **main page**.

### Actions
Thy user enters via the **list of the bookmarked** offers the object he wants to rent.

Then he proceeds to **rent it**.

Afterwards he writes an **email to the provider** by **using the contact provider button**.

### Results
User has bought the offer.

Thy user's outbox should show that he has sent an e-mail to the provider.

## 2: Bidding on an object, getting overbid, and then winning.

### PreState
Thy user starts on the page for a **specific object**, which can only be **obtained by bidding**.

Furthermore, thy user is at the moment the highest bidder.

There are 10 minutes remaining for the **object**, then the bid-time has run out.

### Actions
The user waits happily for the bidding to end.

Suddenly, when about five minutes remain, the user get's overbidden by another user.
(>This means that you need to log in as another user, overbid yourself for this single purpose.)

The user then proceeds to overbid the anon again, staying the most-bidding user until the offer runs out.

### Results
The user should have gotten an e-mail for being overbid.

The user should have gotten an e-mail for winning the auction.

## 3: Placing an ad for a room

### PreState
The user is **logged** in and starts on the **main page**

### Actions
The user goes to the place ad form through the _Place an ad_-Button

He then proceeds to place an ad for a room in 8001 - Z&uuml;rich. The requireds field must be filled with creative ideas. Only given fields are **Prize per month** which must be equal to 900, **Smoking inside allowed** and **Garage** which must be ticked and have at least one visiting time, the rest is up to the user.

### Results
The ad must be placed correctly.

The message "Ad placed successfully. You can take a look at it below." must be displayed.
