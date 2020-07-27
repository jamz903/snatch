# snatch

## Team Members' names and student IDs
1. Jamie Toh (S10194918D)
2. Justin Ng (S10195649H)
3. Shane-Rhys Chua (S10194712H)

# Stage 1
## Description of snatch
snatch is a food ordering app for Ngee Ann Polytechnic (NP) students and staff. It allows users to visit the different food courts available in NP as well as the different food stalls in the respective food courts. Users are able to add and remove food from their cart and are also able to place an order using snatch. In addition, users are also able to upvote different food items. The food in a stall's menu are sorted based on the number of upvotes it has, hence allowing users to know which are the more popular food in NP.

## Individual Roles and Contributions
**- Jamie**\
Jamie created the slideshow to display images of food on the homescreen. She researched and implemented the slideshow to ensure that the homescreen was not too empty.

Upon customer logging in, she also implemented a way to prevent users from going back to the login screen when the back button is pressed on Android. Instead of going back to the login activity, it notifies the user that clicking the back button again would cause the user to exit the application.

She also set up the upvoting system of the food items in Firebase. She changed the hardcoded code and made it reusable for all the food items. She also came up with the initial method to get the number of upvotes of the food items from Firebase. Other than setting up the upvote system within Firebase, she also played a part in trying to debug errors regarding the sorting of food items in the menu by their number of upvotes.

Besides the upvoting system, Jamie also im
plemented the kebab icon and logout feature in the application. She researched on how to implement the logout system and also make it permanent at the header section on all relevant pages.

In addition, she also implemented validation regarding the need for internet connection due to our application using Firebase. In the event that the user is not connected to the Internet, it would inform users to connect to Internet before allowing them to login or sign up, inform users when they want to upvote a dish, and also prevent them from ordering any dishes when they do not have an internet connection.

Lastly, she also changed the header title to reflect the different activities in our application, e.g. Menu, stalls etc.

**- Justin**\
Justin implemented the dropdown list (known as Spinner) listing the various food courts in the homescreen activity. When a food court is selected, it will display the respective food stalls there.

In addition, in the homescreen activity, he added a ScrollView so that the user is able to scroll when on smaller devices/landscape orientation.

He also implemented a RecyclerView in several activities to display content like the different food stalls, the differnet food items sold by the stall, the different items added to the cart and the different items ordered. In addition, he also displayed images of the food when displaying the various food items.

Justin was also made use of a FloatingActionButton to bring users from the menu to their cart.

He was also involved in the logical aspect of the upvoting system, and helped to debug issues regarding the sorting of menu items by their number of upvotes.

Lastly, he also implemented both the ordering activity and receipt activity to display to users what they have in their cart and the food items they have bought respectively. In the event that the user's cart is empty, it would display an empty cart page.

**- Rhys**\
Rhys played a major part in implemeting the Firebase. Whilst Jamie and Justin were doing the initial recycler view at the start of the project, Rhys did numerous research on trying to implement Firebase to our application. He spent time setting up and ensuring that there was a connection between the Realtime Database in Firebase to our application. This was crucial to our application so that we are able to make use of both the login system and the upvote system.

He also made the login and sign up system. This was done by retriving and storing the data obtained from Firebase into lists, to ensure that the users were registered before they could log in and access other activities. For Sign Up, Rhys ensured that duplicate Student IDs could not be registered completed validation for both the Log In System and the Sign Up System.

Rhys was also involved in debugging Firebase issues. When Firebase was not working on both Jamie's and Justin's emulator, Rhys was involved in trying to solve why Firebase was having connectivity issues on our side, and also helped to debug the upvote system and trying to sort the menu items by the number of upvotes.

