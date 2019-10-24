# Car Pooling Application
A carpooling Android application.

Following functionalities are developed: 
• Login Functionality: New user can register to the application. User needs to enter his first name, Last name , email address , mobile number and password. After validation of the entered data, user will get redirected to decision page. Existing users can login by entering their username and password. Validation of the user will be done. If password is entered incorrectly, user will see the message saying incorrect password and he needs to enter it again. 
• Decision Functionality: Every time user logs in, user can decide if he needs a ride or he needs to offer a ride. (Driver or passenger)
• Maps and direction functionality: User can see his location on Google map. User can enter his origin and destination. 
• Connectivity with backend: Database used for the project is Firebase. The system table ‘User’ is used for handling users login data. Geolocation functionality is used to store driver’s and passenger’s origin and destination longitude and latitude. Fetching records in 2 Miles radius. Geolocation radius feature is used to fetch the records of users within 2 Miles of radius.
• Showing records in a list Depending on the entered origin and entered destination, the user will get only those list of available drivers or passengers whose origin lies within a radius of 5 miles from the entered origin and destination lies within 5 miles of the entered destination. 
• User can view detailed information: The user can view detailed information about the available list of drivers/passengers. 

Guidelines for user to use the application step by step: 
Step 1: First time user needs to sign up to the application. User will see a profile creation page where he needs to enter all his personal data. Email address will be his user name and he can set his password at this time. 
Step 2: After successful login, user will see a page where he can choose if he is a driver or passenger for that given time.
Step 3: User will see the Google Map page. He will see his current location on Google map. He can enter his origin and destination. Application will help user to auto complete the origin and destination fields. Click on confirm button.
Step 4: When user click on search button, user will see a list. If user is a driver then he will see a list of all passengers who has origin and destination within the radius of 2 Miles as that of user. User will see all the available passengers, their name, phone number and origin location.

URL of video clip of the running application: Video is created which gives idea about application works : https://drive.google.com/file/d/1KT3trdGqoE39rRD_jhqapXKdrWSTK68N/view

Third party services used in project:

Google Login

Google Map API

Map Direction API

Firebase for Database

Future Scope : User can set a time in advance for future rides when he needs to do car-pooling. Payment options through goggle wallet or paypal. Round trip car-pooling Car-pooling selection preference with respect to gender, age etc.
