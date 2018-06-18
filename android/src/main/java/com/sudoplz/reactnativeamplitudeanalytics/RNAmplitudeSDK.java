package com.sudoplz.reactnativeamplitudeanalytics;

import com.amplitude.api.Amplitude;
import com.amplitude.api.Identify;

import android.app.Activity;
import android.app.Application;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class RNAmplitudeSDK extends ReactContextBaseJavaModule {

  private Activity mActivity = null;
  private Application mApplication = null;

  public RNAmplitudeSDK(ReactApplicationContext reactContext, Application mApplication) {
    super(reactContext);
    this.mActivity = getCurrentActivity();
    this.mApplication = mApplication;
  }

  @Override
  public String getName() {
    return "RNAmplitudeSDK";
  }

  @ReactMethod
  public void initialize(String apiKey, Boolean trackSessionEvents) {
    Amplitude.getInstance().initialize(getReactApplicationContext(), apiKey).enableForegroundTracking(this.mApplication);
    Amplitude.getInstance().trackSessionEvents(trackSessionEvents);
  }

  @ReactMethod
  public void setUserId(String id) {
    Amplitude.getInstance().setUserId(id);
  }

  @ReactMethod
  public void setUserProperties(ReadableMap properties) {
    try {
      JSONObject jProperties = convertReadableToJsonObject(properties);
      Amplitude.getInstance().setUserProperties(jProperties);
    } catch (JSONException e) {
      return;
    }
  }

  @ReactMethod
  public void setOptOut(Boolean optOut) {
    Amplitude.getInstance().setOptOut(optOut);
  }

  @ReactMethod
  public void clearUserProperties() {
    Amplitude.getInstance().clearUserProperties();
  }

  @ReactMethod
  public void regenerateDeviceId() {
    Amplitude.getInstance().regenerateDeviceId();
  }

  @ReactMethod
  public void logEvent(String identifier) {
    Amplitude.getInstance().logEvent(identifier);
  }

  @ReactMethod
  public void logEventWithProps(String identifier, ReadableMap properties) {

    try {
      JSONObject jProperties = convertReadableToJsonObject(properties);
      Amplitude.getInstance().logEvent(identifier, jProperties);
    } catch (JSONException e) {
      return;
    }

  }

  @ReactMethod
  public void logEventWithTimestamp(String identifier, double timestamp, ReadableMap properties) {
    try {
      JSONObject jProperties = convertReadableToJsonObject(properties);
      Amplitude.getInstance().logEvent(identifier, jProperties, new JSONObject(), (long) timestamp, false);
    } catch (JSONException e) {
      return;
    }
  }

  @ReactMethod
  public void logRevenue(String productIdentifier, int quantity, double amount) {
    Amplitude.getInstance().logRevenue(productIdentifier, quantity, amount);
  }

  public static JSONObject convertReadableToJsonObject(ReadableMap map) throws JSONException{
    JSONObject jsonObj = new JSONObject();
    ReadableMapKeySetIterator it = map.keySetIterator();

    while (it.hasNextKey()) {
      String key = it.nextKey();
      ReadableType type = map.getType(key);
      switch (type) {
        case Map:
            jsonObj.put(key, convertReadableToJsonObject(map.getMap(key)));
            break;
        case String:
            jsonObj.put(key, map.getString(key));
            break;
        case Number:
            jsonObj.put(key, map.getDouble(key));
            break;
        case Boolean:
            jsonObj.put(key, map.getBoolean(key));
            break;
        case Array:
            jsonObj.put(key, map.getArray(key));
            break;
        case Null:
            jsonObj.put(key, null);
            break;
      }
    }
    return jsonObj;
 }

  @ReactMethod
  public void addToUserProperty(String property, int value) {
    Identify identify = new Identify().add(property, value);
    Amplitude.getInstance().identify(identify);
  }


}
