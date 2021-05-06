
# react-native-amplitude-analytics

<img src="Logotype primary.png" width="65%" height="65%" />

[![npm version](https://badge.fury.io/js/react-native-amplitude-analytics.svg)](https://badge.fury.io/js/react-native-amplitude-analytics)

## Dependencies

`react-native` version `>0.40`

## Installation

`npm i react-native-amplitude-analytics --save`

For react-native > 0.60 the installation should be automated, for older versions expand and read the Manual installation section below.

<details>
<summary>Manual installation steps</summary>
	
#### Manual iOS installation

1. `react-native link react-native-amplitude-analytics`
After you do that make sure that:
- **RNAmplitudeSDK.xcodeproj** from `node_modules/react-native-amplitude-analytics/ios` is found within your Xcode Project as a subproject (if it's not add it manually with drag and drop).
- **libRNAmplitudeSDK.a** is found within Linked Frameworks and Libraries under General tab (if it's not add it with the plus button) - (you don't need to add libAmplitude-iOS.a as that will be dealt with by Cocoapods in the next step).
2. Either
- add the following line to your "Podfile": `pod 'Amplitude-iOS', '~> 4.3.1'` and run `pod install`

  or

- download the Amplitude-iOS sdk from [here](https://amplitude.zendesk.com/hc/en-us/articles/115002278527#installation) and add it to your project manually. Make sure the `Amplitude-iOS` directory of the SDK is included in the root of your app's ios folder.
3. Run your project (`Cmd+R`)



#### Android Manual installation

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add 

    ```java
    import com.sudoplz.reactnativeamplitudeanalytics.RNAmplitudeSDKPackage;
    ```

    to the imports at the top of the file.
   
  - Add 

    ```java
    new RNAmplitudeSDKPackage(MainApplication.this),
    ``` 

    to the list returned by the `getPackages()` method
  
2. Append the following lines to `android/settings.gradle`:

  	```gradle
  	include ':react-native-amplitude-analytics'
    project(':react-native-amplitude-analytics').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-amplitude-analytics/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:

  	```gradle
	  compile 'com.amplitude:android-sdk:2.19.1' // native sdk of amplitude
    compile project(':react-native-amplitude-analytics') // our react-native module
  	```

4. Add permissions. If you haven't already, add the INTERNET permission to your manifest file:

  	```
  	<uses-permission android:name="android.permission.INTERNET" />
  	```
</details>

## Usage 

  ```javascript

  import RNAmplitude from 'react-native-amplitude-analytics';

  ```


### Example 

```javascript
class testApp extends Component {
  constructor() {
    super();
    const amplitude = new RNAmplitude('Your Amplitude key');

    // set event upload threshold
    amplitude.setEventUploadThreshold(30);
	 
    // log an event
    amplitude.logEvent(eventName);
	 
    // log an event with data
    amplitude.logEvent(eventName, { foo: bar });
	 
    // log an event with a custom timestamp (data is optional)
    // timestamp should be the number of milliseconds since Unix epoch
    amplitude.logEventWithTimestamp(eventName, timestamp, { foo: bar });
	 
    // set the user id
    amplitude.setUserId('1D32FS45');
	 
    // set user props
    amplitude.setUserProperties({ hairColor: 'brown' });

    // sets whether or not to opt a user out logging
    amplitude.setOptOut(true);
	 
    // log revenue
    amplitude.logRevenue(productIdentifier, quantity, amount)

    /**
      *  RevenueProperties {
      *    productId?: string;
      *    quantity?: number; //default = 1
      *    price: number;
      *    revenueType?: string;
      *    receipt?: string;
      *    receiptSignature?: string;
      *    eventProperties?: {[key: string]: any},
      *  }
      */
    amplitude.logRevenueV2(revenueProperties)

    // add to user property
    amplitude.addToUserProperty(property, amount)

    // append to user property
    amplitude.appendToUserProperty(property, 'stringValue')

    // prepend to user property
    amplitude.prependToUserProperty(property, 'stringValue')

    // set user property once
    amplitude.setUserPropertyOnce(property, 'stringValue')

    // get device id as logged in Amplitude
    amplitude.getDeviceId().then(deviceId => ...)

    // get the current session ID as logged in Amplitude
    amplitude.getSessionId().then(sessionId => ...)
  }
  ...
}
```

there's also an example project [here](https://github.com/SudoPlz/react-native-amplitude-analytics/tree/master/Example).
