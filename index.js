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
  constructor(apiKey, trackSessionEvents, eventPrefix) {
    if (apiKey && typeof apiKey === 'string') {
      if (RNAmplitudeSDK) {
        if (eventPrefix) {
          this.evPrefix = eventPrefix;
        }
        RNAmplitudeSDK.initialize(apiKey, trackSessionEvents === true);
      } else {
        throw new Error('RNAmplitudeSDK: No native client found. Is RNAmplitudeSDK installed in your native code project?');
      }
    } else {
      throw new Error('RNAmplitudeSDK: A client must be constructed with an API key. i.e new Amplitude(key);');
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

  setLogEventPrefix(prefix) {
    this.evPrefix = prefix;
  }

  // --------------------------------------------------
  // Track
  // --------------------------------------------------

  logEvent(name, properties) {
    var eventName = this.evPrefix ? this.evPrefix + name : name;
    if (properties) {
      return RNAmplitudeSDK.logEventWithProps(eventName, properties);
    } else {
      return RNAmplitudeSDK.logEvent(eventName);
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
