# Note 
In order to test the code, please add your personal API KEY "The Movie Database API 'https://www.themoviedb.org' " into the "gradle.properties" in project files > Add line > API_KEY="Your API Digits"
________________________________________________________________________________________________________________________________

### This project is implemented on two stages please read throught to understand what was involved in Stage 1 and Stage 2. Additionally, I have added more features than required
________________________________________________________________________________________________________________________________

# Popular Movies, Stage 2

## Project Overview

Welcome back to Popular Movies! In this second and final stage, you’ll add additional functionality to the app you built in Stage 1.

* You’ll add more information to your movie details view:

 * You’ll allow users to view and play trailers ( either in the youtube app or a web browser).
 * You’ll allow users to read reviews of a selected movie.
 * You’ll also allow users to mark a movie as a favorite in the details view by tapping a button(star).
 * You'll create a database and content provider to store the names and ids of the user's favorite movies (and optionally, the rest of the information needed to display their favorites collection while offline).
 * You’ll modify the existing sorting criteria for the main view to include an additional pivot to show their favorites collection.
 * Recall from Stage 1, you built a UI that presented the user with a grid of movie posters, allowed users to change sort order, and presented a screen with additional information on the movie selected by the user.

## What Will I Learn After Stage 2?

* You will build a fully featured application that looks and feels natural on the latest Android operating system (Nougat, as of November 2016).

________________________________________________________________________________________________________________________________
# My Project Completion

## Screenshots
![alt text](https://i.imgur.com/NWptJUY.jpg?1)![alt text](https://i.imgur.com/Qv8oIpN.jpg?1) ![alt text](https://i.imgur.com/VS0zZq2.jpg?1)![alt text](https://i.imgur.com/r9iJOMw.jpg?1)![alt text](https://i.imgur.com/MRWKHrz.jpg?1)![alt text](https://i.imgur.com/G0jiq1Q.jpg?1)

## Libraries & Implementations & Additional Content
* [ButterKnife](https://github.com/JakeWharton/butterknife) 

* [Picasso](https://github.com/square/picasso) 

* [PagerSlidingTabStrip](https://github.com/yuvraaz/slidingpager) 

* [ReadMoreTextView](https://github.com/bravoborja/ReadMoreTextView)

* [FancyButtons](https://github.com/medyo/Fancybuttons) 
________________________________________________________________________________________________________________________________

## Why this Project

To become an Android developer, you must know how to bring particular mobile experiences to life. Specifically, you need to know how to build clean and compelling user interfaces (UIs), fetch data from network services, and optimize the experience for various mobile devices. You will hone these fundamental skills in this project.

By building this app, you will demonstrate your understanding of the foundational elements of programming for Android. Your app will communicate with the Internet and provide a responsive and delightful user experience.
________________________________________________________________________________________________________________________________

# Popular Movies, Stage 1

## Project Overview

Most of us can relate to kicking back on the couch and enjoying a movieDetails with friends and family. In this project, you’ll build an app to allow users to discover the most popular movies playing. We will split the development of this app in two stages. First, let's talk about stage 1.

In this stage you’ll build the core experience of your movies app.

You app will:

* Present the user with a grid arrangement of movieDetails posters upon launch.
* Allow your user to change sort order via a setting:
  * The sort order can be by most popular or by highest-rated
* Allow the user to tap on a movieDetails poster and transition to a details screen with additional information such as:
  * original title
  * movieDetails poster image thumbnail
  * A plot synopsis (called overview in the api)
  * user rating (called vote_average in the api)
  * release date

## What Will I Learn After Stage 1?

* You will fetch data from the Internet with theMovieDB API.
* You will use adapters and custom list layouts to populate list views.
* You will incorporate libraries to simplify the amount of code you need to write