## Appendix
### Video Demo of snatch
[Watch a video demo on how to use snatch!](https://drive.google.com/file/d/105g6VSNkDnVi_JQgz6x8lSOuuCmcnaFU/view?usp=sharing "snatch's Demo")

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

### View Password
![Image of View Password](/githubImages/view_password.png)\
Users can also click on the eye icon at the password field to see the characters that they have entered. This is implemeted both at the login activity as well as the sign up activity.

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

### Upvote System Validation
![Image of No Internet Connection Warning when Upvoting](/githubImages/upvote_no_internet.png)\
If the user has no Internet when trying to upvote a food item, the system would alert him that he currently has no internet connection and therefore, is unable to upvote the food item now. In addition, the number of upvotes of a food item may also not be accurate.

### Add Food to Cart
![Image of Adding Food to Cart](/githubImages/add_to_cart.png)\
If the user wants to add a food item to his cart, he just has to click on the item that he would like to add. Next, the system would prompt the user, using an AlertDialog, if he would like to add the item to his cart. The user just has to click on "Yes" to add the item to his cart.

### Cart
![Image of Cart](/githubImages/cart.png)\
To see the items in his cart, the user just have to click on the Floating Action Button with the shopping cart icon located at the bottom right corner of the screen. From there, the system will proceed to display all the items the user has added, the quantity, as well as the subtotal and grand total. To place the order, the user just has to click on the "PLACE ORDER" button.

### Place Order Validation
![Image of No Internet Connection when Placing Order](/githubImages/place_order_no_internet.png)\
If a user decides to place an order when he has no Internet connection, the system would alert the user that he currently has no internet connection and is unable to place an order.

### Clear Items from Cart
![Image of System Prompting to Clear Cart](/githubImages/leaving_clear_cart.png)\
In the event that the user wishes to exit the current food stall to visit another food stall, the system would warn the user that leaving the current food stall would clear the current items in the cart (if any). Should the user agree, the items in the cart would be removed.

### Empty Cart
![Image of Empty Cart](/githubImages/emptycart.png)\
If the user has no items added to his cart and decides to view his cart, it would show an empty cart screen as shown above.

### Remove Items from Cart
![Image of System Prompting to Remove Item from Cart](/githubImages/remove_item_prompt.png)\
If the user wishes to remove an item, the user has to click on the item that he wishes to remove. The system would then prompt the user if he wishes to remove the item. If the user agree, 1 quantity of the food item will be removed\
\
![Image of Cart after Removal of Item](/githubImages/after_remove.png)\
Upon successful removal of the selected item, the cart will be updated with the correct subtotal, grand total and quantity.

### Mass Remove Selected Item from Cart
![Image of System Prompting to Clear all of a Selected Item from Cart](/githubImages/remove_all_item.png)\
If a user wishes to remove all of an item, he is able to long press the item from the cart. Upon doing so, the system will ask if the user wants to clear all of it from the cart (e.g. clear all Japanese Chicken Katsu from cart). If the user agrees, all of that particular item will be removed from the cart.
\
![Image of Cart after Mass Removal of Item](/githubImages/after_mass_remove.png)\
Similarly, upon successful removal, the cart will update and display the correct grand total of the remaining items.

### Receipt
![Image of Receipt](/githubImages/receipt.png)\
After the user has successfully placed an order, the user would be able to see the receipt. On it, their order number is displayed as well as the current date and time (SGT) when the order was placed. In addition, it would display the final amount and the list of items purchased. When the user clicks on the "OK" button, it will bring the user back to the homescreen and clear all items that was previously in the cart.

### Presentation Slides
[Slides used for Presentation](https://docs.google.com/presentation/d/1EyHYaZNn5Ivg7DkswJTTFgeQ20STXJnDcjCZc1PpXpU/edit?usp=sharing)

## Reference Links for Images
1. Hor Fun - https://www.google.com/search?q=hor+fun&tbm=isch&ved=2ahUKEwj1wOyAgMnpAhXW2XMBHdcPDKEQ2-cCegQIABAA&oq=hor+fun&gs_lcp=CgNpbWcQAzICCAAyBAgAEEMyAggAMgIIADICCAAyAggAMgIIADICCAAyAggAMgIIADoCCCk6BQgAEIMBUInbAVi-5gFgpOgBaAJwAHgAgAFPiAG-A5IBATmYAQCgAQGqAQtnd3Mtd2l6LWltZw&sclient=img&ei=rZLIXrW2C9azz7sP15-wiAo&bih=888&biw=1920&rlz=1C1CHBF_enSG839SG839&hl=en#imgrc=ArBs_BnPcoMEAM
2. Gong Bao Chicken - https://www.seriouseats.com/recipes/2017/08/gong-bao-ji-ding-sichuan-kung-pow-chicken-recipe.html
3. Salted Egg Rice - http://www.pinkypiggu.com/2019/01/best-salted-egg-rice-in-singapore.html
4. Yogurt - https://www.pinterest.com/pin/645633296549934334/?autologin=true&nic_v1=1aLhrYQUbBQMZJiifMk5mVrEawFcCDwxehl%2BFxcba1geJmy1x4l6%2BLeHfFOMDduYcg
5. Iced Lemon Tea - https://www.unileverfoodsolutions.com.au/recipe/american-lemon-iced-tea-R0069771.html
6. Hot Coffee - https://www.bustle.com/p/how-to-keep-your-coffee-hot-even-if-its-the-dead-of-winter-outside-43770
7. Hot Milo - https://www.bustle.com/p/how-to-keep-your-coffee-hot-even-if-its-the-dead-of-winter-outside-43770
8. Iced Milo - https://www.mcdonalds.com.my/menu/iced-milo
9. Papadom - https://says.com/my/news/studies-show-that-papadom-contains-dangerously-high-levels-of-sodium
10. Mee Soto - http://netsenger.com/topwok/best-mee-soto.htm
11. Ayam Penyet - https://www.feastbump.com/menus/sedap-ayam-penyet
12. Bak Kut Teh - https://www.rotinrice.com/bak-kut-teh-pork-ribs-tea/
13. Ban Mian - https://asianfoodnetwork.com/en/recipes/cuisine/singaporean/ban-mian.html
14. Chawanmushi - https://www.justonecookbook.com/instant-pot-chawanmushi/
15. Cheese Fries - https://en.wikipedia.org/wiki/Cheese_fries
16. Chicken Chop - https://keeprecipes.com/recipe/howtocook/black-pepper-chicken-chop
17. Chicken Katsu Curry - https://www.japancentre.com/en/recipes/301-japanese-chicken-katsu-curry
18. Lemon Chicken Rice -  https://www.burpple.com/f/qKFeo-HR
19. Roast Chicken Rice - https://www.pinterest.com/pin/433964114075544548/?nic_v1=1axsN99lx1QcFU%2Bm0J494%2FWY3%2BsmgEXu3fOfczUp61lxpOqbjEulkYLDkPoaIdAYz0
20. Steam Chicken Rice - https://www.thespruceeats.com/hainanese-chicken-rice-very-detailed-recipe-3030408
21. Fish Soup - https://eatwhattonight.com/2019/10/how-to-make-dried-sole-fish-powder-for-fish-soup/
22. Instant noodles - https://en.wikipedia.org/wiki/Instant_noodle
23. Bowl of Rice - https://www.istockphoto.com/sg/photos/bowl-of-rice?mediatype=photography&phrase=bowl%20of%20rice&sort=mostpopular
24. Salmon Don - https://www.freepik.com/free-photos-vectors/food
25. Sausage - https://en.wikipedia.org/wiki/Vegetarian_hot_dog
26. Sliced Fish Noodles - https://www.burpple.com/f/goSJyz7k
27. Taiwan Sausage - http://xtremetaiwan.com/en/food/show.aspx?num=935
28. Vinegar Braised Pork - https://breadetbutter.wordpress.com/2012/10/04/billy-laws-vinegar-braised-pork-belly-eggs/
29. You Tiao - https://thewoksoflife.com/youtiao-recipe/
30. Basil Pork Rice - https://snapguide.com/guides/make-thai-basil-pork-rice/
31. Pad Thai - https://www.recipetineats.com/chicken-pad-thai/
32. Mango Salad - https://food52.com/recipes/53632-thai-green-mango-salad-som-tum-mamuang
33. Fried Egg - https://www.foodnetwork.com/how-to/articles/how-to-fry-eggs-a-step-by-step-guide
34. Sweet Sour Pork - https://www.cookipedia.co.uk/recipes_wiki/Sweet_and_sour_pork
35. Bee Hoon - http://nasilemaklover.blogspot.com/2014/07/economic-fried-bee-hoon-fried-rice.html
36. Hotdog Bun - http://www.seasaltwithfood.com/2014/11/chinese-hot-dog-bunsrolls.html
37. Cream Puff - https://www.foodista.com/blog/2011/06/04/gluten-free-foodie-friday-key-lime-cream-puffs
38. Floss Bun - https://www.flickr.com/photos/acroamatic/2200784817/in/photostream/
39. Chicken Fuyong - https://www.straitstimes.com/lifestyle/food/cheap-good-chicken-fuyong-omelette-beloved-by-ngee-ann-poly-students
40. Bandung - https://www.burpple.com/f/Os5NQ37K
41. Chicken - https://www.thedailymeal.com/eat/internet-still-debating-chicken-sashimi-so-we-found-answer/090717
42. Tick - "Icon made by Pixel perfect from www.flaticon.com"
43. Cute Food Icons - https://dlpng.com/png/6774620
44. Empty cart - https://www.clipartmax.com/middle/m2i8G6A0i8m2N4A0_sign-in-empty-shopping-cart-icon/
45. Shopping cart icon - https://fontawesome.com/license
