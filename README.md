# Trends
An app that lists all the trending repositories based on three categories - Monthly, Weekly and Daily.
The login uses nodejs server for authentication.
The Repositories are listed using recycler view and the items are customised card views.
Navigation view and tab layout are used in navigating the fragments using Viewpager.
The trending developers are given different design using card views and can be viewed through menu.
The add filter opens a bottom sheet view with a spinner where the required language is selected and the apply button is clicked.
The bookmark can be clicked to add or remove a bookmark.On adding a bookmark, data is stored in the local SQLite database and the 
firebase server through the server.If a logged in user opens the app the bookmarks are retrieved from the local database.
When a user logs in the bookmarks if any are stored in the local database. The app directly goes to the list page if a user is logged 
using the contents in the local database. The APIs used are Retrofit, Intuit and Glide.


