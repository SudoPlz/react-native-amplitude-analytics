/**
 * Stub of AmplitudeSDK for React Native
 *
 * @providesModule AmplitudeSDK
 * @flow
 */
'use strict';

// Libraries
import {NativeModules} from 'react-native';

// Native Modules
const { RNAmplitudeSDK } = NativeModules;


class Amplitude {

  /**
   * Creates a new Amplitude client
   */
  constructor(apiKey, trackSessionEvents) {
    if (apiKey && typeof apiKey === 'string') {
      if (RNAmplitudeSDK) {
        RNAmplitudeSDK.initialize(apiKey, trackSessionEvents === true);
      } else {
        throw new Error('Bugsnag: No native client found. Is BugsnagReactNative installed in your native code project?');
      }
    } else {
      throw new Error('Bugsnag: A client must be constructed with an API key or Configuration');
    }
  }


  // --------------------------------------------------
  // Identify
  // --------------------------------------------------
  setUserId(userId) {
    return RNAmplitudeSDK.setUserId(userId.toString());
  }

  setUserProperties(properties) {
    return RNAmplitudeSDK.setUserProperties(properties);
  }

  clearUserProperties() {
    return RNAmplitudeSDK.clearUserProperties();
  }

  // --------------------------------------------------
  // Track
  // --------------------------------------------------

  logEvent(name, properties) {
    if (properties) {
      return RNAmplitudeSDK.logEventWithProps(name, properties);
    } else {
      return RNAmplitudeSDK.logEvent(name);
    }
  }

  // --------------------------------------------------
  // Revenue
  // --------------------------------------------------
  logRevenue(productIdentifier, quantity, amount) {
     return RNAmplitudeSDK.logRevenue(productIdentifier, quantity, amount); 
  }
}


export default Amplitude;
