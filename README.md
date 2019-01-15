# Baking App

This is an Android Baking App that extracts recipe list from a JSON file and allows a user to select a recipe and see video-guided steps for how to complete it.

The JSON file contains the recipes' instructions, ingredients, videos and images. Not all of the steps of a recipe have a video. Some may have a video, an image, or no visual media at all. So the project demonstrates how handle unexpected input.

### The Project is a basic example of how to:
* Use Exoplayer to display videos.
* Handle error cases in Android.
* Add a companion homescreen widget
* Use Fragments to create a responsive design that works on phones and tablets.

### Libraries
* [Retrofit](https://square.github.io/retrofit/) for REST api communication
* [Picasso](http://square.github.io/picasso/) for image loading
* [Exoplayer](http://google.github.io/ExoPlayer/) to play video files
* [Butterknife](http://jakewharton.github.io/butterknife/) for data binding