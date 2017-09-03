package com.pedroza.PolylineTools;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.runtime.collect.Sets;
import com.google.appinventor.components.runtime.util.BoundingBox;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.FileUtil;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.PaintUtil;
import com.google.appinventor.components.common.ComponentConstants;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.util.Log;

/**Hello all, here I present to you my first App Inventor extension ever
* made for decoding and encoding polylines from Google Maps API. First, I found the
* Encoded Polyline Algorithm Format and tried to reverse it using blocks,
* but then I found the <a href="http://jeffreysambells.com/">Jeffrey Sambells' method</a>
* and I felt like learning how to make an extension.
* 
* So this is it! I hope you find this useful and you find how to
* make it better, please let me know so I can learn from it
* @author Carlos Pedroza & Diego Pedroza
*/

@DesignerComponent(
	version = 1,
	description = "Tool developed by Carlos and Diego Pedroza to decode and encode Polylines from Google Maps API based on Jeffrey Sambells method.",
	category = ComponentCategory.EXTENSION,
	nonVisible = true,
	iconName = "images/extension.png")

@SimpleObject(external = true)
public class PolylineTools extends AndroidNonvisibleComponent implements Component {
	private ComponentContainer container;
	private Context context;
	private static final String LOG_TAG = "PolylineTools Extension";
	public static final int VERSION = 1;
	
	public PolylineTools(ComponentContainer container) {
		super(container.$form());
		this.container = container;
		context = (Context) container.$context();
		Log.d(LOG_TAG, "PolylineTools created");
	}

/**Decodes an encoded polyline string reversing Encoded Polyline Algorithm
* @param	encodedPolyline is the string that will be decoded
* @return	polylineDecoded	is a list of paired lat/lng
*/

@SimpleFunction(description = "Decodes polyline and returns a list of paired lat/lng")
public static List Decode (String encodedPolyline) {
	List<ArrayList<Double>> polylineDecoded = new ArrayList<ArrayList<Double>>();
    int index = 0;
	int len = encodedPolyline.length();
	int lat = 0;
	int lng = 0;
    while (index < len) {
		int b, shift = 0, result = 0;
		do {
			b = encodedPolyline.charAt(index++) - 63;
			result |= (b & 0x1f) << shift;
			shift += 5;
		} while (b >= 0x20);
		int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
		lat += dlat;
		shift = 0;
		result = 0;
		do {
			b = encodedPolyline.charAt(index++) - 63;
			result |= (b & 0x1f) << shift;
			shift += 5;
		} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;
	ArrayList<Double> point = new ArrayList<Double>();
    point.add(((double) lat / 1E5));
    point.add(((double) lng / 1E5));
    polylineDecoded.add(point);      
  }
    return(polylineDecoded);
}

/**Encodes a number lat or lng into a string following the Encoding Polyline Algorithm Format
* @param	latORlng is the number that will be decoded
* @return	EncodedPolyline	is a string with the number decoded
*/
@SimpleFunction(description = "Encodes a number and return a string with the number encoded")
public static String Encode (double latORlng) {
	String EncodedPolyline = "";
	int dpoint = ((int)(latORlng*1E5));
    int Result = 0;
	int Shift = 0;
	Result = (dpoint <= 0 ? ~(dpoint << 1) : (dpoint << 1));
	int[] Chars = {0,1,2,3,4,5};
	for (int t: Chars) {
		int variable = ((Result >> Shift) & 0x1f);
		Chars[t] = variable;
		Chars[t] = (t == 5 ? variable : (variable | 0x20)) + 63;
		Shift += 5;
		EncodedPolyline += (char)Chars[t];
    }
    return (EncodedPolyline); 
}
}
