#   CEG 4110 Fall 2018 Homework 1
##   Seth Keadle

This project is developed for the android os and has a minSDK of 20 and a target sdk of 26. This allows users from Kit-Kat and newer to run and compile this application. The application was developed in the Android Studio SDK.

##   Part 1
The first page is a simple constraint layout composed of a textbox, label, and two buttons. The text box is the standard android user input and display object. The first button, change color, when pressed generates a random color in the RGB scale. Takes the random color and sets the font color of all the text in the textbox to that color. Then the label shows the current color of the font in the textbox. The second button on this screen allows the user to go to the second activity that is for the second part of this assignment.

##   Part 2
The second page is a user draw app, similar to simple paint application. The user can take their finger to draw on the screen and has the ability to pick in color in RGB spectrum, clear the entire canvas, and save the image as a .PNG file on their device.

### Libraries and instructions
####    DrawView Library by ByoxCode
Add the following line to the application gradle

 implementation 'com.byox.drawview:drawview:X.X.X'

 Add the following xml script to the page where you want to have a drawing canvas.

 <com.byox.drawview.views.DrawView
        android:id="@+id/draw_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:dv_draw_alpha="255"
        app:dv_draw_anti_alias="true"
        app:dv_draw_color="@color/colorAccent"
        app:dv_draw_corners="round"
        app:dv_draw_dither="true"
        app:dv_draw_enable_zoom="true"
        app:dv_draw_font_family="default_font"
        app:dv_draw_font_size="12"
        app:dv_draw_max_zoom_factor="8"
        app:dv_draw_mode="draw"
        app:dv_draw_style="stroke"
        app:dv_draw_tool="pen"
        app:dv_draw_width="4"
        app:dv_draw_zoomregion_maxscale="5"
        app:dv_draw_zoomregion_minscale="2"
        app:dv_draw_zoomregion_scale="4" />

        The DrawView wiki page provides all functions available inside the library.
#### ColorPicker by jaredrummler
Add the following line to App gradle

implementation 'com.jaredrummler:colorpicker:1.0.2'
