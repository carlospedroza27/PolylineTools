# Polyline Tools
Hello all, here I present to you my first App Inventor extension ever made for decoding and decoding polylines from Google Maps API. First, I found the Encoding Polyline Algorithm Format and tried to reverse it using blocks, but then I found the <a href="http://jeffreysambells.com/">Jeffrey Sambells' method</a> and I felt like learning how to make an extension.

So this is it! I hope you find this useful and you find how to make it better, please let me know so I can learn from it.

This extension was made in colaboration with Diego Pedroza.

Simple funcion: *Decode* with only one parameter. Based on <a href="http://jeffreysambells.com/">Jeffrey Sambells' method</a>.
- *encodedPolyline* which is the string that will be decoded.
And it returns a list of paired lat/lng (*DecodedPolyline*).

Simple funcion: *EncodeLatORLng*  with only one parameter. Based on <a href="http://statsciolist.blogspot.com/2013/05/java-google-maps-polyline-encoding.html">Sciolist's method</a>.
- *latORlng* which is the number that will be encoded following the Encoding Polyline Algorithm Format.
And it returns a string with lat/lng encoded (*EncodedPolyline*). 

Simple funcion: *EncodePoint*  with only one parameter. Based on <a href="http://statsciolist.blogspot.com/2013/05/java-google-maps-polyline-encoding.html">Sciolist's method</a>.
- *point* which is the YailList with the latitude and longitude that will be encoded following the Encoding Polyline Algorithm Format.
And it returns a string encoded (*EncodedPolyline*). 
