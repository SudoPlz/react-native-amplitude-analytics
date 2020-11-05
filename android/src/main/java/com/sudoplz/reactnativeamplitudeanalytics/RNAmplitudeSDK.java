package com.sudoplz.reactnativeamplitudeanalytics;

import com.amplitude.api.Amplitude;
import com.amplitude.api.Identify;
import com.amplitude.api.Revenue;

import android.app.Activity;
import android.app.Application;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
  public void setEventUploadThreshold(int threshold) {
    Amplitude.getInstance().setEventUploadThreshold(threshold);
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
  public void getDeviceId(Promise promise) {
    String deviceId = Amplitude.getInstance().getDeviceId();
    promise.resolve(deviceId);
  }

  @ReactMethod
  public void getSessionId(Promise promise) {
    Long sessionId = Amplitude.getInstance().getSessionId();
    promise.resolve(sessionId.doubleValue());
  }

  @ReactMethod
  public void setDeviceId(String id) {
    Amplitude.getInstance().setDeviceId(id);
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

  @ReactMethod
  public void logRevenueV2(ReadableMap properties) {
    Revenue revenue = populateRevenue(properties);
    Amplitude.getInstance().logRevenueV2(revenue);
  }

  private Revenue populateRevenue(ReadableMap properties) {
    Revenue revenue = new Revenue();
    try {
      if (properties.hasKey("productId")) {
        revenue.setProductId(properties.getString("productId"));
      }
      if (properties.hasKey("quantity")) {
        revenue.setQuantity(properties.getInt("quantity"));
      } else {
        revenue.setQuantity(1);
      }
      if (properties.hasKey("price")) {
        revenue.setPrice(properties.getDouble("price"));
      }
      if (properties.hasKey("revenueType")) {
        revenue.setRevenueType(properties.getString("revenueType"));
      }
      if (properties.hasKey("receipt") && properties.hasKey("receiptSignature")) {
        revenue.setReceipt(properties.getString("receipt"), properties.getString("receiptSignature"));
      }
      if (properties.hasKey("eventProperties")) {
        JSONObject eventProperties = convertReadableToJsonObject(properties.getMap("eventProperties"));
        revenue.setEventProperties(eventProperties);
      }
    } catch (JSONException e) {
      // do nothing
    }
    return revenue;
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
          try {
            jsonObj.put(key, convertReadableToJsonArray(map.getArray(key)));
            break;
          } catch (JSONException e) {
            break;
          }
        case Null:
          jsonObj.put(key, null);
          break;
      }
    }
    return jsonObj;
  }

  public static JSONArray convertReadableToJsonArray(ReadableArray readableArray) throws JSONException {
    JSONArray array = new JSONArray();
    for (int i = 0; i < readableArray.size(); i++) {
      switch (readableArray.getType(i)) {
        case Null:
          break;
        case Boolean:
          array.put(readableArray.getBoolean(i));
          break;
        case Number:
          array.put(readableArray.getDouble(i));
          break;
        case String:
          array.put(readableArray.getString(i));
          break;
        case Map:
          try {
            array.put(convertReadableToJsonObject(readableArray.getMap(i)));
            break;
          } catch (JSONException e) {
            break;
          }
        case Array:
          array.put(convertReadableToJsonArray(readableArray.getArray(i)));
          break;
      }
    }
    return array;
  }

  @ReactMethod
  public void addToUserProperty(String property, int value) {
    Identify identify = new Identify().add(property, value);
    Amplitude.getInstance().identify(identify);
  }

  @ReactMethod
  public void setUserPropertyOnce(String property, String value) {
    Identify identify = new Identify().setOnce(property, value);
    Amplitude.getInstance().identify(identify);
  }

}
