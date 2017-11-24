package com.pedroza.PolylineTools;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.runtime.collect.Sets;
import com.google.appinventor.components.runtime.util.*;
import com.google.appinventor.components.common.ComponentConstants;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.components.runtime.util.YailList;
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

/**Decodes an encoded polyline string reversing Encoded Polyline Algorithm.
* Based on <a href="http://jeffreysambells.com/">Jeffrey Sambells' method</a>
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
		lat += ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
		shift = 0;
		result = 0;
		do {
			b = encodedPolyline.charAt(index++) - 63;
			result |= (b & 0x1f) << shift;
			shift += 5;
		} while (b >= 0x20);
			lng += ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	ArrayList<Double> point = new ArrayList<Double>();
    point.add(((double) lat / 1E5));
    point.add(((double) lng / 1E5));
    polylineDecoded.add(point);      
  }
    return(polylineDecoded);
}

/**Encodes a list of paired lat/lng using the the Encoded Polyline Algorithm.
* Based on <a href="http://statsciolist.blogspot.com/2013/05/java-google-maps-polyline-encoding.html">Sciolist's method</a>
* @param	encodedPolyline is the string that will be decoded
* @return	polylineDecoded	is a list of paired lat/lng
*/
@SimpleFunction(description = "Encodes a list of paired lat/lng and return a string with the point encoded")
public String Encode (YailList decodedPolyline) {
	String json = decodedPolyline.toJSONString();
	try {
		ArrayList<ArrayList<Double>> objects = (ArrayList<ArrayList<Double>>)decodeJsonText(json);
		return EncodeEncode(objects);
	} catch (IllegalArgumentException e) {
		return "";
	}
}

private String EncodeEncode (ArrayList<ArrayList<Double>> points) {
	String EncodedPolyline = "";
	double[] previousPoint = {0,0};
	for (ArrayList<Double> point : points){
		for (int k = 0; k < 2; k++) {
			double encodingP = point.get(k) - previousPoint[k];
			previousPoint[k] = point.get(k);
			int b = (int) Math.round(encodingP*1E5);
			ArrayList<Integer> ab = new ArrayList<Integer>();
			if (b==0){
				EncodedPolyline += "?";
			} else {
				b = (b>0 ? (b<<1) : ~(b<<1));
				while(b>0){
					ab.add(b % 32);
					b=b>>5;
				}
				StringBuilder latlng = new StringBuilder();
				for(int i=0;i<ab.size()-1;i++){
					char c = (char) ((ab.get(i) | 0x20)+63);
					latlng.append(c);
				}
				latlng.append((char) (ab.get(ab.size()-1)+63));
				EncodedPolyline += latlng.toString();
			}
		}
	}
	return (EncodedPolyline);
}

private Object decodeJsonText(String jsonText) throws IllegalArgumentException {
	try {
		return JsonUtil.getObjectFromJson(jsonText);
	} catch (JSONException e) {
		throw new IllegalArgumentException("jsonText is not a legal JSON value");
	}
}
	
	
/**Encodes a number lat or lng into a string following the Encoding Polyline Algorithm Format (Deprecated)
* Based on <a href="http://statsciolist.blogspot.com/2013/05/java-google-maps-polyline-encoding.html">Sciolist's method</a>
* @param	latORlng is the number that will be decoded
* @return	EncodedPolyline	is a string with the number decoded
*/
@SimpleFunction(description = "Encodes a number and return a string with the number encoded. DEPRECATED: Use Encode method instead.")
public static String EncodeLatORLng (double latORlng) {
	String EncodedPolyline = "";
	int b = (int) Math.round(latORlng*1E5);
	ArrayList<Integer> ab = new ArrayList<Integer>();
	if (b==0){
			EncodedPolyline += "?";
		} else {
	b = (b>0 ? (b<<1) : ~(b<<1));
	while(b>0){
		ab.add(b % 32);
		b=b>>5;
		}
		StringBuilder latlng = new StringBuilder();
		for(int i=0;i<ab.size()-1;i++){
			char c = (char) ((ab.get(i) | 0x20)+63);
			latlng.append(c);
		}
		latlng.append((char) (ab.get(ab.size()-1)+63));
		EncodedPolyline += latlng.toString();
		}
		return (EncodedPolyline);
}

/**Encodes a point (lat or lng ) into a string following the Encoding Polyline Algorithm Format
* Based on <a href="http://statsciolist.blogspot.com/2013/05/java-google-maps-polyline-encoding.html">Sciolist's method</a>
* @param	point is a YailList with the latitude and the longitude that will be decoded
* @return	EncodedPolyline	is a string with the point decoded
*/
@SimpleFunction(description = "Encodes a paired lat/lng and return a string with the point encoded. DEPRECATED: Use Encode method instead.")
public static String EncodePoint (YailList point) {
	String[] pairedLatLng = point.toStringArray();
	String EncodedPolyline = "";
	for (String latORlng: pairedLatLng) {
		int b = (int) Math.round(Double.parseDouble(latORlng)*1E5);
		ArrayList<Integer> ab = new ArrayList<Integer>();
		if (b==0){
			EncodedPolyline += "?";
		} else {
		b = (b>0 ? (b<<1) : ~(b<<1));
		while(b>0){
			ab.add(b % 32);
			b=b>>5;
		}
		StringBuilder latlng = new StringBuilder();
		for(int i=0;i<ab.size()-1;i++){
			char c = (char) ((ab.get(i) | 0x20)+63);
			latlng.append(c);
		}
		latlng.append((char) (ab.get(ab.size()-1)+63));
		EncodedPolyline += latlng.toString();
		}
	}
	return (EncodedPolyline);
}
}
