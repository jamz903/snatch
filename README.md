# snatch

## Team Members' names and student IDs
1. Jamie Toh (S10194918D)
2. Justin Ng (S10195649H)
3. Shane-Rhys Chua (S10194712H)

## Description of snatch

## Individual Roles and Contributions
**- Jamie**\
Jamie created the slideshow to display images of food on the homescreen. She researched and implemented the slideshow to ensure that the homescreen was not too empty.

Upon customer logging in, she also implemented a way to prevent users from going back to the login screen when the back button is pressed on Android. Instead of going back to the login activity, it notifies the user that clicking the back button again would cause the user to exit the application.

She also set up the upvoting system of the food items in Firebase. She changed the hardcoded code and made it reusable for all the food items. She also came up with the initial method to get the number of upvotes of the food items from Firebase. Other than setting up the upvote system within Firebase, she also played a part in trying to debug errors regarding the sorting of food items in the menu by their number of upvotes.

In addition, she also implemented validation regarding the need for internet connection due to our application using Firebase. In the event that the user is not connected to the Internet, it would inform users to connect to Internet before allowing them to login.

Lastly, she also changed the header title to reflect the different activities in our application, e.g. Menu, stalls etc.

**- Justin**\
Justin implemented the dropdown list (known as Spinner) listing the various food courts in the homescreen activity. When a food court is selected, it will display the respective food stalls there.

In addition, in the homescreen activity, he added a ScrollView so that the user is able to scroll when on smaller devices/landscape orientation.

He also implemented a RecyclerView in several activities to display content like the different food stalls, the differnet food items sold by the stall, the different items added to the cart and the different items ordered. In addition, he also displayed images of the food when displaying the various food items.

Justin was also made use of a FloatingActionButton to bring users from the menu to their cart.

He was also involved in the logical aspect of the upvoting system, and helped to debug issues regarding the sorting of menu items by their number of upvotes.

Lastly, he also implemented both the ordering activity and receipt activity to display to users what they have in their cart and the food items they have bought respectively. In the event that the user's cart is empty, it would display an empty card page.

**- Rhys**\
Rhys played a major part in implemeting the Firebase. Whilst Jamie and Justin were doing the initial recycler view at the start of the project, Rhys did numerous research on trying to implement Firebase to our application. This was crucial to our application so that we are able to make use of both the login system and the upvote system.

He also made the login and sign up system and ensured that the users were registered to the Firebase.

Once, when Firebase was not working on both Jamie's and Justin's emulator, Rhys was involved in trying to debug the upvote system and trying to sort the menu items by the number of upvotes.

## Appendix
### Login
![Image of Login screen](/githubImages/login.png)\
This is snatch's login screen. Upon entering a correct student ID and password in Firebase, it will then bring the user to the homescreen.

### Login Successful
![Image of Successful Login](/githubImages/success_login.png)\
Upon successful login, the application will display a successful login message to the user and bring user to the homescreen.

### Invalid Login
![Image of Invalid Login screen](/githubImages/invalid_login.png)\
In the event that the user has entered in a wrong account, the app will display an error to the user as shown below.

### No Internet Connection
![Image of No Internet Connection](/githubImages/no_internet.png)\
In addition, if the user has no internet connection, he will be unable to login and the app will inform the user using an AlertDialog.

### Sign Up
![Image of Sign Up screen](/githubImages/sign_up.png)\
This is the sign up page of snatch. Users are prompted for a username, their student ID as well as their password. Upon succesfully creating an account, it will then redirect users back to the login page to get them to login.

### Sign Up Validation
![Image of Incorrect Student ID format](/githubImages/sign_up_validation.png)\
There is validation done for the Sign Up page where users have to enter in an valid student ID matching the student ID format.\
\
![Image of Existing Student ID](/githubImages/sign_up_existing.png)\
Furthermore, if the user tries to create an account for an existing student ID, the application will prevent the user from doing so and will display an error message as shown above.

### Homescreen
![Image of Home screen](/githubImages/homescreen.png)\
This is the home screen of snatch and it has a carousel/slideshow to display different images of food. It also features a dropdown list that allows user to pick a foodcourt. In addition, it also has a welcome message with the user's username entered when he signs up at the sign up page. It also makes use of a ScrollView to allow users to scroll when on smaller devices/landscape orientation.

### Dropdown List
![Image of Dropdown List](/githubImages/dropdown_list.png)\
This is the dropdown list and it has all of the food courts in Ngee Ann Polytechnic (NP) listed. Upon choosing an option, it would redirect the user to a new activity, displaying all the food stalls in that particular food court.

### Logout
![Image of Logout](/githubImages/logout.png)\
Anytime the user wishes to logout, they just have to click on the 3 dots icon located at the top right of the app, and just click on Logout.

### List of stalls
![Image of List of Stalls](/githubImages/storesList.png)\
After selecting a food court from the dropdown list, the user would be able to see the list of stalls available.

### Prompt to visit Stall
![Image of Prompt to visit Stall](/githubImages/stall_prompt.png)\
When the user clicks on a particular stall, the app will display an AlertDialog asking if the user would like to visit the stall's page. If the user clicks "No", nothing will happen. On the other hand, if the user selects "Yes", the user will be brought to see the selected stall's menu page.

### Menu Page
![Image of Japanese Stall's Menu Page](/githubImages/menu.png)\
Making use of the RecyclerView, the app is able to display all of the food items that the stall offers. Each row shows the name of the food, a short description of the food, the price of the food, an image of the food, and lastly, the number of upvotes the food has. The food is sorted by popularity, determined by the number of upvotes that they have. The more upvotes they have, the higher up in the menu they would be.

### Upvote System
![Image of snatch's Upvote System](/githubImages/upvote.png)\
If the user clicks on the "UPVOTE" button, the system would ask if the user wanted to upvote the food item. (For now) It will also inform the user that if he clicks on upvote, he would be unable to retract back his vote. If the user agrees to upvote the food item, the food item's number of upvotes would increase by 1. Based on its number of upvotes, its position on the menu will also update accordingly in real time due to Firebase.

### Add Food to Cart
![Image of Adding Food to Cart](/githubImages/add_to_cart.png)\
If the user wants to add a food item to his cart, he just has to click on the item that he would like to add. Next, the system would prompt the user, using an AlertDialog, if he would like to add the item to his cart. The user just has to click on "Yes" to add the item to his cart.

### Cart
![Image of Cart](/githubImages/cart.png)\
To see the items in his cart, the user just have to click on the Floating Action Button with the shopping cart icon located at the bottom right corner of the screen. From there, the system will proceed to display all the items the user has added, the quantity, as well as the subtotal and grand total.

### Clear Items from Cart

### Empty Cart
![Image of Empty Cart](/githubImages/emptycart.png)\
If the user has no items added to his cart and decides to view his cart, it would show an empty cart screen as shown above.

