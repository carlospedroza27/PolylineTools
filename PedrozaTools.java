package com.pedroza.PedrozaTools;

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
import android.content.Context;
import android.util.Log;

/**Hello all, here I present my second extension ever made!
*/

@DesignerComponent(
	version = 1,
	description = "Tool developed by Carlos Pedroza",
	category = ComponentCategory.EXTENSION,
	nonVisible = true,
	iconName = "images/extension.png")

@SimpleObject(external = true)
public class PedrozaTools extends AndroidNonvisibleComponent implements Component {
	private ComponentContainer container;
	private Context context;
	private static final String LOG_TAG = "Pedroza Extension";
	public static final int VERSION = 1;
	
	public PedrozaTools(ComponentContainer container) {
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
public static String HOLA (YailList LatLng) {
	String EncodedPolyline = "Hola";
	return(EncodedPolyline);
	}
}
